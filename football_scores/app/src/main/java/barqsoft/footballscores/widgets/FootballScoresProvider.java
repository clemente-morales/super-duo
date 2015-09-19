package barqsoft.footballscores.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

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

            views.setTextViewText(R.id.homeNameTextView, "Barcelona");
            views.setTextViewText(R.id.scoreTextView, "2 : 0");
            views.setTextViewText(R.id.awayNameTextView, "Real Madrid");

            Log.d(TAG, "Loading widget");

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
