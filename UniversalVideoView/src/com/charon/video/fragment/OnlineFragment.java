
package com.charon.video.fragment;

import com.charon.video.R;
import com.charon.video.WebActivity;
import com.charon.video.domain.WebSite;
import com.charon.video.util.WebsiteUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OnlineFragment extends Fragment {

    public static OnlineFragment getInstance() {
        OnlineFragment fragment = new OnlineFragment();
        return fragment;
    }

    private ListView mListView;
    private WebsiteAdapter mAdapter;
    private List<WebSite> mWebsiteList;
    public static final String URL = "url";
    public static final String LOCAL = "local";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fr_online, null);
        findView(view);
        initView();
        return view;
    }

    private void findView(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_list_online);
    }

    private void initView() {
        mWebsiteList = new ArrayList<WebSite>();
        mWebsiteList = WebsiteUtil.addWebsite(mWebsiteList);
        mAdapter = new WebsiteAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra(URL, mWebsiteList.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    private class WebsiteAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mWebsiteList != null) {
                return mWebsiteList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mWebsiteList == null) {
                return null;
            }
            return mWebsiteList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                final LayoutInflater mInflater = OnlineFragment.this.getActivity()
                        .getLayoutInflater();
                view = mInflater.inflate(R.layout.item_online, null, false);
                holder = new ViewHolder();
                holder.iv_cover = (ImageView) view.findViewById(R.id.iv_cover);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            WebSite webSite = mWebsiteList.get(position);
            holder.iv_cover.setImageResource(webSite.getResourceID());
            holder.tv_name.setText(webSite.getTitle());

            return view;
        }
    }

    static class ViewHolder {
        ImageView iv_cover;
        TextView tv_name;
    }
}
