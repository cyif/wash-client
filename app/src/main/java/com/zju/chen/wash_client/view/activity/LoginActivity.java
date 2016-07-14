package com.zju.chen.wash_client.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.net.LoginController;
import com.zju.chen.wash_client.util.CustomApplication;
import com.zju.chen.wash_client.zxing.activity.CaptureActivity;

/**
 * Created by ab on 2016/7/14.
 */
public class LoginActivity extends AppCompatActivity {

    private CustomApplication app;
    private EditText accountEditText;
    private EditText veriCodeEditText;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        app = (CustomApplication)getApplication();

        accountEditText = (EditText)findViewById(R.id.phone);
        veriCodeEditText = (EditText)findViewById(R.id.vericode);

        Button get_code=(Button)findViewById(R.id.get_code);
        final Button login=(Button)findViewById(R.id.login);

        get_code.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                LoginController loginController = new LoginController();
                loginController.setUrl(app.getUrl2());
                loginController.setParams("/user/user/login/tel/");
                loginController.setAccount(accountEditText.getText().toString());
                loginController.getVerifyCode();
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
