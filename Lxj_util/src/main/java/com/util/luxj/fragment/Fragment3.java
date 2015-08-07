package com.util.luxj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.util.luxj.R;
import com.util.luxj.R;
/**
 * Created by Luxj on 2015/8/7 15:23
 */
public class Fragment3 extends Fragment {
    private View view;

    public static Fragment3 newInstance(String s) {
        Fragment3 newFragment = new Fragment3();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.tab1, null);
        Bundle args = getArguments();
        if (args != null) {
            TextView textView = (TextView) view.findViewById(R.id.te);
            textView.setText(args.getString("hello"));
        }
        return view;
    }
}
