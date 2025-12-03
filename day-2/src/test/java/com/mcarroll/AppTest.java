package com.mcarroll;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testRepeat() {
        assertNotNull(App.findRepeat((long) 575757, false));
        assertNotNull(App.findRepeat((long) 55555, false));
        assertNotNull(App.findRepeat((long) 111, false));
        assertNotNull(App.findRepeat((long) 88, false));
        assertNotNull(App.findRepeat((long) 577577, true));
        assertNull(App.findRepeat((long) 57577, true));
    }
}
