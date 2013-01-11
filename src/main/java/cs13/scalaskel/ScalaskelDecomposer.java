package cs13.scalaskel;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class ScalaskelDecomposer {
    public Set<ScalaskelDecomposition> decompose(int groDecimaux) {
        return decompose(groDecimaux, EnumSet.allOf(ScalaskelDecomposition.Coin.class));
    }

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
}
