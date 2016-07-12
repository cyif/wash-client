package com.zju.chen.wash_client.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Code;

/**
 * Created by chen on 16/7/12.
 */
public class TestActivity extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView = (TextView)findViewById(R.id.testTextView);

        Code code = (Code) getIntent().getSerializableExtra("Code");
        textView.setText(code.getAccount() + "\n"
            + code.getWashId() + "\n"
            + code.getTypes().toString());
    }
}
