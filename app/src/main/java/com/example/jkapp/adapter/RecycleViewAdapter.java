package com.example.jkapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jkapp.R;
import com.example.jkapp.bean.Question;
import com.example.jkapp.utils.Constant;
import com.example.jkapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dufangyu on 2017/9/7.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{

    private List<Question> dataList = new ArrayList<>();
    private ClickListenr listenr;

    public void addAllData(List<Question> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }
    public void setListenr(ClickListenr listenr) {
        this.listenr = listenr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        Question question = dataList.get(position);
        int tem_int = Util.getRandomValue(0,2);
        holder.mTitle.setText(question.getTitle());
        holder.mTime.setText(question.getPublishTime());
        holder.mContent.setText(question.getContent());
        holder.userImg.setImageResource(Constant.mSpArr_select.get(tem_int));
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(listenr!=null)
                {
                    listenr.onClickEvent(position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTitle;
        public TextView mTime;
        public TextView mContent;
        public ImageView userImg;
        public View rootView;


        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mContent = (TextView) itemView.findViewById(R.id.content);
            userImg = (ImageView) itemView.findViewById(R.id.userimg);
        }
    }


    public interface ClickListenr{
        void onClickEvent(int positon);
    }
}
