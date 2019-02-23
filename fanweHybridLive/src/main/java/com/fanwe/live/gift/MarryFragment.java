package com.fanwe.live.gift;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
 *
 */

public class MarryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.marry, null,false);
        marryAnimation(view);
        return view;
    }

    private void marryAnimation(View view) {
        final ImageView imgBalloon = (ImageView) view.findViewById(R.id.img_marry_balloon);
        final ImageView imgBalloonFly = (ImageView) view.findViewById(R.id.img_marry_balloon_fly);
        final ImageView imgLight = (ImageView) view.findViewById(R.id.img_marry_light);
        final ImageView imgLovers = (ImageView) view.findViewById(R.id.img_marry_lovers);
        final ImageView imgStage = (ImageView) view.findViewById(R.id.img_marry_stage);
        final ImageView imgStar = (ImageView) view.findViewById(R.id.img_marry_star);
        imgBalloon.setVisibility(View.GONE);
        imgBalloonFly.setVisibility(View.GONE);
        imgLight.setVisibility(View.GONE);
        imgLovers.setVisibility(View.GONE);
        imgStage.setVisibility(View.GONE);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imgStar, "translationY", -1000f, imgStar.getY());
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(imgStar, "alpha", 1.0f, 0f, 1.0f);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000);
        set.playSequentially(animator1, animator2);
        set.start();
        final ObjectAnimator animator3 = ObjectAnimator.ofFloat(imgBalloon, "translationY", 1000f, imgBalloon.getY());
        animator3.setDuration(3000);
        animator3.setStartDelay(3000);
        animator3.start();
        animator3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                imgBalloon.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imgBalloonFly.setVisibility(View.VISIBLE);
                imgLight.setVisibility(View.VISIBLE);
                final ObjectAnimator animator5 = ObjectAnimator.ofFloat(imgBalloonFly, "translationY", imgBalloonFly.getY(), -1000f);
                final ObjectAnimator animator6 = ObjectAnimator.ofFloat(imgLight, "alpha", 1.0f, 0.4f, 1.0f);
                final ObjectAnimator animator7 = ObjectAnimator.ofFloat(imgLight, "scaleX", 1.0f, 1.4f);
                final ObjectAnimator animator8 = ObjectAnimator.ofFloat(imgLight, "scaleY", 1.0f, 1.4f);
                animator5.setRepeatCount(ValueAnimator.INFINITE);
                animator6.setRepeatCount(ValueAnimator.INFINITE);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(animator5, animator6, animator7, animator8);
                set.setDuration(3000);
                set.start();
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(imgStage, "translationY", 1000f, imgStage.getY());
                ObjectAnimator animator4 = ObjectAnimator.ofFloat(imgLovers, "translationX", 1000f, imgLovers.getX());
                AnimatorSet set1 = new AnimatorSet();
                set1.play(animator3).with(animator4);
                set1.setDuration(4000);
                set1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        imgStage.setVisibility(View.VISIBLE);
                        imgLovers.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        final Handler handler = new Handler() {
                            public void handleMessage(Message msg) {
                                if (msg.what == 0x521) {
                                    FragmentOps.removeFragment(getFragmentManager(),"marry");
                                }
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            public void run() {
                                Message msg = new Message();
                                msg.what = 0x521;
                                handler.sendMessage(msg);
                            }
                        }, 1500);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                set1.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }

}
