package com.zuzush.zuzush.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.LinkedList;

/**
 * Created by Administrator on 2017/9/13 0013.
 */
public class ExitApp implements Application.ActivityLifecycleCallbacks {
    public static LinkedList<Activity> activityLinkedList;
    public ExitApp(){
        activityLinkedList = new LinkedList<>();
    }
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityLinkedList.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityLinkedList.remove(activity);
    }
    public static void exitApp(){
        for (Activity activity:activityLinkedList){
            activity.finish();
        }
        System.exit(0);
    }
}
