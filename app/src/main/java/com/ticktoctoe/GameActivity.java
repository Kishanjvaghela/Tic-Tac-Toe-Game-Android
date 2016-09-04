package com.ticktoctoe;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.ticktoctoe.databinding.ActivityGameBinding;

import java.util.ArrayList;

public class GameActivity extends Activity implements View.OnClickListener {

    private static final int VALUE_DEFAULT = -1;
    private static final int VALUE_ROBOT = 0;
    private static final int VALUE_USER = 1;
    private static final int VALUE_NONE = 2;

    private int[][] boxArray = {{VALUE_DEFAULT, VALUE_DEFAULT, VALUE_DEFAULT}
            , {VALUE_DEFAULT, VALUE_DEFAULT, VALUE_DEFAULT},
            {VALUE_DEFAULT, VALUE_DEFAULT, VALUE_DEFAULT}};

    private ImageView[][] boxViews;

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
            setRobotValues();
            user();
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
        ArrayList<Pair<Integer, Integer>> emptyList = getEmptyMoveList();
        if (emptyList.size() == 0) {
            return false;
        } else {
            int i, j;

            // get best possible move to win robot.
            for (Pair<Integer, Integer> pair : emptyList) {
                i = pair.first;
                j = pair.second;
                if (boxArray[i][j] == VALUE_DEFAULT) {
                    boxArray[i][j] = VALUE_ROBOT;
                    int winner = checkWinner();
                    if (winner == VALUE_ROBOT || winner == VALUE_NONE) {
                        selectRobotBox(boxViews[i][j]);
                        return true;
                    } else {
                        boxArray[i][j] = VALUE_DEFAULT;
                    }
                }
            }
            // get best possible move to loose user.
            for (Pair<Integer, Integer> pair : emptyList) {
                i = pair.first;
                j = pair.second;
                if (boxArray[i][j] == VALUE_DEFAULT) {
                    boxArray[i][j] = VALUE_USER;
                    int winner = checkWinner();
                    if (winner == VALUE_USER) {
                        boxArray[i][j] = VALUE_ROBOT;
                        selectRobotBox(boxViews[i][j]);
                        return true;
                    } else {
                        boxArray[i][j] = VALUE_DEFAULT;
                    }
                }
            }

            // choose random move position
            int randomPos = Utils.getRandom(0, emptyList.size());
            Pair<Integer, Integer> emptyPair = emptyList.get(randomPos);
            boxArray[emptyPair.first][emptyPair.second] = VALUE_ROBOT;
            selectRobotBox(boxViews[emptyPair.first][emptyPair.second]);
            return true;
        }
    }

    private void selectRobotBox(ImageView imageView) {
        imageView.setImageResource(R.drawable.ic_circle);
        imageView.setEnabled(false);
    }

    /**
     * return all empty pair from box
     *
     * @return
     */
    private ArrayList<Pair<Integer, Integer>> getEmptyMoveList() {
        ArrayList<Pair<Integer, Integer>> emptyList = new ArrayList<>();
        for (int i = 0; i < boxArray.length; i++) {
            for (int j = 0; j < boxArray.length; j++) {
                if (boxArray[i][j] == VALUE_DEFAULT) {
                    emptyList.add(new Pair<>(i, j));
                }
            }
        }
        return emptyList;
    }

    private void userTurn(ImageView imageView) {
        imageView.setImageResource(R.drawable.ic_cross);
        imageView.setEnabled(false);
        robotTurn();
    }

    private int checkWinner() {
        // check vertical row
        for (int i = 0; i < boxArray.length; i++) {
            if (boxArray[i][0] == boxArray[i][1]) {
                if (boxArray[i][1] == boxArray[i][2]) {
                    return boxArray[i][0];
                }
            }
        }
        // check horizontal row
        for (int i = 0; i < boxArray.length; i++) {
            if (boxArray[0][i] == boxArray[1][i]) {
                if (boxArray[1][i] == boxArray[2][i]) {
                    return boxArray[0][i];
                }
            }
        }
        // check diagonal row
        if (boxArray[0][0] == boxArray[1][1]) {
            if (boxArray[1][1] == boxArray[2][2]) {
                return boxArray[2][2];
            }
        }
        // check diagonal row
        if (boxArray[0][2] == boxArray[1][1]) {
            if (boxArray[1][1] == boxArray[2][0]) {
                return boxArray[0][2];
            }
        }
        // check for game over
        int count = 0;
        for (int i = 0; i < boxArray.length; i++) {
            for (int j = 0; j < boxArray.length; j++) {
                if (boxArray[i][j] != -1) {
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
        boxViews = new ImageView[3][3];
        boxViews[0][0] = mBinding.text00;
        boxViews[0][1] = mBinding.text01;
        boxViews[0][2] = mBinding.text02;

        boxViews[1][0] = mBinding.text10;
        boxViews[1][1] = mBinding.text11;
        boxViews[1][2] = mBinding.text12;

        boxViews[2][0] = mBinding.text20;
        boxViews[2][1] = mBinding.text21;
        boxViews[2][2] = mBinding.text22;

        int size = boxViews.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boxViews[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text00:
                boxArray[0][0] = VALUE_USER;
                userTurn(mBinding.text00);
                break;
            case R.id.text01:
                boxArray[0][1] = VALUE_USER;
                userTurn(mBinding.text01);
                break;
            case R.id.text02:
                boxArray[0][2] = VALUE_USER;
                userTurn(mBinding.text02);
                break;
            case R.id.text10:
                boxArray[1][0] = VALUE_USER;
                userTurn(mBinding.text10);
                break;
            case R.id.text11:
                boxArray[1][1] = VALUE_USER;
                userTurn(mBinding.text11);
                break;
            case R.id.text12:
                boxArray[1][2] = VALUE_USER;
                userTurn(mBinding.text12);
                break;
            case R.id.text20:
                boxArray[2][0] = VALUE_USER;
                userTurn(mBinding.text20);
                break;
            case R.id.text21:
                boxArray[2][1] = VALUE_USER;
                userTurn(mBinding.text21);
                break;
            case R.id.text22:
                boxArray[2][2] = VALUE_USER;
                userTurn(mBinding.text22);
                break;
        }
    }
}
