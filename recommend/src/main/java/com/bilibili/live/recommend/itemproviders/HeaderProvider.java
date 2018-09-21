package com.bilibili.live.recommend.itemproviders;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.bilibili.live.recommend.R;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.entity.RecommendEntity;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

public class HeaderProvider extends BaseItemProvider<RecommendInfo.ResultBean.HeadBean,BaseViewHolder> {


    private static final String TYPE_RECOMMENDED = "recommend";

    private static final String TYPE_LIVE = "live";

    private int[] icons = new int[] {
            R.drawable.ic_header_hot, R.drawable.ic_head_live,
            R.drawable.ic_category_t13, R.drawable.ic_category_t1,
            R.drawable.ic_category_t3, R.drawable.ic_category_t129,
            R.drawable.ic_category_t4, R.drawable.ic_category_t119,
            R.drawable.ic_category_t36, R.drawable.ic_category_t160,
            R.drawable.ic_category_t155, R.drawable.ic_category_t5,
            R.drawable.ic_category_t11, R.drawable.ic_category_t23
    };


    @Override
    public int viewType() {
        return RecommendEntity.VIEW_TYPE_HEADER;
    }

    @Override
    public int layout() {
        return R.layout.recommend_item_header_layout;
    }

    @Override
    public void convert(BaseViewHolder helper, RecommendInfo.ResultBean.HeadBean data, int position) {
        setTypeIcon(helper,data.getTitle());
        helper.setText(R.id.item_type_tv, data.getTitle());
        switch (data.getTitleType()) {
            case TYPE_RECOMMENDED:
                helper.setVisible(R.id.item_type_more,false);
                helper.setVisible(R.id.item_type_rank_btn,true);
                helper.setVisible(R.id.item_live_all_num,false);
                break;
            case TYPE_LIVE:
                helper.setVisible(R.id.item_type_rank_btn,false);
                helper.setVisible(R.id.item_type_more,true);
                helper.setVisible(R.id.item_live_all_num,true);
                SpannableStringBuilder stringBuilder = new SpannableStringBuilder("当前" + data.getCount() + "个直播");
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
                        mContext.getResources().getColor(R.color.pink_text_color));
                stringBuilder.setSpan(foregroundColorSpan, 2,
                        stringBuilder.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                helper.setText(R.id.item_live_all_num,stringBuilder);
                break;
            default:
                helper.setVisible(R.id.item_type_rank_btn,false);
                helper.setVisible(R.id.item_type_more,true);
                helper.setVisible(R.id.item_live_all_num,false);
                break;
        }

    }

    private void setTypeIcon(BaseViewHolder helper,String title) {
        switch (title) {
            case "热门焦点":
                helper.setImageResource(R.id.item_type_img, icons[0]);
                break;
            case "正在直播":
                helper.setImageResource(R.id.item_type_img, icons[1]);
                break;
            case "番剧区":
                helper.setImageResource(R.id.item_type_img, icons[2]);
                break;
            case "动画区":
                helper.setImageResource(R.id.item_type_img, icons[3]);
                break;
            case "音乐区":
                helper.setImageResource(R.id.item_type_img, icons[4]);
                break;
            case "舞蹈区":
                helper.setImageResource(R.id.item_type_img, icons[5]);
                break;
            case "游戏区":
                helper.setImageResource(R.id.item_type_img, icons[6]);
                break;
            case "鬼畜区":
                helper.setImageResource(R.id.item_type_img, icons[7]);
                break;
            case "科技区":
                helper.setImageResource(R.id.item_type_img, icons[8]);
                break;
            case "生活区":
                helper.setImageResource(R.id.item_type_img, icons[9]);
                break;
            case "时尚区":
                helper.setImageResource(R.id.item_type_img, icons[10]);
                break;
            case "娱乐区":
                helper.setImageResource(R.id.item_type_img, icons[11]);
                break;
            case "电视剧区":
                helper.setImageResource(R.id.item_type_img, icons[12]);
                break;
            case "电影区":
                helper.setImageResource(R.id.item_type_img, icons[13]);
                break;
        }

    }
}
