package com.fanwe.live.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.games.adapter.LiveCreaterPluginAdapter;
import com.fanwe.games.model.App_plugin_initActModel;
import com.fanwe.games.model.PluginModel;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.LiveCreaterPluginToolView;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.event.EUpdateUserInfo;

/**
 * 直播间主播插件窗口
 */
public class LiveCreaterPluginDialog extends LiveBaseDialog
{
    public LiveCreaterPluginDialog(Activity activity)
    {
        super(activity);
        init();
    }

    private LinearLayout ll_base_plugin;//基础插件
    private LiveCreaterPluginToolView view_plugin;
    private LinearLayout ll_plugins;
    private GridView gv_content;
    private TextView tv_account;
    private TextView tv_open_game;

    private LiveCreaterPluginAdapter mAdapter;
    private LiveRechargeDialog mLiveRechargeDialog;

    private int mSelectedPos;
    private PluginModel mPluginModel;

    private void init()
    {
        setContentView(R.layout.dialog_live_creater_plugin);
        paddings(SDViewUtil.dp2px(10));
        setCanceledOnTouchOutside(true);
        ll_base_plugin = (LinearLayout) findViewById(R.id.ll_base_plugin);
        gv_content = (GridView) findViewById(R.id.gv_content);
        ll_plugins = (LinearLayout) findViewById(R.id.ll_plugins);
        view_plugin = (LiveCreaterPluginToolView) findViewById(R.id.view_plugin);
        tv_account = (TextView) findViewById(R.id.tv_account);
        tv_account.setOnClickListener(this);
        tv_open_game = (TextView) findViewById(R.id.tv_open_game);
        tv_open_game.setOnClickListener(this);
        setAdapter();
    }

    public LiveCreaterPluginToolView getPluginToolView()
    {
        return view_plugin;
    }

    private void setAdapter()
    {
        mAdapter = new LiveCreaterPluginAdapter(null, getOwnerActivity());
        mAdapter.getSelectManager().setMode(SDSelectManager.Mode.SINGLE_MUST_ONE_SELECTED);
        mAdapter.getSelectManager().addSelectCallback(new SDSelectManager.SelectCallback<PluginModel>()
        {
            @Override
            public void onNormal(int index, PluginModel item)
            {
                item.setSelected(false);
                mAdapter.updateData(index, item);
            }

            @Override
            public void onSelected(int index, PluginModel item)
            {
                mSelectedPos = index;
                mPluginModel = item;
                item.setSelected(true);
                mAdapter.updateData(index, item);
            }
        });
        mAdapter.setItemClickCallback(new SDItemClickCallback<PluginModel>()
        {
            @Override
            public void onItemClick(int position, PluginModel item, View view)
            {
                mAdapter.getSelectManager().performClick(position);
            }
        });
        gv_content.setAdapter(mAdapter);

    }

    public void onRequestInitPluginSuccess(App_plugin_initActModel actModel)
    {
        if (actModel.isOk())
        {
            if (actModel.getRs_count() > 0)
            {
                SDViewUtil.setVisible(ll_plugins);
                mAdapter.updateData(actModel.getList());
                mAdapter.getSelectManager().performClick(mSelectedPos);
            } else
            {
                SDViewUtil.setGone(ll_plugins);
            }
            setAccountDiamonds(actModel.getUser_diamonds());
        }
    }

    public void setAccountDiamonds(String account_diamonds)
    {
        SDViewBinder.setTextView(tv_account, account_diamonds);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == tv_account)
        {
            clickRechargeDialog();
        } else if (v == tv_open_game)
        {
            clickOpenGame();
        }
    }

    private void clickRechargeDialog()
    {
        if (mLiveRechargeDialog == null)
        {
            mLiveRechargeDialog = new LiveRechargeDialog(getOwnerActivity());
        }
        mLiveRechargeDialog.showCenter();
    }

    private void clickOpenGame()
    {
        if (mPluginModel == null)
        {
            return;
        }
        if (mPluginItemClickCallBack != null)
        {
            mPluginItemClickCallBack.onItemClick(mPluginModel);
        }
    }

    /**
     * @param show 设置基础插件显示隐藏
     */
    public void setBasePluginsVisible(boolean show)
    {
        SDViewUtil.setVisibleOrGone(ll_base_plugin, true);
    }

    private PluginItemClickCallBack mPluginItemClickCallBack;

    public void setPluginItemClickCallBack(PluginItemClickCallBack pluginItemClickCallBack)
    {
        mPluginItemClickCallBack = pluginItemClickCallBack;
    }

    public interface PluginItemClickCallBack
    {
        void onItemClick(PluginModel model);
    }

    public void onEventMainThread(EUpdateUserInfo event)
    {
        SDViewBinder.setTextView(tv_account, AppRuntimeWorker.getStrGameAccount());
    }

    @Override
    public void show()
    {
        setBasePluginsVisible(getLiveActivity().isPushing());
        super.show();
    }
}
