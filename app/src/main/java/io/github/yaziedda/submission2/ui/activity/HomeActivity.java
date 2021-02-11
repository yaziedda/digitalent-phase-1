package io.github.yaziedda.submission2.ui.activity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import io.github.yaziedda.submission2.R;
import io.github.yaziedda.submission2.adapter.SectionPagerAdapter;
import io.github.yaziedda.submission2.database.MovieContract;
import io.github.yaziedda.submission2.database.MovieHelper;
import io.github.yaziedda.submission2.service.AlarmManagerReceiver;
import io.github.yaziedda.submission2.service.ReminderJobService;
import io.github.yaziedda.submission2.ui.fragment.MoviesFragment;
import io.github.yaziedda.submission2.ui.fragment.TVShowFragment;


public class HomeActivity extends AppCompatActivity {

    TabLayout tab;
    ViewPager viewPager;
    private int jobId = 10;
    private static final DateFormat sdf = new SimpleDateFormat("HH:mm");


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPager);

        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        sectionPagerAdapter.addFragment(new MoviesFragment());
        sectionPagerAdapter.addFragment(new TVShowFragment());
        viewPager.setAdapter(sectionPagerAdapter);
        tab.setupWithViewPager(viewPager);

        tab.getTabAt(0).setText(getResources().getString(R.string.tab_1));
        tab.getTabAt(1).setText(getResources().getString(R.string.tab_2));

        startJob();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_change_settings:
//                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
//                startActivity(mIntent);
                Intent mIntent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(mIntent);

                break;
            case R.id.action_favorit_film:
                mIntent = new Intent(getApplicationContext(), FavoritMoviesActivity.class);
                startActivity(mIntent);
                break;
            case R.id.action_favorit_tv:
                mIntent = new Intent(getApplicationContext(), FavoritTVShowActivity.class);
                startActivity(mIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startJob(){
        if (isJobRunning(this)) {
//            Toast.makeText(this, "Job Service is already scheduled", Toast.LENGTH_SHORT).show();
            return;
        }
        ComponentName mServiceComponent = new ComponentName(this, ReminderJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(jobId, mServiceComponent);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setRequiresDeviceIdle(false);
        builder.setRequiresCharging(false);
        // 1000 ms = 1 detik
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            builder.setPeriodic(900000); //15 menit
            builder.setMinimumLatency(5000); //15 menit
        } else {
            builder.setPeriodic(5000); //3 menit
        }
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
//        Toast.makeText(this, "Job Service started", Toast.LENGTH_SHORT).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void cancelJob(){
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancel(jobId);
//        Toast.makeText(this, "Job Service canceled", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MovieHelper movieHelper = new MovieHelper(getApplicationContext());
        movieHelper.open();
        Cursor cursor = movieHelper.queryProvider();
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(MovieContract.MovieColumns.MOVIE_ID));
            String title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieColumns.MOVIE_TITLE));
            String desc = cursor.getString(cursor.getColumnIndex(MovieContract.MovieColumns.MOVIE_DESC));
            String img = cursor.getString(cursor.getColumnIndex(MovieContract.MovieColumns.MOVIE_IMAGE));
            System.out.println("Cursor -> id : "+id+", title : "+title+", desc : "+desc+", img : "+img);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean isJobRunning(Context context) {
        boolean isScheduled = false;
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (scheduler != null) {
            for (JobInfo jobInfo : scheduler.getAllPendingJobs()) {
                if (jobInfo.getId() == jobId) {
                    isScheduled = true;
                    break;
                }
            }
        }
        return isScheduled;
    }
}
