package com.ngenge.apps.notestaker.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.ngenge.apps.notestaker.R;
import com.ngenge.apps.notestaker.models.Note;

/**
 * Implementation of App Widget functionality.
 */
public class NoteWidget extends AppWidgetProvider {

    public static Note newNote;
    static String TAG = "Note";

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds,Note note) {


        newNote = note;
        Log.d(TAG, "updateAppWidget: called");
        for (int widgetId: appWidgetIds) {
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.note_widget);
            views.setTextViewText(R.id.notetitleTextView, note.getTitle());
            views.setTextViewText(R.id.noteBodyTextView,note.getDescription());

            Log.d(TAG, "updateAppWidget: "+ note.getDescription());
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(widgetId, views);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

            super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

