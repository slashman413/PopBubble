package com.slashbase.util;

import android.content.Context;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.ITextureRegion;

public class GameUtil
{
    public static void clearITextureRegion(ITextureRegion paramITextureRegion)
    {
        paramITextureRegion.setTextureWidth(paramITextureRegion.getWidth() - 1.0F);
        paramITextureRegion.setTextureHeight(paramITextureRegion.getHeight() - 1.0F);
    }

    public static int getRandomIndex(int paramInt1, int paramInt2)
    {
        return paramInt1 + (int)(Math.random() * (1 + (paramInt2 - paramInt1)));
    }

    public static float getRatio(Context paramContext)
    {
        float f1 = 0.421875F;
        float f2 = paramContext.getResources().getDisplayMetrics().density;
        if (f2 >= 3.0D) {
            f1 = 1.6875F;
        }
        // TODO: check this logic
        if (f2 < 1.0D) {
            return f1;
        }
        if (f2 >= 2.0D) {
            return 1.0F;
        }
        if (f2 >= 1.5D) {
            return 0.75F;
        }

        return f1;
    }

    public static void sortChildren(Scene paramScene)
    {
        try
        {
            paramScene.sortChildren();
            return;
        }
        catch (Exception localException) {}
    }
}
