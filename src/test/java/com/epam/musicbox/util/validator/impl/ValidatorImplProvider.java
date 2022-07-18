package com.epam.musicbox.util.validator.impl;

import org.testng.annotations.DataProvider;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ValidatorImplProvider {

    private static final String NULL_STRING = null;
    private static final String EMPTY_STRING = "";
    private static final String VERY_LONG_STRING = "1".repeat(65);
    private static final String BAD_STRING = IntStream.range(0, 32)
            .mapToObj(Character::toString)
            .collect(Collectors.joining());

    @DataProvider
    public static Object[][] invalidLoginProvider() {
        return new Object[][]{
                {NULL_STRING},
                {EMPTY_STRING},
                {BAD_STRING},
                {VERY_LONG_STRING},
                {"login{}[]()<>"},
                {"1234)*&("},
        };
    }

    @DataProvider
    public static Object[][] validLoginProvider() {
        return new Object[][]{
                {"login"},
                {"1234"},
                {"login1234"},
                {"login 1234"},
                {"LoGiN1234 "},
        };
    }

    @DataProvider
    public static Object[][] invalidPasswordProvider() {
        return new Object[][]{
                {NULL_STRING},
                {EMPTY_STRING},
                {BAD_STRING},
                {VERY_LONG_STRING},
                {"pasSword{}[]()<>"},
                {"1234{}[]()<>"},
                {"pas<>sword1234"},
                {"passW[ord 1234"}
        };
    }

    @DataProvider
    public static Object[][] validPasswordProvider() {
        return new Object[][]{
                {"password"},
                {"PaSsWoRd"},
                {"12345678"},
                {"Password1234"},
                {"1234@$!%*#?&"},
                {"Password1234@$!%*#?&"}
        };
    }

    @DataProvider
    public static Object[][] invalidEmailProvider() {
        return new Object[][]{
                {NULL_STRING},
                {EMPTY_STRING},
                {BAD_STRING},
                {VERY_LONG_STRING},
                {"user.namemail.epam.com"},
                {"user@name@mail.epam.com"},
                {"user.name.@mail.epam.com"},
                {"user.name@@mail.epam.com"},
                {"user@name@@mail.epam.com"},
                {"user.name.@@mail.epam.com"},
                {"chrhrbst@@cs.epam.com"},
                {"walida.fayez@@mailbox.epam.com"},
                {"wazir@@jankhanman@kabul.com"},
                {"asgharzada85@@yahoo.com"},
                {"aaali@@mailbox.epam.com"},
                {"ashraf.tanin.@@yahoo.com"},
                {"hamid_khanzai@@mailbox.epam.com"},
                {"ghulam.s.ghaznawi@@mailbox.epam.com"},
                {"abdul.r.hamraz@@googlemail.com"}
        };
    }

    @DataProvider
    public static Object[][] validEmailProvider() {
        return new Object[][]{
                {"user.name@mail.epam.com"},
                {"chrname@cs.epam.com"},
                {"rb.magnus@mailbox.epam.com"},
                {"thamag@cs.epam.com"},
                {"ghezalahmad.zia@mailbox.epam.com"},
                {"ashraf.a.tanin@mailbox.epam.com"},
                {"ahmadnawid.mz@gmail.com"},
                {"aaali@mailbox.epam.com"},
                {"walida.fayez@mailbox.epam.com"},
                {"hamid_khanzai@yahoo.com"},
                {"sayedsahm@yahoo.com"},
                {"rafibahez@hotmail.com"},
                {"zabihisomayeh@yahoo.com"},
                {"somaia.zabihi@mailbox.epam.com"},
                {"ghulam.s.ghaznawi@mailbox.epam.com"},
                {"abdul.r.hamraz@googlemail.com"},
                {"test5@test.com"},
                {"test2@test.com"},
        };
    }

    @DataProvider
    public static Object[][] invalidNameProvider() {
        return new Object[][]{
                {NULL_STRING},
                {EMPTY_STRING},
                {BAD_STRING},
                {VERY_LONG_STRING},
        };
    }

    @DataProvider
    public static Object[][] validNameProvider() {
        return new Object[][]{
                {"name"},
                {"albumName"},
                {"1234"},
                {"Name1234"},
                {"name 1234"},
                {"Track (Remix[Version 12-34])"}
        };
    }

    @DataProvider
    public static Object[][] invalidAudioFileNameProvider() {
        return new Object[][]{
                {NULL_STRING},
                {EMPTY_STRING},
                {BAD_STRING},
                {VERY_LONG_STRING},
                {"audio"},
                {"1234"},
                {"audio1234"},
                {"@$!%*#?&-_ "},
        };
    }

    @DataProvider
    public static Object[][] validAudioFileNameProvider() {
        return new Object[][]{
                {"audio.mp3"},
                {"audio.wav"},
                {"1234.mp3"},
                {"1234.wav"},
                {"audio1234.mp3"},
                {"audio1224.wav"},
                {"@$!%*#?&-_ .mp3"},
        };
    }

    @DataProvider
    public static Object[][] invalidImageFileNameProvider() {
        return new Object[][]{
                {NULL_STRING},
                {EMPTY_STRING},
                {BAD_STRING},
                {VERY_LONG_STRING},
                {"image"},
                {"1234"},
                {"image1234"},
                {"@$!%*#?&-_ "},
        };
    }

    @DataProvider
    public static Object[][] validImageFileNameProvider() {
        return new Object[][]{
                {"image.gif"},
                {"image.jpeg"},
                {"image.jpg"},
                {"image.png"},
                {"1234.gif"},
                {"1234.jpeg"},
                {"1234.jpg"},
                {"1234.png"},
                {"image1234.gif"},
                {"image1224.jpeg"},
                {"image1234.jpg"},
                {"image1234.png"},
                {"@$!%*#?&-_ .png"},
        };
    }
}
