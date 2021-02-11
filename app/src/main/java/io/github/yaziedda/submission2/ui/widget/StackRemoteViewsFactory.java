package io.github.yaziedda.submission2.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.github.yaziedda.submission2.R;
import io.github.yaziedda.submission2.model.MoviesResult;
import io.github.yaziedda.submission2.util.Static;

/**
 * Created by yaziedda on 2019-11-13.
 */
public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<MoviesResult> mWidgetItems = new ArrayList<>();
    private final Context mContext;

    public StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        //required
    }

    @Override
    public void onDataSetChanged() {
        //Ini berfungsi untuk melakukan refresh saat terjadi perubahan.
        mWidgetItems.clear();
        List<MoviesResult> list = SQLite.select().from(MoviesResult.class).queryList();
        mWidgetItems.addAll(list);

    }

    @Override
    public void onDestroy() {
        //required
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        MoviesResult moviesResult = mWidgetItems.get(position);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Bitmap bitmap = getBitmapFromURL(Static.getUrlPoster(Static.POSTER_w780, moviesResult.getPosterPath()));
        if(bitmap != null) {
            rv.setImageViewBitmap(R.id.imageView, bitmap);
        }else{
            rv.setImageViewBitmap(R.id.imageView, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.logo));
        }

        Bundle extras = new Bundle();
        extras.putInt(FavoritWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
