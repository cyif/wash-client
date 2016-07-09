package com.zju.chen.wash_client.view.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Room;
import com.zju.chen.wash_client.model.WashMachine;

import java.util.List;

/**
 * Created by chen on 16/7/9.
 */
public class MachineAdapter extends ArrayAdapter<WashMachine> {

    private int mResourceId;

    public MachineAdapter(Context context, int resourceId, List<WashMachine> objects) {

        super(context, resourceId, objects);
        Log.d("Machine!!!!!", "   11111");
        this.mResourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("Machine!!!!!!!","   22222");

        WashMachine washMachine = getItem(position);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);
        TextView machineText = (TextView) view.findViewById(R.id.machine);
        TextView timeText = (TextView) view.findViewById(R.id.time);

        machineText.setText("洗衣机编号: " + washMachine.getId());

        if (washMachine.getStatus() != 0) {

            long time = washMachine.getEndTime().getTime() - washMachine.getBeginTime().getTime();
            TimeCount timeCount = new TimeCount(time, 1000, timeText);
        }
        else {
            timeText.setText("可用");
        }

        return view;
    }

    class TimeCount extends CountDownTimer {

        TextView statusView;

        public TimeCount(long millisInFuture, long countDownInterval, View view) {
            super(millisInFuture, countDownInterval);
            statusView = (TextView)view;
        }

        @Override
        public void onFinish() {
            statusView.setText("可用");
            statusView.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){

            statusView.setText("剩余时间: " + millisUntilFinished /1000 + "秒");
        }
    }
}
