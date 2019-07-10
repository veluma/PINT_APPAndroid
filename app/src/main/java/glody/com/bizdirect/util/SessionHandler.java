package glody.com.bizdirect.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.Date;
import java.util.Set;

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "full_name";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_EMPTY = "";

    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;
    private SharedPreferences _sharedPreferences;


    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        _sharedPreferences= PreferenceManager.getDefaultSharedPreferences(mContext);
        this.mEditor = _sharedPreferences.edit();

    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param nome
     * @param email
     */
    public void loginUser(int id,String nome,String email,String morada,String Telefone) {

        mEditor.putInt("id", id);
        mEditor.putString(KEY_FULL_NAME, nome);
        mEditor.putString("email", email);
        mEditor.putString("morada", morada);
        mEditor.putString("telefone", Telefone);

        Date date = new Date();

       // Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = _sharedPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public user getUserDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        user user = new user();
        user.setId(_sharedPreferences.getInt("id",0 ));
        user.setNome(_sharedPreferences.getString(KEY_USERNAME, KEY_EMPTY));
        user.setEmail(_sharedPreferences.getString("email", KEY_EMPTY));
        user.setMorada(_sharedPreferences.getString("morada", KEY_EMPTY));
        user.setTelefone(_sharedPreferences.getString("telefone", KEY_EMPTY));
        user.setTelefone(_sharedPreferences.getString("dtnascimento", KEY_EMPTY));

        user.setSessionExpiryDate(new Date(_sharedPreferences.getLong(KEY_EXPIRES, 0)));

        return user;
    }

    /**
     * Logs out user by clearing the session
     */
    public  void logoutUser(){
        mEditor.clear();
        mEditor.commit();
    }

}