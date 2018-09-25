package com.bilibili.live.netcasting.components;

import com.bilibili.live.base.constants.RouteInfo;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;

/**
 * Created by jason on 2018/9/25.
 */

public class NetcastingComponent implements IComponent {

    @Override
    public String getName() {
        return RouteInfo.NETCASTING_COMPONENT_NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String callId = cc.getCallId();
        CCResult ccResult = CCResult.success("recommend", new NetcastingComponent());
        CC.sendCCResult(callId,ccResult);
        return false;
    }
}
