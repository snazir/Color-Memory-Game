package com.android.cardsgame.bl;

import com.android.cardsgame.R;
import com.android.cardsgame.entities.Card;

import java.util.ArrayList;

/**
 * Created by Salman on 10/26/2015.
 */
public class BoardCardsGenerator {

    public static BoardCardsGenerator instance = new BoardCardsGenerator();

    public static final int[] CARDS = {R.drawable.colour1, R.drawable.colour2, R.drawable.colour3,
            R.drawable.colour4, R.drawable.colour5, R.drawable.colour6,
            R.drawable.colour7, R.drawable.colour8};

    public static BoardCardsGenerator getInstance() {
        return instance;
    }

    public ArrayList<Card> generateBoardCards() {
        ArrayList<Card> mCards = new ArrayList<Card>();
        for(int i = 0; i < CARDS.length; i++) {
            Card mCard = new Card(i, CARDS[i]);
            mCards.add(mCard);

            mCard = new Card(i, CARDS[i]);
            mCards.add(mCard);
        }
        return CardsShuffler.shuffleCards(mCards);
    }

}
