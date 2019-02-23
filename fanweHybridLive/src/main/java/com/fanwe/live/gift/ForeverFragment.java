package com.fanwe.live.gift;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.live.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangxiaoya on 2017/8/22.
 */

public class ForeverFragment extends Fragment {
    ImageView imgForeverPetal1;
    ImageView imgForeverPetal2;
    ImageView imgForeverPetal3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.forever, null, false);
        imgForeverPetal1 = (ImageView) view.findViewById(R.id.img_forever_petal1);
        imgForeverPetal2 = (ImageView) view.findViewById(R.id.img_forever_petal2);
        imgForeverPetal3 = (ImageView) view.findViewById(R.id.img_forever_petal3);
        foreverAnimation(view);
        return view;
    }

    private void foreverAnimation(View view) {
        final ImageView imgForever = (ImageView) view.findViewById(R.id.img_forever);
        final ImageView imgForeverLove = (ImageView) view.findViewById(R.id.img_forever_love);
        final ImageView imgForeverMountain = (ImageView) view.findViewById(R.id.img_forever_mountain);
        final ImageView imgForeverPeach = (ImageView) view.findViewById(R.id.img_forever_peach);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imgForeverMountain, "translationY", 1000f, imgForeverMountain.getY());
        animator1.setDuration(2500);
        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                imgForever.setVisibility(View.GONE);
                imgForeverLove.setVisibility(View.GONE);
                imgForeverPetal1.setVisibility(View.GONE);
                imgForeverPetal2.setVisibility(View.GONE);
                imgForeverPetal3.setVisibility(View.GONE);
                imgForeverPeach.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imgForeverPeach.setVisibility(View.VISIBLE);
                ObjectAnimator animator = ObjectAnimator.ofFloat(imgForeverPeach, "translationY", 1000f, imgForeverPeach.getY() + 60);
                animator.setDuration(2500);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        imgForever.setVisibility(View.VISIBLE);
                        imgForeverLove.setVisibility(View.VISIBLE);
                        //获取图片所显示的ClipDrawble对象
                        ClipDrawable foreverClip = (ClipDrawable) imgForever.getDrawable();
                        setClipAnim(foreverClip);
                        ClipDrawable foreverLoveClip = (ClipDrawable) imgForeverLove.getDrawable();
                        setClipLoveAnim(foreverLoveClip);
                        startPetalAnimation();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }

                });
                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator1.start();
    }

    private void setClipAnim(final ClipDrawable drawable) {
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x1233) {
                    //修改ClipDrawable的level值
                    drawable.setLevel(drawable.getLevel() + 200);
                }
            }
        };
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Message msg = new Message();
                msg.what = 0x1233;
                //发送消息,通知应用修改ClipDrawable对象的level值
                handler.sendMessage(msg);
                //取消定时器
                if (drawable.getLevel() >= 10000) {
                    timer.cancel();
                }
            }
        }, 0, 50);
    }

    private void setClipLoveAnim(final ClipDrawable drawable) {
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x1234) {
                    //修改ClipDrawable的level值
                    drawable.setLevel(drawable.getLevel() + 200);
                }
            }
        };
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Message msg = new Message();
                msg.what = 0x1234;
                //发送消息,通知应用修改ClipDrawable对象的level值
                handler.sendMessage(msg);
                //取消定时器
                if (drawable.getLevel() >= 10000) {
                    timer.cancel();
                }
            }
        }, 2500, 50);
    }

    private void startPetalAnimation() {
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x1235) {
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(imgForeverPetal1, "translationX", 1000f, -1000f);
                    animator1.setDuration(2500);
                    animator1.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            imgForeverPetal1.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ObjectAnimator animator2 = ObjectAnimator.ofFloat(imgForeverPetal2, "translationX", 1000f, -1200f);
                            animator2.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    imgForeverPetal2.setVisibility(View.VISIBLE);

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    ObjectAnimator animator3 = ObjectAnimator.ofFloat(imgForeverPetal3, "translationX", 1000f, -1000f);
                                    animator3.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                            imgForeverPetal3.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            FragmentOps.removeFragment(getFragmentManager(), "forever");
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    });
                                    animator3.setDuration(2500);
                                    animator3.start();

                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                            animator2.setDuration(2500);
                            animator2.start();

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animator1.setStartDelay(3000);
                    animator1.start();
                }
            }
        };

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Message msg = new Message();
                msg.what = 0x1235;
                handler.sendMessage(msg);
            }
        }, 3500);
    }

}
