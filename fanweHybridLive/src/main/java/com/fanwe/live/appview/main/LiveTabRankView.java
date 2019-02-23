package com.fanwe.live.appview.main;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.baimei.adapter.BMHomeLiveCenterTabAdapter;
import com.fanwe.baimei.fragment.BMPopularityFragment;
import com.fanwe.baimei.fragment.BMTyrantFragment;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.adapter.SDFragmentPagerAdapter;
import com.fanwe.library.common.SDFragmentManager;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.library.fragment.SDBaseFragment;
import com.fanwe.library.listener.SDViewVisibilityCallback;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewSizeLocker;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabText;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveTabHotBannerPagerAdapter;
import com.fanwe.live.event.EFinishAdImg;
import com.fanwe.live.fragment.LiveTabLiveFragment;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017-7-29.
 */
public class LiveTabRankView extends LiveTabBaseView{
    @ViewInject(R.id.tab_popularity)
    private SDTabText tab_popularity;
    @ViewInject(R.id.tab_tyrant)
    private SDTabText tab_tyrant;
    @ViewInject(R.id.fl_content_anchor)
    private ViewPager fl_content_anchor;
    @ViewInject(R.id.fragment_bmp)
    private BMPopularityFragment fragment_bmp;
    @ViewInject(R.id.fragment_bmt)
    private BMTyrantFragment fragment_bmt;
    @ViewInject(R.id.lv_bmp)
    private LinearLayout lv_bmp;
    @ViewInject(R.id.lv_bmt)
    private LinearLayout lv_bmt;
   private Context  context;
    private Fragment fragment;
    private LiveTabLiveFragment fragmentManager;
    private SDSelectViewManager<SDTabText> selectViewManager = new SDSelectViewManager<>();
    private PagerAdapter  adapter;

    public LiveTabRankView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context=context;
        init();
    }

    public LiveTabRankView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context=context;
        init();
    }

    public LiveTabRankView(Context context)
    {
        super(context);
        this.context=context;
        init();
    }
    protected void init()
    {
        setContentView(R.layout.bm_act_anchor_ranklist);
        initTitle();
        initTabs();
    }
    private void initTitle()
    {
    //    mTitle.setMiddleTextTop("主播榜");
    }
    public void setFragment(LiveTabLiveFragment fragment)
    {
        //this.fragment = fragment;
        fragmentManager= fragment;
    }
    private void initTabs()
    {
        tab_popularity.setTextTitle("人气榜");
        tab_popularity.mTv_title.setPadding(SDViewUtil.dp2px(20), SDViewUtil.dp2px(2), SDViewUtil.dp2px(20), SDViewUtil.dp2px(2));
        tab_popularity.getViewConfig(tab_popularity.mTv_title).setBackgroundNormal(SDResourcesUtil.getDrawable(R.drawable.bm_bg_no_color_corner_5dp))
                .setBackgroundSelected(SDResourcesUtil.getDrawable(R.drawable.bm_bg_main_color_corner_5dp));
        tab_popularity.getViewConfig(tab_popularity.mTv_title).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.white))
                .setTextSizeNormal(SDViewUtil.dp2px(13)).setTextSizeSelected(SDViewUtil.dp2px(13));

        tab_tyrant.mTv_title.setPadding(SDViewUtil.dp2px(20), SDViewUtil.dp2px(2), SDViewUtil.dp2px(20), SDViewUtil.dp2px(2));
        tab_tyrant.setTextTitle("土豪榜");
        tab_tyrant.getViewConfig(tab_tyrant.mTv_title).setBackgroundNormal(SDResourcesUtil.getDrawable(R.drawable.bm_bg_no_color_corner_5dp))
                .setBackgroundSelected(SDResourcesUtil.getDrawable(R.drawable.bm_bg_main_color_corner_5dp));
        tab_tyrant.getViewConfig(tab_tyrant.mTv_title).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.white))
                .setTextSizeNormal(SDViewUtil.dp2px(13)).setTextSizeSelected(SDViewUtil.dp2px(13));

        SDTabText[] items = new SDTabText[]{tab_popularity, tab_tyrant};

        selectViewManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabText>() {

            @Override
            public void onNormal(int index, SDTabText item) {
            }

            @Override
            public void onSelected(int index, SDTabText item) {
                switch (index) {
                    case 0:
                        clickTabMerits();
                        break;
                    case 1:
                        clickTabContribution();
                        break;
                    default:
                        break;
                }
            }
        });
        selectViewManager.setItems(items);
        selectViewManager.setSelected(0, true);
     //   adapter = new PagerAdapter(fragmentManager.getChildFragmentManager());
     //   fl_content_anchor.setAdapter(adapter);
    }

    protected void clickTabMerits()
    {
        //fragment_bmp.getSDFragmentManager().findFragmentById(R.id.fl_content_ptbase).getView().setVisibility(VISIBLE);
      //  fragment_bmt.getSDFragmentManager().findFragmentById(R.id.fl_content_ptbase).getView().setVisibility(GONE);
        lv_bmp.setVisibility(VISIBLE);
        lv_bmt.setVisibility(GONE);
        // fl_content_anchor.setCurrentItem(0);
       //getSDFragmentManager().toggle(R.id.fl_content_anchor, null, BMPopularityFragment.class);
        /*fragment_bmp.getSDFragmentManager().show(s).setVisibility(VISIBLE);
        fragment_bmt.getView().setVisibility(GONE);*/
    }

    protected void clickTabContribution()
    {
       // fragment_bmt.getSDFragmentManager().findFragmentById(R.id.fl_content_ptbase).getView().setVisibility(VISIBLE);
       // fragment_bmp.getSDFragmentManager().findFragmentById(R.id.fl_content_ptbase).getView().setVisibility(GONE);
        lv_bmt.setVisibility(VISIBLE);
        lv_bmp.setVisibility(GONE);
      //  fl_content_anchor.setCurrentItem(1);
      //  getSDFragmentManager().toggle(R.id.fl_content_anchor, null, BMTyrantFragment.class);
       /* fragment_bmp.getView().setVisibility(GONE);
        fragment_bmt.getView().setVisibility(VISIBLE);*/
       // fragment_bmt.hideFragmentView();

    }

   /* @Override
    public void onDestroy()
    {
     super.onDestroy();
        onSendEvent();
    }*/

    /**
     * 发送关闭事件
     */
    private void onSendEvent()
    {
        EFinishAdImg event = new EFinishAdImg();
        SDEventManager.post(event);
    }
    /**
     * 自定义一个PagerAdapter
     */
    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);

        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fg=null;

            if(position==0){
           fg= new BMPopularityFragment();

            } else if (position==1) {
                fg = new BMTyrantFragment();
            }
            return fg;
        }
        @Override
        public int getCount() {
            return  2;
        }
    }
}
