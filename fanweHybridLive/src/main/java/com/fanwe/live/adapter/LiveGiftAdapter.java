package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.LiveGiftModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

public class LiveGiftAdapter extends SDSimpleRecyclerAdapter<LiveGiftModel>
{
    public LiveGiftAdapter(List<LiveGiftModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<LiveGiftModel> holder, final int position, final LiveGiftModel model)
    {
        ImageView iv_gift = holder.get(R.id.iv_gift);
        ImageView iv_selected = holder.get(R.id.iv_selected);
        ImageView iv_much = holder.get(R.id.iv_much);
        TextView tv_price = holder.get(R.id.tv_price);
        TextView tv_score = holder.get(R.id.tv_score);

        if (model.isSelected())
        {
            SDViewUtil.setVisible(iv_selected);
            SDViewUtil.setGone(iv_much);
        } else
        {
            SDViewUtil.setGone(iv_selected);
            SDViewUtil.setVisibleOrGone(iv_much, model.getIs_much() == 1);
        }

        SDViewBinder.setTextView(tv_price, String.valueOf(model.getDiamonds()));
        SDViewBinder.setTextView(tv_score, model.getScore_fromat());

        GlideUtil.load(model.getIcon()).into(iv_gift);
        LogUtil.i(String.valueOf(position));
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType)
    {
        return R.layout.item_live_gift;
    }

}
