package barqsoft.footballscores;

import android.app.Application;

import barqsoft.footballscores.utils.FootballscoreAlarmManager;

/**
 * Created by clerks on 9/24/15.
 */
public class FootballscoresApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FootballscoreAlarmManager.setScoresUpdateEveryHour(this);
    }
}
