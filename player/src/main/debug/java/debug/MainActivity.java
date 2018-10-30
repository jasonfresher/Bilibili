package debug;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.player.R;
import com.billy.cc.core.component.CC;

import java.io.File;

import okhttp3.OkHttpClient;


/**
 * Created by jason on 2018/9/17.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + File.separator + "1.mp4";
//        System.out.println("@@@===>" + videoPath);
        CC.obtainBuilder(RouteInfo.PLAYER_COMPONENT_NAME)
                .addParam("playUrl",
                        videoPath
//                        "http://ivi.bupt.edu.cn/hls/btv1hd.m3u8"
//                        "http://221.228.226.23/11/t/j/v/b/tjvbwspwhqdmgouolposcsfafpedmb/sh.yinyuetai.com/691201536EE4912BF7E4F1E2C67B8119.mp4"
//                        "http://221.228.226.5/15/t/s/h/v/tshvhsxwkbjlipfohhamjkraxuknsc/sh.yinyuetai.com/88DC015DB03C829C2126EEBBB5A887CB.mp4"
                        )
                .addParam("hardDecode",true)
                .addParam("title","debug测试视频")
                .build().call();
    }
}
