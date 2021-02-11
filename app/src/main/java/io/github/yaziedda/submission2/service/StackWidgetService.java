package io.github.yaziedda.submission2.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import io.github.yaziedda.submission2.ui.widget.StackRemoteViewsFactory;

/**
 * Created by yaziedda on 2019-11-13.
 */
public class StackWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}
