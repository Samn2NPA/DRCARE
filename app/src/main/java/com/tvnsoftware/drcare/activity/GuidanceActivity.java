package com.tvnsoftware.drcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.adapter.GuiAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuidanceActivity extends AppCompatActivity {

    private final String TAG = "GuidanceActivity";
    private int mPagerPointer = 0;
    @BindView(R.id.pager_gui)
    ViewPager mPagerGui;

    @BindView(R.id.indicator_gui)
    CirclePageIndicator circlePageIndicator;

    @BindView(R.id.but_skip)
    Button mBtnSkip;

    @BindView(R.id.but_next)
    Button mButNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance);
        ButterKnife.bind(GuidanceActivity.this);
        GuiAdapter guiAdapter = new GuiAdapter(getSupportFragmentManager());
        mPagerGui.setAdapter(guiAdapter);

        circlePageIndicator.setViewPager(mPagerGui);
        ContextCompat.getColor(this, R.color.mdtp_white);

        mPagerGui.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    mButNext.setText(getString(R.string.label_but_start));
                    mBtnSkip.setVisibility(View.INVISIBLE);
                } else {
                    mButNext.setText(getString(R.string.label_next));
                    mBtnSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuidanceActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mButNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPagerGui.getCurrentItem() == 1) {
                    Intent intent = new Intent(GuidanceActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    mPagerPointer = mPagerGui.getCurrentItem() + 1;
                    mPagerGui.setCurrentItem(mPagerPointer);
                }
            }
        });
    }
}
