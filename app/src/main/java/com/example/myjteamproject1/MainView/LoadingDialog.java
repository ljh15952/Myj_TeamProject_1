package com.example.myjteamproject1.MainView;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.example.myjtest.R;

public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context)
    {
        super(context);
        // 다이얼 로그 제목을 안보이게...
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_progress);
    }
}
