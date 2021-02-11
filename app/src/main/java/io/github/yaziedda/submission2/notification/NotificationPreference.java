package io.github.yaziedda.submission2.notification;

import android.content.Context;
import android.content.SharedPreferences;

import static io.github.yaziedda.submission2.util.Static.KEY_REMINDER_DAILY;
import static io.github.yaziedda.submission2.util.Static.KEY_REMINDER_MESSAGE_Daily;
import static io.github.yaziedda.submission2.util.Static.KEY_REMINDER_MESSAGE_Release;
import static io.github.yaziedda.submission2.util.Static.MyPref;

public class NotificationPreference {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public NotificationPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setReminderReleaseTime(String time){
        editor.putString(KEY_REMINDER_DAILY,time);
        editor.commit();
    }
    public void setReminderReleaseMessage (String message){
        editor.putString(KEY_REMINDER_MESSAGE_Release,message);
    }
    public void setReminderDailyTime(String time){
        editor.putString(KEY_REMINDER_DAILY,time);
        editor.commit();
    }
    public void setReminderDailyMessage(String message){
        editor.putString(KEY_REMINDER_MESSAGE_Daily,message);
    }
}
