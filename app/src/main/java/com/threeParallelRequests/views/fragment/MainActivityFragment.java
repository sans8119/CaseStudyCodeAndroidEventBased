package com.threeParallelRequests.views.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.threeParallelRequests.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivityFragment extends Fragment {
    @Bind(R.id.textView)
    TextView textView;
    public String data="no data";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        if(textView!=null)
        textView.setText(String.valueOf(data));
        return rootView;
    }

    public void update(String data){
        if(textView!=null) {
            textView.setText(data);
            textView.invalidate();
        }
    }

}

