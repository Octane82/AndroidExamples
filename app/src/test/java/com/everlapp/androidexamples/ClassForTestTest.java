package com.everlapp.androidexamples;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 *
 * https://github.com/kirillF/technotrack-mockito  -- Mockito usage example
 */
public class ClassForTestTest {

    @Before
    public void setUp() throws Exception {
        // Init
    }

    @After
    public void tearDown() throws Exception {
        // clean resources
    }


    @BeforeClass
    public static void init() {
        // example - work with Db

        // Init DB
    }

    @AfterClass
    public static void cleanup() {
        // clear Db res
    }


    @Test
    public void testMethodOne() {
        assertEquals("Sum 2 values incorrect", 2 + 2, 4);
    }

}