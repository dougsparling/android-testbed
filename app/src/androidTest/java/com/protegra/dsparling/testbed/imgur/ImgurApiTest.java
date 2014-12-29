package com.protegra.dsparling.testbed.imgur;

import junit.framework.TestCase;

public class ImgurApiTest extends TestCase {
    public void testApi() {
        assertTrue(Imgur.API.latestMemes(1).toBlocking().first().success);
    }
}
