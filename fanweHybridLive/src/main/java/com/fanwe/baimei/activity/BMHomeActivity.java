package com.fanwe.baimei.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.fanwe.baimei.appview.BMHomeBottomNavigationView;
import com.fanwe.baimei.dialog.BMLiveVerifyDialog;
import com.fanwe.baimei.fragment.BMGameCenterFragment;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.service.AppUpgradeHelper;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.event.EOnClick;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveCreateRoomActivity;
import com.fanwe.live.activity.LiveCreaterAgreementActivity;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.dialog.LevelLoginFirstDialog;
import com.fanwe.live.dialog.LevelUpgradeDialog;
import com.fanwe.live.event.EIMLoginError;
import com.fanwe.live.event.EImOnForceOffline;
import com.fanwe.live.fragment.LiveTabFollowFragment;
import com.fanwe.live.fragment.LiveTabLiveFragment;
import com.fanwe.live.fragment.LiveTabMeNewFragment;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.UserModel;

/**
 * 包名 com.fanwe.baimei.activity
 * 描述 百媚主页
 * 作者 Su
 * 创建时间 2017/5/15 9:07
 **/
public class BMHomeActivity extends BaseActivity
{
    private BMHomeBottomNavigationView mBottomNavigationView;
    private FrameLayout mBottomNavigationContainer;
    private BMLiveVerifyDialog dialogVerify;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.bm_act_home;
    }

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        mIsExitApp = true;

        dialogVerify = new BMLiveVerifyDialog(this);
        afterVerified();
        dialogVerify.show();
    }

    private void afterVerified()
    {
        setUpHome();

        checkUpdate();
        AppRuntimeWorker.startContext();
        CommonInterface.requestUser_apns(null);
        CommonInterface.requestMyUserInfo(new AppRequestCallback<App_userinfoActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                UserModel model=UserModelDao.query();
                dialogVerify.setTip(model!=null?model.getNick_name():"");
            }
        });

        checkVideo();

        initUpgradeDialog();
        initLoginFirstDialog();
    }

    private void setUpHome()
    {
        getBottomNavigationContainer().addView(getBottomNavigationView());
        clickTabLive();
    }

    private FrameLayout getBottomNavigationContainer()
    {
        if (mBottomNavigationContainer == null)
        {
            mBottomNavigationContainer = (FrameLayout) findViewById(R.id.fl_container_bottom_navigation);
        }
        return mBottomNavigationContainer;
    }

    private BMHomeBottomNavigationView getBottomNavigationView()
    {
        if (mBottomNavigationView == null)
        {
            mBottomNavigationView = new BMHomeBottomNavigationView(getActivity());
            mBottomNavigationView.setCallback(new BMHomeBottomNavigationView.BMHomeBottomNavigationViewCallback()
            {
                @Override
                public void onTabHomeSelected(View v, int index)
                {
                    clickTabLive();
                }

                @Override
                public void onTabGameSelected(View v, int index)
                {
                    clickTabGame();
                }

                @Override
                public void onTabAttentionSelected(View v, int index) {
                    clickTabAttention();
                }
                @Override
                public void onTabPersonSelected(View v, int index) {
                    clickTabMe();
                }

                @Override
                public void onLiveClick(View v)
                {
                    clickTabCreateLive();
                }
            });
        }
        return mBottomNavigationView;
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        initUpgradeDialog();
        initLoginFirstDialog();
    }

    private void initUpgradeDialog()
    {
        LevelUpgradeDialog.check(this);
    }

    //首次登陆奖励和升级提示
    private void initLoginFirstDialog()
    {
        LevelLoginFirstDialog.check(this);
    }

    private void checkUpdate()
    {
        new AppUpgradeHelper(this).check(0);
    }

    public void onEventMainThread(EIMLoginError event)
    {
        SDDialogConfirm dialog = new SDDialogConfirm(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTextContent("登录IM失败").setTextCancel("退出").setTextConfirm("重试");
        dialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
        {
            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {
                App.getApplication().logout(false);
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                AppRuntimeWorker.startContext();
            }

            @Override
            public void onDismiss(SDDialogCustom dialog)
            {
            }
        }).show();
    }
    public void onEventMainThread(EOnClick event)
    {
        if (R.id.tv_tab_live_follow_goto_live == event.view.getId()) {
            getBottomNavigationView().setTabSelected(0, true);
            clickTabLive();
        }
    }

    /**
     * 异地登录
     *
     * @param event
     */
    public void onEventMainThread(EImOnForceOffline event)
    {
        SDDialogConfirm dialog = new SDDialogConfirm(this);
        dialog.setTextContent("你的帐号在另一台手机上登录");
        dialog.setTextCancel("退出");
        dialog.setTextConfirm("重新登录");
        dialog.setmListener(new SDDialogCustom.SDDialogCustomListener() {

            @Override
            public void onDismiss(SDDialogCustom dialog) {
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog) {
                AppRuntimeWorker.startContext();
            }

            @Override
            public void onClickCancel(View v, SDDialogCustom dialog) {
                App.getApplication().logout(true);
            }
        });
        dialog.show();
    }

    private void clickTabCreateLive()
    {
        if (AppRuntimeWorker.isLogin(this))
        {
            final UserModel userModel = UserModelDao.query();
            if (userModel.getIs_agree() == 1)
            {
                Intent intent = new Intent(this, LiveCreateRoomActivity.class);
                startActivity(intent);
            } else
            {
                Intent intent = new Intent(this, LiveCreaterAgreementActivity.class);
                startActivity(intent);
            }
        }
    }

    private void clickTabLive()
    {
        getSDFragmentManager().toggle(R.id.fl_container_content, null, LiveTabLiveFragment.class);
    }

    private void clickTabGame()
    {
        getSDFragmentManager().toggle(R.id.fl_container_content, null, BMGameCenterFragment.class);
    }
    private void clickTabMe()
{
    getSDFragmentManager().toggle(R.id.fl_container_content, null, LiveTabMeNewFragment.class);
}
    private void clickTabAttention()
    {
        getSDFragmentManager().toggle(R.id.fl_container_content, null, LiveTabFollowFragment.class);
    }
}
