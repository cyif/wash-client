package com.zju.chen.wash_client.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Deal;
import com.zju.chen.wash_client.model.Room;
import com.zju.chen.wash_client.model.Type;
import com.zju.chen.wash_client.model.Code;
import com.zju.chen.wash_client.model.WashMachine;
import com.zju.chen.wash_client.net.PayController;
import com.zju.chen.wash_client.net.WashMachineController;
import com.zju.chen.wash_client.util.CustomApplication;
import com.zju.chen.wash_client.view.adapter.ChooseAdapter;
import com.zju.chen.wash_client.view.adapter.LogAdapter;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ab on 2016/7/11.
 */
public class choose_activity extends AppCompatActivity {

    CustomApplication app;
    private Handler handler;

    GridView gridView;
    HorizontalScrollView horizontalScrollView;
    private int numPerLine=3;
    DisplayMetrics dm;
    private int LIEWIDTH;
    Button button;
    Deal deal;
    ChooseAdapter adapter;

    private Code code;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        app = (CustomApplication)getApplication();
        setContentView(R.layout.activity_choose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.choose_toolbar);
        setSupportActionBar(toolbar);

        code = (Code) getIntent().getSerializableExtra("Code");
        deal=new Deal();
        deal.setFrom(app.getAccountName());
        deal.setTo(code.getAccount());

        button=(Button)findViewById(R.id.button_choose);
        final SweetAlertDialog sad = new SweetAlertDialog(choose_activity.this, SweetAlertDialog.WARNING_TYPE);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Deal deal = (Deal) msg.getData().getSerializable("Deal");
                        /*new AlertDialog.Builder(choose_activity.this)
                                .setTitle("支付成功")
                                .setMessage("支付" + deal.getMoney() + "元")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(choose_activity.this, status_activity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .show();*/
                        startWashMachine(msg.getData().getInt("status"), deal.getMoney());
                        sad.setTitleText("支付成功")
                                .setContentText("支付" + deal.getMoney() + "元")
                                .setConfirmText("确认")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent intent = new Intent(choose_activity.this, status_activity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .showCancelButton(false)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        break;
                    case -1:
                        /*new AlertDialog.Builder(choose_activity.this)
                                .setTitle("支付失败")
                                .setMessage((String) msg.getData().getString("msg"))
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();*/
                        sad.setTitleText("支付失败")
                                .setContentText((String) msg.getData().getString("msg"))
                                .setConfirmText("确认")
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };

        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                /*Dialog dialog = new AlertDialog.Builder(choose_activity.this)
                        .setTitle("pay:")
                        .setMessage(deal.getMoney() + "")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                PayController payController = new PayController();
                                payController.setUrl(app.getUrl2());
                                payController.setStatus(status);
                                payController.setDeal(deal);
                                payController.postDeal(handler);
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                dialog.show();*/

                sad.setTitleText("支付确认")
                        .setContentText("支付 " + deal.getMoney() + "元")
                        .setConfirmText("确认")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                sweetAlertDialog.getProgressHelper()
                                        .setBarColor(Color.parseColor("#A5DC86"));
                                sweetAlertDialog.
                                        changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                                sweetAlertDialog.setTitleText("处理中")
                                        .showCancelButton(false)
                                        .setCancelable(false);

                                PayController payController = new PayController();
                                payController.setUrl(app.getUrl2());
                                payController.setStatus(status);
                                payController.setDeal(deal);
                                payController.postDeal(handler);

                            }
                        })
                        .setCancelText("取消")
                        .show();
            }
        });

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
        gridView = (GridView) findViewById(R.id.gridView);

        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        getScreenDen();
        LIEWIDTH=dm.widthPixels/numPerLine;
        setValue();
        gridView.setOnItemClickListener(chooseListListener);

    }


    private void getScreenDen(){
        dm=new DisplayMetrics() ;
        getWindowManager().getDefaultDisplay().getMetrics(dm);
    }


    private void setValue(){

        adapter=new ChooseAdapter(this, R.layout.choose_list, code.getTypes());
        gridView.setAdapter(adapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(adapter.getCount() * LIEWIDTH,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        gridView.setLayoutParams(params);
        gridView.setColumnWidth(dm.widthPixels / numPerLine);
        gridView.setStretchMode(GridView.NO_STRETCH);
        int count = adapter.getCount();
        gridView.setNumColumns(count);
    }

    private AdapterView.OnItemClickListener chooseListListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Type choose=(Type)parent.getItemAtPosition(position);
            deal.setMoney(choose.getPrice());
            deal.setTo(code.getAccount());
            status = choose.getStatus();
            adapter.setSelection(position);
            adapter.notifyDataSetChanged();

            button.setText("pay: "+choose.getPrice());
            button.setVisibility(View.VISIBLE);
            Log.d("!!!!!!!","visible");
        }
    };

    private void startWashMachine(int status, double money) {
        WashMachineController wmc = new WashMachineController();
        wmc.setUrl(app.getUrl());
        wmc.startWashMachine(new Handler(), code.getWashId(), app.getAccountName(), status, money);
    }
}
