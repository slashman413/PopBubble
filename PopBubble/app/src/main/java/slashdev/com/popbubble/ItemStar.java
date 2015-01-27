package slashdev.com.popbubble;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class ItemStar
        extends AnimatedSprite
{
    boolean isCheck = false;
    int move = 0;

    public ItemStar(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, ITiledTextureRegion paramITiledTextureRegion, VertexBufferObjectManager paramVertexBufferObjectManager)
    {
        super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramITiledTextureRegion, paramVertexBufferObjectManager);
    }

    public int getMove()
    {
        return this.move;
    }

    public boolean isCheck()
    {
        return this.isCheck;
    }

    public void setCheck(boolean paramBoolean)
    {
        this.isCheck = paramBoolean;
    }

    public void setMove(int paramInt)
    {
        this.move = paramInt;
    }
}
