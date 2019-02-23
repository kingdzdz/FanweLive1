package com.fanwe.live.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.model.App_tipoff_typeModel;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-5-26 下午7:41:51 类说明
 */
public class LiveTipoffTypeAdapter extends SDSimpleAdapter<App_tipoff_typeModel>
{
	private int mCurSelected = -1;

	public App_tipoff_typeModel getCurrentSelectedModel()
	{
		if (getData() != null && getData().size() > 0 && mCurSelected >= 0)
		{
			return getData().get(mCurSelected);
		} else
		{
			return null;
		}
	}

	public LiveTipoffTypeAdapter(List<App_tipoff_typeModel> listModel, Activity activity)
	{
		super(listModel, activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		return R.layout.item_live_tipoff_type;
	}

	@Override
	public void bindData(final int postion, View convertView, final ViewGroup parent, final App_tipoff_typeModel model)
	{
		final CheckBox cb = ViewHolder.get(R.id.cb, convertView);
		TextView tv_type = ViewHolder.get(R.id.tv_type, convertView);
		SDViewBinder.setTextView(tv_type, model.getName(), "未知类型");
		cb.setChecked(model.isSelected());
		convertView.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (cb.isChecked())
				{
					return;
				}
				cb.setSelected(true);
				model.setSelected(true);
				if (mCurSelected != -1)
				{
					getData().get(mCurSelected).setSelected(false);
				}

				mCurSelected = postion;
				notifyDataSetChanged();
			}
		});
	}
}
