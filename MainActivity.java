package com.fajuary.archeryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;

import com.fajuary.archeryapp.view.ElectionLayout;

public class MainActivity extends AppCompatActivity {

    private ElectionLayout mElectionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView(){
        mElectionLayout=findViewById(R.id.activity_main_mElectionLayout);
    }

    public void btnClick(View view){
        mElectionLayout.reset();
        final SparseIntArray left = new SparseIntArray(4);
        final SparseIntArray right = new SparseIntArray(4);

        left.put(1, 1);
        left.put(2, 2);
        left.put(3, 3);
        left.put(4, 4);

        right.put(1, 1);
        right.put(2, 2);
        right.put(3, 3);
        right.put(4, 4);


        mElectionLayout.election(left, right);


    }

}
