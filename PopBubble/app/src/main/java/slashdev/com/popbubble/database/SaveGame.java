package slashdev.com.popbubble.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.slashbase.mylog.Log;
import com.google.gson.Gson;

import java.lang.reflect.Array;

import slashdev.com.popbubble.object.Data;
import slashdev.com.popbubble.ItemStar;
import slashdev.com.popbubble.scenes.PlayScene;

public class SaveGame
{
    Context mContext;

    public SaveGame(Context paramContext)
    {
        this.mContext = paramContext;
    }

    public DataSaveGame getDataSaveGame(PlayScene paramPlayScene)
    {
        DataSaveGame localDataSaveGame = new DataSaveGame();
        localDataSaveGame.setCoin(paramPlayScene.getSCORE());
        localDataSaveGame.setLevel(paramPlayScene.getLEVEL());
        ItemStar[][] arrayOfItemStar = paramPlayScene.getsPop();
        Data[][] arrayOfData = (Data[][])Array.newInstance(Data.class, new int[] { 10, 10 });

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (arrayOfItemStar[i][j] != null) {
                    arrayOfData[i][j] = ((Data)arrayOfItemStar[i][j].getUserData());
                }
            }
        }

        localDataSaveGame.setmBall(arrayOfData);
        return localDataSaveGame;
    }

    public DataSaveGame read(int paramInt)
    {
        try
        {
            SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
            DataSaveGame localDataSaveGame = (DataSaveGame)new Gson().fromJson(localSharedPreferences.getString("key" + paramInt, ""), DataSaveGame.class);
            return localDataSaveGame;
        }
        catch (Exception localException)
        {
            Log.e(getClass().toString(), localException.toString());
        }
        return null;
    }

    public void resetData(int paramInt)
    {
        try
        {
            SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mContext).edit();
            localEditor.putString("key" + paramInt, "");
            localEditor.commit();
            return;
        }
        catch (Exception localException)
        {
            Log.e(getClass().toString(), getClass() + " Save = " + localException.toString());
        }
    }

    public void save(int paramInt, PlayScene paramPlayScene)
    {
        DataSaveGame localDataSaveGame = getDataSaveGame(paramPlayScene);
        try
        {
            SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mContext).edit();
            String str = new Gson().toJson(localDataSaveGame);
            localEditor.putString("key" + paramInt, str);
            localEditor.commit();
            Log.e("", "===========================json================= " + str);
            return;
        }
        catch (Exception localException)
        {
            Log.e(getClass().toString(), getClass() + " Save = " + localException.toString());
        }
    }
}
