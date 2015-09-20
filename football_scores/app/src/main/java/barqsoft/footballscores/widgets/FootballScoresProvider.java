package barqsoft.footballscores.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.scoresAdapter;

/**
 * Created by clerks on 9/18/15.
 */
public class FootballScoresProvider extends AppWidgetProvider {
    private static String TAG = FootballScoresProvider.class.getSimpleName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate");
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.football_score_widget);
            //views.setOnClickPendingIntent(R.id.home_crest, pendingIntent);

            Date fragmentdate = new Date(System.currentTimeMillis());
            SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
            String[] selectionArgs = new String[1];
            selectionArgs[0] = mformat.format(fragmentdate);

            Cursor cursor = context.getContentResolver().query(DatabaseContract.scores_table.buildScoreWithDate(),
                    null, null, selectionArgs, null);

            while (cursor.moveToNext()) {
                views.setTextViewText(R.id.homeNameTextView, cursor.getString(scoresAdapter.COL_HOME));
                String score = Utilies.getScores(cursor.getInt(scoresAdapter.COL_HOME_GOALS), cursor.getInt(scoresAdapter.COL_AWAY_GOALS));
                views.setTextViewText(R.id.scoreTextView, score);
                views.setTextViewText(R.id.awayNameTextView, cursor.getString(scoresAdapter.COL_AWAY));
            }
            cursor.close();

            Log.d(TAG, "Loading widget");

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
