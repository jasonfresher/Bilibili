package debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.constants.RouteInfo;
import com.billy.cc.core.component.CC;


/**
 * Created by jason on 2018/9/17.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CC.obtainBuilder(RouteInfo.BROWSER_COMPONENT_NAME)
                .addParam(ParamsConstant.EXTRA_URL,"http://www.baidu.com")
                .addParam(ParamsConstant.EXTRA_TITLE,"百度")
                .build()
                .call();
//        Intent intent = new Intent(this,VideoDetailsActivity.class);
//        intent.putExtra("extra_av", 34993707);
//        intent.putExtra("extra_img_url","http://i2.hdslb.com/bfs/archive/87cfb8e6756159e190d789448bdc512c17413382.jpg");
//        startActivity(intent);
    }
}
