package com.util.luxj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.util.luxj.R;

/**
 * Created by Luxj on 2015/8/7 15:23
 */
public class Fragment2 extends Fragment implements View.OnClickListener {
    private View view;
    private MyFragment_Activity activity;

    public static Fragment2 newInstance(String s) {
        Fragment2 newFragment = new Fragment2();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.tab2, null);
        Bundle args = getArguments();
        if (args != null) {
            TextView textView = (TextView) view.findViewById(R.id.te);
            textView.setText(args.getString("hello"));
        }
        activity = MyFragment_Activity.newInstance();
        view.findViewById(R.id.tz).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tz:
                activity.selectPage(4);

                break;
        }
    }
}
