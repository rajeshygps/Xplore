package com.hclsample.util

import android.content.Intent
import android.provider.Settings
import androidx.test.espresso.Espresso
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiSelector

/**
 * Provides methods that allows tests to manipulate device under test.
 */
class Device {
    val instrumentationUtil = InstrumentationUtil()
    fun closeSoftKeyboard() {
        Espresso.closeSoftKeyboard()
    }

    fun pressBackButton() {
        Espresso.pressBack()
    }

    /**
     * Performs a sleep on the current thread.
     *
     *
     * IMPORTANT! This should never be used unless absolutely necessary. Necessary cases should have
     * FIXMEs... Only use this as a LAST RESORT for a TEMPORARY ISSUE.
     *
     * @param millis the amount of time to sleep in milliseconds
     */
    fun sleep(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (ie: InterruptedException) {
            // do nothing
        }
    }

    /**
     * Uses an [androidx.test.espresso.IdlingResource] to pause and halt execution of
     * the test method until it times out (and fails). This is useful for debugging what happens at
     * a certain point of a test method. Usages of this method should not be committed. It is only
     * for local debugging purposes.
     */
    fun haltTest() {
        val idlingResource = CountingIdlingResource("HALT_TEST")
        Espresso.registerIdlingResources(idlingResource)
        idlingResource.increment()
    }

    @Throws(Exception::class)
    protected fun clickLocationSlider() {
        val mDevice =
            UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val currentActivity = instrumentationUtil.currentActivity()
        //go directly to location settings in App Settings
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        currentActivity!!.startActivity(intent)
        //directly toggle the slider. The resource id of the button was found using the Uiautomator viewer tool
        mDevice.findObject(UiSelector().resourceId("com.android.settings:id/switch_widget")).click()
        //close the dialog that sometimes shows on API 29 after toggling location settings
        denyLocationAndDismissPopupsIfShown()
        mDevice.pressBack()
    }

    protected fun selectOptionOnDialogWithText(text: String?) {
        val mDevice =
            UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val optionButton = mDevice.findObject(UiSelector().textMatches(text))
        try {
            optionButton.click()
        } catch (exception: UiObjectNotFoundException) {
        }
    }

    protected fun denyLocationAndDismissPopupsIfShown() {
        selectOptionOnDialogWithText("CLOSE")
    }

    fun allowLocationAndDismissPopupsIfShown() {
        selectOptionOnDialogWithText("Allow all the time")
        selectOptionOnDialogWithText("ALLOW")
    }
}