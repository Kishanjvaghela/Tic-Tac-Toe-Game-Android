package com.ticktoctoe;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ticktoctoe.databinding.ActivityGameBinding;

public class GameActivity extends Activity implements View.OnClickListener {

    private static final int VALUE_DEFAULT = -1;
    private static final int VALUE_ROBOT = 0;
    private static final int VALUE_USER = 1;
    private static final int VALUE_NONE = 2;

    private int[][] valueArray = {{VALUE_DEFAULT, VALUE_DEFAULT, VALUE_DEFAULT}
            , {VALUE_DEFAULT, VALUE_DEFAULT, VALUE_DEFAULT},
            {VALUE_DEFAULT, VALUE_DEFAULT, VALUE_DEFAULT}};

    private TextView[][] textViews;

    private ActivityGameBinding mBinding;

    private int turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_game);
        mBinding.resultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initLayout();
        turn = Utils.getRandom(0, 1);
        if (turn == VALUE_ROBOT) {
            robotTurn();
        } else {
            user();
        }
    }

    private void user() {
        int winner = checkWinner();
        if (winner != VALUE_DEFAULT) {
            showResult(winner);
        }
    }

    private void robotTurn() {
        int winner = checkWinner();
        if (winner == VALUE_DEFAULT) {
            if (setRobotValues()) {
                user();
            }
        } else {
            showResult(winner);
        }

    }

    private void showResult(int winner) {
        String result = "";
        switch (winner) {
            case VALUE_ROBOT:
                result = "You lose";
                break;
            case VALUE_USER:
                result = "You win, Congratulations!";
                break;
            case VALUE_NONE:
                result = "Nobody wins";
                break;
        }
        mBinding.resultTextView.setText(result);
        mBinding.resultLayout.setVisibility(View.VISIBLE);
    }


    private boolean setRobotValues() {
        for (int i = 0; i < valueArray.length; i++) {
            for (int j = 0; j < valueArray.length; j++) {
                if (valueArray[i][j] == VALUE_DEFAULT) {
                    valueArray[i][j] = VALUE_ROBOT;
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
        robotTurn();
    }

    private int checkWinner() {
        // check vertical row
        for (int i = 0; i < valueArray.length; i++) {
            if (valueArray[i][0] == valueArray[i][1]) {
                if (valueArray[i][1] == valueArray[i][2]) {
                    return valueArray[i][0];
                }
            }
        }
        // check horizontal row
        for (int i = 0; i < valueArray.length; i++) {
            if (valueArray[0][i] == valueArray[1][i]) {
                if (valueArray[1][i] == valueArray[2][i]) {
                    return valueArray[0][i];
                }
            }
        }
        // check diagonal row
        if (valueArray[0][0] == valueArray[1][1]) {
            if (valueArray[1][1] == valueArray[2][2]) {
                return valueArray[2][2];
            }
        }
        // check diagonal row
        if (valueArray[0][2] == valueArray[1][1]) {
            if (valueArray[1][1] == valueArray[2][0]) {
                return valueArray[0][2];
            }
        }
        // check for game over
        int count = 0;
        for (int i = 0; i < valueArray.length; i++) {
            for (int j = 0; j < valueArray.length; j++) {
                if (valueArray[i][j] != -1) {
                    count++;
                }
            }
        }
        if (count == 9) {
            return VALUE_NONE;
        } else {
            return VALUE_DEFAULT;
        }
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
                valueArray[0][0] = VALUE_USER;
                userTurn(mBinding.text00);
                break;
            case R.id.text01:
                valueArray[0][1] = VALUE_USER;
                userTurn(mBinding.text01);
                break;
            case R.id.text02:
                valueArray[0][2] = VALUE_USER;
                userTurn(mBinding.text02);
                break;
            case R.id.text10:
                valueArray[1][0] = VALUE_USER;
                userTurn(mBinding.text10);
                break;
            case R.id.text11:
                valueArray[1][1] = VALUE_USER;
                userTurn(mBinding.text11);
                break;
            case R.id.text12:
                valueArray[1][2] = VALUE_USER;
                userTurn(mBinding.text12);
                break;
            case R.id.text20:
                valueArray[2][0] = VALUE_USER;
                userTurn(mBinding.text20);
                break;
            case R.id.text21:
                valueArray[2][1] = VALUE_USER;
                userTurn(mBinding.text21);
                break;
            case R.id.text22:
                valueArray[2][2] = VALUE_USER;
                userTurn(mBinding.text22);
                break;
        }
    }
}
