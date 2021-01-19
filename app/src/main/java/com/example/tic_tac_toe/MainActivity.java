package com.example.tic_tac_toe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    Button[][] btn = new Button[3][3]; // Button Array
    boolean p1_turn = true; // p1的回合
    int round;  // 回合數
    int p1_point, p2_point; // 計分
    TextView p1, p2; // 分數的 TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p1 = findViewById(R.id.p1_point);
        p2 = findViewById(R.id.p2_point);

        // 宣告 Button 物件 和 呼叫方法
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String btn_ID = "btn_" + i + j;
                int resID = getResources().getIdentifier(btn_ID, "id", getPackageName());
                btn[i][j] = findViewById(resID);
                btn[i][j].setOnClickListener(btnListener);
            }
        }

        Button reset = findViewById(R.id.btn_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_game();
            }
        });

    }

    // 九宮格按鈕
    private final Button.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!((Button) v).getText().toString().equals("")) {
                return;
            }

            if (p1_turn) {
                ((Button)v).setText("O");
            } else {
                ((Button)v).setText("X");
            }

            round++;

            if (CheckForWin()) {
                if (p1_turn) {
                    p1_win();
                } else {
                    p2_win();
                }
            } else if (round == 9) {
                round_draw();
            } else {
                p1_turn = !p1_turn;
            }
        }
    };

    // 確認輸贏
    private boolean CheckForWin() {
        String[][] field = new String[3][3];

        // 取 Button 的值
        for (int i = 0 ; i < 3 ; i++) {
            for (int j = 0 ; j < 3 ; j++) {
                field[i][j] = btn[i][j].getText().toString();
            }
        }

        // 以 Y 軸為基準
        for (int i = 0 ; i < 3 ; i++){
            if (field[i][0].equals(field[i][1])
                && field[i][0].equals(field[i][2])
                && !field[i][0].equals("")) {
                return true;
            }
        }

        // 以 X 軸為基準
        for (int i = 0 ; i < 3 ; i++){
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        // 左上往右下
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        // 左下往右上
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    // p1 贏
    private void p1_win() {
        p1_point++;
        Toast.makeText(this,"Player One Wins!", Toast.LENGTH_SHORT).show();
        update_point();
        resetBoard();
    }

    // p2贏
    private void p2_win() {
        p2_point++;
        Toast.makeText(this,"Player Two Wins!", Toast.LENGTH_SHORT).show();
        update_point();
        resetBoard();
    }

    // 平手
    private void round_draw() {
        Toast.makeText(this,"DRAW!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    // 更新分數
    private void update_point() {
        p1.setText(String.valueOf(p1_point));
        p2.setText(String.valueOf(p2_point));
    }

    // 更新記分板
    private void resetBoard() {
        for (int i = 0 ; i < 3 ; i++) {
            for (int j = 0 ; j < 3 ; j++) {
                btn[i][j].setText("");
            }
        }

        round = 0;
        p1_turn = true;

    }

    // 重置遊戲
    private  void reset_game() {
        p1_point = 0;
        p2_point = 0;
        update_point();
        resetBoard();
    }

    // 儲存狀態
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("round", round);
        outState.putInt("p1_point", p1_point);
        outState.putInt("p2_point", p2_point);
        outState.putBoolean("p1_turn", p1_turn);
    }

    // 恢復狀態
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        round = savedInstanceState.getInt("round");
        p1_point = savedInstanceState.getInt("p1_point");
        p2_point = savedInstanceState.getInt("p2_point");
        p1_turn = savedInstanceState.getBoolean("p1_turn");
    }

}
