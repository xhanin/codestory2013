codestory2013
=============

Code utilisé pour réaliser le challenge codestory 2013.
Pour plus d'infos sur le concours :
- http://code-story.net/2013/01/04/concours-2013.html
- http://code-story.net/2013/01/22/scalaskel.html
- http://code-story.net/2013/02/02/jajascript.html
- http://www.ybonnel.fr/2013/02/ma-participation-codestory-en-java-intro.html

## Architecture générale

Il a été réalisé en Java, en utilisant [Webbit](https://github.com/webbit/webbit) comme serveur http, et hébergé le temps du concours sur une [instance xs OVH public cloud](http://www.ovh.com/fr/cloud/instances/index.xml#instances) à 0.01€ de l'heure.

Le point d'entrée est la classe [CodeStory2013](https://github.com/xhanin/codestory2013/blob/master/src/main/java/cs13/CodeStory2013.java) qui configure le serveur avec quelques handlers webbit, plus ou moins un par problème posé :
```java
    public CodeStory2013(int port) {
        webServer = new NettyWebServer(port) {
            @Override
            protected void setupDefaultHandlers() {
                add(new ServerHeaderHandler("GuessIt!"));
                add(new DateHeaderHandler());
                add(new LoggingHandler());
            }
        }
        .maxContentLength(20 * MEGA)
        .add("/jajascript/optimize", new JJRentalOptimizerHandler())
        .add("/scalaskel/change/.*", new ScalaskelDecomposerHandler())
        .add("/enonce/.*", new MarkdownQuestionHandler())
        .add("/", new BasicQuestionHandler())
        .add(new AnyPostHandler())
        .add(new NotFoundHttpHandler());
    }
```

Pour les tests, j'ai créé une [JUnit Rule pour démarrer le serveur](https://github.com/xhanin/codestory2013/blob/master/src/test/java/cs13/util/ServerRule.java) (qui met 100ms à démarrer au premier run, puis seulement de 2ms, merci webbit), en démarrant le serveur sur un port libre (comme ça je peux lancer les tests même si j'ai une instance en debug). La rule permet ausssi d'obtenir un client HTTP pour executer les requetes de test. 
Pour ce client, après un essai avec resty + une bibliothèque URI builder, [je suis passé à RESTeasy](https://github.com/xhanin/codestory2013/commit/171cb5135e0fd49e597ecdb614274ec05cf219e3#L4L53).

## Questions basiques

Après quelques évolutions du fait du nombre de questions, j'ai fini par [utiliser les Theories JUnit pour faire les tests sur ces questions](https://github.com/xhanin/codestory2013/blob/master/src/test/java/cs13/handlers/BasicQuestionHandlerTest.java) :
```java
    @DataPoints
    public static Question[] data() {
        return new Question[] {
            q("Quelle est ton adresse email", "xavier.hanin@gmail.com"),
            q("Es tu abonne a la mailing list(OUI/NON)", "OUI"),
            q("Es tu heureux de participer(OUI/NON)", "OUI"),
            q("Es tu pret a recevoir une enonce au format markdown par http post(OUI/NON)", "OUI"),
            q("As tu bien recu le premier enonce(OUI/NON)", "OUI"),
            q("Est ce que tu reponds toujours oui(OUI/NON)", "NON"),
            q("1+1", "2"),
            q("2+2", "4"),
            q("(1+2)/2", "1,5"),
            q("((1+2)+3+4+(5+6+7)+(8+9+10)*3)/2*5", "272,5"),
            q("1,5*4", "6"),
            q("((1,1+2)+3,14+4+(5+6+7)+(8+9+10)*4267387833344334647677634)/2*553344300034334349999000",
                    "31878018903828899277492024491376690701584023926880"),
            q("As tu passe une bonne nuit malgre les bugs de l etape precedente(PAS_TOP/BOF/QUELS_BUGS)", "PAS_TOP"),
            q("As tu bien recu le second enonce(OUI/NON)", "OUI"),
            q("As tu copie le code de ndeloof(OUI/NON/JE_SUIS_NICOLAS)", "NON"),
            q("Souhaites-tu-participer-a-la-suite-de-Code-Story(OUI/NON)", "OUI"),
        };
    }

    @Theory
    public void should_server_respond_to_basic_question(Question q) throws Exception {
        ClientResponse<String> response = server.request("/").queryParameter("q", q.question).get(String.class);
        assertThat(
                "expected " + q.answer + " when asking " + q.question,
                response.getEntity(),
                is(q.answer));
    }
```

Pour l'[implémentation](https://github.com/xhanin/codestory2013/blob/master/src/main/java/cs13/handlers/BasicQuestionHandler.java) j'ai simplement utilisé une Map :
```java
private static final Map<String, String> BASIC_QUESTIONS = ImmutableMap.<String, String>builder()
            .put("Quelle est ton adresse email", "xavier.hanin@gmail.com")
            .put("Es tu abonne a la mailing list(OUI/NON)", "OUI")
            .put("Es tu heureux de participer(OUI/NON)", "OUI")
            .put("Est ce que tu reponds toujours oui(OUI/NON)", "NON")
            .put("Es tu pret a recevoir une enonce au format markdown par http post(OUI/NON)", "OUI")
            .put("As tu bien recu le premier enonce(OUI/NON)", "OUI")
            .put("As tu passe une bonne nuit malgre les bugs de l etape precedente(PAS_TOP/BOF/QUELS_BUGS)", "PAS_TOP")
            .put("As tu bien recu le second enonce(OUI/NON)", "OUI")
            .put("As tu copie le code de ndeloof(OUI/NON/JE_SUIS_NICOLAS)", "NON")
            .put("Souhaites-tu-participer-a-la-suite-de-Code-Story(OUI/NON)", "OUI")
            .build();
```

## Réception des énoncés

J'ai utilisé logback pour les logs, avec un [appender smtp](https://github.com/xhanin/codestory2013/blob/master/src/main/resources/logback.xml) pour être au courant des nouveaux énoncés (pas de stockage en base des énoncés, les logs me suffisaient) :
```xml
    <appender name="EMAIL" class="ch.qos.logback.classic.net.SMTPAppender">
        <evaluator class="ch.qos.logback.classic.boolex.OnMarkerEvaluator">
            <marker>NOTIFY-PROD</marker>
        </evaluator>
        <smtpHost>localhost</smtpHost>
        <from>noreply@codestory.xhan.in</from>
        <to>xavier.hanin@gmail.com</to>
        <layout class="ch.qos.logback.classic.html.HTMLLayout"/>
    </appender>
```

J'avais quand même écrit un [test pour vérifier que l'énoncé partait bien dans les logs](https://github.com/xhanin/codestory2013/blob/master/src/test/java/cs13/handlers/MarkdownQuestionHandlerTest.java) :
```java
    @Test
    public void should_server_store_question_and_return_201_when_markdown_question_asked() throws Exception {
        LogbackCapturingAppender capturing = LogbackCapturingAppender.Factory
                .weaveInto(LoggerFactory.getLogger("QUESTION"));
        ClientResponse response = server.request("/enonce/1")
                .body("text/x-markdown", A_QUESTION_EXAMPLE.getBytes("UTF-8")).post();

        assertThat(response.getResponseStatus().getStatusCode(), is(201));
        assertThat(
                capturing.getCapturedLogMessage(),
                containsString(A_QUESTION_EXAMPLE));
    }
```

## Scalaskel

Pas spécialement fier de mon code là dessus, j'ai fait au plus vite, avec [quelques tests au niveau API](https://github.com/xhanin/codestory2013/blob/master/src/test/java/cs13/scalaskel/ScalaskelDecomposerTest.java) :
```java
    @Test
    public void should_7_return_7_foo_and_1_bar() {
        Collection<ScalaskelDecomposition> decomposition = decomposer.decompose(7);

        assertThat(decomposition, is(notNullValue()));
        assertThat(decomposition.size(), is(2));
        assertThat(decomposition, hasItem(new ScalaskelDecomposition().setFoo(7)));
        assertThat(decomposition, hasItem(new ScalaskelDecomposition().setBar(1)));
    }

    @Test
    public void should_89_return_many_results() {
        Collection<ScalaskelDecomposition> decomposition = decomposer.decompose(89);

        assertThat(decomposition, is(notNullValue()));
        assertThat(decomposition, hasItem(new ScalaskelDecomposition().setQix(1).setFoo(78)));
        assertThat(decomposition, hasItem(new ScalaskelDecomposition().setFoo(89)));
        assertThat(decomposition.size(), is(132));
    }

    @Test
    public void should_never_find_bad_results() {
        for (int i = 0; i <= 100; i++) {

            Collection<ScalaskelDecomposition> decomposition = decomposer.decompose(i);
            assertThat(decomposition, is(notNullValue()));

            if (i > 0) {
                assertThat(decomposition.isEmpty(), is(false));
            }

            for (ScalaskelDecomposition d : decomposition) {
                assertThat(d.total(), is(i));
            }
        }
    }
```

Quelques [tests aussi au niveau HTTP](https://github.com/xhanin/codestory2013/blob/master/src/test/java/cs13/handlers/ScalaskelDecomposerHandlerTest.java) :
```java
@Test
    public void should_decompose_89() throws Exception {
        JsonNode json = doGetChangeAndCheckResultIsArray(89);
        assertThat(json.size(), is(132));
    }

    private JsonNode doGetChangeAndCheckResultIsArray(int groDecimaux) throws Exception {
        String s = server.request("/scalaskel/change/{groDecimaux}")
                .pathParameter("groDecimaux", groDecimaux)
                .get(String.class)
                .getEntity();
        JsonNode json = new ObjectMapper().readTree(s);

        assertThat(json, is(notNullValue()));
        assertThat(json.isArray(), is(true));
        return json;
    }
```

Au niveau de l'[implem](https://github.com/xhanin/codestory2013/blob/master/src/main/java/cs13/scalaskel/ScalaskelDecomposer.java), c'est recursif et pas bien joli, je n'ai pas pris le temps de refactorer :
```java
   /**
     * Decompose groDecimaux value in coins, using only available coins.
     *
     * @param groDecimaux the value to decompose
     * @param coins the coins to use
     * @return all the possible decompositions.
     */
    private Set<ScalaskelDecomposition> decompose(int groDecimaux, EnumSet<ScalaskelDecomposition.Coin> coins) {
        if (groDecimaux == 0) {
            return Collections.emptySet();
        }

        Set<ScalaskelDecomposition> decompositions = new HashSet<>();

        for (ScalaskelDecomposition.Coin coin : coins) {
            if (coin.getValue() == 1) {
                // this is a special case, all numbers are dividible by 1, we don't iterate over the options
                // we simply add the corresponding coins
                decompositions.add(new ScalaskelDecomposition().increment(coin, groDecimaux));
            } else {
                // we check the maximum number of these coins we can put by using a integer division
                int d = groDecimaux / coin.getValue();

                // then for each possible number of coins, we recursively decompose the remaining
                // and add to each result the number of this coins of the iteration
                for (int i = 1; i <= d; i++) {
                    if (i*coin.getValue() == groDecimaux) {
                        // in this case the recursive decomposition has no sense, it will bring nothing
                        // we just add a new decomposition made up of the number of coins we have
                        decompositions.add(new ScalaskelDecomposition().increment(coin, i));
                    } else {
                        EnumSet<ScalaskelDecomposition.Coin> otherCoins = coins.clone();
                        coins.remove(coin);
                        Set<ScalaskelDecomposition> s = decompose(groDecimaux - i * coin.getValue(), otherCoins);
                        for (ScalaskelDecomposition decomposition : s) {
                            decompositions.add(decomposition.increment(coin, i));
                        }
                    }
                }
            }
        }

        return decompositions;
    }
```
C'est quand j'ai écrit ça que je me suis senti rouillé au niveau algorithmie... :)

## Calculatrice

Après avoir répondu aux premières questions en dur, j'ai essayé la librairie [exp4j](https://github.com/fasseg/exp4j) qui m'a permis de passer les premières questions.
Puis il y a eu le problème de la question qui s'attendait à un résultat avec des calculs entiers (j'étais parmi les premiers à répondre, j'ai appris plus tard que c'était un bug), j'ai donc [patché la librairie pour qu'elle fasse du calcul entier](https://github.com/xhanin/exp4j/commit/583247f317ad7464194280126c2af38f4dc35ddf).
Puis cette solution a atteint ces limites pour les calculs sur les grands nombres, je suis alors [passé à une expression groovy](https://github.com/xhanin/codestory2013/commit/ce20fa990e277a027cec90eae781251547ae7ef0#L2L0) et son très pratique Eval.me qui fait ses calculs avec des BigDecimal :
```java
            // it seems it was not url encoded, so + get converted to spaces.
            // And we want dot as decimal separator...
            q = q.replace(' ', '+').replace(',', '.');
            try {
                r = String.valueOf(Eval.me(q)).replace('.', ',');
                if (Pattern.compile("\\d+,0+").matcher(r).matches()) {
                    r = r.substring(0, r.indexOf(','));
                }
            } catch (Exception e) {
                respondError(response, 412, ErrorMessages.BAD_QUESTION);
                return;
            }
```
C'est tout simple, mais la série de commits du 11 et 12 janvier montrent bien l'évolution de mes essais :
https://github.com/xhanin/codestory2013/commits/master/src/main/java/cs13/handlers/BasicQuestionHandler.java

## Jajascript

Là dessus j'ai été dans un premier temps au plus vite, avec une [solution relativement simple](https://github.com/xhanin/codestory2013/blob/1cde90ac8737598cd5e8f07bfa5a39a5a33ed8b0/src/main/java/cs13/jjrental/JJRentalOptimizer.java), mais très loin d'être optimale (toujours rouillé au niveau algo, et je ne savais pas que les perfs seraient mesurées) :
```java
public JJOptimization optimize(Collection<TripOrder> tripOrders) {
        if (tripOrders.size() == 1) {
            return new JJOptimization(Lists.newArrayList(tripOrders));
        }

        List<TripOrder> orders = Lists.newArrayList(tripOrders);
        Collections.sort(orders);

        JJOptimization optimization = new JJOptimization(Collections.<TripOrder>emptyList());
        Set<TripOrder> testedOrders = new HashSet<>();
        for (int i = 0; i < orders.size(); i++) {
            final TripOrder tripOrder = orders.get(i);

            if (testedOrders.contains(tripOrder)) {
                // it has already been tested after a previous order, it can't provide a better cost
                continue;
            }

            Collection<TripOrder> otherOrders = Collections2.filter(orders, new Predicate<TripOrder>() {
                @Override
                public boolean apply(TripOrder input) {
                    return input != null && tripOrder.isCompatibleWith(input);
                }
            });

            JJOptimization newOptimization = new JJOptimization(Lists.asList(
                    tripOrder, optimize(otherOrders).getOrdersPath().toArray(new TripOrder[0])));

            if (newOptimization.compareTo(optimization) > 0) {
                optimization = newOptimization;
            }

            testedOrders.addAll(otherOrders);
        }

        return optimization;
    }
```

Je passe les premières réponses, puis je vois que je suis bloqué à cause de mes pauvres perfs. Je [travaille pour améliorer ça](https://github.com/xhanin/codestory2013/commit/df5b4da582d92d51e5851977eceffc1b560243e8#src/main/java/cs13/jjrental/JJRentalOptimizer.java), j'ai l'illumination en voyant qu'on peut considérer cela comme un graphe acyclique orienté (DAG) sur lequel l'algo du plus long chemin est connu :
```java
public class JJRentalOptimizer {
    public JJOptimization optimize(Collection<TripOrder> tripOrders) {
        if (tripOrders.size() <= 1) {
            return new JJOptimization(Lists.newArrayList(tripOrders));
        }

        List<TripOrder> orders = Lists.newArrayList(tripOrders);
        Collections.sort(orders);
        List<TripOrderEdge> edges = Lists.newArrayListWithCapacity(orders.size());
        for (int i = 0; i < orders.size(); i++) {
            edges.add(new TripOrderEdge(orders.get(i), getPredecessors(orders.get(i), edges)));
        }

        TripOrderEdge best = doOptimize(edges);

        return new JJOptimization(buildPath(best));
    }

    private List<TripOrder> buildPath(TripOrderEdge edge) {
        List<TripOrder> path = Lists.newLinkedList();

        for (TripOrderEdge current = edge; current != null; current = current.getPredecessor()) {
            path.add(0, current.getOrder());
        }

        return path;
    }

    private List<TripOrderEdge> getPredecessors(TripOrder tripOrder, List<TripOrderEdge> edges) {
        List<TripOrderEdge> predecessors = Lists.newArrayList();
        for (int i = edges.size() - 1; i >= 0; i--) {
            TripOrderEdge edge = edges.get(i);
            if (edge.getOrder().isCompatibleWith(tripOrder)) {
                predecessors.add(edge);
            }
        }
        return predecessors;
    }

    private TripOrderEdge doOptimize(List<TripOrderEdge> orders) {
        TripOrderEdge best = null;
        for (int i = orders.size() - 1; i >= 0; i--) {
            TripOrderEdge order = orders.get(i);
            doOptimize(order);

            if (best == null || order.getGain() > best.getGain()) {
                best = order;
            }
        }
        return best;
    }

    private void doOptimize(TripOrderEdge order) {
        if (order.getGain() > 0) {
            // edge already optimized
            return;
        }

        if (order.getPredecessors().isEmpty()) {
            order.setGain(order.getOrder().getCost());
            return;
        }

        for (TripOrderEdge predecessor : order.getPredecessors()) {
            doOptimize(predecessor);

            long potentialGain = predecessor.getGain() + order.getOrder().getCost();
            if (potentialGain > order.getGain()) {
                order.setGain(potentialGain);
                order.setPredecessor(predecessor);
            }
        }
    }
}
```

C'est pas plus simple qu'avant (plutôt le contraire), mais je passe de quelques dizaines de secondes à quelques dizaine de millis pour 100 vols.

Mais mon petit serveur OVH peine quand même quand on monte à 50000 vols, et met une dizaine de secondes avec cette version malgré quelques optimisations mineures.

Je finis par [revoir mon algo en ordonnant les vols par heure de fin](https://github.com/xhanin/codestory2013/commit/f3d6b9dc3d9e1e172791beb0ff47b1ab4767933d#src/main/java/cs13/jjrental/JJRentalOptimizer.java), après le tri j'ai un algo linéaire plus simple que le précédent et qui passe les 50000 vols en une dizaine de ms (hors processing json) :
```java
public class JJRentalOptimizer {
    public JJOptimization optimize(Collection<TripOrder> tripOrders) {
        Preconditions.checkNotNull(tripOrders);
        if (tripOrders.size() <= 1) {
            return new JJOptimization(
                    tripOrders.isEmpty() ? 0 : Iterables.getFirst(tripOrders, null).getCost(),
                    Lists.newArrayList(tripOrders));
        }

        List<TripOrder> sortedOrders = Lists.newArrayList(tripOrders);
        Collections.sort(sortedOrders);

        TripOrderEdge best = doOptimize(sortedOrders);

        return new JJOptimization(best.getGain(), best.buildPath());
    }

    private TripOrderEdge doOptimize(List<TripOrder> edges) {
        TripOrderEdge best = null;
        TripOrderEdge[] bestsByEndHour = new TripOrderEdge[edges.get(edges.size() - 1).getEndHour() + 1];
        for (TripOrder order : edges) {
            long gain = -1;
            TripOrderEdge predecessor = null;
            for (int i = order.getStartHour(); i>=0 && gain == -1; i--) {
                if (bestsByEndHour[i] != null) {
                    predecessor = bestsByEndHour[i];
                    gain = order.getCost() + predecessor.getGain();
                }
            }

            if (gain == -1) {
                gain = order.getCost();
            }

            if (best == null || gain > best.getGain()) {
                bestsByEndHour[order.getEndHour()] = best = new TripOrderEdge(order, Optional.fromNullable(predecessor), gain);
            }
        }
        return best;
    }
}
```

## Conclusion

Bref, je me suis bien amusé avec cette épreuve qui m'a permis de marquer des points sur ma résolution 2013 "back to code".

Pour la prochaine étape je cherche un binome Java (et JS si il faut faire du front), et puisqu'on partage le même ordi je suis plutôt MacOS / Intellij / QWERTY, je fais moins de code depuis que je m'occupe de l'agence de Bordeaux de 4SH mais j'ai quelques restes avec mes 15 ans de Java.
