package com.nikolai.mynews;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;import android.content.SharedPreferences;import java.util.Calendar;
import java.util.Date;

    /**
     * Unit tests for the {@link SharedPreferencesWrapper} that mocks {@link SharedPreferences}.
     */
    @RunWith(MockitoJUnitRunner.class)
    public class SharedPreferencesHelperTest {
        private static final String TEST_DATE = "2019-10-04T16:25:00Z";
        private static final Boolean TEST_ISCHECKED = false;

    private SharedPreferenceEntry mSharedPreferenceEntry;
        private SharedPreferencesWrapper mMockSharedPreferencesWrapper;
        private SharedPreferencesWrapper mMockBrokenSharedPreferencesWrapper;

        @Mock
    SharedPreferences mMockSharedPreferences;
        @Mock
    SharedPreferences mMockBrokenSharedPreferences;
        @Mock
    SharedPreferences.Editor mMockEditor;
        @Mock
    SharedPreferences.Editor mMockBrokenEditor;
    @Before
    public void initMocks() {
        // Create SharedPreferenceEntry to persist.
        mSharedPreferenceEntry = new SharedPreferenceEntry(TEST_DATE, TEST_ISCHECKED);
        // Create a mocked SharedPreferences.
        mMockSharedPreferencesWrapper = createMockSharedPreference();
        // Create a mocked SharedPreferences that fails at saving data.
        mMockBrokenSharedPreferencesWrapper = createBrokenMockSharedPreference();
    }

    @Test
    public void sharedPreferencesWrapper_SaveAndReadInformation() {
        // Save the information to SharedPreferences
        boolean success = mMockSharedPreferencesWrapper.saveLastUpdate(mSharedPreferenceEntry);
        assertThat("Checking that SharedPreferenceEntry.save... returns true",
                success, is(true));
        // Read information from SharedPreferences
        SharedPreferenceEntry savedSharedPreferenceEntry =
                mMockSharedPreferencesWrapper.getLastSeenUpdate();
        // Make sure both written and retrieved information are equal.
        assertThat("Checking that SharedPreferenceEntry.lastSeenUpdate has been persisted and read correctly",
                mSharedPreferenceEntry.getmLastUpdated(),
                is(equalTo(savedSharedPreferenceEntry.getLastSeenUpdate())));

    }

    @Test
    public void sharedPreferencesHelper_SaveInformationFailed_ReturnsFalse() {
        // Read personal information from a broken SharedPreferencesHelper
        boolean success =
                mMockBrokenSharedPreferencesWrapper.saveLastUpdate(mSharedPreferenceEntry);
        assertThat("Makes sure writing to a broken SharedPreferencesHelper returns false", success,
                is(false));
    }    /**
     * Creates a mocked SharedPreferences.
     */
    private SharedPreferencesWrapper createMockSharedPreference() {
        // Mocking reading the SharedPreferences as if mMockSharedPreferences was previously written
        // correctly.
        when(mMockSharedPreferences.getString(eq(SharedPreferencesHelperTest.TEST_DATE), anyString()))
                .thenReturn(mSharedPreferenceEntry.getmLastUpdated());
        when(mMockSharedPreferences.getBoolean(eq(SharedPreferencesHelperTest.TEST_ISCHECKED), anyBoolean()))
                .thenReturn(mSharedPreferenceEntry.getmIsChecked());

        // Mocking a successful commit.
        when(mMockEditor.commit()).thenReturn(true);
        // Return the MockEditor when requesting it.
        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);
        return new SharedPreferencesWrapper(mMockSharedPreferences);
    }    /**
     * Creates a mocked SharedPreferences that fails when writing.
     */
    private SharedPreferencesWrapper createBrokenMockSharedPreference() {
        // Mocking a commit that fails.
        when(mMockBrokenEditor.commit()).thenReturn(false);
        // Return the broken MockEditor when requesting it.
        when(mMockBrokenSharedPreferences.edit()).thenReturn(mMockBrokenEditor);
        return new SharedPreferencesWrapper(mMockBrokenSharedPreferences);
    }
    }
}
