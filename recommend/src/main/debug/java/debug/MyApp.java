package debug;

import com.bilibili.live.base.application.BaseApplication;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

/**
 * @author billy.qi
 * @since 17/11/20 20:02
 */
public class MyApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinCompatManager.withoutActivity(this)
                .addInflater(new SkinMaterialViewInflater())
                .addInflater(new SkinCardViewInflater())
                .setSkinStatusBarColorEnable(true)
                .setSkinWindowBackgroundEnable(true)
                .loadSkin();
        SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
//        SkinCompatManager.getInstance().restoreDefaultTheme();
    }
}
