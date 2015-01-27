package slashdev.com.popbubble.object;

public class Coin
{
    private int coin;
    private String name;

    public Coin(String paramString, int paramInt)
    {
        this.name = paramString;
        this.coin = paramInt;
    }

    public int getCoin()
    {
        return this.coin;
    }

    public String getName()
    {
        return this.name;
    }

    public void setCoin(int paramInt)
    {
        this.coin = paramInt;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }
}
