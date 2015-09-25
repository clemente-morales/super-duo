package barqsoft.footballscores.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import barqsoft.footballscores.security.Authenticator;

/**
 * Allows the sync adapter framework to access the Authenticator. This service provides an Android
 * Binder object that allows the framework to call the Authenticator and pass data between the
 * authenticator and the framework.
 * Created by clerks on 9/21/15.
 */
public class AuthenticatorService extends Service {
    private Authenticator authenticator;

    @Override
    public void onCreate() {
        authenticator = new Authenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
