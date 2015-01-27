package slashdev.com.popbubble.mylog;

import com.slashbase.mylog.Log;

import org.andengine.entity.sprite.Sprite;

public class Mylog
{
    public void showMatrix(Sprite[][] paramArrayOfSprite, int paramInt1, int paramInt2)
    {
        Log.e("testMatrix", "======================testMatrix======================");
        int i = 0;
        int j;
        for (i = 0;; i++)
        {
            if (i >= paramInt1)
            {
                Log.e("testMatrix", "======================END testMatrix======================");
                return;
            }
            j = 0;
            if (j < paramInt2) {
                break;
            }
            System.out.println();
        }
        if (paramArrayOfSprite[i][j] == null) {
            System.out.print("N,");
        }
        j++;
    }
}
