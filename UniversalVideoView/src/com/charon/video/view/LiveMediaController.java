
package com.charon.video.view;

import com.charon.video.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

public class LiveMediaController extends MediaController {

    public LiveMediaController(Context context) {
        super(context);
    }

    public LiveMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initControllerView(View v) {
        super.initControllerView(v);
        if (v == null) {
            return;
        }
        SeekBar sb = (SeekBar) v.findViewById(R.id.mediacontroller_seekbar);
        if (sb != null) {
            sb.setVisibility(View.INVISIBLE);
        }
    }
}
