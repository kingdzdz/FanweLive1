package com.fanwe.live.gift;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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

public class CastleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.castle, null,false);
        castleAnimation(view);
        return view;
    }
    private void castleAnimation(View view) {
        final ImageView imgCoach = (ImageView) view.findViewById(R.id.img_castle_coach);
        final ImageView imgHeart = (ImageView) view.findViewById(R.id.img_castle_heart);
        final ImageView imgHouse = (ImageView) view.findViewById(R.id.img_castle_house);
        final ImageView imgSnowField = (ImageView) view.findViewById(R.id.img_castle_snowfield);
        final ImageView imgSnowFlake = (ImageView) view.findViewById(R.id.img_castle_snowflake);
        final ImageView imgStar = (ImageView) view.findViewById(R.id.img_castle_star);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imgSnowField, "translationY", 1000f, imgSnowField.getY());
        animator1.setDuration(2500);
        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                imgHeart.setVisibility(View.GONE);
                imgStar.setVisibility(View.GONE);
                imgSnowFlake.setVisibility(View.GONE);
                imgCoach.setVisibility(View.GONE);
                imgHouse.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imgHouse.setVisibility(View.VISIBLE);
                final ObjectAnimator animator2 = ObjectAnimator.ofFloat(imgHouse, "translationY", 1000f, imgHouse.getY());
                animator2.setDuration(3000);
                animator2.start();
                animator2.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        imgHeart.setVisibility(View.VISIBLE);
                        imgStar.setVisibility(View.VISIBLE);
                        imgCoach.setVisibility(View.VISIBLE);
                        final ObjectAnimator animator5 = ObjectAnimator.ofFloat(imgStar, "alpha", 0.3f, 1f, 0.3f);
                        final ObjectAnimator animator6 = ObjectAnimator.ofFloat(imgHeart, "alpha", 0.3f, 1f, 0.3f);
                        animator5.setRepeatCount(ValueAnimator.INFINITE);
                        animator6.setRepeatCount(ValueAnimator.INFINITE);
                        AnimatorSet set = new AnimatorSet();
                        set.play(animator5).with(animator6);
                        set.setDuration(4000);
                        set.start();
                        final ObjectAnimator animator3 = ObjectAnimator.ofFloat(imgCoach, "translationX", 1000f, imgCoach.getX());
                        animator3.setDuration(4000);
                        animator3.start();
                        animator3.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                imgSnowFlake.setVisibility(View.VISIBLE);
                                final ObjectAnimator animator4 = ObjectAnimator.ofFloat(imgSnowFlake, "translationY", -1000f, imgSnowFlake.getY());
                                animator4.setRepeatCount(2);
                                animator4.setDuration(3000);
                                animator4.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        FragmentOps.removeFragment(getFragmentManager(),"castle");
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });
                                animator4.start();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

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
