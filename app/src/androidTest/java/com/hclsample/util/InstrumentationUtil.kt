package com.hclsample.util

import android.app.Activity
import androidx.test.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage

/**
 * Contains instrumentation utilities.
 */
class InstrumentationUtil {
    // Taken from http://qathread.blogspot.com/2014/09/discovering-espresso-for-android-how-to.html
    fun currentActivity(): Activity? {
        val currentActivity = arrayOfNulls<Activity>(1)
        InstrumentationRegistry.getInstrumentation()
            .runOnMainSync {
                val activityMonitor =
                    ActivityLifecycleMonitorRegistry.getInstance()
                val resumedActivities =
                    activityMonitor.getActivitiesInStage(Stage.RESUMED)
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity[0] = resumedActivities.iterator().next()
                }
            }
        checkNotNull(currentActivity[0]) { "There is no activity that is in the RESUMED state." }
        return currentActivity[0]
    }
}