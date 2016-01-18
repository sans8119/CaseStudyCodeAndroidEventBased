package com.threeParallelRequests.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.threeParallelRequests.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ErrorActivity extends AppCompatActivity {
    @Bind(R.id.error_text)
    TextView errorText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        errorText.setText(getIntent().getStringExtra("error"));
    }
}
