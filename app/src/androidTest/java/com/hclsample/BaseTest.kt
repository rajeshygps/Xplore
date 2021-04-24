package com.hclsample

import android.content.Intent
import androidx.annotation.CallSuper
import androidx.paging.ExperimentalPagingApi
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hclsample.rajesh.xplore.model.Product
import com.hclsample.rajesh.xplore.model.ProductListResponse
import com.hclsample.rajesh.xplore.util.Device
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestName
import org.junit.runner.RunWith
import java.util.*

/**
 * The base class that all test classes must extend. This class defines the
 * [.activityTestRule]. It also declares and instantiates all of the different testable
 * screens that the application displays.
 *
 *
 * MSP is disabled by default.
 *
 *
 * Note that the instantiation of all screens in this base class is done for convenience purposes.
 * Not every test class will use all of the defined screens here. Instantiating all of these screen
 * objects here does not affect performance as screen objects are very light- only containing
 * method signatures.
 */
@RunWith(AndroidJUnit4::class)
@ExperimentalPagingApi
abstract class BaseTest {
    /**
     * Allows us to retrieve the current test method name that is running. This is used for determining the
     */
    @Rule
    val testNameRule = TestName()

    /**
     * The [org.junit.rules.TestRule] that specifies that the starting activity for all tests
     * is the [MainActivity]. Unfortunately, the application flow is interdependent on different
     * Activities. This means that in order for the application to work properly, the splash activity
     * must first be traversed. Activities are not guaranteed to be independent from one another =(
     *
     *
     * Note that we do not immediately launch the activity, hence the third parameter being false
     * (default is true). We do this so we have to opportunity to execute some code in [.setUp]
     * before launching the activity. This is important in order to do things like clear preferences
     * before activity launch, among other things.
     *
     *
     * We are using the [IntentsTestRule], which is an extension of the regular
     * [androidx.test.rule.ActivityTestRule]. This allows us to optionally stub/mock
     * and validate intents sent out by the application. Note that this does not automatically
     * intercepts intents- it first has to be specified in the tests.
     *
     *
     * See https://developer.android.com/training/testing/espresso/intents.html
     */
    @Rule
    val activityTestRule = IntentsTestRule(
        MainActivity::class.java, true, false
    )
    @JvmField
    protected val productListScreen = ProductListScreen()
    protected val productsDetailsScreen = ProductsDetailsScreen()
    @JvmField
    protected val device = Device()

    /**
     * Called before the start of each test method (annotated with [org.junit.Test]).
     */
    @CallSuper
    @Before
    @Throws(Exception::class)
    fun setUp() {
        launchActivity()
    }

    @ExperimentalPagingApi
    private fun launchActivity() {
        activityTestRule.launchActivity(Intent())
        device.allowLocationAndDismissPopupsIfShown()
    }

    @CallSuper
    @After
    @Throws(Exception::class)
    fun cleanUp() {
        activityTestRule.finishActivity()
    }

    private fun testProductModel(): ProductListResponse {
        val products: MutableList<Product> = ArrayList()
        for (i in 0..28) {
            val product = Product(
                "003e3e6a-3f84-43ac-8ef3-a5ae2db0f80e",
                "Ellerton TV Console $i",
                "White Glove Delivery Included",
                "The Ellerton media console is well-suited for today&rsquo;s casual lifestyle.",
                "$949.00",
                "/images/image2.jpeg",
                2.5F,
                1,
                true
            )
            products.add(product)
        }
        return ProductListResponse(products, 30, 1, 30, 200)
    }
}