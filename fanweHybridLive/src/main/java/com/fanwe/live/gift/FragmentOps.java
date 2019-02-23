package com.fanwe.live.gift;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

public class FragmentOps {

    /**
     * activity --> fragment, 默认全屏
     *
     * @param manager
     * @param targetFragment
     * @param tag
     */
    public static void initFragment(FragmentManager manager, Fragment targetFragment, String tag) {
        initFragment(manager, targetFragment, tag, Window.ID_ANDROID_CONTENT);
    }

    /**
     * activity --> fragment, 指定位置
     *
     * @param manager
     * @param targetFragment
     * @param tag
     * @param position
     */
    public static void initFragment(FragmentManager manager, Fragment targetFragment, String tag, int position) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(position, targetFragment, tag);
        transaction.commitAllowingStateLoss();
    }

    /**
     * fragment add fragment, 默认全屏
     *
     * @param manager
     * @param targetFragment
     * @param tag
     */
    public static void addFragment(FragmentManager manager, Fragment targetFragment, String tag) {
        addFragment(manager, targetFragment, tag, Window.ID_ANDROID_CONTENT, true, true);
    }

    public static void addFragmentNoHide(FragmentManager manager, Fragment targetFragment, String tag) {
        addFragment(manager, targetFragment, tag, Window.ID_ANDROID_CONTENT, false, true);
    }

    public static void addFragmentNoHide(FragmentManager manager, Fragment targetFragment, String tag, int content) {
        addFragment(manager, targetFragment, tag, content, false, true);
    }

    public static void addFragment(FragmentManager manager, Fragment targetFragment, String tag, int content) {
        addFragment(manager, targetFragment, tag, content, true, true);
    }

    public static void addFragmentNoAnim(FragmentManager manager, Fragment targetFragment, String tag, int content) {
        addFragment(manager, targetFragment, tag, content, false, false);
    }

    /**
     * add fragment, 指定位置
     *
     * @param manager
     * @param targetFragment
     * @param tag
     * @param content
     */
    private static void addFragment(FragmentManager manager, Fragment targetFragment, String tag, int content, boolean isHide, boolean isAnim) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (isHide) {
            Fragment oldFragment = manager.findFragmentById(content);
            if (null != oldFragment) {
                transaction.hide(oldFragment);
            }
        }
        transaction.add(content, targetFragment, tag);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    public static void addFragment(FragmentManager manager, Fragment targetFragment, String tag, String oldTag) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment oldFragment = manager.findFragmentByTag(oldTag);
        if (null != oldFragment) {
            transaction.hide(oldFragment);
        }
        transaction.add(Window.ID_ANDROID_CONTENT, targetFragment, tag);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    public static void addFragmentForHome(FragmentManager manager, Fragment targetFragment, String tag, int content) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(content, targetFragment, tag);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    /**
     * replace fragment, 指定位置
     *
     * @param manager
     * @param targetFragment
     * @param tag
     * @param position
     */
    public static void replaceFragment(FragmentManager manager, Fragment targetFragment, String tag, int position) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(position, targetFragment, tag);
        transaction.commitAllowingStateLoss();
    }

    /**
     * remove fragment
     *
     * @param manager
     * @param tag
     */
    public static void removeFragment(FragmentManager manager, String tag) {

        if (null != manager && null != manager.findFragmentByTag(tag)) {
            manager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        Fragment fragment = manager.findFragmentByTag(tag);
        if (null != fragment) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(fragment);
            transaction.commitAllowingStateLoss();
            manager.executePendingTransactions();
        }
    }

    public static void removeFragment(FragmentManager fragmentManager, Fragment targetFragment) {
        if (null != targetFragment) {
            removeFragment(fragmentManager, targetFragment.getTag());
        }

    }

    public static void addFragmentBottomInTopOut(FragmentManager manager, Fragment targetFragment,
                                                 String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(Window.ID_ANDROID_CONTENT, targetFragment, tag);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

}
