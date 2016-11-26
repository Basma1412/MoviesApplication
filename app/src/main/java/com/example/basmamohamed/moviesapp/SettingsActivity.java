package com.example.basmamohamed.moviesapp;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import static android.R.attr.value;
import static java.security.AccessController.getContext;

public class SettingsActivity extends PreferenceActivity  implements Preference.OnPreferenceChangeListener{


    InternetConnectivity iconnection;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iconnection = new InternetConnectivity(this);
         if (iconnection.isConnected()) {
             addPreferencesFromResource(R.xml.pref_general);
             bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_sort_key)));
         }
        else
         {
             Toast.makeText(this, "Please Try connecting to the internet and try again from settngs on create", Toast.LENGTH_LONG).show();

         }

    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        if (iconnection.isConnected()) {
            preference.setOnPreferenceChangeListener(this);
            onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }
        else
        {
            Toast.makeText(this, "Please Try connecting to the internet and try again from bind summary", Toast.LENGTH_LONG).show();

        }
    }
    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();
        if (iconnection.isConnected()) {
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }
        else
        {
            Toast.makeText(this, "Please Try connecting to the internet and try again from prefrence change", Toast.LENGTH_LONG).show();
            return false;
        }

    }
}