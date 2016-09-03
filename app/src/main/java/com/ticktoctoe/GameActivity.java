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
        if (setRobotValues()) {
            user();
        }
    }

    private boolean setRobotValues() {
        for (int i = 0; i < valueArray.length; i++) {
            for (int j = 0; j < valueArray.length; j++) {
                if (valueArray[i][j] == 2) {
                    valueArray[i][j] = 0;
                    textViews[i][j].setText("0");
                    textViews[i][j].setEnabled(false);
                    return true;
                }
            }
        }
        return false;
    }

    private void userTurn(TextView textView) {
        textView.setText("1");
        textView.setEnabled(false);
        robot();
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
                valueArray[0][0] = 1;
                userTurn(mBinding.text00);
                break;
            case R.id.text01:
                valueArray[0][1] = 1;
                userTurn(mBinding.text01);
                break;
            case R.id.text02:
                valueArray[0][2] = 1;
                userTurn(mBinding.text02);
                break;
            case R.id.text10:
                valueArray[1][0] = 1;
                userTurn(mBinding.text10);
                break;
            case R.id.text11:
                valueArray[1][1] = 1;
                userTurn(mBinding.text11);
                break;
            case R.id.text12:
                valueArray[1][2] = 1;
                userTurn(mBinding.text12);
                break;
            case R.id.text20:
                valueArray[2][0] = 1;
                userTurn(mBinding.text20);
                break;
            case R.id.text21:
                valueArray[2][1] = 1;
                userTurn(mBinding.text21);
                break;
            case R.id.text22:
                valueArray[2][2] = 1;
                userTurn(mBinding.text22);
                break;
        }
    }
}
