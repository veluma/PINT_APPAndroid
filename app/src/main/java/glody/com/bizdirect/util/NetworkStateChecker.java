package glody.com.bizdirect.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStateChecker {

    private static NetworkStateChecker sNetworkStateChecker;
    private Context mAppContext;

    public NetworkStateChecker(Context appContext) {
        mAppContext = appContext;
    }

    //verifica o estado da internet
    public static NetworkStateChecker getInstance(Context appContext) {
        if (sNetworkStateChecker == null) {
            sNetworkStateChecker = new NetworkStateChecker(appContext);
        }

        return sNetworkStateChecker;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException(
                "Cloning NetworkStateChecker is not allowed.");
    }

    public boolean isNetworkConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) mAppContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}