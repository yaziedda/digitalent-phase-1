package io.github.yaziedda.submission2.notification;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.github.yaziedda.submission2.R;
import io.github.yaziedda.submission2.model.MoviesResult;
import io.github.yaziedda.submission2.model.ResponseMovies;
import io.github.yaziedda.submission2.networking.ApiInterface;
import io.github.yaziedda.submission2.networking.RetrofitService;
import io.github.yaziedda.submission2.ui.activity.HomeActivity;
import io.github.yaziedda.submission2.util.Static;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.github.yaziedda.submission2.util.Static.EXTRA_MESSAGE_RECIEVE;
import static io.github.yaziedda.submission2.util.Static.EXTRA_TYPE_RECIEVE;
import static io.github.yaziedda.submission2.util.Static.NOTIFICATION_ID_;


public class MovieReleaseReceiver extends BroadcastReceiver {
    public List<MoviesResult> listMovie = new ArrayList<>();

    public MovieReleaseReceiver() {

    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        Date upcomingDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("");
        String dateUpcoming = simpleDateFormat.format(upcomingDate);
        ApiInterface apiInterface = RetrofitService.cteateService(ApiInterface.class);
        apiInterface.getMoviesListUpcoming(Static.API_KEY, dateUpcoming, dateUpcoming)
                .enqueue(new Callback<ResponseMovies>() {
                    @Override
                    public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
                        if (response.isSuccessful()){
                            if(response.body() != null){
                                for (MoviesResult moviesResult : response.body().getResults()){

                                    sendNotification(context, moviesResult.getOriginalTitle(), moviesResult.getOverview(), moviesResult.getId());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMovies> call, Throwable t) {

                    }
                });

    }

    private void sendNotification(Context context, String title, String desc, int id) {
        String CHANNEL_ID = "channel_01";
        CharSequence CHANNEL_NAME = "dicoding channel";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true)
                .setSound(uriTone);

        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(id, notification);
        }

//        if (notificationManager != null) {
//            notificationManager.notify(id, builder.build());
//        }
    }

    public void setAlarm(Context context, String type, String time, String message) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MovieReleaseReceiver.class);
        intent.putExtra(EXTRA_MESSAGE_RECIEVE, message);
        intent.putExtra(EXTRA_TYPE_RECIEVE, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID_, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, R.string.on_movie_release_reminder, Toast.LENGTH_SHORT).show();
    }


    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MovieReleaseReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID_, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, R.string.off_movie_release_reminder, Toast.LENGTH_SHORT).show();
    }


}