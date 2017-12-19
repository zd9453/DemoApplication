package com.example.zd.demoapplication.view;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zd.demoapplication.R;

/**
 *
 * Created by zhangdong on 2017/12/12 0012.
 */

public class DialogTest extends DialogFragment {

    private static volatile DialogTest mDialog = null;

    private DialogTest() {
    }

    public static DialogTest newInstance() {
        if (mDialog == null) {
            synchronized (DialogTest.class) {
                mDialog = new DialogTest();
                Bundle bundle = new Bundle();
                mDialog.setArguments(bundle);
            }
        }
        return mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_constraint_layout1, container, false);
        return inflate;
    }
}
