package slashdev.com.popbubble;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharePref
{
    public static final int USE_EXTERNAL = 1;
    public static final int USE_INTERNAL = 0;
    public static final int USE_ONLINE = 2;
    protected Context mContext;

    public SharePref(Context paramContext)
    {
        this.mContext = paramContext;
    }

    public boolean getBoolean(String paramString, boolean paramBoolean)
    {
        return PreferenceManager.getDefaultSharedPreferences(this.mContext).getBoolean(paramString, paramBoolean);
    }

    public int getInt(String paramString, int paramInt)
    {
        return PreferenceManager.getDefaultSharedPreferences(this.mContext).getInt(paramString, paramInt);
    }

    public String getString(String paramString1, String paramString2)
    {
        return PreferenceManager.getDefaultSharedPreferences(this.mContext).getString(paramString1, paramString2);
    }

    public void set(String paramString, int paramInt)
    {
        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mContext).edit();
        localEditor.putInt(paramString, paramInt);
        localEditor.commit();
    }

    public void set(String paramString1, String paramString2)
    {
        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mContext).edit();
        localEditor.putString(paramString1, paramString2);
        localEditor.commit();
    }

    public void set(String paramString, boolean paramBoolean)
    {
        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mContext).edit();
        localEditor.putBoolean(paramString, paramBoolean);
        localEditor.commit();
    }
}
