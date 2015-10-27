package com.android.cardsgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cardsgame.bl.BoardCardsGenerator;
import com.android.cardsgame.database.DBProcessor;
import com.android.cardsgame.entities.Card;
import com.android.cardsgame.entities.CardView;

import java.util.ArrayList;

/**
 * Created by Salman on 10/26/2015.
 */

public class GameActivity extends ActionBarActivity {

    public static final long ROUND_START_DELAY = 1000;
    public static final int SUCCESS_BONUS_POINTS = 2;
    public static final int WRONG_BONUS_POINTS = -1;

    public static final int ROWS = 4;
    public static final int COLS = 4;
    private static final int GAME_COMPLETE = 100;
    private static final int VIEW_HIGH_SCORES = 101;

    View btnHighScores;
    TextView mUserScore;

    ArrayList<Card> mCards;

    CardView mLastPickedCardView;
    LinearLayout mCardsContainer;

    int mCurrentScore = 0;

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mUserScore = (TextView) findViewById(R.id.lb_current_score);
        btnHighScores = findViewById(R.id.btn_high_scores);
        mCardsContainer = (LinearLayout) findViewById(R.id.cards_container);

        btnHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(GameActivity.this, HighScoresActivity.class), VIEW_HIGH_SCORES);
            }
        });
        startNewRound();

    }

    private void startNewRound() {
        mCurrentScore = 0;
        updateScoreView();
        mCardsContainer.removeAllViews();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Card> mCards = BoardCardsGenerator.getInstance().generateBoardCards();

                populateCards(mCards);
                GameActivity.this.mCards = mCards;
            }
        }, ROUND_START_DELAY);
    }

    private void populateCards(ArrayList<Card> mCards) {
        for (int i = 0; i < ROWS; i++) {
            LinearLayout rl = new LinearLayout(this);
            rl.setOrientation(LinearLayout.HORIZONTAL);
            rl.setWeightSum(COLS);
            LinearLayout.LayoutParams pParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            pParams.weight = 1;
            rl.setLayoutParams(pParams);

            for (int j = 0; j < COLS; j++) {
                Card card = mCards.get(i * COLS + j);
                CardView view = new CardView(this, card);
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                params.weight = 1;
                view.setLayoutParams(params);
                view.setOnClickListener(mCardClickListner);

                rl.addView(view, params);
            }

            mCardsContainer.addView(rl, pParams);
        }

    }

    View.OnClickListener mCardClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CardView currentView = (CardView) view;

            if (currentView.getState() == Card.CardState.DISABLED || currentView.getState() == Card.CardState.FACE_UP) {
                return;
            }

            if (mLastPickedCardView == null) {
                mLastPickedCardView = currentView;
                mLastPickedCardView.setState(Card.CardState.FACE_UP);
            } else {
                currentView.setState(Card.CardState.FACE_UP);
                if (currentView.getCard().isIdenticalCard(mLastPickedCardView.getCard())) {
                    mCurrentScore = mCurrentScore + 2;
                    currentView.setStateDelayed(Card.CardState.DISABLED);
                    mLastPickedCardView.setStateDelayed(Card.CardState.DISABLED);
                    mLastPickedCardView = null;
//                    Toast.makeText(GameActivity.this, "match", Toast.LENGTH_SHORT).show();
                } else {
                    mCurrentScore--;
                    currentView.setStateDelayed(Card.CardState.FACE_DOWN);
                    mLastPickedCardView.setStateDelayed(Card.CardState.FACE_DOWN);
                    mLastPickedCardView = null;
//                    Toast.makeText(GameActivity.this, "NO match", Toast.LENGTH_SHORT).show();
                }
                updateScoreView();
                if (checkGameStatus() == GameStatus.COMPLETE) {
                    // handle game compelte case here...
                  //  Toast.makeText(GameActivity.this, "Finished", Toast.LENGTH_SHORT).show();
                    showUserNameDialog();
                }
            }

        }
    };

    public void updateScoreView() {
        mUserScore.setText("Score: " + mCurrentScore);
    }

    public GameStatus checkGameStatus() {
        boolean isGameCompleted = true;
        for (Card card : mCards) {
            if (card.getState() != Card.CardState.DISABLED) {
                isGameCompleted = false;
            }
        }
        return isGameCompleted ? GameStatus.COMPLETE : GameStatus.RUNNING;
    }

    public enum GameStatus {
        RUNNING, COMPLETE
    }

    public void showUserNameDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Game Finished");
        alertDialog.setMessage("Enter User Name");
        alertDialog.setCancelable(false);

        final EditText input = new EditText(this);
        input.setSingleLine();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("SAVE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String userName = input.getText().toString();
                        if (userName.length() != 0) {
                            DBProcessor.getInstance(GameActivity.this).createNewUser(userName, mCurrentScore);
                            startActivityForResult(new Intent(GameActivity.this, HighScoresActivity.class), GAME_COMPLETE);
                        } else
                            showUserNameDialog();
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GAME_COMPLETE) {
            startNewRound();
        }
    }
}
