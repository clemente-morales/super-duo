package barqsoft.footballscores.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import barqsoft.footballscores.receivers.AlarmReceiver;

/**
 * Created by clerks on 9/24/15.
 */
public class FootballscoreAlarmManager {
    private static final String TAG = FootballscoreAlarmManager.class.getSimpleName();

    public static void setScoresUpdateEveryHour(Context context) {
        Intent senderPhoneCallHistory = new Intent(context, AlarmReceiver.class);
        PendingIntent recurringDownloadInformation = PendingIntent.getBroadcast(context,
                0, senderPhoneCallHistory, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(
                Context.ALARM_SERVICE);
        alarms.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, AlarmManager.INTERVAL_HOUR,
                AlarmManager.INTERVAL_HOUR, recurringDownloadInformation);
    }

    public static void cancelAlarm(Context context) {
        AlarmManager alarms = (AlarmManager) context.getSystemService(
                Context.ALARM_SERVICE);
        Intent senderPhoneCallHistory = new Intent(context, AlarmReceiver.class);
        PendingIntent recurringSendingInformation = PendingIntent.getBroadcast(context,
                0, senderPhoneCallHistory, PendingIntent.FLAG_CANCEL_CURRENT);
        alarms.cancel(recurringSendingInformation);
    }
}
