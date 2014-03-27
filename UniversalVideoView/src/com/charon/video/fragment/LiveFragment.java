
package com.charon.video.fragment;

import com.charon.video.R;
import com.charon.video.UniversalVideoViewActivity;
import com.charon.video.domain.WebSite;
import com.charon.video.util.WebsiteUtil;

import android.content.Context;
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

public class LiveFragment extends Fragment {
    private ListView mListView;
    private List<WebSite> mLiveSites;
    private LiveAdapter mAdapter;
    private Context mContext;

    public static LiveFragment getInstance() {
        LiveFragment liveFragment = new LiveFragment();
        return liveFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_live, null);
        findView(view);
        initView();
        return view;
    }

    private void findView(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_list_live);
    }

    private void initView() {
        mContext = getActivity();
        mLiveSites = new ArrayList<WebSite>();
        mAdapter = new LiveAdapter();
        mLiveSites = WebsiteUtil.addLivesite(mLiveSites);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, UniversalVideoViewActivity.class);
                intent.putExtra(OnlineFragment.URL, mLiveSites.get(position).getUrl());
                intent.putExtra(OnlineFragment.LOCAL, false);
                startActivity(intent);
            }
        });
    }

    private class LiveAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mLiveSites != null) {
                return mLiveSites.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mLiveSites == null) {
                return null;
            }
            return mLiveSites.get(position);
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
                final LayoutInflater mInflater = LiveFragment.this.getActivity()
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
            WebSite webSite = mLiveSites.get(position);
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
