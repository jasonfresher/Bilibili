package com.bilibili.live.bangumi.components;

import com.bilibili.live.bangumi.ui.BangumiRecommendFragment;
import com.bilibili.live.base.constants.RouteInfo;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;

/**
 * Created by jason on 2018/9/25.
 */

public class BangumiComponent implements IComponent {

    @Override
    public String getName() {
        return RouteInfo.BANGUMI_COMPONENT_NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String callId = cc.getCallId();
        CCResult ccResult = CCResult.success(RouteInfo.BANGUMI_COMPONENT_NAME, BangumiRecommendFragment.newInstance(true));
        CC.sendCCResult(callId,ccResult);
        return false;
    }
}
