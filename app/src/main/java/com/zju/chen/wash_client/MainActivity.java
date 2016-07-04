package com.zju.chen.wash_client;

import android.view.View;
import android.view.View.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView)findViewById(R.id.testText);
        textView.setText(num + "");

        OnClickListener lis = new OnClickListener() {
            @Override
            public void onClick(View v) {
                num ++;
                textView.setText(num + "");
            }
        };

        Button b = (Button)findViewById(R.id.addButton);
        b.setOnClickListener(lis);


    }
}
