package com.bilibili.live.player.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.constants.RouteActionName;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.player.ui.LivePlayerActivity;
import com.bilibili.live.player.ui.VideoPlayerActivity;
import com.bilibili.live.player.ui.VideoPlayerFragment;
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
        String actionName = cc.getActionName();
        String callId = cc.getCallId();
        if(RouteActionName.MINI_PLAYER_FRAGMENT.equals(actionName)){
            String playUrl = cc.getParamItem(ParamsConstant.EXTRA_PLAYER_URL);
            String title = cc.getParamItem(ParamsConstant.EXTRA_PLAYER_TITLE);
            boolean hardDecode = cc.getParamItem(ParamsConstant.EXTRA_PLAYER_HARDDECODE);
            VideoPlayerFragment playerFragment = VideoPlayerFragment.newInstance(true, playUrl, title, hardDecode);
            CCResult ccResult = CCResult.success(RouteInfo.PLAYER_COMPONENT_NAME, playerFragment);
            CC.sendCCResult(callId,ccResult);
        }else if(RouteActionName.NORMAL_PLAYER.equals(actionName)){
            Context context = cc.getContext();
            Intent intent = new Intent(context, VideoPlayerActivity.class);
            String playUrl = cc.getParamItem(ParamsConstant.EXTRA_PLAYER_URL);
            String title = cc.getParamItem(ParamsConstant.EXTRA_PLAYER_TITLE);
            boolean hardDecode = cc.getParamItem(ParamsConstant.EXTRA_PLAYER_HARDDECODE);
            intent.putExtra(ParamsConstant.EXTRA_PLAYER_URL,playUrl);
            intent.putExtra(ParamsConstant.EXTRA_PLAYER_TITLE,title);
            intent.putExtra(ParamsConstant.EXTRA_PLAYER_HARDDECODE,hardDecode);
            if (!(context instanceof Activity)) {
                //调用方没有设置context或app间组件跳转，context为application
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
            CC.sendCCResult(cc.getCallId(), CCResult.success());
        }else if(RouteActionName.LIVE_PLAYER.equals(actionName)){
            Context context = cc.getContext();
            Intent intent = new Intent(context, LivePlayerActivity.class);

            String playUrl = cc.getParamItem(ParamsConstant.EXTRA_PLAYER_URL);
            String title = cc.getParamItem(ParamsConstant.EXTRA_TITLE);
            boolean hardDecode = cc.getParamItem(ParamsConstant.EXTRA_PLAYER_HARDDECODE);
            int cid = cc.getParamItem(ParamsConstant.EXTRA_CID);
            int online = cc.getParamItem(ParamsConstant.EXTRA_ONLINE);
            String face = cc.getParamItem(ParamsConstant.EXTRA_FACE);
            String name = cc.getParamItem(ParamsConstant.EXTRA_NAME);
            int mid = cc.getParamItem(ParamsConstant.EXTRA_MID);

            intent.putExtra(ParamsConstant.EXTRA_PLAYER_URL,playUrl);
            intent.putExtra(ParamsConstant.EXTRA_TITLE,title);
            intent.putExtra(ParamsConstant.EXTRA_PLAYER_HARDDECODE,hardDecode);
            intent.putExtra(ParamsConstant.EXTRA_CID,cid);
            intent.putExtra(ParamsConstant.EXTRA_ONLINE,online);
            intent.putExtra(ParamsConstant.EXTRA_FACE,face);
            intent.putExtra(ParamsConstant.EXTRA_NAME,name);
            intent.putExtra(ParamsConstant.EXTRA_MID,mid);

            if (!(context instanceof Activity)) {
                //调用方没有设置context或app间组件跳转，context为application
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
            CC.sendCCResult(cc.getCallId(), CCResult.success());
        }
        return false;
    }
}
