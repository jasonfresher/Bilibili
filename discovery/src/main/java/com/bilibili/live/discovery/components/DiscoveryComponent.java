package com.bilibili.live.discovery.components;

import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.discovery.ui.DiscoveryFragment;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;

import static com.bilibili.live.base.constants.RouteInfo.DISCOVERY_COMPONENT_NAME;

/**
 * Created by jason on 2018/11/28.
 */

public class DiscoveryComponent implements IComponent {

    @Override
    public String getName() {
        return DISCOVERY_COMPONENT_NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String callId = cc.getCallId();
        CCResult ccResult = CCResult.success(RouteInfo.DISCOVERY_COMPONENT_NAME, DiscoveryFragment.newInstance(true));
        CC.sendCCResult(callId,ccResult);
        return false;
    }
}
