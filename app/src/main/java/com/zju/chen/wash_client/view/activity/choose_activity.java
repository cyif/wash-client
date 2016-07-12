package com.zju.chen.wash_client.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zju.chen.wash_client.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.choose_toolbar);
        setSupportActionBar(toolbar);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
        gridView = (GridView) findViewById(R.id.gridView);

        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        getScreenDen();
        LIEWIDTH=dm.widthPixels/numPerLine;
        setValue();
    }
    private void getScreenDen(){
        dm=new DisplayMetrics() ;
        getWindowManager().getDefaultDisplay().getMetrics(dm);
    }
    private void setValue(){
        List<type> list=new ArrayList<type>();
        type choose1=new type();
        choose1.setName("1");
        choose1.setPrice(10);
        list.add(choose1);
        type choose2=new type();
        choose2.setName("2");
        choose2.setPrice(20); list.add(choose2);

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

}
