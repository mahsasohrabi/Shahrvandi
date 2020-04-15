package com.shahrvandi.shahrvandi.Cache;

import android.content.Context;
import android.content.SharedPreferences;


public class HSp {

    private Context context;

    public HSp(Context context) {
        this.context = context;
    }

    public void saveToPreferences(String file, String name, String value) {

        SharedPreferences sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(name, value);
        edit.commit();

    }

    public String readFromPreferences(String file, String name, String value) {
        SharedPreferences sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sp.getString(name, value);
    }


    public void clear(String file) {
        SharedPreferences sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public void remove(String file, String name) {
        SharedPreferences sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(name);
        editor.apply();
        boolean success = editor.commit();
        if (!success) {

        }

    }


}
