package com.bilibili.live.region.components;

import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.region.ui.RegionFragment;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;

/**
 * Created by jason on 2018/10/15.
 */

public class RegionComponent implements IComponent {
    @Override
    public String getName() {
        return RouteInfo.REGION_COMPONENT_NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String callId = cc.getCallId();
        CCResult ccResult = CCResult.success(RouteInfo.REGION_COMPONENT_NAME, RegionFragment.newInstance(true));
        CC.sendCCResult(callId, ccResult);
        return false;
    }
}
