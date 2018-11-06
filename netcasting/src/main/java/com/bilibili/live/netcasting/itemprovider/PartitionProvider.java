package com.bilibili.live.netcasting.itemprovider;

import android.widget.ImageView;

import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.constants.RouteActionName;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.netcasting.R;
import com.bilibili.live.netcasting.bean.LiveAppIndexInfo;
import com.bilibili.live.netcasting.entity.NetcastingEntity;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * Created by jason on 2018/9/26.
 */

public class PartitionProvider extends BaseItemProvider<NetcastingEntity<LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean>,BaseViewHolder> {
    @Override
    public int viewType() {
        return NetcastingEntity.VIEW_TYPE_ITEM_LOADED;
    }

    @Override
    public int layout() {
        return R.layout.item_live_partition;
    }

    @Override
    public void convert(BaseViewHolder helper, NetcastingEntity<LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean> data, int position) {
        LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean livesBean = data.content;
        ImageView liveCover = helper.getView(R.id.item_live_cover);
        ImageView liveUserCover = helper.getView(R.id.item_live_user_cover);
        Glide.with(mContext)
                .load(livesBean.getCover().getSrc())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(liveCover);
        Glide.with(mContext)
                .load(livesBean.getOwner().getFace())
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(liveUserCover);
        helper.setText(R.id.item_live_title,livesBean.getTitle());
        helper.setText(R.id.item_live_user,livesBean.getOwner().getName());
        helper.setText(R.id.item_live_count,String.valueOf(livesBean.getOnline()));
    }

    @Override
    public void onClick(BaseViewHolder helper, NetcastingEntity<LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean> data, int position) {
        LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean content = data.content;
        String param = String.valueOf(content.getArea_id());
        String cover = content.getOwner().getFace();
        int mid = content.getOwner().getMid();
        String face = content.getOwner().getFace();
        String name = content.getOwner().getName();
        int online = content.getOnline();
        String title = content.getTitle();
        int cid = content.getRoom_id();
        boolean hardDecode = false;
        if(position%2 == 0){
            CC.obtainBuilder(RouteInfo.VIDEODETAILS_COMPONENT_NAME)
                    .addParam(ParamsConstant.EXTRA_AV, Integer.parseInt(param))
                    .addParam(ParamsConstant.EXTRA_IMG_URL, cover)
                    .build()
                    .call();
        }else{
            CC.obtainBuilder(RouteInfo.PLAYER_COMPONENT_NAME)
                    .addParam(ParamsConstant.EXTRA_MID,mid)
                    .addParam(ParamsConstant.EXTRA_CID,cid)
                    .addParam(ParamsConstant.EXTRA_TITLE,title)
                    .addParam(ParamsConstant.EXTRA_PLAYER_HARDDECODE,hardDecode)
                    .addParam(ParamsConstant.EXTRA_ONLINE,online)
                    .addParam(ParamsConstant.EXTRA_FACE,face)
                    .addParam(ParamsConstant.EXTRA_NAME,name)
                    .setActionName(RouteActionName.LIVE_PLAYER)
                    .build().call();
        }
    }
}
