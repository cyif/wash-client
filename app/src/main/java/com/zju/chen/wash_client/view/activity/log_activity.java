package com.zju.chen.wash_client.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Code;
import com.zju.chen.wash_client.model.DealLog;
import com.zju.chen.wash_client.net.DealLogController;
import com.zju.chen.wash_client.net.RoomController;
import com.zju.chen.wash_client.util.CustomApplication;
import com.zju.chen.wash_client.util.JacksonUtil;
import com.zju.chen.wash_client.view.adapter.LogAdapter;
import com.zju.chen.wash_client.view.adapter.RoomAdapter;
import com.zju.chen.wash_client.zxing.activity.CaptureActivity;

import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ab on 2016/7/9.
 */
public class log_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private CustomApplication app;
    private Handler handler;
    private DealLogController dealLogController;
    private LogAdapter logAdapter;
    private TextView accountTextView;
    ImageButton imageButton;

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        app = (CustomApplication)getApplication();
        setContentView(R.layout.activity_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_log).setChecked(true);


        lv = (ListView)findViewById(R.id.listView);
        accountTextView = (TextView)navigationView.getHeaderView(0).findViewById(R.id.accountTextView);
        accountTextView.setText(app.getAccountName());

        imageButton=(ImageButton)findViewById(R.id.code);
        imageButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(log_activity.this, CaptureActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        dealLogController = new DealLogController();
        dealLogController.setUrl(app.getUrl2());

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        List<DealLog> dealLogs = dealLogController.getDealLogList();
                        Collections.reverse(dealLogs);
                        logAdapter = new LogAdapter(log_activity.this, R.layout.log_list, dealLogs);
                        lv.setAdapter(logAdapter);
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };

        dealLogController.getDealLog(handler, app.getAccountName());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_log).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_homepage) {
            Intent intent=new Intent(log_activity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);

        } else if (id == R.id.nav_status) {
            Intent intent=new Intent(log_activity.this,status_activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else if (id == R.id.nav_log) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
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
            } else {
                Intent intent = new Intent(this, choose_activity.class);
                intent.putExtra("Code", code);
                startActivity(intent);
            }
        }
    }
}
