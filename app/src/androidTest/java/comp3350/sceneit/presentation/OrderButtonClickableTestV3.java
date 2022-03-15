package comp3350.sceneit.presentation;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.sceneit.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class OrderButtonClickableTestV3 {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void orderButtonClickableTestV3() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.signInUserNames)));
        appCompatEditText.perform(replaceText("111"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.signInUserPassword)));
        appCompatEditText2.perform(replaceText("111111"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.logInBtn)));
        materialButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rvNowPlaying)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editTextCalender)));
        appCompatEditText3.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1)));
        materialButton2.perform(click());

        ViewInteraction toggleButton = onView(
                allOf(withText("12:30 PM\n$10")));
        toggleButton.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editTextNumberofTickets)));
        appCompatEditText4.perform(replaceText("10"));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.editTextNumberofTickets)));
        appCompatEditText5.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.editTextNumberofTickets)));
        appCompatEditText6.perform(pressImeActionButton());

        ViewInteraction button = onView(
                allOf(withId(R.id.buttonOrderTickets)));
        button.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
