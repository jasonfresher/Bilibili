package com.bilibili.live.recommend.components;

import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.recommend.ui.RecommendFragment;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;

/**
 * Created by jason on 2018/9/14.
 */

public class RecommendComponent implements IComponent {
    @Override
    public String getName() {
        return RouteInfo.RECOMMEND_COMPONENT_NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String callId = cc.getCallId();
        CCResult ccResult = CCResult.success(RouteInfo.RECOMMEND_COMPONENT_NAME, new RecommendFragment());
        CC.sendCCResult(callId,ccResult);
        return false;
    }
}
