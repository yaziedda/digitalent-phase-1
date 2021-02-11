package io.github.yaziedda.submission2.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.github.yaziedda.submission2.R;
import io.github.yaziedda.submission2.notification.DailyReceiver;
import io.github.yaziedda.submission2.notification.MovieReleaseReceiver;
import io.github.yaziedda.submission2.notification.NotificationPreference;

import static io.github.yaziedda.submission2.util.Static.KEY_FIELD_DAILY_REMINDER;
import static io.github.yaziedda.submission2.util.Static.KEY_FIELD_UPCOMING_REMINDER;
import static io.github.yaziedda.submission2.util.Static.KEY_HEADER_DAILY_REMINDER;
import static io.github.yaziedda.submission2.util.Static.KEY_HEADER_UPCOMING_REMINDER;
import static io.github.yaziedda.submission2.util.Static.TYPE_REMINDER_PREF;
import static io.github.yaziedda.submission2.util.Static.TYPE_REMINDER_RECIEVE;

public class SettingActivity extends AppCompatActivity {

    Switch dailyReminder;
    Switch releaseReminder;
    TextView button;

    public DailyReceiver dailyReceiver;
    public MovieReleaseReceiver movieReleaseReceiver;
    public NotificationPreference notificationPreference;
    public SharedPreferences sReleaseReminder, sDailyReminder;
    public SharedPreferences.Editor editorReleaseReminder, editorDailyReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        button = findViewById(R.id.local_setting);
        dailyReminder = findViewById(R.id.daily_reminder);
        releaseReminder = findViewById(R.id.release_Reminder);

        movieReleaseReceiver = new MovieReleaseReceiver();
        dailyReceiver = new DailyReceiver();
        notificationPreference = new NotificationPreference(this);
        setPreference();

        dailyReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editorDailyReminder = sDailyReminder.edit();
            if (isChecked) {
                editorDailyReminder.putBoolean(KEY_FIELD_DAILY_REMINDER, true);
                editorDailyReminder.commit();
                dailyReminderOn();
            } else {
                editorDailyReminder.putBoolean(KEY_FIELD_DAILY_REMINDER, false);
                editorDailyReminder.commit();
                dailyReminderOff();
            }
        });

        releaseReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editorReleaseReminder = sReleaseReminder.edit();
            if (isChecked) {
                editorReleaseReminder.putBoolean(KEY_FIELD_UPCOMING_REMINDER, true);
                editorReleaseReminder.commit();
                releaseReminderOn();
            } else {
                editorReleaseReminder.putBoolean(KEY_FIELD_UPCOMING_REMINDER, false);
                editorReleaseReminder.commit();
                releaseReminderOff();
            }
        });

        button.setOnClickListener(v -> {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        });


    }

    private void releaseReminderOn() {
        String time = "08:00";
        String message = getResources().getString(R.string.release_movie_message);
        notificationPreference.setReminderReleaseTime(time);
        notificationPreference.setReminderReleaseMessage(message);
        movieReleaseReceiver.setAlarm(SettingActivity.this, TYPE_REMINDER_PREF, time, message);
    }

    private void releaseReminderOff() {
        movieReleaseReceiver.cancelAlarm(SettingActivity.this);
    }

    private void dailyReminderOn() {
        String time = "07:00";
        String message = getResources().getString(R.string.daily_reminder);
        notificationPreference.setReminderDailyTime(time);
        notificationPreference.setReminderDailyMessage(message);
        dailyReceiver.setAlarm(SettingActivity.this, TYPE_REMINDER_RECIEVE, time, message);
    }

    private void dailyReminderOff() {
        dailyReceiver.cancelAlarm(SettingActivity.this);
    }

    private void setPreference() {
        sReleaseReminder = getSharedPreferences(KEY_HEADER_UPCOMING_REMINDER, MODE_PRIVATE);
        sDailyReminder = getSharedPreferences(KEY_HEADER_DAILY_REMINDER, MODE_PRIVATE);
        boolean checkUpcomingReminder = sReleaseReminder.getBoolean(KEY_FIELD_UPCOMING_REMINDER, false);
        releaseReminder.setChecked(checkUpcomingReminder);
        boolean checkDailyReminder = sDailyReminder.getBoolean(KEY_FIELD_DAILY_REMINDER, false);
        dailyReminder.setChecked(checkDailyReminder);
    }
}
