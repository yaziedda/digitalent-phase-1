package io.github.yaziedda.submission2.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.github.yaziedda.submission2.R;
import io.github.yaziedda.submission2.model.MoviesResult;
import io.github.yaziedda.submission2.model.ResponseMovies;
import io.github.yaziedda.submission2.networking.ApiInterface;
import io.github.yaziedda.submission2.networking.RetrofitService;
import io.github.yaziedda.submission2.util.Static;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.github.yaziedda.submission2.util.Static.KEY_FIELD_DAILY_REMINDER;
import static io.github.yaziedda.submission2.util.Static.KEY_FIELD_UPCOMING_REMINDER;
import static io.github.yaziedda.submission2.util.Static.KEY_HEADER_DAILY_REMINDER;
import static io.github.yaziedda.submission2.util.Static.KEY_HEADER_UPCOMING_REMINDER;

/**
 * Created by yaziedda on 2019-11-13.
 */
public class ReminderJobService extends JobService {

    public static final String TAG = ReminderJobService.class.getSimpleName();
    ApiInterface apiInterface;
    public SharedPreferences sReleaseReminder, sDailyReminder;

    @Override
    public boolean onStartJob(JobParameters params) {
        apiInterface = RetrofitService.cteateService(ApiInterface.class);
        Log.d(TAG, "onStartJob() Executed");

        sReleaseReminder = getSharedPreferences(KEY_HEADER_UPCOMING_REMINDER, MODE_PRIVATE);
        sDailyReminder = getSharedPreferences(KEY_HEADER_DAILY_REMINDER, MODE_PRIVATE);

        boolean checkUpcomingReminder = sReleaseReminder.getBoolean(KEY_FIELD_UPCOMING_REMINDER, false);
        if (checkUpcomingReminder){
            checkReminderDaily(params);
        }
        boolean checkDailyReminder = sDailyReminder.getBoolean(KEY_FIELD_DAILY_REMINDER, false);
        if (checkDailyReminder){
            checkReminderUpcoming(params);
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob() Executed");
        return true;
    }

    private void checkReminderDaily(final JobParameters job){
        try {
            Date d = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String start_time = simpleDateFormat.format(d)+" 07:00:00";
            Date start_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start_time);
            Calendar start_calendar = Calendar.getInstance();
            start_calendar.setTime(start_date);
            start_calendar.add(Calendar.DATE, 1);


            String end_time = simpleDateFormat.format(d)+" 08:00:00";
            Date end_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end_time);
            Calendar end_calendar = Calendar.getInstance();
            end_calendar.setTime(end_date);
            end_calendar.add(Calendar.DATE, 1);

            SimpleDateFormat simpleDateFormatCurrent = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String cur = simpleDateFormatCurrent.format(d);

            Date current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cur);
            Calendar current_calendar = Calendar.getInstance();
            current_calendar.setTime(current);
            current_calendar.add(Calendar.DATE, 1);

//            Date current = current_date.getTime();
            if (current_calendar.getTime().after(start_calendar.getTime()) && current_calendar.getTime().before(end_calendar.getTime())) {
//                Toast.makeText(this, "Can get "+ simpleDateFormat.format(current), Toast.LENGTH_SHORT).show();
                System.out.println("Can get "+ simpleDateFormat.format(current));
                showNotification(getApplicationContext(), getString(R.string.app_name), getString(R.string.desc_app), 100);
            }else{
//                Toast.makeText(this, "Cannot !!! get "+ simpleDateFormat.format(current), Toast.LENGTH_SHORT).show();
                System.out.println("Cannot !!! get "+ simpleDateFormat.format(current));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void checkReminderUpcoming(final JobParameters job){
        try {

            Date d = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String start_time = simpleDateFormat.format(d)+" 08:00:00";
            Date start_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start_time);
            Calendar start_calendar = Calendar.getInstance();
            start_calendar.setTime(start_date);
            start_calendar.add(Calendar.DATE, 1);


            String end_time = simpleDateFormat.format(d)+" 09:00:00";
            Date end_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end_time);
            Calendar end_calendar = Calendar.getInstance();
            end_calendar.setTime(end_date);
            end_calendar.add(Calendar.DATE, 1);


            SimpleDateFormat simpleDateFormatCurrent = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String cur = simpleDateFormatCurrent.format(d);

            Date current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cur);
            Calendar current_calendar = Calendar.getInstance();
            current_calendar.setTime(current);
            current_calendar.add(Calendar.DATE, 1);

            boolean after_start = current_calendar.getTime().after(start_calendar.getTime());
            boolean before_end = current_calendar.getTime().before(end_calendar.getTime());
            if (after_start && before_end) {
                Date upcomingDate = new Date();
//                Toast.makeText(this, "Can get "+ simpleDateFormat.format(current), Toast.LENGTH_SHORT).show();
                System.out.println("Can get "+ simpleDateFormat.format(current));
                String dateUpcoming = simpleDateFormat.format(upcomingDate);
                apiInterface.getMoviesListUpcoming(Static.API_KEY, dateUpcoming, dateUpcoming)
                        .enqueue(new Callback<ResponseMovies>() {
                            @Override
                            public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
                                if (response.isSuccessful()){
                                    if(response.body() != null){
                                        for (MoviesResult moviesResult : response.body().getResults()){
                                            showNotification(getApplicationContext(), "Up Coming : "+moviesResult.getOriginalTitle(), moviesResult.getOriginalTitle()+"-"+moviesResult.getOverview(), moviesResult.getId());
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseMovies> call, Throwable t) {

                            }
                        });
            }else{
//                Toast.makeText(this, "Can get "+ simpleDateFormat.format(current), Toast.LENGTH_SHORT).show();
                System.out.println("Cann get "+ simpleDateFormat.format(current));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void showNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Catalogue Movie";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.logo)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }

}
