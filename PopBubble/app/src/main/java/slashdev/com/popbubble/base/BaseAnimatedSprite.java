package slashdev.com.popbubble.base;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import slashdev.com.popbubble.myinterface.IButtonAnimatedSprite;

public class BaseAnimatedSprite
        extends AnimatedSprite
{
    boolean isUp = true;
    IButtonAnimatedSprite mIButtonAnimatedSprite;

    public BaseAnimatedSprite(float paramFloat1, float paramFloat2, ITiledTextureRegion paramITiledTextureRegion, VertexBufferObjectManager paramVertexBufferObjectManager)
    {
        super(paramFloat1, paramFloat2, paramITiledTextureRegion, paramVertexBufferObjectManager);
    }

    public IButtonAnimatedSprite getmIButtonAnimatedSprite()
    {
        return this.mIButtonAnimatedSprite;
    }

    public boolean onAreaTouched(TouchEvent paramTouchEvent, float paramFloat1, float paramFloat2)
    {
        // TODO: check this logic
        if (paramTouchEvent.isActionDown())
        {
            setScale(0.8F);
            this.isUp = true;
            if (this.mIButtonAnimatedSprite != null) {
                this.mIButtonAnimatedSprite.onDown(this);
            }
        }
        if (paramTouchEvent.isActionUp()) {
            setScale(1.0F);
            if (this.mIButtonAnimatedSprite != null) {
                this.mIButtonAnimatedSprite.onUp(this);
                if (this.isUp) {
                    this.mIButtonAnimatedSprite.onClick(this);
                }
            }
        }
        if (paramTouchEvent.isActionMove()) {
            if (this.mIButtonAnimatedSprite != null) {
                this.mIButtonAnimatedSprite.onMove(this);
            }
            if ((paramFloat1 < 1.0F) || (paramFloat1 > getWidth() - 1.0F) || (paramFloat2 < 1.0F) || (paramFloat2 > getHeight() - 1.0F))
            {
                setScale(1.0F);
                if (this.mIButtonAnimatedSprite != null) {
                    this.mIButtonAnimatedSprite.onMoveOut(this);
                }
                this.isUp = false;
            }
        }
        if (paramTouchEvent.isActionOutside()) {
            if (this.mIButtonAnimatedSprite != null) {
                this.mIButtonAnimatedSprite.onMove(this);
            }
        }
        return true;
    }

    public void setmIButtonAnimatedSprite(IButtonAnimatedSprite paramIButtonAnimatedSprite)
    {
        this.mIButtonAnimatedSprite = paramIButtonAnimatedSprite;
    }
}
