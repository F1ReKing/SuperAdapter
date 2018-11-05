package com.f1reking.adapter_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.f1reking.adapter.SuperAdapter;
import com.f1reking.adapter.SuperMultiSupport;
import com.f1reking.adapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author F1ReKing
 * @time 2018/5/2
 */
public class MainActivity extends AppCompatActivity {

    private List<String> dataList = new ArrayList<>();
    private android.support.v7.widget.RecyclerView rvlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.rvlist = (RecyclerView) findViewById(R.id.rv_list);
        initView();
    }

    private void initView() {

        for (int i = 0; i < 10; i++) {
            dataList.add("数据" + i);
        }


        rvlist.setLayoutManager(new LinearLayoutManager(this));
        rvlist.setAdapter(new SuperAdapter<String>(this, dataList, new MultiAdapter()) {
            @Override
            protected void convert(SuperViewHolder holder, String item, int position) {
                holder.setText(R.id.tv_item, item);

            }

        });
    }

    private class MultiAdapter implements SuperMultiSupport<String> {

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getLayoutId(String data) {

            if (data.contains("1")) {
                return R.layout.item_header;
            }
            return R.layout.item_recycler;
        }

        @Override
        public int getItemViewType(String data) {
            return 1;
        }

        @Override
        public boolean isSpan(String data) {
            return false;
        }
    }
}
