package it.jaschke.alexandria.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by clerks on 9/26/15.
 */
public final class NetworkHelper {
    private NetworkHelper() {

    }

    /**
     * Allows you to check if the device has connection to the internet network.
     * @param context Application context.
     * @return If the device  has connection to the internet network.
     */
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
