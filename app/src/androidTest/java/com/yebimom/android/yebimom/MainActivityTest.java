package com.yebimom.android.yebimom;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by corikachu on 15. 3. 10..
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest extends AndroidTestCase {

    public MainActivityTest(){
        super();
    }

    @Test
    public void defaultTest2(){
        assertTrue(true);
    }
}
