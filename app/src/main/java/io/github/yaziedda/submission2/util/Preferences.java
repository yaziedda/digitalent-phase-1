package io.github.yaziedda.submission2.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yaziedda on 2019-11-13.
 */
public class Preferences {

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Static.MyPref, Context.MODE_PRIVATE);
        return preferences.edit();
    }

    public static void putString(Context context, String key, String value){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value).commit();
    }

    public static void putBoolean(Context context, String key, boolean value){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, value).commit();
    }

    public static void putInt(Context context, String key, int value){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(key, value).commit();
    }


    public static String getString(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(Static.MyPref, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static boolean getBoolean(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(Static.MyPref, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static int getInt(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(Static.MyPref, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

}
