package john.pazekha.bnp

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import assertk.assertThat
import assertk.assertions.*
import com.nhaarman.mockitokotlin2.*
import john.pazekha.bnp.controller.IController
import john.pazekha.bnp.controller.IController.STATE.*
import john.pazekha.bnp.model.Position
import john.pazekha.bnp.model.SYMBOL
import john.pazekha.bnp.model.Situation
import john.pazekha.bnp.view.IView
import john.pazekha.bnp.view.impl.MainActivity
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var mockController: IController
    private lateinit var viewCaptor: KArgumentCaptor<IView>
    private lateinit var activity: MainActivity

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java,
        true,  // initialTouchMode
        false  // launchActivity. False to customize the intent
    )


    @Before
    fun before() {
        mockController = Mockito.mock(IController::class.java)
        ClassFactory.setController(mockController)

        viewCaptor = argumentCaptor<IView>()

        activity = activityRule.launchActivity(Intent())
    }

    @After
    fun after() {
        ClassFactory.setController(null)
    }

    private fun getView() : IView {
        verify(mockController).setView(viewCaptor.capture())
        return viewCaptor.firstValue
    }

    @Test
    fun testMoveButtons() {
        /* ********  GIVEN ********* */
        val captor = argumentCaptor<Position>()

        /* ********  WHEN ********* */
        onView(withId(R.id.row1col2)).perform(click())
        onView(withId(R.id.row2col1)).perform(click())

        /* ********  THEN ********* */
        verify(mockController, times(2)).onUserMove(captor.capture())
        assertThat(captor.allValues).containsOnly(
            Position(1, 2),
            Position(2, 1)
        )
    }

    @Test
    fun testControlButtons() {
        /* ********  WHEN ********* */
        onView(withId(R.id.start)).perform(click())
        onView(withId(R.id.stop)).perform(click())

        /* ********  THEN ********* */
        verify(mockController, times(1)).onStartGame(any())
        verify(mockController, times(1)).onStopGame()
    }

    @Test
    fun testStateBeforeGame() {
        /* ********  GIVEN ********* */
        val view = getView()

        /* ********  WHEN ********* */
        activity.runOnUiThread {
            view.setState(BEFORE_GAME)
        }

        /* ********  THEN ********* */
        getInstrumentation().waitForIdleSync()
        Thread.sleep(100)

        onView(withId(R.id.start)).check(matches(isEnabled()))
        onView(withId(R.id.stop)).check(matches(not(isEnabled())))

        onView(withId(R.id.radioCross)).check(matches(isEnabled()))
        onView(withId(R.id.radioDonut)).check(matches(isEnabled()))

        onView(withId(R.id.row0col0)).check(matches(not(isEnabled())))
        onView(withId(R.id.row0col1)).check(matches(not(isEnabled())))
        onView(withId(R.id.row0col2)).check(matches(not(isEnabled())))
        onView(withId(R.id.row1col0)).check(matches(not(isEnabled())))
        onView(withId(R.id.row1col1)).check(matches(not(isEnabled())))
        onView(withId(R.id.row1col2)).check(matches(not(isEnabled())))
        onView(withId(R.id.row2col0)).check(matches(not(isEnabled())))
        onView(withId(R.id.row2col1)).check(matches(not(isEnabled())))
        onView(withId(R.id.row2col2)).check(matches(not(isEnabled())))
    }

    @Test
    fun testStateInGame() {
        /* ********  GIVEN ********* */
        val view = getView()

        /* ********  WHEN ********* */
        activity.runOnUiThread {
            view.setState(IN_GAME)
        }

        /* ********  THEN ********* */
        getInstrumentation().waitForIdleSync()
        Thread.sleep(100)

        onView(withId(R.id.start)).check(matches(not(isEnabled())))
        onView(withId(R.id.stop)).check(matches(isEnabled()))

        onView(withId(R.id.radioCross)).check(matches(not(isEnabled())))
        onView(withId(R.id.radioDonut)).check(matches(not(isEnabled())))
    }

    @Test
    fun testStateGameOver() {
        /* ********  GIVEN ********* */
        val view = getView()

        /* ********  WHEN ********* */
        activity.runOnUiThread {
            view.setState(GAME_OVER)
        }

        /* ********  THEN ********* */
        getInstrumentation().waitForIdleSync()
        Thread.sleep(100)

        onView(withId(R.id.start)).check(matches(isEnabled()))
        onView(withId(R.id.stop)).check(matches(not(isEnabled())))

        onView(withId(R.id.radioCross)).check(matches(isEnabled()))
        onView(withId(R.id.radioDonut)).check(matches(isEnabled()))
    }

    @Test
    fun testSituation() {
        /* ********  GIVEN ********* */
        val array = Array(3) {Array(3) {SYMBOL.BLANK} }
        array[1][0] = SYMBOL.CROSS
        array[2][1] = SYMBOL.DONUT
        val mockSituation = Situation(array)

        val view = getView()

        /* ********  WHEN ********* */
        activity.runOnUiThread {
            view.setState(IN_GAME)
            view.setSituation(mockSituation)
        }

        /* ********  THEN ********* */
        getInstrumentation().waitForIdleSync()
        Thread.sleep(100)

        /* ********  THEN ********* */
        onView(withId(R.id.row0col0)).check(matches(isEnabled()))
        onView(withId(R.id.row0col1)).check(matches(isEnabled()))
        onView(withId(R.id.row0col2)).check(matches(isEnabled()))
        onView(withId(R.id.row1col0)).check(matches(not(isEnabled()))) // Cross
        onView(withId(R.id.row1col0)).check(matches(withText("X")))    // Cross
        onView(withId(R.id.row1col1)).check(matches(isEnabled()))
        onView(withId(R.id.row1col2)).check(matches(isEnabled()))
        onView(withId(R.id.row2col0)).check(matches(isEnabled()))
        onView(withId(R.id.row2col1)).check(matches(not(isEnabled()))) // Donut
        onView(withId(R.id.row2col1)).check(matches(withText("O")))    // Donut
        onView(withId(R.id.row2col2)).check(matches(isEnabled()))
    }
}
