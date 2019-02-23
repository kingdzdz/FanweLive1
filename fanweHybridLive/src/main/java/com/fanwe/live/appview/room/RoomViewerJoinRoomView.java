package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.live.R;
import com.fanwe.live.model.custommsg.CustomMsgViewerJoin;
import com.fanwe.live.model.custommsg.CustomMsgViewerQuit;
import com.fanwe.live.view.LiveViewerJoinRoomView;

import java.util.Iterator;
import java.util.LinkedList;

public class RoomViewerJoinRoomView extends RoomLooperMainView<CustomMsgViewerJoin>
{
    public RoomViewerJoinRoomView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomViewerJoinRoomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomViewerJoinRoomView(Context context)
    {
        super(context);
    }

    private static final long DURATION_LOOPER = 1000;

    private LiveViewerJoinRoomView view_viewer_join;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_viewer_join_room;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();

        view_viewer_join = find(R.id.view_viewer_join);
    }

    @Override
    public void onMsgViewerJoin(CustomMsgViewerJoin msg)
    {
        super.onMsgViewerJoin(msg);

        if (msg.getSender().isProUser())
        {
            if (queue.contains(msg))
            {
                // 不处理
            } else
            {
                if (msg.equals(view_viewer_join.getMsg()) && view_viewer_join.isPlaying())
                {
                    // 不处理
                } else
                {
                    offerModel(msg);
                }
            }
        }
    }

    @Override
    public void onMsgViewerQuit(CustomMsgViewerQuit msg)
    {
        super.onMsgViewerQuit(msg);

        if (msg.getSender().isProUser())
        {
            Iterator<CustomMsgViewerJoin> it = queue.iterator();
            while (it.hasNext())
            {
                CustomMsgViewerJoin item = it.next();
                if (msg.getSender().equals(item.getSender()))
                {
                    it.remove();
                }
            }
        }
    }

    @Override
    protected void startLooper(long period)
    {
        super.startLooper(DURATION_LOOPER);
    }

    @Override
    protected void looperWork(LinkedList<CustomMsgViewerJoin> queue)
    {
        if (view_viewer_join.canPlay())
        {
            view_viewer_join.playMsg(queue.poll());
        }
    }
}
