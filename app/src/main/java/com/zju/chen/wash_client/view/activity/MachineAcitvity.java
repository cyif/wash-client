package com.zju.chen.wash_client.view.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Room;
import com.zju.chen.wash_client.model.WashMachine;
import com.zju.chen.wash_client.net.RoomController;
import com.zju.chen.wash_client.net.WashMachineController;
import com.zju.chen.wash_client.util.CustomApplication;
import com.zju.chen.wash_client.view.adapter.MachineAdapter;
import com.zju.chen.wash_client.view.adapter.RoomAdapter;
import com.zju.chen.wash_client.zxing.activity.CaptureActivity;

import java.util.Date;

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

        lv = (ListView)findViewById(R.id.machineListView);

        washMachineController = new WashMachineController();
        washMachineController.setUrl(app.getUrl());
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
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

        imageButton=(ImageButton)findViewById(R.id.code);
        imageButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MachineAcitvity.this, CaptureActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }


}
