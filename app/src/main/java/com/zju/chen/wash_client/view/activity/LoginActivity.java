package com.zju.chen.wash_client.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.zxing.activity.CaptureActivity;

/**
 * Created by ab on 2016/7/14.
 */
public class LoginActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button get_code=(Button)findViewById(R.id.get_code);
        Button login=(Button)findViewById(R.id.login);
        get_code.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){

            }
        });
        login.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}
