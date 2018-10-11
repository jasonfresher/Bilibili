package com.bilibili.live.player.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.player.ui.VideoPlayerActivity;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;


/**
 * Created by jason on 2018/10/11.
 */

public class PlayerComponent implements IComponent {
    @Override
    public String getName() {
        return RouteInfo.PLAYER_COMPONENT_NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        Context context = cc.getContext();
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        String playUrl = cc.getParamItem("playUrl");
        String title = cc.getParamItem("title");
        intent.putExtra("playUrl",playUrl);
        intent.putExtra("title",title);
        if (!(context instanceof Activity)) {
            //调用方没有设置context或app间组件跳转，context为application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        CC.sendCCResult(cc.getCallId(), CCResult.success());
        return false;
    }
}
