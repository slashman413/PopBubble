package slashdev.com.popbubble.myinterface;

import org.andengine.entity.sprite.Sprite;

public abstract interface IButtonSprite
{
    public abstract Sprite onClick(Sprite paramSprite);

    public abstract Sprite onDown(Sprite paramSprite);

    public abstract Sprite onMove(Sprite paramSprite);

    public abstract Sprite onMoveOut(Sprite paramSprite);

    public abstract Sprite onUp(Sprite paramSprite);
}
