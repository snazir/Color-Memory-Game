package com.android.cardsgame.entities;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.android.cardsgame.R;

/**
 * Created by Salman on 10/26/2015.
 */
public class CardView extends ImageView {

    public static final long DELAY = 1000;
    Card mCard;

    public CardView(Context context, Card card) {
        super(context);
        this.mCard = card;
        init();
    }

    public void init() {
        this.setImageResource(R.drawable.card_bg);
    }

    public void changeState(Card.CardState state, int resId) {
        /*if(state == this.mState) {
            return;
        }
        int newImgResId = 0;
        if(state == Card.CardState.DISABLED) {
            newImgResId = 0;
        } else if(state == Card.CardState.FACE_UP) {
            newImgResId = resId;
        } else if(state == Card.CardState.FACE_DOWN) {
            newImgResId = R.drawable.card_bg;
        }
        final int id = newImgResId;
        Animation fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        Animation.AnimationListener mListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                setImageResource(id);
                if(id != 0) {
                    Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                    startAnimation(fadeIn);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        startAnimation(fadeOut);*/
    }

    public void setState(Card.CardState state) {
        if(state == Card.CardState.FACE_DOWN) {
            setImageResource(R.drawable.card_bg);
        } else if(state == Card.CardState.FACE_UP) {
            setImageResource(mCard.image_resource_id);
        } else if(state == Card.CardState.DISABLED) {
            setImageResource(0);
            setBackgroundColor(Color.BLACK);
        }
        this.mCard.changeState(state);
    }

    public Card getCard() {
        return mCard;
    }

    public Card.CardState getState() {
        return mCard.getState();
    }

    public void setStateDelayed(final Card.CardState state) {
        this.mCard.changeState(state);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setState(state);
            }
        }, DELAY);
    }
}
