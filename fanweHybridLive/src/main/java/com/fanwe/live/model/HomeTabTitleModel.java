package com.fanwe.live.model;

import com.fanwe.library.common.SDSelectManager;

/**
 * Created by Administrator on 2017/5/15.
 */

public class HomeTabTitleModel implements SDSelectManager.Selectable
{
    private String name;

    //
    private boolean selected;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
}
