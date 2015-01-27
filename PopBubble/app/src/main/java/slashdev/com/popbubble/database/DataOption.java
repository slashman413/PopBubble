package slashdev.com.popbubble.database;


import slashdev.com.popbubble.MainGame;
import slashdev.com.popbubble.SharePref;

public class DataOption
{
    public static final int BAll3 = 3;
    public static final int BAll4 = 4;
    public static final int BAll5 = 5;
    public static final int BAll6 = 6;
    public static final int BAll7 = 7;
    public static final int DIFFICULT = 5;
    public static final int EASY = 3;
    public static final int MEDIUM = 4;
    final String keyLevel = "keyLevel";
    final String keyNumberBallColor = "keyNumberBallColor";
    SharePref mSharePref;
    int numberBallColor = 3;
    int typeLevel = 3;

    public DataOption(MainGame paramMainGame)
    {
        this.mSharePref = new SharePref(paramMainGame);
        this.typeLevel = this.mSharePref.getInt("keyLevel", 4);
        this.numberBallColor = this.mSharePref.getInt("keyNumberBallColor", 4);
    }

    public String getKeyLevel()
    {
        return "keyLevel";
    }

    public String getKeyNumberBallColor()
    {
        return "keyNumberBallColor";
    }

    public int getNumberBallColor()
    {
        return this.numberBallColor;
    }

    public int getTypeLevel()
    {
        return this.typeLevel;
    }

    public SharePref getmSharePref()
    {
        return this.mSharePref;
    }

    public void setNumberBallColor(int paramInt)
    {
        this.numberBallColor = paramInt;
        this.mSharePref.set("keyNumberBallColor", paramInt);
    }

    public void setTypeLevel(int paramInt)
    {
        this.typeLevel = paramInt;
        this.mSharePref.set("keyLevel", paramInt);
    }

    public void setmSharePref(SharePref paramSharePref)
    {
        this.mSharePref = paramSharePref;
    }
}

