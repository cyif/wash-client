package com.zju.chen.wash_client.view.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Code;
import com.zju.chen.wash_client.model.Room;
import com.zju.chen.wash_client.model.WashMachine;
import com.zju.chen.wash_client.net.RoomController;
import com.zju.chen.wash_client.net.WashMachineController;
import com.zju.chen.wash_client.util.CustomApplication;
import com.zju.chen.wash_client.util.JacksonUtil;
import com.zju.chen.wash_client.view.adapter.MachineAdapter;
import com.zju.chen.wash_client.view.adapter.RoomAdapter;
import com.zju.chen.wash_client.zxing.activity.CaptureActivity;

import org.w3c.dom.Text;

import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by chen on 16/7/9.
 */
public class MachineAcitvity extends AppCompatActivity {

    private CustomApplication app;
    private ListView lv;
    private WashMachineController washMachineController;
    private MachineAdapter machineAdapter;

    private Handler handler;
    private int room;
    ImageButton imageButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machines);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = (CustomApplication)getApplication();

        Bundle extra = getIntent().getExtras();
        room = extra.getInt("Room");

        TextView title = (TextView)findViewById(R.id.title);
        title.setText("房间: " + room);

        lv = (ListView)findViewById(R.id.machineListView);

        imageButton=(ImageButton)findViewById(R.id.code);
        imageButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MachineAcitvity.this, CaptureActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onStart() {
        super.onStart();
        washMachineController = new WashMachineController();
        washMachineController.setUrl(app.getUrl());
        Log.d("SHOW!!!!", "11111111111");
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Log.d("SHOW!!!!", "222222222222");
                        machineAdapter = new MachineAdapter(MachineAcitvity.this,
                                R.layout.machine_list,
                                washMachineController.getMachineList());
                        lv.setAdapter(machineAdapter);
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };
        washMachineController.getWashMachineByRoom(handler, room);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String result = data.getStringExtra("result");
            Code code = JacksonUtil.parseJson(result, new TypeReference<Code>() {
            }, null);

            if (code == null) {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("出错")
                        .setContentText("数据格式错误！")
                        .setConfirmText("确定")
                        .show();
            }
            else {
                Intent intent = new Intent(this, choose_activity.class);
                intent.putExtra("Code", code);
                startActivity(intent);
            }
        }
    }

}
