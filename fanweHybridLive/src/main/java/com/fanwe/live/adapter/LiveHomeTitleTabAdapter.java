package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.HomeTabTitleModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class LiveHomeTitleTabAdapter extends SDSimpleAdapter<HomeTabTitleModel>
{
    public LiveHomeTitleTabAdapter(List<HomeTabTitleModel> listModel, Activity activity)
    {
        super(listModel, activity);
        getSelectManager().setMode(SDSelectManager.Mode.SINGLE_MUST_ONE_SELECTED);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_home_title_tab;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, HomeTabTitleModel model)
    {
        TextView tv_name = get(R.id.tv_name, convertView);
        ImageView iv_underline = get(R.id.iv_underline, convertView);
        ImageView iv_underline_bg = get(R.id.iv_underline_bg, convertView);
        tv_name.setText(model.getName());
        SDViewBinder.setTextView(tv_name, model.getName());
        if (model.isSelected())
        {
            //tv_name.setTextColor(SDResourcesUtil.getColor(R.color.main_color));
            tv_name.setTextColor(SDResourcesUtil.getColor(R.color.white));
        } else
        {
           // tv_name.setTextColor(SDResourcesUtil.getColor(R.color.text_title));
            tv_name.setTextColor(SDResourcesUtil.getColor(R.color.white));
        }
        if (model.isSelected())
        {
            if(position==1){
                SDViewUtil.setVisible(iv_underline_bg);
                SDViewUtil.setInvisible(iv_underline);
            }else{
                SDViewUtil.setVisible(iv_underline);
                SDViewUtil.setInvisible(iv_underline_bg);
            }
          //  SDViewUtil.setVisible(iv_underline);
        } else
        {
            SDViewUtil.setInvisible(iv_underline);
            SDViewUtil.setInvisible(iv_underline_bg);
        }
    }
}
