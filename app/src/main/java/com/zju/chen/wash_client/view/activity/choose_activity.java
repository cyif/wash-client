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

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Deal;
import com.zju.chen.wash_client.model.Room;
import com.zju.chen.wash_client.model.Type;
import com.zju.chen.wash_client.model.Code;
import com.zju.chen.wash_client.view.adapter.ChooseAdapter;

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

    private Code code;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.choose_toolbar);
        setSupportActionBar(toolbar);
        button=(Button)findViewById(R.id.button_choose);
        deal=new Deal();

        code = (Code) getIntent().getSerializableExtra("Code");

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

        ChooseAdapter adapter=new ChooseAdapter(this, R.layout.choose_list, code.getTypes());
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


            button.setText("pay: "+choose.getPrice());
            button.setVisibility(View.VISIBLE);
            Log.d("!!!!!!!","visible");
        }
    };
}
