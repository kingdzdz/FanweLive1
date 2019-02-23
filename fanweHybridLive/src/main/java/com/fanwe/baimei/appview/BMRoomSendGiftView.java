package com.fanwe.baimei.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.animator.SDAnim;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewGroup;
import com.fanwe.live.IMHelper;
import com.fanwe.live.R;
import com.fanwe.live.appview.LiveSendGiftView;
import com.fanwe.live.appview.room.RoomView;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_pop_propActModel;
import com.fanwe.live.model.App_propActModel;
import com.fanwe.live.model.Deal_send_propActModel;
import com.fanwe.live.model.LiveGiftModel;
import com.fanwe.live.model.custommsg.CustomMsgPrivateGift;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

public class BMRoomSendGiftView extends RoomView
{
    public BMRoomSendGiftView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public BMRoomSendGiftView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public BMRoomSendGiftView(Context context)
    {
        super(context);
    }


    private SDSelectViewGroup svg_tabs;
    private SDTabUnderline view_tab_diamond;
    private SDTabUnderline view_tab_coin;

    private LiveSendGiftView view_send_gift_diamond;
    private BMLiveSendGiftCoinView view_send_gift_coin;

    private SDAnim mAnimVisible;
    private SDAnim mAnimInvisible;

    private App_propActModel mGiftActModel; //礼物接口实体

    @Override
    protected int onCreateContentView()
    {
        return R.layout.bm_view_room_send_gift;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        svg_tabs = (SDSelectViewGroup) findViewById(R.id.svg_tabs);
        view_tab_diamond = (SDTabUnderline) findViewById(R.id.view_tab_diamond);
        view_tab_coin = (SDTabUnderline) findViewById(R.id.view_tab_coin);
    }

    /**
     * 当前是否是秀豆模式
     *
     * @return
     */
    private boolean isDiamond()
    {
        if (AppRuntimeWorker.isUseGameCurrency())
        {
            if (svg_tabs.getSelectViewManager().getSelectedIndex() == 0)
            {
                return true;
            } else
            {
                return false;
            }
        } else
        {
            return true;
        }
    }

    /**
     * 绑定数据
     */
    public void bindData()
    {
        if (mGiftActModel == null) {
            CommonInterface.requestGift(new AppRequestCallback<App_propActModel>()
            {
                @Override
                protected void onSuccess(SDResponse sdResponse)
                {
                    if (actModel.isOk())
                    {
                        mGiftActModel = actModel;
                        bindDataInternal();
                    }
                }
            });
        } else {
            if (isDiamond()) {
                getSendGiftViewDiamond();
            } else {
                getSendGiftViewCoin();
            }
        }
    }

    public void bindDataInternal()
    {
        if (SDCollectionUtil.isEmpty(mGiftActModel.getCoins_list())) {
            SDViewUtil.setGone(svg_tabs);
            toggleView(R.id.fl_container_send_gift, getSendGiftViewDiamond());
        } else {
            SDViewUtil.setVisible(svg_tabs);
            view_tab_diamond.setTextTitle("秀豆礼物");
            view_tab_coin.setTextTitle("游戏币礼物");

            svg_tabs.getSelectViewManager().addSelectCallback(new SDSelectManager.SelectCallback<View>()
            {
                @Override
                public void onNormal(int index, View item)
                {
                }

                @Override
                public void onSelected(int index, View item)
                {
                    if (index == 0)
                    {
                        toggleView(R.id.fl_container_send_gift, getSendGiftViewDiamond());
                    } else
                    {
                        toggleView(R.id.fl_container_send_gift, getSendGiftViewCoin());
                    }
                }
            });
            svg_tabs.getSelectViewManager().performClick(0);
        }
    }

    /**
     * 返回发送秀豆礼物view
     *
     * @return
     */
    public LiveSendGiftView getSendGiftViewDiamond()
    {
        if (view_send_gift_diamond == null)
        {
            view_send_gift_diamond = new LiveSendGiftView(getContext());
            view_send_gift_diamond.setCallback(new LiveSendGiftView.SendGiftViewCallback()
            {
                @Override
                public void onClickSend(LiveGiftModel model, int is_plus)
                {
                    sendGift(model, is_plus, true);
                }
            });
        }
        if (mGiftActModel != null)
        {
            view_send_gift_diamond.setDataGift(mGiftActModel.getList());
        }
        return view_send_gift_diamond;
    }

