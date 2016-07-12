package com.zju.chen.wash_client.view.activity;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ListView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Deal;
import com.zju.chen.wash_client.model.Room;
import com.zju.chen.wash_client.model.type;
import com.zju.chen.wash_client.view.adapter.ChooseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ab on 2016/7/11.
 */
public class choose_activity extends AppCompatActivity {
    GridView gridView;
    HorizontalScrollView horizontalScrollView;
    private int numPerLine=3;
    DisplayMetrics dm;
    private int LIEWIDTH;
    Button button;
    Deal deal;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.choose_toolbar);
        setSupportActionBar(toolbar);
        button=(Button)findViewById(R.id.button_choose);
        deal=new Deal();

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
        List<type> list=new ArrayList<type>();

        ChooseAdapter adapter=new ChooseAdapter(this,R.layout.choose_list,list);
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
           type choose=(type)parent.getItemAtPosition(position);


            button.setText("pay: "+choose.getPrice());
            button.setVisibility(View.VISIBLE);
            Log.d("!!!!!!!","visible");
        }
    };
}
