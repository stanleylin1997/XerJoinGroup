package com.example.stanley.xerjoingroup.Activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.stanley.xerjoingroup.FakeAdapter.AddgroupFakeAdapter;
import com.example.stanley.xerjoingroup.R;
import com.github.florent37.bubbletab.BubbleTab;

import butterknife.Bind;
import butterknife.ButterKnife;

public class JFGroupActivity extends AppCompatActivity {
    @Bind(R.id.bubbleTab)
    BubbleTab bubbleTab;
    @Bind(R.id.viewPager)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bubbletab_group);
        ButterKnife.bind(this);



        viewPager.setAdapter(new AddgroupFakeAdapter(getSupportFragmentManager()));
        bubbleTab.setupWithViewPager(viewPager);
    }
}
