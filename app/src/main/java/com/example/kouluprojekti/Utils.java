package com.example.kouluprojekti;

import android.app.Activity;
import android.content.Intent;

public class Utils {

    private static int themee;
    public final static int THEME_PURPLE = 0;
    public final static int THEME_BLUE = 1;
    public final static int THEME_RED = 2;
    public final static int THEME_YELLOW = 3;

    public static void changeToTheme(Activity activity, int theme) {
        themee = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (themee) {
            default:
            case THEME_PURPLE:
                activity.setTheme(R.style.Purple);
                break;
            case THEME_BLUE:
                activity.setTheme(R.style.Blue);
                break;
            case THEME_RED:
                activity.setTheme(R.style.Red);
                break;
            case THEME_YELLOW:
                activity.setTheme(R.style.Yellow);
                break;
        }
    }

    public static void setThemee(int themee) {
        Utils.themee = themee;
    }

    public static int getThemee() {
        return themee;
    }
}
