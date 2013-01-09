package cs13;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *  L’échoppe de monade sur Scalaskel.
 *  ==============
 *  Sur la planète Scalaskel, une planète en marge de la galaxie, aux confins de l'univers,
 *  la monnaie se compte en cents, comme chez nous. 100 cents font un groDessimal.
 *  Le groDessimal est la monnaie standard utilisable partout sur toutes les planètes de
 *  l’univers connu. C'est un peu compliqué à manipuler, mais si on ne s'en sert pas y'a
 *  toujours des erreurs d'arrondis incroyables quand on les soustrais ou on les divise,
 *  c’est idiot, mais c’est comme ça. Sur Scalaskel, on utilise rarement des groDessimaux,
 *  on utilise des pièces plus petites :
 *  Le **Foo** vaut **1 cent**,
 *  le **Bar** vaut **7 cents**,
 *  le **Qix** vaut **11 cents**
 *  et le **Baz** vaut **21 cents**.
 *
 *  Vous tenez une échoppe de monade et autres variables méta-syntaxique sur Scalaskel.
 *  Pour faire face à l’afflux de touristes étrangers avec les poches remplies de groDessimaux
 *  vous avez besoin d’écrire un programme qui pour toute somme de 1 à 100 cents, vous donnera
 *  toutes les décompositions possibles en pièces de **Foo**, **Bar**, **Qix** ou **Baz**.
 *
 *  Par exemple, 1 cent ne peut se décomposer qu’en une seule pièce Foo.
 *  Par contre 7 cents peuvent se décomposer soit en 7 pièces Foo, soit en 1 pièce Bar.
 *
 */
public class ScalaskelDecomposerTest {

    private ScalaskelDecomposer decomposer = new ScalaskelDecomposer();

    @Test
    public void should_7_return_7_foo_and_1_bar() {
        Collection<ScalaskelDecomposition> decomposition = decomposer.decompose(7);

        assertThat(decomposition, is(notNullValue()));
        assertThat(decomposition.size(), is(2));
        assertThat(decomposition, hasItem(new ScalaskelDecomposition().setFoo(7)));
        assertThat(decomposition, hasItem(new ScalaskelDecomposition().setBar(1)));
    }

    @Test
    public void should_15_return_4_results() {
        Collection<ScalaskelDecomposition> decomposition = decomposer.decompose(15);

        assertThat(decomposition, is(notNullValue()));
        assertThat(decomposition.size(), is(4));
        assertThat(decomposition, hasItem(new ScalaskelDecomposition().setQix(1).setFoo(4)));
        assertThat(decomposition, hasItem(new ScalaskelDecomposition().setBar(1).setFoo(8)));
        assertThat(decomposition, hasItem(new ScalaskelDecomposition().setBar(2).setFoo(1)));
        assertThat(decomposition, hasItem(new ScalaskelDecomposition().setFoo(15)));
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
}
