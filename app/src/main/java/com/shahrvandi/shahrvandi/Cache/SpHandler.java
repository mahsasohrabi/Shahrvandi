package com.shahrvandi.shahrvandi.Cache;

import android.content.Context;

public class SpHandler {

    private static SpHandler shared = null;
    private HSp sp;
    Context context;

    private SpHandler(Context context) {
        sp = new HSp(context);
        this.context = context;
    }

    public static SpHandler get(Context context) {
        if (shared == null)
            shared = new SpHandler(context);
        return shared;
    }


    public void setUserId(String userId) {
        sp.saveToPreferences("user", "id", userId);
    }

    public String getUserId() {
        return sp.readFromPreferences("user", "id", "");
    }


    public void removeAll() {

        sp.clear("uer");


    }


}
