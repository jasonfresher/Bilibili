package debug;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.details.ui.VideoDetailsActivity;
import com.billy.cc.core.component.CC;


/**
 * Created by jason on 2018/9/17.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CC.obtainBuilder(RouteInfo.VIDEODETAILS_COMPONENT_NAME)
                .addParam("extra_av",Integer.parseInt("12"))
                .addParam("extra_img_url","111")
                .build()
                .call();
//        Intent intent = new Intent(this,VideoDetailsActivity.class);
//        intent.putExtra("extra_av", 34993707);
//        intent.putExtra("extra_img_url","http://i2.hdslb.com/bfs/archive/87cfb8e6756159e190d789448bdc512c17413382.jpg");
//        startActivity(intent);
    }
}
