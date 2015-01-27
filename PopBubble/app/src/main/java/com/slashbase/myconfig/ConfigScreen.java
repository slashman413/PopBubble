package com.slashbase.myconfig;

import android.app.Activity;
import android.util.DisplayMetrics;

import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;

public class ConfigScreen
{
    public static int SCREENHEIGHT;
    public static int SCREENHEIGHT_REAL = 0;
    public static int SCREENWIDTH = 0;
    public static int SCREENWIDTH_REAL;
    public static RatioResolutionPolicy mRatioResolutionPolicy;
    public static ScreenOrientation mScreenOrientation;

    static
    {
        SCREENHEIGHT = 0;
        mScreenOrientation = ScreenOrientation.LANDSCAPE_FIXED;
        SCREENWIDTH_REAL = 0;
    }

    public static float getCenterX()
    {
        return SCREENWIDTH / 2;
    }

    public static float getCenterY()
    {
        return SCREENHEIGHT / 2;
    }

    public static void ini(Activity paramActivity)
    {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        paramActivity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        SCREENHEIGHT = localDisplayMetrics.heightPixels;
        SCREENWIDTH = localDisplayMetrics.widthPixels;
        mRatioResolutionPolicy = new RatioResolutionPolicy(SCREENWIDTH, SCREENHEIGHT);
        SCREENWIDTH_REAL = localDisplayMetrics.widthPixels;
        SCREENHEIGHT_REAL = localDisplayMetrics.heightPixels;
    }
}
