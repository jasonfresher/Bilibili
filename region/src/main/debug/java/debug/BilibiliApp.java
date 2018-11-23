package debug;

import com.bilibili.live.base.application.BaseApplication;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

public class BilibiliApp extends BaseApplication{

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
  }

}
