package com.ticktoctoe;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ticktoctoe.databinding.ActivityGameBinding;

public class GameActivity extends Activity implements View.OnClickListener {

    private int[][] valueArray = {{2, 2, 2}, {2, 2, 2}, {2, 2, 2}};

    private TextView[][] textViews;

    private ActivityGameBinding mBinding;

    private int turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_game);
        initLayout();
        turn = Utils.getRandom(0, 1);
        if (turn == 0) {
            robot();
        } else {
            user();
        }
    }

    private void user() {

    }

    private void robot() {

    }

    private void initLayout() {
        textViews = new TextView[3][3];
        textViews[0][0] = mBinding.text00;
        textViews[0][1] = mBinding.text01;
        textViews[0][2] = mBinding.text02;

        textViews[1][0] = mBinding.text10;
        textViews[1][1] = mBinding.text11;
        textViews[1][2] = mBinding.text12;

        textViews[2][0] = mBinding.text20;
        textViews[2][1] = mBinding.text21;
        textViews[2][2] = mBinding.text22;

        int size = textViews.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                textViews[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text00:
                break;
            case R.id.text01:
                break;
            case R.id.text02:
                break;
            case R.id.text10:
                break;
            case R.id.text11:
                break;
            case R.id.text12:
                break;
            case R.id.text20:
                break;
            case R.id.text21:
                break;
            case R.id.text22:
                break;
        }
    }
}
