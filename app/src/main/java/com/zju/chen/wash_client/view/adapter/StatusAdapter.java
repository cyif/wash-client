package com.zju.chen.wash_client.view.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.WashMachine;
import com.zju.chen.wash_client.util.NotifyUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by ab on 2016/7/13.
 */
public class StatusAdapter extends ArrayAdapter<WashMachine> {

    private Context context;
    private int mResourceId;
    public StatusAdapter(Context context, int resourceId, List<WashMachine> objects) {
        super(context, resourceId, objects);
        this.mResourceId = resourceId;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        long time;
        WashMachine washMachine = getItem(position);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);
        TextView status_id = (TextView) view.findViewById(R.id.status_id);
        TextView status_info = (TextView) view.findViewById(R.id.status_info);

        status_id.setText("machine id:  "+washMachine.getId());
        if(washMachine.getStatus()==1){
            if(washMachine.getEndTime()!=null){
                time=washMachine.getEndTime().getTime()-new Date().getTime();
                TimeCount timeCount = new TimeCount(time, 1000, status_info, washMachine.getId());
                timeCount.start();
            }
            //status_image.setImageResource();
        }
        else{
            status_info.setText("end");
        }

        return view;
    }

    class TimeCount extends CountDownTimer{
        TextView tv;
        private int washId;
        public TimeCount(long millisInFuture, long countDownInterval, View textView, int id){
            super(millisInFuture, countDownInterval);
            tv = (TextView)textView;
            this.washId = id;
        }
        @Override
        public void onTick(long millisUntilFinished){

            millisUntilFinished /= 1000;
            long hour = millisUntilFinished / (60 * 60);
            long min = millisUntilFinished % (60 * 60) / 60;
            long sec = millisUntilFinished % 60;

            String time;
            if (hour > 0) {
                time = hour + "小时" + min + "分" + sec + "秒";
            }
            else if (min > 0) {
                time = min + "分" + sec + "秒";
            }
            else
                time = sec + "秒";
            tv.setText("剩余时间： " + time);
        }
        @Override
        public void onFinish() {
            NotifyUtil.sendNotify(context, "洗衣完成", "洗衣完成！", "您在" + washId + "机器的衣服已洗完");
            tv.setText("洗衣结束，请尽快拿走衣服");
        }
    }
}