    /**
     * 返回发送金币礼物view
     *
     * @return
     */
    public BMLiveSendGiftCoinView getSendGiftViewCoin()
    {
        if (view_send_gift_coin == null)
        {
            view_send_gift_coin = new BMLiveSendGiftCoinView(getContext());
            view_send_gift_coin.setCallback(new BMLiveSendGiftCoinView.SendGiftViewCallback()
            {
                @Override
                public void onClickSend(LiveGiftModel model, int is_plus)
                {
                    sendGift(model, is_plus, false);
                }
            });
        }
        if (mGiftActModel != null)
        {
            view_send_gift_coin.setDataGift(mGiftActModel.getCoins_list());
        }
        return view_send_gift_coin;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mAnimVisible == null)
        {
            mAnimVisible = SDAnim.from(this);
            setVisibleAnimator(mAnimVisible.get());
        }
        mAnimVisible.translationY(h, 0);

        if (mAnimInvisible == null)
        {
            mAnimInvisible = SDAnim.from(this);
            setInvisibleAnimator(mAnimInvisible.get());
        }
        mAnimInvisible.translationY(0, h);
    }

    private void sendGift(final LiveGiftModel giftModel, int is_plus, final boolean isDiamond)
    {
        if (giftModel != null)
        {
            if (getLiveActivity().isCreater())
            {

            } else
            {
                if (giftModel.getIs_much() != 1)
                {
                    SDToast.showToast("发送完成");
                }
            }

            if (getLiveActivity().getRoomInfo() == null)
            {
                return;
            }

            if (getLiveActivity().getRoomInfo().getLive_in() == 0) {
                //私聊发礼物接口
                final String createrId = getLiveActivity().getCreaterId();
                if (createrId != null) {
                    CommonInterface.requestSendGiftPrivate(giftModel.getId(), 1, createrId, new AppRequestCallback<Deal_send_propActModel>()
                    {
                        @Override
                        protected void onSuccess(SDResponse resp)
                        {
                            if (actModel.isOk())
                            {
                                if (isDiamond)
                                {
                                    getSendGiftViewDiamond().sendGiftSuccess(giftModel);
                                } else
                                {
                                    getSendGiftViewCoin().sendGiftSuccess(giftModel);
                                }

                                // 发送私聊消息给主播
                                final CustomMsgPrivateGift msg = new CustomMsgPrivateGift();
                                msg.fillData(actModel);
                                IMHelper.sendMsgC2C(createrId, msg, new TIMValueCallBack<TIMMessage>()
                                {
                                    @Override
                                    public void onError(int i, String s)
                                    {
                                    }

                                    @Override
                                    public void onSuccess(TIMMessage timMessage)
                                    {
                                        // 如果私聊界面不是每次都加载的话要post一条来刷新界面
                                        // IMHelper.postMsgLocal(msg, createrId);
                                    }
                                });
                            }
                        }
                    });
                }
            } else {
                int is_coins = isDiamond ? 0 : 1;

                AppRequestParams params = CommonInterface.requestSendGiftParams(giftModel.getId(), 1, is_plus, is_coins, getLiveActivity().getRoomId());
                AppHttpUtil.getInstance().post(params, new AppRequestCallback<App_pop_propActModel>()
                {
                    @Override
                    protected void onSuccess(SDResponse resp)
                    {
                        // 扣费
                        if (actModel.isOk())
                        {
                            if (isDiamond)
                            {
                                getSendGiftViewDiamond().sendGiftSuccess(giftModel);
                            } else
                            {
                                getSendGiftViewCoin().sendGiftSuccess(giftModel);
                            }
                        }
                    }

                    @Override
                    protected void onError(SDResponse resp)
                    {
                        CommonInterface.requestMyUserInfo(null);
                    }
                });
            }
        }
    }

    @Override
    protected boolean onTouchDownOutside(MotionEvent ev)
    {
        getVisibilityHandler().setInvisible(true);
        return true;
    }

    @Override
    public boolean onBackPressed()
    {
        getVisibilityHandler().setInvisible(true);
        return true;
    }
}
