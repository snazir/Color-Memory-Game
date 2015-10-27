package com.android.cardsgame;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.cardsgame.database.DBProcessor;
import com.android.cardsgame.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salman on 10/27/2015.
 */
public class HighScoresActivity extends Activity {

    ListView mLIstView;
    RanksAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        mLIstView = (ListView) findViewById(R.id.listView);
        mAdapter = new RanksAdapter(this, DBProcessor.getInstance(this.getApplicationContext()).getUsers());
        mLIstView.setAdapter(mAdapter);

    }

    public class RanksAdapter extends ArrayAdapter<User> {

        public RanksAdapter(Context context, ArrayList<User> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if(convertView == null) {
                view = getLayoutInflater().inflate(R.layout.high_scores_item, null);
            } else {
                view = convertView;
            }
            User user = getItem(position);
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView score = (TextView) view.findViewById(R.id.score);
            TextView rank = (TextView) view.findViewById(R.id.rank);

            name.setText(user.name);
            score.setText(user.score + "");
            rank.setText((position + 1) + "");

            return view;
        }
    }

}
