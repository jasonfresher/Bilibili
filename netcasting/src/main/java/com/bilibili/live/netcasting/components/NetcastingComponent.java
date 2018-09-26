package com.bilibili.live.netcasting.components;

import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.netcasting.ui.NetcastingFragment;
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
        CCResult ccResult = CCResult.success("netcasting", NetcastingFragment.newInstance(true));
        CC.sendCCResult(callId,ccResult);
        return false;
    }
}
