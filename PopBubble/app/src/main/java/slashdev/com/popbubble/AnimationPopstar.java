package slashdev.com.popbubble;

import android.util.Log;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.util.modifier.IModifier;

import slashdev.com.popbubble.myinterface.ObjectPopstar;

public class AnimationPopstar
        extends ObjectPopstar
{
    MainGame mainGame;

    public AnimationPopstar(MainGame paramMainGame)
    {
        super(paramMainGame);
        this.mainGame = paramMainGame;
    }

    public void animation1_on(ItemStar paramItemStar)
    {
        paramItemStar.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new IEntityModifier[] { new AlphaModifier(0.5F, 1.0F, 0.0F), new AlphaModifier(0.5F, 0.0F, 1.0F) }), 3, new IEntityModifier.IEntityModifierListener()
        {
            public void onModifierFinished(IModifier<IEntity> paramAnonymousIModifier, IEntity paramAnonymousIEntity)
            {
                Log.e("TAG", " finish ");
                AnimationPopstar.this.mainGame.mPlayScene.hideBonus();
            }

            public void onModifierStarted(IModifier<IEntity> paramAnonymousIModifier, IEntity paramAnonymousIEntity) {}
        }));
    }
}
