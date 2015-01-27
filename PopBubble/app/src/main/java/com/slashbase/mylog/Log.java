package com.slashbase.mylog;

public class Log
{
    public static boolean DEBUG = true;

    public static void d(String paramString1, String paramString2)
    {
        if (DEBUG) {
            android.util.Log.d(paramString1, paramString2);
        }
    }

    public static void e(String paramString1, String paramString2)
    {
        if (DEBUG) {
            android.util.Log.e(paramString1, paramString2);
        }
    }

    public static void setDebug(boolean paramBoolean)
    {
        DEBUG = paramBoolean;
    }
}
