package com.bilibili.live.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.base.utils.ThemeHelper;
import com.bilibili.live.base.widget.CardPickerDialog;
import com.bilibili.magicasakura.utils.ThemeUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity基类
 */
public abstract class RxBaseActivity<V,P extends BasePresenter<V>> extends RxAppCompatActivity
        implements CardPickerDialog.ClickListener {

    protected Unbinder bind;

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());
        //创建Presenter
        mPresenter = createPresenter();
        //关联View
        if(mPresenter != null)
            mPresenter.attachView((V) this);

        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
    }

    protected abstract P createPresenter();

    protected abstract int getLayoutId();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initToolBar();

    @Override
    public void onConfirm(int currentTheme) {
        if (ThemeHelper.getTheme(RxBaseActivity.this) != currentTheme) {
            ThemeHelper.setTheme(RxBaseActivity.this, currentTheme);
            ThemeUtils.refreshUI(RxBaseActivity.this, new ThemeUtils.ExtraRefreshable() {
                        @Override
                        public void refreshGlobal(Activity activity) {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                final RxBaseActivity context = RxBaseActivity.this;
                                ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(
                                        null,
                                        null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
                                setTaskDescription(taskDescription);
                                getWindow().setStatusBarColor(ThemeUtils.getColorById(context,
                                        R.color.theme_color_primary_dark));
                            }
                        }
                        @Override
                        public void refreshSpecificView(View view) {

                        }
                    }
            );
        }
    }


    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ThemeUtils.getColorById(this, R.color.theme_color_primary_dark));
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, null,
                    ThemeUtils.getThemeAttrColor(this, android.R.attr.colorPrimary));
            setTaskDescription(description);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bind != null)
            bind.unbind();
        if(mPresenter != null)
            mPresenter.detachView();
    }
}
