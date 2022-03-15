package comp3350.sceneit.presentation;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class AcceptanceTestCreditActivity {


    //Acceptance test. Goes through the steps to make it to the credit card activity. Then
    //Checks all data from previous page was displayed correctly at the top.
    //Then enter all correct info into every field and makes sure that the app open
    //The main activity once the Confirm Purchase button is pressed.
    @Test
    public void AllCorrectInfo() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), LoginActivity.class);

        try(ActivityScenario<CreditActivity> scenario = ActivityScenario.launch(intent)){

            ViewInteraction appCompatEditText = onView(
                    allOf(withId(R.id.signInUserNames)));
            appCompatEditText.perform(replaceText("a"), closeSoftKeyboard());

            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.signInUserPassword)));
            appCompatEditText2.perform(replaceText("aaaaaa"), closeSoftKeyboard());

            ViewInteraction materialButton = onView(
                    allOf(withId(R.id.logInBtn)));
            materialButton.perform(click());

            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.rvNowPlaying)));
            recyclerView.perform(actionOnItemAtPosition(0, click()));

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.editTextNumberofTickets)));
            appCompatEditText3.perform(replaceText("10"));

            ViewInteraction appCompatEditText4 = onView(
                    allOf(withId(R.id.editTextNumberofTickets)));
            appCompatEditText4.perform(closeSoftKeyboard());

            ViewInteraction appCompatEditText5 = onView(
                    allOf(withId(R.id.editTextCalender)));
            appCompatEditText5.perform(click());

            ViewInteraction materialButton2 = onView(
                    allOf(withId(android.R.id.button1)));
            materialButton2.perform(click());

            ViewInteraction toggleButton = onView(
                    allOf(withText("12:30 PM\n$10")));
            toggleButton.perform(click());

            ViewInteraction appCompatEditText6 = onView(
                    allOf(withId(R.id.editTextNumberofTickets)));
            appCompatEditText6.perform(pressImeActionButton());

            ViewInteraction materialButton3 = onView(
                    allOf(withId(R.id.buttonOrderTickets)));
            materialButton3.perform(click());

            ViewInteraction materialButton4 = onView(
                    allOf(withId(android.R.id.button1)));
            materialButton4.perform(scrollTo(), click());

            ViewInteraction appCompatEditText8 = onView(
                    allOf(withId(R.id.nameCard)));
            appCompatEditText8.perform(replaceText("John Doe"), closeSoftKeyboard());

            ViewInteraction appCompatEditText9 = onView(
                    allOf(withId(R.id.numberCard)));
            appCompatEditText9.perform(replaceText("4024007197504847"), closeSoftKeyboard());

            ViewInteraction appCompatEditText10 = onView(
                    allOf(withId(R.id.cvc)));
            appCompatEditText10.perform(replaceText("111"), closeSoftKeyboard());

            ViewInteraction appCompatEditText11 = onView(
                    allOf(withId(R.id.expDate)));
            appCompatEditText11.perform(replaceText("1124"), closeSoftKeyboard());

            ViewInteraction appCompatEditText12 = onView(
                    allOf(withId(R.id.country)));
            appCompatEditText12.perform(replaceText("Canada"), closeSoftKeyboard());

            ViewInteraction appCompatEditText13 = onView(
                    allOf(withId(R.id.province)));
            appCompatEditText13.perform(replaceText("Manitoba"), closeSoftKeyboard());

            ViewInteraction appCompatEditText14 = onView(
                    allOf(withId(R.id.addressOne)));
            appCompatEditText14.perform(replaceText("Address"), closeSoftKeyboard());

            ViewInteraction appCompatEditText15 = onView(
                    allOf(withId(R.id.city)));
            appCompatEditText15.perform(replaceText("Winnipeg"), closeSoftKeyboard());

            ViewInteraction appCompatEditText16 = onView(
                    allOf(withId(R.id.postalCode)));
            appCompatEditText16.perform(replaceText("R0C3Z3"), closeSoftKeyboard());

            ViewInteraction appCompatEditText17 = onView(
                    allOf(withId(R.id.telephoneNumber)));
            appCompatEditText17.perform(replaceText("2222222"), closeSoftKeyboard());

            ViewInteraction appCompatEditText18 = onView(
                    allOf(withId(R.id.email)));
            appCompatEditText18.perform(replaceText("old@hotmail.com"), closeSoftKeyboard());

            Intents.init();

            ViewInteraction materialButton5 = onView(
                    allOf(withId(R.id.button)));
            materialButton5.perform(click());
            //Check we actually made it to main activity
            intended(hasComponent(MainActivity.class.getName()));
            Intents.release();

        }
    }


    //The purpose of this test is to test that all the alerts display with the correct message on the screen.
    //Navigate to the Credit page and hit confirm purchase. Enter the info the alert tells you to enter
    //then hit confirm purchase. Continue till no alerts pop up and you are back on the main movie page.
    @Test
    public void TestAllAlerts() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), LoginActivity.class);

        try(ActivityScenario<CreditActivity> scenario = ActivityScenario.launch(intent)){

            ViewInteraction appCompatEditText = onView(
                    allOf(withId(R.id.signInUserNames)));
            appCompatEditText.perform(replaceText("a"), closeSoftKeyboard());

            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.signInUserPassword)));
            appCompatEditText2.perform(replaceText("aaaaaa"), closeSoftKeyboard());

            ViewInteraction materialButton = onView(
                    allOf(withId(R.id.logInBtn)));
            materialButton.perform(click());

            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.rvNowPlaying)));
            recyclerView.perform(actionOnItemAtPosition(0, click()));

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.editTextNumberofTickets)));
            appCompatEditText3.perform(replaceText("10"));

            ViewInteraction appCompatEditText4 = onView(
                    allOf(withId(R.id.editTextNumberofTickets)));
            appCompatEditText4.perform(closeSoftKeyboard());

            ViewInteraction appCompatEditText5 = onView(
                    allOf(withId(R.id.editTextCalender)));
            appCompatEditText5.perform(click());

            ViewInteraction materialButton2 = onView(
                    allOf(withId(android.R.id.button1)));
            materialButton2.perform(click());

            ViewInteraction toggleButton = onView(
                    allOf(withText("12:30 PM\n$10")));
            toggleButton.perform(click());

            ViewInteraction appCompatEditText6 = onView(
                    allOf(withId(R.id.editTextNumberofTickets)));
            appCompatEditText6.perform(pressImeActionButton());

            ViewInteraction materialButton3 = onView(
                    allOf(withId(R.id.buttonOrderTickets)));
            materialButton3.perform(click());

            ViewInteraction materialButton4 = onView(
                    allOf(withId(android.R.id.button1)));
            materialButton4.perform(scrollTo(), click());

            ViewInteraction PurchaseButton = onView(
                    allOf(withId(R.id.button)));
            PurchaseButton.perform(click());

            ViewInteraction textView2 = onView(
                    allOf(withId(android.R.id.message)));
            textView2.check(matches(withText("Invalid Credit Card Number")));

            ViewInteraction AlertOKButton = onView(
                    allOf(withId(android.R.id.button1)));
            AlertOKButton.perform(scrollTo(), click());

            ViewInteraction appCompatEditText9 = onView(
                    allOf(withId(R.id.numberCard)));
            appCompatEditText9.perform(replaceText("4024007197504847"), closeSoftKeyboard());

            PurchaseButton.perform(click());

            textView2.check(matches(withText("Please fill in the name located on your Credit card")));

            AlertOKButton.perform(click());

            ViewInteraction appCompatEditText8 = onView(
                    allOf(withId(R.id.nameCard)));
            appCompatEditText8.perform(replaceText("John Doe"), closeSoftKeyboard());

            PurchaseButton.perform(click());

            textView2.check(matches(withText("Please fill in the cvc located on your Credit card")));

            AlertOKButton.perform(click());

            ViewInteraction appCompatEditText10 = onView(
                    allOf(withId(R.id.cvc)));
            appCompatEditText10.perform(replaceText("111"), closeSoftKeyboard());

            PurchaseButton.perform(click());

            textView2.check(matches(withText("This card may be expired, check your expiry date")));

            AlertOKButton.perform(click());

            ViewInteraction appCompatEditText11 = onView(
                    allOf(withId(R.id.expDate)));
            appCompatEditText11.perform(replaceText("1124"), closeSoftKeyboard());

            PurchaseButton.perform(click());

            textView2.check(matches(withText("Please fill in your country")));

            AlertOKButton.perform(click());

            ViewInteraction appCompatEditText12 = onView(
                    allOf(withId(R.id.country)));
            appCompatEditText12.perform(replaceText("Canada"), closeSoftKeyboard());

            PurchaseButton.perform(click());

            textView2.check(matches(withText("Please fill in your Region/Province/State")));

            AlertOKButton.perform(click());

            ViewInteraction appCompatEditText13 = onView(
                    allOf(withId(R.id.province)));
            appCompatEditText13.perform(replaceText("Manitoba"), closeSoftKeyboard());

            PurchaseButton.perform(click());

            textView2.check(matches(withText("Please fill in your Address One")));

            AlertOKButton.perform(click());

            ViewInteraction appCompatEditText14 = onView(
                    allOf(withId(R.id.addressOne)));
            appCompatEditText14.perform(replaceText("Address"), closeSoftKeyboard());

            PurchaseButton.perform(click());

            textView2.check(matches(withText("Please fill in your city")));

            AlertOKButton.perform(click());

            ViewInteraction appCompatEditText15 = onView(
                    allOf(withId(R.id.city)));
            appCompatEditText15.perform(replaceText("Winnipeg"), closeSoftKeyboard());

            PurchaseButton.perform(click());

            textView2.check(matches(withText("Please fill in your Postal Code")));

            AlertOKButton.perform(click());

            ViewInteraction appCompatEditText16 = onView(
                    allOf(withId(R.id.postalCode)));
            appCompatEditText16.perform(replaceText("R0C3Z3"), closeSoftKeyboard());

            PurchaseButton.perform(click());

            textView2.check(matches(withText("Please fill in your Telephone")));

            AlertOKButton.perform(click());

            ViewInteraction appCompatEditText17 = onView(
                    allOf(withId(R.id.telephoneNumber)));
            appCompatEditText17.perform(replaceText("2222222"), closeSoftKeyboard());

            PurchaseButton.perform(click());

            textView2.check(matches(withText("Please fill in your email")));

            AlertOKButton.perform(click());

            ViewInteraction appCompatEditText18 = onView(
                    allOf(withId(R.id.email)));
            appCompatEditText18.perform(replaceText("old@hotmail.com"), closeSoftKeyboard());

            Intents.init();
            PurchaseButton.perform(click());
            intended(hasComponent(MainActivity.class.getName()));
            Intents.release();

        }
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
