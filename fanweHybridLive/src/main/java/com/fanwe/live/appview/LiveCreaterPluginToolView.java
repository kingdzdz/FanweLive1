package com.fanwe.live.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.live.R;
import com.fanwe.live.view.RoomPluginToolView;

/**
 * 主播插件基础工具view
 */
public class LiveCreaterPluginToolView extends BaseAppView
{
    public LiveCreaterPluginToolView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveCreaterPluginToolView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveCreaterPluginToolView(Context context)
    {
        super(context);
        init();
    }

    private RoomPluginToolView view_music;
    private RoomPluginToolView view_beauty;
    private RoomPluginToolView view_mic;
    private RoomPluginToolView view_switch_camera;
    private RoomPluginToolView view_flash_light;

    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    protected void init()
    {
        setContentView(R.layout.view_live_creater_plugin_tool);
        view_music = find(R.id.view_music);
        view_beauty = find(R.id.view_beauty);
        view_mic = find(R.id.view_mic);
        view_switch_camera = find(R.id.view_switch_camera);
        view_flash_light = find(R.id.view_flash_light);

        initData();
    }

    private void initData()
    {
        view_music.setTextName("音乐");
        view_music.getViewConfig(view_music.iv_image)
                .setImageNormalResId(R.drawable.selector_plugin_tool_music);
        view_music.updateViewState();

        view_beauty.setTextName("美颜");
        view_beauty.getViewConfig(view_beauty.iv_image)
                .setImageNormalResId(R.drawable.selector_plugin_tool_beauty);
        view_beauty.updateViewState();

        view_mic.setTextName("麦克风");
        view_mic.getViewConfig(view_mic.iv_image)
                .setImageNormalResId(R.drawable.ic_plugin_tool_mic_normal)
                .setImageSelectedResId(R.drawable.ic_plugin_tool_mic_selected);
        view_mic.setSelected(true);

        view_switch_camera.setTextName("切换");
        view_switch_camera.getViewConfig(view_switch_camera.iv_image)
                .setImageNormalResId(R.drawable.selector_plugin_tool_switch_camera);
        view_switch_camera.updateViewState();

        view_flash_light.setTextName("闪光灯");
        view_flash_light.getViewConfig(view_flash_light.iv_image)
                .setImageNormalResId(R.drawable.ic_plugin_tool_flashlight_normal)
                .setImageSelectedResId(R.drawable.ic_plugin_tool_flashlight_selected);
        view_flash_light.updateViewState();

        view_music.setOnClickListener(this);
        view_beauty.setOnClickListener(this);
        view_mic.setOnClickListener(this);
        view_switch_camera.setOnClickListener(this);
        view_flash_light.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (clickListener != null)
        {
            if (v == view_music)
            {
                clickListener.onClickMenuMusic(view_music);
            } else if (v == view_beauty)
            {
                clickListener.onClickMenuBeauty(view_beauty);
            } else if (v == view_mic)
            {
                clickListener.onClickMenuMic(view_mic);
            } else if (v == view_switch_camera)
            {
                clickListener.onClickMenuSwitchCamera(view_switch_camera);
            } else if (v == view_flash_light)
            {
                clickListener.onClickMenuFlashLight(view_flash_light);
            }
        }
    }

    public void dealFlashLightState(boolean isBackCamera)
    {
        if (isBackCamera)
        {
        } else
        {
            view_flash_light.setSelected(false);
        }
    }

    public interface ClickListener
    {
        /**
         * 音乐
         */
        void onClickMenuMusic(RoomPluginToolView view);

        /**
         * 美颜
         */
        void onClickMenuBeauty(RoomPluginToolView view);

        /**
         * 麦克风
         */
        void onClickMenuMic(RoomPluginToolView view);

        /**
         * 切换摄像头
         */
        void onClickMenuSwitchCamera(RoomPluginToolView view);

        /**
         * 闪关灯
         */
        void onClickMenuFlashLight(RoomPluginToolView view);
    }
}
