package com.fanwe.live.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.baimei.fragment.BMPTBaseFragment;
import com.fanwe.baimei.fragment.BMPopularityFragment;
import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.common.SDFragmentManager;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.event.EOnClick;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDViewPageIndicator;
import com.fanwe.live.IMHelper;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveChatC2CActivity;
import com.fanwe.live.activity.LiveSearchUserActivity;
import com.fanwe.live.adapter.LiveHomeTitleTabAdapter;
import com.fanwe.live.appview.main.LiveTabBaseView;
import com.fanwe.live.appview.main.LiveTabFollowView;
import com.fanwe.live.appview.main.LiveTabHotView;
import com.fanwe.live.appview.main.LiveTabNewView;
import com.fanwe.live.appview.main.LiveTabRankView;
import com.fanwe.live.dialog.LiveSelectLiveDialog;
import com.fanwe.live.event.EIMLoginSuccess;
import com.fanwe.live.event.EReSelectTabLiveBottom;
import com.fanwe.live.event.ERefreshMsgUnReaded;
import com.fanwe.live.event.ESelectLiveFinish;
import com.fanwe.live.model.HomeTabTitleModel;
import com.fanwe.live.model.TotalConversationUnreadMessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播列表
 *
 * @author Administrator
 * @date 2016-7-2 上午11:28:44
 */
public class LiveTabLiveFragment extends BaseFragment
{

    private View ll_search;
    private View ll_private_chat_list;
    private TextView tv_total_unreadnum;
    private SDViewPageIndicator vpg_indicator;
    private SDViewPager vpg_content;

    private List<HomeTabTitleModel> mListModel = new ArrayList<>();
    private LiveHomeTitleTabAdapter mAdapterTitleTab;
   private LiveTabLiveFragment context;
    private SparseArray<LiveTabBaseView> mArrContentView = new SparseArray<>();
    SDFragmentManager sdFragmentManager ;
    @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_live_tab_live;
    }

    @Override
    protected void init()
    {
        super.init();
        context=this;
        ll_search = findViewById(R.id.ll_search);
        ll_private_chat_list = findViewById(R.id.ll_private_chat_list);
        tv_total_unreadnum = (TextView) findViewById(R.id.tv_total_unreadnum);
        vpg_indicator = (SDViewPageIndicator) findViewById(R.id.vpg_indicator);
        vpg_content = (SDViewPager) findViewById(R.id.vpg_content);

        ll_search.setOnClickListener(this);
        ll_private_chat_list.setOnClickListener(this);

        initTabsData();
        initViewPager();
        initViewPagerIndicator();
    }

    private void initTabsData()
    {
        HomeTabTitleModel tabFollow = new HomeTabTitleModel();
        //tabFollow.setName("关注");
        tabFollow.setName("附近");
        HomeTabTitleModel tabHot = new HomeTabTitleModel();
       // tabHot.setName("热门");
        tabHot.setName("热门");

        HomeTabTitleModel tabNearby = new HomeTabTitleModel();
       // tabNearby.setName("附近");
        tabNearby.setName("榜单");
        mListModel.add(tabFollow);
        mListModel.add(tabHot);
        mListModel.add(tabNearby);
    }

    private void initViewPager()
    {
        vpg_indicator.setViewPager(vpg_content);

        vpg_content.setOffscreenPageLimit(2);
        vpg_content.setAdapter(new SDPagerAdapter<HomeTabTitleModel>(mListModel, getActivity()) {
            @Override
            public View getView(ViewGroup container, int position) {
                LiveTabBaseView view = null;
                switch (position) {
                    case 0:
                        view = new LiveTabNewView(getActivity());
                        //   view = new LiveTabFollowView(getActivity());
                        break;
                    case 1:
                        view = new LiveTabHotView(getActivity());
                        break;
                    case 2:
                        view = new LiveTabRankView(getActivity());
                        ((LiveTabRankView) view).setFragment(context);
                        break;

                    default:
                        break;
                }
                if (view != null) {
                    mArrContentView.put(position, view);
                    view.setParentViewPager(vpg_content);
                }

                return view;
            }
        });
    }

    private void initViewPagerIndicator()
    {
        mAdapterTitleTab = new LiveHomeTitleTabAdapter(mListModel, getActivity());
        mAdapterTitleTab.setItemClickCallback(new SDItemClickCallback<HomeTabTitleModel>() {
            @Override
            public void onItemClick(int position, HomeTabTitleModel item, View view) {
                vpg_indicator.setCurrentItem(position);
            }
        });
        vpg_indicator.setAdapter(mAdapterTitleTab);
        vpg_indicator.setCurrentItem(1);

        vpg_indicator.setItemSelectedCallback(new SDViewPageIndicator.ItemSelectedCallback() {
            @Override
            public void onItemSelected(final int i, View view) {
//               /* if(i==1){
//                    view.findViewById(R.id.iv_underline_bg).setVisibility(View.VISIBLE);
//                    view.findViewById(R.id.iv_underline).setVisibility(View.GONE);
//                }*/
                view.findViewById(R.id.iv_underline_bg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i == 1) {
                            LiveSelectLiveDialog selectDialog = new LiveSelectLiveDialog(getActivity());
                            selectDialog.showBottom();
                        }
                    }
                });
            }
        });
    }

    public void onEventMainThread(ESelectLiveFinish event)
    {
        String text = SDConfig.getInstance().getString(R.string.config_live_select_city, "");
        if (TextUtils.isEmpty(text))
        {
            text = LiveConstant.LIVE_HOT_CITY;
        }
        mAdapterTitleTab.getData(1).setName(text);
        mAdapterTitleTab.updateData(1);
    }

    public void onEventMainThread(EReSelectTabLiveBottom event)
    {
        if (event.index == 0)
        {
            int index = vpg_content.getCurrentItem();
            LiveTabBaseView view = mArrContentView.get(index);
            if (view != null)
            {
                view.scrollToTop();
            }
        }
    }

    public void onEventMainThread(EOnClick event)
    {
        if (R.id.tv_tab_live_follow_goto_live == event.view.getId())
        {
            vpg_indicator.setCurrentItem(0);
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v == ll_search)
        {
            clickSearch();
        } else if (v == ll_private_chat_list)
        {
            clickChatList();
        }
        super.onClick(v);
    }

    /**
     * 私聊列表
     */
    private void clickChatList()
    {
        Intent intent = new Intent(getActivity(), LiveChatC2CActivity.class);
        startActivity(intent);
    }

    /**
     * 搜索
     */
    private void clickSearch()
    {
        Intent intent = new Intent(getActivity(), LiveSearchUserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume()
    {
        initUnReadNum();
        super.onResume();
    }

    private void initUnReadNum()
    {
        TotalConversationUnreadMessageModel model = IMHelper.getC2CTotalUnreadMessageModel();
        setUnReadNumModel(model);
    }

    public void onEventMainThread(ERefreshMsgUnReaded event)
    {
        TotalConversationUnreadMessageModel model = event.model;
        setUnReadNumModel(model);
    }

    //SDK启动成功接收事件获取未读数量
    public void onEventMainThread(EIMLoginSuccess event)
    {
        initUnReadNum();
    }

    private void setUnReadNumModel(TotalConversationUnreadMessageModel model)
    {
        SDViewUtil.setGone(tv_total_unreadnum);
        if (model != null && model.getTotalUnreadNum() > 0)
        {
            SDViewUtil.setVisible(tv_total_unreadnum);
            tv_total_unreadnum.setText(model.getStr_totalUnreadNum());
        }
    }
}
