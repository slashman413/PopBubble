package com.slashbase.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilLib
{
    public static void actionView(Activity paramActivity, String paramString)
    {
        try
        {
            paramActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
            return;
        }
        catch (Exception localException) {}
    }

    public static void actionView(Context paramContext, String paramString)
    {
        try
        {
            paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
            return;
        }
        catch (Exception localException) {}
    }

    public static void addShortcut(Context paramContext, String paramString1, String paramString2, int paramInt)
    {
        Intent localIntent1 = new Intent();
        localIntent1.setAction("android.intent.action.VIEW");
        localIntent1.setData(Uri.parse(paramString2));
        Intent localIntent2 = new Intent();
        localIntent2.putExtra("android.intent.extra.shortcut.INTENT", localIntent1);
        localIntent2.putExtra("android.intent.extra.shortcut.NAME", paramString1);
        localIntent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(paramContext, paramInt));
        localIntent2.putExtra("duplicate", false);
        localIntent2.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        paramContext.getApplicationContext().sendBroadcast(localIntent2);
    }

    public static void addShortcut(Context paramContext, String paramString1, String paramString2, Bitmap paramBitmap, String paramString3)
    {
        if ((paramString3.length() != 0) && (appInstalledOrNot(paramString3, paramContext))) {
            return;
        }
        Intent localIntent1 = new Intent();
        localIntent1.setAction("android.intent.action.VIEW");
        localIntent1.setData(Uri.parse(paramString2));
        Intent localIntent2 = new Intent();
        localIntent2.putExtra("android.intent.extra.shortcut.INTENT", localIntent1);
        localIntent2.putExtra("android.intent.extra.shortcut.NAME", paramString1);
        localIntent2.putExtra("android.intent.extra.shortcut.ICON", paramBitmap);
        localIntent2.putExtra("duplicate", false);
        localIntent2.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        paramContext.getApplicationContext().sendBroadcast(localIntent2);
    }

    public static boolean appInstalledOrNot(String paramString, Context paramContext)
    {
        if (paramString.length() == 0) {
            return false;
        }
        PackageManager localPackageManager = paramContext.getPackageManager();
        try
        {
            localPackageManager.getPackageInfo(paramString, 1);
            return true;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
        return false;
    }



    public static String getDeviceId(Context paramContext)
    {
        return Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
    }

    public static Drawable getDrawableFromAssets(Context paramContext, String paramString)
    {
        try
        {
            Drawable localDrawable = Drawable.createFromStream(paramContext.getAssets().open(paramString), null);
            return localDrawable;
        }
        catch (Exception localException) {}
        return null;
    }

    public static int getRandomIndex(int paramInt1, int paramInt2)
    {
        return paramInt1 + (int)(Math.random() * (1 + (paramInt2 - paramInt1)));
    }

    public static Bitmap getResizedBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2)
    {
        if (paramBitmap == null) {
            return paramBitmap;
        }
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        float f1 = paramInt2 / i;
        float f2 = paramInt1 / j;
        Matrix localMatrix = new Matrix();
        localMatrix.postScale(f1, f2);
        return Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, false);
    }

    public static int getVersionCode(Context paramContext, String paramString)
    {
        try
        {
            int i = paramContext.getPackageManager().getPackageInfo(paramString, 0).versionCode;
            return i;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
        return -1;
    }

    public static String md5(String paramString)
    {
        try
        {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            byte[] arrayOfByte = localMessageDigest.digest();
            StringBuffer localStringBuffer = new StringBuffer();
            for (int i = 0;; i++)
            {
                if (i >= arrayOfByte.length) {
                    return localStringBuffer.toString();
                }
                localStringBuffer.append(Integer.toHexString(0xFF & arrayOfByte[i]));
            }
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
        {
            localNoSuchAlgorithmException.printStackTrace();
        }
        return "";
    }

    public static void nextChoseLiveWallpaper(Activity paramActivity, Class<?> paramClass)
    {
        try
        {
            Intent localIntent1 = new Intent("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
            localIntent1.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", new ComponentName(paramActivity, paramClass));
            paramActivity.startActivity(localIntent1);
            return;
        }
        catch (Exception localException1)
        {
            try
            {
                Intent localIntent2 = new Intent();
                localIntent2.setAction("android.service.wallpaper.LIVE_WALLPAPER_CHOOSER");
                paramActivity.startActivityForResult(localIntent2, 1);
                return;
            }
            catch (Exception localException2) {}
        }
    }

    public static void nextMyListAppOnGooglePlay(Context paramContext, String paramString)
    {
        try
        {
            paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=" + paramString)));
            return;
        }
        catch (Exception localException) {}
    }

    public static void shareImageAndText(Context paramContext, String paramString1, String paramString2, String paramString3)
    {
        try
        {
            File localFile = new File(paramString1);
            String str = MimeTypeMap.getSingleton().getMimeTypeFromExtension(localFile.getName().substring(1 + localFile.getName().lastIndexOf(".")));
            Intent localIntent = new Intent("android.intent.action.SEND");
            localIntent.setType(str);
            localIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(localFile));
            localIntent.putExtra("android.intent.extra.SUBJECT", paramString2);
            localIntent.putExtra("android.intent.extra.TEXT", paramString3);
            paramContext.startActivity(Intent.createChooser(localIntent, "Share using"));
            return;
        }
        catch (Exception localException)
        {
            Log.e("", "shareImageAndText error = " + localException.toString());
        }
    }

    public static void showDetailApp(Activity paramActivity, String paramString)
    {
        try
        {
            paramActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + paramString)));
            return;
        }
        catch (Exception localException) {}
    }

    public static void showDetailApp(Context paramContext, String paramString)
    {
        try
        {
            paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + paramString)));
            return;
        }
        catch (Exception localException) {}
    }

    public static void showDialogShare(Context paramContext, String paramString)
    {
        try
        {
            Intent localIntent = new Intent();
            localIntent.setAction("android.intent.action.SEND");
            localIntent.putExtra("android.intent.extra.TEXT", paramString);
            localIntent.setType("text/plain");
            paramContext.startActivity(localIntent);
            return;
        }
        catch (Exception localException) {}
    }

    public static void showToast(final Context paramContext, final String paramString)
    {
        if (paramContext != null) {
            new Handler().post(new Runnable()
            {
                public void run()
                {
                    Toast.makeText(paramContext, paramString, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
