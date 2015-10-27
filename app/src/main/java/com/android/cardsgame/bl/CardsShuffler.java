package com.android.cardsgame.bl;

import com.android.cardsgame.entities.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Salman on 10/26/2015.
 */
public class CardsShuffler {

    public static ArrayList<Card> shuffleCards(ArrayList<Card> objects) {
        long seed = System.nanoTime();
        Collections.shuffle(objects, new Random(seed));
        return objects;
    }
}
