package com.epam.musicbox.util.hasher.impl;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class PBKDF2HasherTest {

    @DataProvider
    public Object[][] plainTextProvider() {
        return new Object[][]{
                {null},
                {""},
                {"1".repeat(64)},
                {"~!@#$%^&".repeat(16)},
                {"\u0000\u0001\u0002\u0003\u0004"},
                {"\r\n password\t"},
                {"password"},
                {"PASSWORD"},
                {"12345678"},
                {"!@#$%^&*IU"},
        };
    }

    private PBKDF2Hasher hasher;

    @BeforeClass
    public void setUp() {
        hasher = PBKDF2Hasher.getInstance();
    }

    @Test(groups = {"hasher"}, dataProvider = "plainTextProvider")
    public void testHasher(String plain) {
        String hash = hasher.hash(plain);
        assertTrue(hasher.check(plain, hash));
    }
}
