package com.zju.chen.wash_client.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.net.DealLogController;
import com.zju.chen.wash_client.net.RoomController;
import com.zju.chen.wash_client.util.CustomApplication;
import com.zju.chen.wash_client.view.adapter.LogAdapter;
import com.zju.chen.wash_client.view.adapter.RoomAdapter;

/**
 * Created by ab on 2016/7/9.
 */
public class log_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private CustomApplication app;
    private Handler handler;
    private DealLogController dealLogController;
    private LogAdapter logAdapter;

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
                        logAdapter = new LogAdapter(log_activity.this, R.layout.log_list, dealLogController.getDealLogList());
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
}
