package com.hahafather007.ringview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.hahafather007.ringview.utils.Logger;
import com.hahafather007.ringview.view.RingView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RingView ringView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ringView = findViewById(R.id.ring_view);
        initView();
    }

    /**
     * 不要忘记Activity结束时释放资源
     */
    @Override
    protected void onDestroy() {
        ringView.release();

        super.onDestroy();
    }

    private void initView() {
        List<Object> list = new ArrayList<>();
        list.add("https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/" +
                "sign=4f7bf38ac3fc1e17fdbf8b3772ab913e/d4628535e5dde7119c3d076aabefce1b9c1661ba.jpg");
        list.add(LayoutInflater.from(this).inflate(R.layout.item_ring_view,null));

        ringView.updateView(list);
        ringView.startRing(true);
        ringView.setPhotoTouchListener(new RingView.OnPhotoTouchListener() {
            @Override
            public void click(int position, String url) {
                Logger.i("Click position=== " + position + " Url=== " + url);
            }

            @Override
            public void touch(View v, MotionEvent event) {
                Logger.i("MotionEvent===" + event.toString());
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringView.setRingTime(1000);
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringView.setRingTime(5000);
            }
        });
    }
}
