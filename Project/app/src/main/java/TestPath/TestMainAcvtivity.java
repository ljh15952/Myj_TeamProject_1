package TestPath;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myj_teamproj_1.R;

public class TestMainAcvtivity extends AppCompatActivity {
    Button button1, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.path_activity);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        button1.setBackgroundColor(Color.GRAY);
        button2.setBackgroundColor(Color.BLACK);
        button3.setBackgroundColor(Color.BLACK);

        button3.setText("확대");
    }

    public void stop(View view){
        if(PathView.move)
            PathView.move = false;
        else
            PathView.move = true;
    }

    public void pressButton1(View view){
        button1.setBackgroundColor(Color.GRAY);
        button2.setBackgroundColor(Color.BLACK);

        PathView.type = 0;
        setButton3(view);
    }

    public void pressButton2(View view){
        button1.setBackgroundColor(Color.BLACK);
        button2.setBackgroundColor(Color.GRAY);

        PathView.type = 1;
        setButton3(view);
    }

    public void pressButton3(View view){
        if(PathView.screen == 0) {
            PathView.screen = 1;
            button3.setBackgroundColor(Color.GRAY);
            button3.setText("축소");
        }else{
            PathView.screen = 0;
            button3.setBackgroundColor(Color.BLACK);
            button3.setText("확대");
        }
    }

    public void setButton3(View view){
        PathView.screen = 0;
        button3.setBackgroundColor(Color.BLACK);
        button3.setText("확대");
    }
}
