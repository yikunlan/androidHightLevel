package com.hyk.sdk.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hyk on 2019/4/3.
 */

public class RViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    private View mConvertView;

    public RViewHolder(View itemView) {
        super(itemView);

        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static RViewHolder createViewHolder(Context context, ViewGroup parent,int layoutId){
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new RViewHolder(itemView);
    }

    public View getConvertView(){
        return mConvertView;
    }

    public <T extends  View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }
}
