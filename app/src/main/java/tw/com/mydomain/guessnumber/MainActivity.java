package tw.com.mydomain.guessnumber;
// Android Studio 貼心幫忙引入函式庫
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends Activity {
    private boolean isoncl=true;
    // 先宣告 View 的變數
    ImageButton imageButton2;

    Button submitButton;
    Button restartButton;
    Button startButton;
    EditText inputNumber;
    TextView historyInput;
    TextView historyResult;
    TextView tv;
    TextView timer;
    // Toast 是畫面下面會跳出來的小提示框
    Toast toast;
    int counter;
    char[] chs = new char[10000];
    int w;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 找到畫面中的 View
        submitButton = (Button) findViewById(R.id.submitButton);
        restartButton = (Button) findViewById(R.id.restartButton);
        startButton = findViewById(R.id.startButton);
        inputNumber = (EditText) findViewById(R.id.inputNumber);
        historyInput = (TextView) findViewById(R.id.history_input);
        historyResult = (TextView) findViewById(R.id.history_result);
        tv = findViewById(R.id.tv);
        timer = findViewById(R.id.timer);

        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);

        counter = 0;
        // 宣告一個 Game 實體
        final Game game = new Game();
        // 產生一個隨機的答案

        final Context that = this;
        inputNumber.setEnabled(false);
        submitButton.setEnabled(false);
        restartButton.setEnabled(false);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(isoncl) {


                   tv.setText(tv.getText().toString() + game.getAnswer() + "\n");
               }
                isoncl=false;
            }
        }
            );

        // 設定送出按鈕的點擊事件
        startButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                w = 0;
                inputNumber.setEnabled(true);
                submitButton.setEnabled(true);
                restartButton.setEnabled(true);
                historyInput.setText("");
                historyResult.setText("");
                startButton.setVisibility(View.INVISIBLE);
                game.generateAnswer();

                CountDownTimer countDownTimer = new CountDownTimer(300000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer.setText("Time:" + String.format(Locale.getDefault(), " %ds.", millisUntilFinished / 1000L));
                    }
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onFinish() {
                        timer.setText("時間到!!");
                        Toast.makeText(that, ("Last answer: " + game.getAnswer()+"\n\n   Game restarted"), Toast.LENGTH_LONG).show();
                        inputNumber.setEnabled(false);
                        submitButton.setEnabled(false);
                        restartButton.setEnabled(false);
                        startButton.setVisibility(View.VISIBLE);
                        historyInput.setText("");
                        historyResult.setText("");
                        tv.setText("");
                        historyInput.setText("猜中次數");
                        historyResult.setText(String.format("%d",w));
                    }
                }.start();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v){
                // 一定要輸入四個數字才有反應
                if(inputNumber.getText().length() == 4) {
                    // 將使用者輸入的數字跟幾 A 幾 B 放入文字框框中
                    historyInput.setText((inputNumber.getText() + "\n") + historyInput.getText());
                    historyResult.setText((game.checkAnswer(inputNumber.getText().toString()) + "\n") + historyResult.getText());
                    for (int i = 0; i < inputNumber.getText().length(); i ++)
                    {
                        chs[i] = inputNumber.getText().charAt(i);
//                        System.out.print(chs[i] + " ");
                    }

//                    if((game.checkAnswer(inputNumber.getText().toString())).equals( game.checkAnswer(inputNumber.getText().toString()))){
//                        Toast.makeText(that, "You mistake number ", Toast.LENGTH_LONG).show();
//                    }
                    // 清空輸入框
                    inputNumber.setText("");
                    // 如果猜中了
                    if() {
                        for (int i = 0; i <
                                chs.length - 1; i++) {
                            if (chs[i] == chs[i + 1]) {
                                Toast.makeText(that, "You mistake number ", Toast.LENGTH_LONG).show();
                                break;

                            }
                        }
                    }
//                           Toast.makeText(that, "You mistake number ", Toast.LENGTH_LONG).show();

                    if (game.isWin()) {
                        // 跳出獲勝的訊息
                        Toast.makeText(that, "You win", Toast.LENGTH_LONG).show();
                        historyInput.setText("");
                        historyResult.setText("");
                        tv.setText("");

                        game.generateAnswer();
//                        tv.setText(tv.getText().toString()+game.getAnswer()+"\n");
                        w++;
//                        if((inputNumber.getText().toString()).equals( inputNumber.getText().toString())){
//                            Toast.makeText(that, "You mistake number ", Toast.LENGTH_LONG).show();
//                        }

                    }
                }

            }
        });
        // 設定重新開始按鈕的點擊事件
        restartButton.setOnClickListener(v -> {
            Toast.makeText(that, ("Last answer: " + game.getAnswer()+"\n\n   Game restarted"), Toast.LENGTH_LONG).show();
            inputNumber.setEnabled(false);
            inputNumber.setEnabled(true);
            submitButton.setEnabled(true);
            game.generateAnswer();
//            tv.setText(tv.getText().toString()+game.getAnswer()+"\n");
            historyInput.setText("");
            historyResult.setText("");
            tv.setText("");
        });
    }
}
