package com.fanwe.live.gift;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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

public class CakeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.cake, null,false);
        cakeAnimation(view);
        return view;
    }

    private void cakeAnimation(View view) {
        final ImageView imageviewcake = (ImageView) view.findViewById(R.id.img_cake);
        final ImageView imageviewpoints = (ImageView) view.findViewById(R.id.img_cake_points);
        final ImageView imageviewleftone = (ImageView) view.findViewById(R.id.img_cake_left_one);
        final ImageView imageviewlefttwo = (ImageView) view.findViewById(R.id.img_cake_left_two);
        final ImageView imageviewrightone = (ImageView) view.findViewById(R.id.img_cake_right_one);
        final ImageView imageviewrighttwo = (ImageView) view.findViewById(R.id.img_cake_right_two);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageviewcake, "translationY", 1000f, imageviewcake.getY());
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(imageviewcake, "scaleY", 1f, 0.7f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(imageviewcake, "scaleX", 1f, 0.7f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(animator2).with(animator3).after(animator1);
        set.setDuration(3000);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                imageviewleftone.setVisibility(View.GONE);
                imageviewlefttwo.setVisibility(View.GONE);
                imageviewrightone.setVisibility(View.GONE);
                imageviewrighttwo.setVisibility(View.GONE);
                imageviewpoints.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageviewleftone.setVisibility(View.VISIBLE);
                imageviewlefttwo.setVisibility(View.VISIBLE);
                imageviewrightone.setVisibility(View.VISIBLE);
                imageviewrighttwo.setVisibility(View.VISIBLE);
                ObjectAnimator animator5 = ObjectAnimator.ofFloat(imageviewleftone, "translationX", imageviewleftone.getX(), imageviewleftone.getX() - 270f);
                ObjectAnimator animator6 = ObjectAnimator.ofFloat(imageviewlefttwo, "translationX", imageviewlefttwo.getX(), imageviewlefttwo.getY() - 230f);
                ObjectAnimator animator7 = ObjectAnimator.ofFloat(imageviewrightone, "translationX", imageviewrightone.getX(), imageviewrightone.getX() + 280f);
                ObjectAnimator animator8 = ObjectAnimator.ofFloat(imageviewrighttwo, "translationX", imageviewrighttwo.getX(), imageviewrighttwo.getX() + 230f);
                AnimatorSet set0 = new AnimatorSet();
                set0.play(animator5).before(animator6);
                set0.setDuration(2000);
                AnimatorSet set1 = new AnimatorSet();
                set1.play(animator7).before(animator8);
                set1.setDuration(2000);
                AnimatorSet set = new AnimatorSet();
                set.play(set0).before(set1);
                set.start();
                set.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        imageviewpoints.setVisibility(View.VISIBLE);
                        ObjectAnimator animator = ObjectAnimator.ofFloat(imageviewpoints, "translationY", 1000f, imageviewpoints.getY());
                        animator.setDuration(4000).start();
                        animator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                final ObjectAnimator animator = ObjectAnimator.ofFloat(imageviewpoints, "alpha", 1.0f, 0f);
                                animator.setDuration(1500).start();
                                animator.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        FragmentOps.removeFragment(getFragmentManager(),"cake");
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

}
