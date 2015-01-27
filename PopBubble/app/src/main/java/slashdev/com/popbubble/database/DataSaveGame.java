package slashdev.com.popbubble.database;


import slashdev.com.popbubble.object.Data;

public class DataSaveGame
{
    int coin = 0;
    int level = 0;
    Data[][] mStar;

    public int getCoin()
    {
        return this.coin;
    }

    public int getLevel()
    {
        return this.level;
    }

    public Data[][] getmBall()
    {
        return this.mStar;
    }

    public void setCoin(int paramInt)
    {
        this.coin = paramInt;
    }

    public void setLevel(int paramInt)
    {
        this.level = paramInt;
    }

    public void setmBall(Data[][] paramArrayOfData)
    {
        this.mStar = paramArrayOfData;
    }
}
