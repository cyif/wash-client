package com.zju.chen.wash_client.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Code;
import com.zju.chen.wash_client.model.Room;
import com.zju.chen.wash_client.net.RoomController;
import com.zju.chen.wash_client.util.CustomApplication;
import com.zju.chen.wash_client.util.JacksonUtil;
import com.zju.chen.wash_client.view.adapter.RoomAdapter;
import com.zju.chen.wash_client.zxing.activity.CaptureActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CustomApplication app;

    private ListView lv;
    private RoomController roomController;
    private RoomAdapter roomAdapter;

    private Handler handler;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        app = (CustomApplication)getApplication();

        lv = (ListView)findViewById(R.id.listView);
        //lv.toString();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_homepage).setChecked(true);

        //String[] test = new String[] {"1", "22", "333"};

        //ListView lv = (ListView)findViewById(R.id.listView);

        imageButton=(ImageButton)findViewById(R.id.code);
        imageButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        roomController = new RoomController();
        roomController.setUrl(app.getUrl());

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        roomAdapter = new RoomAdapter(MainActivity.this, R.layout.room_list, roomController.getRoomList());
                        lv.setAdapter(roomAdapter);
                        lv.setOnItemClickListener(roomListListener);

                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };

        roomController.getRooms(handler);
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
        navigationView.getMenu().findItem(R.id.nav_homepage).setChecked(true);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_homepage) {

        } else if (id == R.id.nav_status) {
            Intent intent=new Intent(MainActivity.this,status_activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else if (id == R.id.nav_log) {
            Intent intent=new Intent(MainActivity.this,log_activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private AdapterView.OnItemClickListener roomListListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Room room = (Room)parent.getItemAtPosition(position);

            Intent intent = new Intent(MainActivity.this, MachineAcitvity.class);
            intent.putExtra("Room", room.getRoom());
            startActivity(intent);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String result = data.getStringExtra("result");
            Code code = JacksonUtil.parseJson(result, new TypeReference<Code>() {
            }, null);

            if (code == null) {
                new AlertDialog.Builder(this).setTitle("错误！")
                        .setMessage("数据格式错误！")
                        .setPositiveButton("确定", null)
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
