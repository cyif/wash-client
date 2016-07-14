package com.zju.chen.wash_client.view.activity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Code;
import com.zju.chen.wash_client.model.DealLog;
import com.zju.chen.wash_client.model.WashMachine;
import com.zju.chen.wash_client.net.DealLogController;
import com.zju.chen.wash_client.net.WashMachineController;
import com.zju.chen.wash_client.util.JacksonUtil;
import com.zju.chen.wash_client.view.adapter.ChooseAdapter;
import com.zju.chen.wash_client.view.adapter.LogAdapter;
import com.zju.chen.wash_client.view.adapter.StatusAdapter;
import com.zju.chen.wash_client.util.CustomApplication;
import com.zju.chen.wash_client.zxing.activity.CaptureActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ab on 2016/7/8.
 */
public class status_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ImageButton imageButton;
    GridView gridView;
    HorizontalScrollView horizontalScrollView;
    private int numPerLine=1;
    DisplayMetrics dm;
    private int LIEWIDTH;
    StatusAdapter adapter;
    private WashMachine washMachine;
    CustomApplication app;
    TextView accountTextView;
    private Handler handler;
    private WashMachineController washMachineController;
    private StatusAdapter statusAdapter;

    public static status_activity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        instance=this;
        setContentView(R.layout.activity_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = (CustomApplication) getApplication();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_status).setChecked(true);


        accountTextView = (TextView)navigationView.getHeaderView(0).findViewById(R.id.accountTextView);
        accountTextView.setText(app.getAccountName());

        imageButton=(ImageButton)findViewById(R.id.code);
        imageButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(status_activity.this, CaptureActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scrollView_status);
        gridView = (GridView) findViewById(R.id.gridView_status);

        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        getScreenDen();
        LIEWIDTH=dm.widthPixels/numPerLine;
        Log.d("!!!!status_activity:","onCreate!!!!!!");

        washMachineController = new WashMachineController();
        washMachineController.setUrl(app.getUrl2());

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        List<WashMachine> washMachineList = washMachineController.getMachineList();
                        setValue(washMachineList);
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };

        washMachineController.getWashMachineByAccount(handler, app.getAccountName());
    }

    @Override
    public void onStart() {
        super.onStart();

       /* washMachineController = new WashMachineController();
        washMachineController.setUrl(app.getUrl2());

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        List<WashMachine> washMachineList = washMachineController.getMachineList();
                        setValue(washMachineList);
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };

        washMachineController.getWashMachineByAccount(handler, app.getAccountName());*/
        Log.d("!!!!status_activity:","onStart!!!!!!");
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
        navigationView.getMenu().findItem(R.id.nav_status).setChecked(true);
        Log.d("!!!!status_activity:","onResume!!!!!!");
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_homepage) {
            Intent intent=new Intent(status_activity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else if (id == R.id.nav_status) {

        } else if (id == R.id.nav_log) {
            Intent intent=new Intent(status_activity.this,log_activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
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
    private void getScreenDen(){
        dm=new DisplayMetrics() ;
        getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    private void setValue(List<WashMachine> washMachines){
        TextView tv=(TextView)findViewById(R.id.null_msg);
        if(washMachines.isEmpty())
            tv.setVisibility(View.VISIBLE);
        else
            tv.setVisibility(View.GONE);


        adapter=new StatusAdapter(this, R.layout.status_list,washMachines);
        gridView.setAdapter(adapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(adapter.getCount() * LIEWIDTH,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        gridView.setLayoutParams(params);
        gridView.setColumnWidth(dm.widthPixels / numPerLine);
        gridView.setStretchMode(GridView.NO_STRETCH);
        int count = adapter.getCount();
        gridView.setNumColumns(count);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        PackageManager pm = getPackageManager();
        ResolveInfo homeInfo =
                pm.resolveActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), 0);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityInfo ai = homeInfo.activityInfo;
            Intent startIntent = new Intent(Intent.ACTION_MAIN);
            startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            startIntent.setComponent(new ComponentName(ai.packageName, ai.name));
            startActivitySafely(startIntent);
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }
    private void startActivitySafely(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "null",
                    Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            Toast.makeText(this, "null",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
