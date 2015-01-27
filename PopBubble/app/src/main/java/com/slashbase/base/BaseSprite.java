package com.slashbase.base;

import com.slashbase.myinterface.IButtonClick;
import com.slashbase.util.GameUtil;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class BaseSprite
        extends Sprite
{
    IButtonClick mIButtonClick;

    public BaseSprite(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, ITextureRegion paramITextureRegion, VertexBufferObjectManager paramVertexBufferObjectManager)
    {
        super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramITextureRegion, paramVertexBufferObjectManager);
        GameUtil.clearITextureRegion(paramITextureRegion);
    }

    public BaseSprite(float paramFloat1, float paramFloat2, ITextureRegion paramITextureRegion, VertexBufferObjectManager paramVertexBufferObjectManager, IButtonClick paramIButtonClick)
    {
        super(paramFloat1, paramFloat2, paramITextureRegion, paramVertexBufferObjectManager);
        this.mIButtonClick = paramIButtonClick;
        GameUtil.clearITextureRegion(paramITextureRegion);
    }

    public IButtonClick getmIButtonClick()
    {
        return this.mIButtonClick;
    }

    public boolean onAreaTouched(TouchEvent paramTouchEvent, float paramFloat1, float paramFloat2)
    {
        if ((paramTouchEvent.getAction() == 1) && (this.mIButtonClick != null)) {
            this.mIButtonClick.onClick();
        }
        return super.onAreaTouched(paramTouchEvent, paramFloat1, paramFloat2);
    }

    public void setmIButtonClick(IButtonClick paramIButtonClick)
    {
        this.mIButtonClick = paramIButtonClick;
    }
}
