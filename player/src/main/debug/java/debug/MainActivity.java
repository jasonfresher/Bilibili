package debug;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.player.R;
import com.billy.cc.core.component.CC;

import java.io.File;


/**
 * Created by jason on 2018/9/17.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
//        String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + File.separator + "1.mp4";
//        System.out.println("@@@===>" + videoPath);
        CC.obtainBuilder(RouteInfo.PLAYER_COMPONENT_NAME)
                .addParam("playUrl",
                        //videoPath
                        "http://ivi.bupt.edu.cn/hls/btv1hd.m3u8"
                        )
                .addParam("title","debug测试视频")
                .build().call();
    }
}
