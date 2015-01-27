package slashdev.com.popbubble.myinterface;

import org.andengine.entity.sprite.AnimatedSprite;

public abstract interface IButtonAnimatedSprite
{
    public abstract AnimatedSprite onClick(AnimatedSprite paramAnimatedSprite);

    public abstract AnimatedSprite onDown(AnimatedSprite paramAnimatedSprite);

    public abstract AnimatedSprite onMove(AnimatedSprite paramAnimatedSprite);

    public abstract AnimatedSprite onMoveOut(AnimatedSprite paramAnimatedSprite);

    public abstract AnimatedSprite onUp(AnimatedSprite paramAnimatedSprite);
}
