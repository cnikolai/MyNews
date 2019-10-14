package com.nikolai.mynews;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.nikolai.mynews.SharedPreferencesWrapper.LAST_UPDATED;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
     * Unit tests for the {@link SharedPreferencesWrapper} that mocks {@link SharedPreferences}.
     */
    @RunWith(MockitoJUnitRunner.class)
    public class SharedPreferencesWrapperTest {
        //private static final String TEST_DATE = "2019-10-04T16:25:00Z";
        //private static final Boolean TEST_ISCHECKED = false;

        @Mock
    SharedPreferences mMockSharedPreferences;
        @Mock
    SharedPreferences.Editor mMockEditor;
        @Mock
    SharedPreferences.Editor mMockBrokenEditor;
        private SharedPreferencesWrapper mSharedPreferencesWrapper;

        @Before
    public void setup() {
        // Create SharedPreferenceEntry to persist.
        //mSharedPreferenceEntry = new SharedPreferenceEntry(TEST_DATE, TEST_ISCHECKED);
        initMocks(this);
        // Create a mocked SharedPreferences.
        mSharedPreferencesWrapper = new SharedPreferencesWrapper(mMockSharedPreferences);
        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);
    }

    @Test
    public void sharedPreferencesWrapper_SaveAndReadInformation() {
        // given, arrange
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy", Locale.US);
        // when, act
        mSharedPreferencesWrapper.saveLastUpdate(date);
        // then, assert
        verify(mMockEditor).apply();
        verify(mMockEditor).putString(LAST_UPDATED,dateFormat.format(date));
    }
}
