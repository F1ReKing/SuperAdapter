package com.f1reking.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.List;

/**
 * @author HuangYH
 * @date 2018/11/6 17:38
 * @Description
 */
public class RecyclerAdapter<T> extends RecyclerView.Adapter<SuperViewHolder> {

    private static final String TAG = RecyclerAdapter.class.getSimpleName();

    protected Context mContext;
    protected List<T> mDatas;
    protected int mLayoutRedId;

    public RecyclerAdapter(Context context, List<T> datas, @LayoutRes int layoutRedId) {
        mContext = context;
        mDatas = datas;
        mLayoutRedId = layoutRedId;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
    }

    @NonNull
    @Override
    public SuperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final SuperViewHolder holder = new SuperViewHolder(parent,viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SuperViewHolder superViewHolder, int position) {
        int viewType = getItemViewType(position);

    }

    @Override
    public int getItemCount() {
        int size = mDatas == null ? 0 : mDatas.size();
        return size;
    }
}
