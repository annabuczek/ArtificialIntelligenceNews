package com.example.android.artificialintelligencenews;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class ArticlesPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_fragment);

            // Find preference by its key and use a method to show value on UI
            Preference pageSize = findPreference(getString(R.string.page_size_pref_key));
            bindPrefSummaryToValue(pageSize);

            Preference category = findPreference(getString(R.string.category_pref_key));
            bindPrefSummaryToValue(category);
        }

        /**
         * Method to implement from interface onPreferenceChangeListener
         * To notify when Preference was changed by the user
         *
         * @param preference preference to be changed
         * @param newValue   new value of preference
         * @return tru if preference was updated
         */
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            // Convert new value of preference to String
            String preferenceValue = newValue.toString();

            //For List preference use Labels to show summary
            if (preference instanceof ListPreference) {
                // Bind given preference to ListPreference
                ListPreference listPreference = (ListPreference) preference;
                // Find index of preference list item
                int preferenceIndex = listPreference.findIndexOfValue(preferenceValue);
                if (preferenceIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[preferenceIndex]);
                }
            } else {
                // For other cases use new value to show summary
                preference.setSummary(preferenceValue);
            }
            return true;
        }

        /**
         * Helper method to show summary of Preference on UI
         */
        private void bindPrefSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}
