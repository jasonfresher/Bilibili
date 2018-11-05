package com.bilibili.live.browser.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.browser.ui.BrowserActivity;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;

/**
 * Created by jason on 2018/11/5.
 */

public class BrowserComponent implements IComponent {

    @Override
    public String getName() {
        return RouteInfo.BROWSER_COMPONENT_NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        Context context = cc.getContext();
        Intent intent = new Intent(context, BrowserActivity.class);
        String url = cc.getParamItem(ParamsConstant.EXTRA_URL);
        String title = cc.getParamItem(ParamsConstant.EXTRA_TITLE);
        intent.putExtra(ParamsConstant.EXTRA_URL,url);
        intent.putExtra(ParamsConstant.EXTRA_TITLE,title);
        if (!(context instanceof Activity)) {
            //调用方没有设置context或app间组件跳转，context为application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        CC.sendCCResult(cc.getCallId(), CCResult.success());
        return false;
    }
}
