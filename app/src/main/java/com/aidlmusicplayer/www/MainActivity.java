package com.aidlmusicplayer.www;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.aidlmusicplayer.www.bean.PaySongBean;
import com.aidlmusicplayer.www.net.NetCallBack;
import com.aidlmusicplayer.www.net.NetHelper;
import com.aidlmusicplayer.www.net.NetManager;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> commonParams = NetHelper.getMusicApiCommonParams("baidu.ting.song.playAAC");
        commonParams.put("songid", "877578");

       final TextView tv = (TextView) findViewById(R.id.tv);

        NetManager.
                getInstance().
                getPayMusicData("877578", new NetCallBack<PaySongBean>() {
            @Override
            public void onSuccess(PaySongBean payMusicDataBean) {
                tv.setText(payMusicDataBean.bitrate.file_link);
            }

            @Override
            public void onFailure(String msg) {
                tv.setText(msg);
            }

            @Override
            public void onFinish() {

            }
        });


    }


}
