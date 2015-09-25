package barqsoft.footballscores.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import barqsoft.footballscores.utils.FootballscoreAlarmManager;

/**
 * Allows to restart the alarm manager to update the football scores every hour.
 * Created by clerks on 9/25/15.
 */
public class DeviceBootReceiver extends BroadcastReceiver
{
    private static final String TAG = DeviceBootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Boot completed, enabling AlarmManger to update football scores");
        FootballscoreAlarmManager.setScoresUpdateEveryHour(context);
    }
}
