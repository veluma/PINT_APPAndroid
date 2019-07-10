package glody.com.bizdirect;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import glody.com.bizdirect.util.AppCompatPreferenceActivity;
import glody.com.bizdirect.util.SessionHandler;


public class settings extends AppCompatPreferenceActivity {
    private SharedPreferences mPreferences;
    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    private static Context mContext;
    private static SessionHandler session;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        session= new SessionHandler(getApplicationContext());
        mContext = this.getApplicationContext();
        SharedPreferences _sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Toast.makeText(getApplicationContext(),
                _sharedPreferences.getString("full_name",""), Toast.LENGTH_SHORT).show();

    }

    public static void goToLoginActivity() {
        session.logoutUser();
        Intent login = new Intent(mContext, MainActivity.class);
        login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(login);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<PreferenceActivity.Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //
            int preferenceFile_toLoad = -1;
            String settings = getArguments().getString("settings");
            if ("prefs_general".equalsIgnoreCase(settings)) {
                // Load the preferences from an XML resource
                preferenceFile_toLoad = R.xml.pref_general;
            } else if ("prefs_notification".equalsIgnoreCase(settings)) {
                // Load the preferences from an XML resource
                preferenceFile_toLoad = R.xml.pref_notification;
            } else if ("prefs_sync".equals(settings)) {
                // Load the preferences from an XML resource
                preferenceFile_toLoad = R.xml.pref_data_sync;
            }
            else if ("sair".equals(settings)) {
                // Load the preferences from an XML resource
                goToLoginActivity();

            }

           addPreferencesFromResource(preferenceFile_toLoad);


        }


    }
}


