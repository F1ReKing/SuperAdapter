/*
 * Copyright 2018 F1ReKing. https://github.com/f1reking
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.f1reking.adapter;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：SuperAdapter同时支持RecyclerView和ListView
 *
 * @author F1ReKing
 * @time 2018/5/2
 */
public abstract class SuperAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mData;
    private int mLayoutId;
    private SuperMultiSupport<T> mMultiSupport;
    private boolean isRecycler;
    private int mPosition;

    public SuperAdapter(Context context, List<T> data, int layoutId) {
        mContext = context;
        mData = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        mLayoutId = layoutId;
    }

    public SuperAdapter(Context context, List<T> data, SuperMultiSupport<T> multiSupport) {
        this(context, data, 0);
        mMultiSupport = multiSupport;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SuperViewHolder holder;
        if (convertView == null) {
            int layoutId = mLayoutId;
            if (mMultiSupport != null) {
                layoutId = mMultiSupport.getLayoutId(mData.get(position));
            }
            holder = createListHolder(parent, layoutId);
        } else {
            holder = (SuperViewHolder) convertView.getTag();
            if (mMultiSupport != null) {
                int layoutId = mMultiSupport.getLayoutId(mData.get(position));
                if (layoutId != holder.getLayoutId()) {
                    holder = createListHolder(parent, layoutId);
                }
            }
        }
        convert(holder, mData.get(position), position);
        return holder.itemView;
    }

    @Override
    public int getViewTypeCount() {
        if (mMultiSupport != null) {
            return mMultiSupport.getViewTypeCount() + super.getViewTypeCount();
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        mPosition = position;
        if (mMultiSupport != null) {
            return mMultiSupport.getItemViewType(mData.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    //ListView
    private SuperViewHolder createListHolder(ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        SuperViewHolder holder = new SuperViewHolder(itemView, layoutId);
        itemView.setTag(holder);
        return holder;
    }

    //RecyclerView
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        isRecycler = true;
        View view;
        if (mMultiSupport != null) {
            int layoutId = mMultiSupport.getLayoutId(mData.get(mPosition));
            view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        }
        SuperViewHolder holder = new SuperViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SuperViewHolder) {
            convert((SuperViewHolder) holder, mData.get(position), position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (mMultiSupport == null || recyclerView == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mMultiSupport.isSpan(mData.get(position))) {
                        return gridLayoutManager.getSpanCount();
                    } else if (spanSizeLookup != null) {
                        return spanSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (mMultiSupport == null) {
            return;
        }
        int position = holder.getLayoutPosition();
        if (mMultiSupport.isSpan(mData.get(position))) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) lp;
                params.setFullSpan(true);
            }
        }
    }

    protected abstract void convert(SuperViewHolder holder, T item, int position);

    ///////////////////////////////////////////////////////////////////////////
    // 数据操作
    ///////////////////////////////////////////////////////////////////////////
    public void add(T e) {
        mData.add(e);
        notifyData();
    }

    public void addAll(List<T> data) {
        mData.addAll(data);
        notifyData();
    }

    public void addFirst(T e) {
        mData.add(0, e);
        notifyData();
    }

    public void set(T oldE, T newE) {
        set(mData.indexOf(oldE), newE);
        notifyData();
    }

    public void set(int index, T e) {
        mData.set(index, e);
        notify();
    }

    public void remove(T e) {
        mData.remove(e);
        notifyData();
    }

    public void remove(int index) {
        mData.remove(index);
        notifyData();
    }

    public void replaceAll(List<T> e) {
        mData.clear();
        mData.addAll(e);
        notifyData();
    }

    public void clear() {
        mData.clear();
        notifyData();
    }

    public List<T> getData() {
        return mData;
    }

    private void notifyData() {
        if (isRecycler) {
            notifyDataSetChanged();
        } else {
            notifyListDataSetChanged();
        }
    }
}
