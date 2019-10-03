package com.everlapp.androidexamples;

import android.content.Context;
import android.content.Intent;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class ClassForTestInstrumentationTest {

    // targetContext - это контекст того приложения, которое тестируем, а не тестового приложения
    private Context targetContext = InstrumentationRegistry.getTargetContext();

    // 1- имя класса активити
    // 2 - активность реагирует или не реагирует на нажатие пальца
    // 3 - стоит ли каждый раз пересоздавать активити перед вызовом каждого метода с аннотацией Test
    @Rule
    public ActivityTestRule<MainActivity> mMainActivityRule =
            new ActivityTestRule<>(MainActivity.class, false, false);


    // public ServiceTestRule


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    private Intent getMainActivityLaunchIntent() {
        Intent intent = new Intent(targetContext, MainActivity.class);
        return intent;
    }


    @Test
    public void testMethodOne() throws Throwable {
        mMainActivityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // get UI views
            }
        });
    }

    @Test
    public void testActivityUnAuthorized() throws Throwable {
        final MainActivity mainActivity = mMainActivityRule.launchActivity(getMainActivityLaunchIntent());

        mMainActivityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tvAndroidExamples = (TextView) mainActivity.findViewById(R.id.tvAndroidExamples);
                assertEquals("Android examples: User register success!", tvAndroidExamples.getText().toString());
            }
        });

    }


}