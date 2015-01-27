package slashdev.com.popbubble.base;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import slashdev.com.popbubble.myinterface.IButtonSprite;

public class BaseSprite
        extends Sprite
{
    boolean isUp = true;
    IButtonSprite mIButtonSprite;

    public BaseSprite(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, ITextureRegion paramITextureRegion, VertexBufferObjectManager paramVertexBufferObjectManager)
    {
        super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramITextureRegion, paramVertexBufferObjectManager);
    }

    public BaseSprite(float paramFloat1, float paramFloat2, ITextureRegion paramITextureRegion, VertexBufferObjectManager paramVertexBufferObjectManager)
    {
        super(paramFloat1, paramFloat2, paramITextureRegion, paramVertexBufferObjectManager);
    }

    public IButtonSprite getmIButtonSprite()
    {
        return this.mIButtonSprite;
    }

    public boolean onAreaTouched(TouchEvent paramTouchEvent, float paramFloat1, float paramFloat2)
    {
        if (paramTouchEvent.isActionDown())
        {
            setScale(0.8F);
            this.isUp = true;
            if (this.mIButtonSprite != null) {
                this.mIButtonSprite.onDown(this);
            }
        }
        // TODO: check this logic
        if (paramTouchEvent.isActionUp()) {
            setScale(1.0F);
            if (this.mIButtonSprite != null) {
                this.mIButtonSprite.onUp(this);
                if (this.isUp) {
                    this.mIButtonSprite.onClick(this);
                }
            }
        }
        if (paramTouchEvent.isActionMove()) {
            if ((paramFloat1 < 1.0F) || (paramFloat1 > getWidth() - 1.0F) || (paramFloat2 < 1.0F) || (paramFloat2 > getHeight() - 1.0F))
            {
                setScale(1.0F);
                if (this.mIButtonSprite != null) {
                    this.mIButtonSprite.onMoveOut(this);
                }
                this.isUp = false;
            }
            if (this.mIButtonSprite != null) {
                this.mIButtonSprite.onMove(this);
            }
        }
        return true;
    }

    public void setmIButtonSprite(IButtonSprite paramIButtonSprite)
    {
        this.mIButtonSprite = paramIButtonSprite;
    }
}
