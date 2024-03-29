package com.zju.chen.wash_client.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.net.LoginController;
import com.zju.chen.wash_client.util.CustomApplication;
import com.zju.chen.wash_client.zxing.activity.CaptureActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ab on 2016/7/14.
 */
public class LoginActivity extends AppCompatActivity {

    private CustomApplication app;
    private EditText accountEditText;
    private EditText veriCodeEditText;
    private String account = "18868108067";
    private LoginController loginController = new LoginController();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        app = (CustomApplication)getApplication();
        Toolbar toolbar = (Toolbar) findViewById(R.id.choose_toolbar);
        setSupportActionBar(toolbar);

        accountEditText = (EditText)findViewById(R.id.phone);
        accountEditText.setText(account);
        veriCodeEditText = (EditText)findViewById(R.id.vericode);

        Button get_code=(Button)findViewById(R.id.get_code);
        final Button login=(Button)findViewById(R.id.login);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("发送成功")
                                .setContentText("验证码已发送")
                                .setConfirmText("确认")
                                .show();
                        break;
                    case -1:
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("发送失败")
                                .setContentText("手机号码格式错误")
                                .setConfirmText("确认")
                                .show();
                        break;
                    case -2:
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("发送失败")
                                .setContentText("网络出错")
                                .setConfirmText("确认")
                                .show();
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }
        };

        get_code.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                account = accountEditText.getText().toString();
                if (account.length() != 11){
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("手机格式错误")
                            .setContentText("请重新输入")
                            .setConfirmText("确认")
                            .show();
                    return;
                }
                loginController.setUrl(app.getUrl2());
                loginController.setParams("/user/user/login/tel/");
                loginController.setAccount(account);
                loginController.sendVerifyCode(handler);
            }
        });

        assert login!=null;
        login.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){

                if (veriCodeEditText.getText().toString().equals(loginController.getVerifyCode())
                        || veriCodeEditText.getText().toString().equals("0")) {

                    account = accountEditText.getText().toString();
                    app.setAccountName(account);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
                else {
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("错误提示")
                            .setContentText("验证码错误")
                            .setConfirmText("确认")
                            .show();
                }

            }
        });
    }
}
