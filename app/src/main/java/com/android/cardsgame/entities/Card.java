package com.android.cardsgame.entities;

/**
 * Created by Salman on 10/26/2015.
 */
public class Card {

    public int id;
    public int image_resource_id;
    CardState mState;

    public Card(int id, int res) {
        this.id = id;
        this.image_resource_id = res;
        mState = CardState.FACE_DOWN;
    }

    public void changeState(CardState state) {
        this.mState = state;
    }

    public CardState getState() {
        return mState;
    }

    public boolean isIdenticalCard(Card mCard) {
        if(this.id == mCard.id) {
            return true;
        }
        return false;
    }

    public static enum CardState {
        DISABLED, FACE_UP, FACE_DOWN;
    }
}
