package com.hahafather007.ringview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        list.add("http://a.hiphotos.baidu.com/image/h%3D300/sign=d569af264b10b912a0c1f0fef3fcfcb" +
                "5/42a98226cffc1e1792fa64ac4690f603728de9e2.jpg");
        list.add("http://h.hiphotos.baidu.com/image/h%3D300/sign=06d6aa88ca5c10383b7ec8c28210931c/" +
                "2cf5e0fe9925bc31b3e6ab0a52df8db1ca1370ed.jpg");
        list.add("http://f.hiphotos.baidu.com/image/h%3D300/sign=231aa19fba003af352bada60052bc619/" +
                "b58f8c5494eef01f2fe05953ecfe9925bd317dab.jpg");

        ringView.updateView(list);
        ringView.startRing(true);
        ringView.setPhotoClickListener(new RingView.OnPhotoClickListener() {
            @Override
            public void click(int position, String url) {
                Logger.i("Click position=== " + position + " Url=== " + url);
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
