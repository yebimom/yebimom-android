/*
package com.yebimom.android.yebimom;


import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import junit.framework.TestCase;


public class FacebookLoginTest extends TestCase {

    public void testAll() throws Exception {
        //assertTrue(false);
        new FacebookButton().run();
    }


    public class FacebookButton extends UiAutomatorTestCase {
        // Facebook Login Button exist and show
        public void testisFacebookButtonExist() throws UiObjectNotFoundException {

            getUiDevice().pressHome();

        UiSelector se = new UiSelector();
        se.text("facebook");
        se.className("android.widget.Button");

        UiObject facebookButton = new UiObject(new UiSelector()
                .text("facebook")
                .className("android.widget.Button"));
        assertTrue("Facebook Button", facebookButton.exists());
        }
    }

}
*/
