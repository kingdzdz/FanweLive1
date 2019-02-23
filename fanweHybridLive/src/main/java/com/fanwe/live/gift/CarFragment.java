package com.fanwe.live.gift;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.live.R;

/**
 * Created by wangxiaoya on 2017/8/22.
 */

public class CarFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.car, null,false);
        carAnimation(view);
        return view;
    }
    private void carAnimation(View view) {
        final ImageView imgCar = (ImageView) view.findViewById(R.id.img_car);
        final ImageView imgCarStage = (ImageView) view.findViewById(R.id.img_car_stage);
        final ImageView imgCarLight = (ImageView) view.findViewById(R.id.img_car_light);
        final ImageView imgCarColorBar = (ImageView) view.findViewById(R.id.img_car_color_bar);
        final ObjectAnimator animator1 = ObjectAnimator.ofFloat(imgCarStage, "translationY", 1000f, imgCarStage.getY());
        animator1.setDuration(3000);
        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                imgCarLight.setVisibility(View.GONE);
                imgCarColorBar.setVisibility(View.GONE);
                imgCar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imgCar.setVisibility(View.VISIBLE);
                final AnimationDrawable animationDrawable = (AnimationDrawable) imgCar.getBackground();
                animationDrawable.start();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(imgCar, "translationY", -1000f, imgCar.getY());
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(imgCar, "translationX", 1000f, imgCar.getX());
                AnimatorSet set = new AnimatorSet();
                set.setDuration(3000);
                set.playTogether(animator2, animator3);
                set.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animationDrawable.stop();
                        imgCarLight.setVisibility(View.VISIBLE);
                        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imgCarLight, "translationY", -1000f, imgCarLight.getY());
                        ObjectAnimator animator2 = ObjectAnimator.ofFloat(imgCarLight, "alpha", 1.0f, 0f, 1.0f);
                        ObjectAnimator animator3 = ObjectAnimator.ofFloat(imgCarColorBar, "translationY", -1000f, imgCarColorBar.getY());
                        animator3.setDuration(3000);
                        animator3.setStartDelay(1500);
                        animator3.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                imgCarColorBar.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        animator3.start();
                        animator2.setRepeatCount(2);
                        AnimatorSet set = new AnimatorSet();
                        set.setDuration(3000);
                        set.playTogether(animator1, animator2);
                        set.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                FragmentOps.removeFragment(getFragmentManager(),"car");
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        set.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                set.start();

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


}
