package slashdev.com.popbubble.object;

public class Data
{
    private int color;
    private int i;
    private boolean isCheck;
    private int j;

    public Data(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
        this.i = paramInt1;
        this.j = paramInt2;
        this.color = paramInt3;
        this.isCheck = paramBoolean;
    }

    public int getColor()
    {
        return this.color;
    }

    public int getI()
    {
        return this.i;
    }

    public int getJ()
    {
        return this.j;
    }

    public boolean isCheck()
    {
        return this.isCheck;
    }

    public void setCheck(boolean paramBoolean)
    {
        this.isCheck = paramBoolean;
    }

    public void setColor(int paramInt)
    {
        this.color = paramInt;
    }

    public void setI(int paramInt)
    {
        this.i = paramInt;
    }

    public void setJ(int paramInt)
    {
        this.j = paramInt;
    }
}
