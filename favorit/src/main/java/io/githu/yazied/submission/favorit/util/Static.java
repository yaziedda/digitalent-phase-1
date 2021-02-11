package io.githu.yazied.submission.favorit.util;


import io.githu.yazied.submission.favorit.BuildConfig;

/**
 * Created by yaziedda on 2019-11-11.
 */
public class Static {
    public static final String HOST = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "7b12624313469a963dfa28cedec6e6b6";
    public static final String getUrlPoster(String type, String name) {
        return "https://image.tmdb.org/t/p/" + type + "/" + name;
    }
    public static final String MyPref = BuildConfig.APPLICATION_ID;
    public static final String POSTER_w92 = "w92";
    public static final String POSTER_w154 = "w154";
    public static final String POSTER_w185 = "w185";
    public static final String POSTER_w342 = "w342";
    public static final String POSTER_w500 = "w500";
    public static final String POSTER_w780 = "w780";
    public static final String POSTER_original = "original";

    public static final String KEY_MOVIES = "KEY_MOVIES";
    public static final String KEY_TVSHOW = "KEY_TVSHOW";

    public final static int NOTIFICATION_ID = 501;
    public final static int NOTIFICATION_ID_ = 502;
    public final static String NOTIFICATION_CHANNEL_ID = "601";

    public static final String EXTRA_MESSAGE_PREF = "message";
    public static final String EXTRA_TYPE_PREF = "type";

    public static final String TYPE_REMINDER_PREF = "reminderAlarm";
    public static final String EXTRA_MESSAGE_RECIEVE = "messageRelease";
    public static final String EXTRA_TYPE_RECIEVE = "typeRelease";
    public final static String KEY_REMINDER_DAILY = "DailyReminder";
    public final static String KEY_REMINDER_MESSAGE_Release = "reminderMessageRelease";
    public final static String KEY_REMINDER_MESSAGE_Daily = "reminderMessageDaily";

    public static final String KEY_HEADER_UPCOMING_REMINDER = "upcomingReminder";
    public static final String KEY_HEADER_DAILY_REMINDER = "dailyReminder";
    public static final String KEY_FIELD_UPCOMING_REMINDER = "checkedUpcoming";
    public static final String KEY_FIELD_DAILY_REMINDER = "checkedDaily";

    public static final String TYPE_REMINDER_RECIEVE = "reminderAlarmRelease";
}
