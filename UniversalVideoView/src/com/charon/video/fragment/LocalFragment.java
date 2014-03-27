
package com.charon.video.fragment;

import com.charon.video.R;
import com.charon.video.UniversalVideoViewActivity;
import com.charon.video.VideoViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class LocalFragment extends Fragment {

    public static LocalFragment getInstance() {
        LocalFragment localFragment = new LocalFragment();
        return localFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_local, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        Button button = (Button) view.findViewById(R.id.bt_go_play);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocalFragment.this.getActivity(),
                        VideoViewActivity.class);
                intent.putExtra(OnlineFragment.LOCAL, true);
                intent.putExtra(OnlineFragment.URL,
                        "http://3gs.ifeng.com/userfiles/video01/2014/03/15/1769126-280-068-1442.mp4");
                startActivity(intent);
            }
        });
    }
}
