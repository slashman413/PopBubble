package slashdev.com.popbubble;

import com.slashbase.base.BaseDialog;
import com.slashbase.myconfig.ConfigScreen;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseElasticIn;
import org.andengine.util.modifier.ease.EaseElasticOut;

import slashdev.com.popbubble.level.Level;
import slashdev.com.popbubble.myinterface.ILoadUnLoadResource;

public class DialogTarget
        extends BaseDialog
        implements ILoadUnLoadResource
{
    Level mLevel;
    Rectangle mRectangle;
    MainGame mainGame;
    Text txtLevel;
    Text txtTarget;

    public DialogTarget(MainGame paramMainGame)
    {
        super(paramMainGame);
        this.mainGame = paramMainGame;
        this.mLevel = new Level();
    }

    public void hide()
    {
        MoveXModifier local2 = new MoveXModifier(1.5F, this.mRectangle.getX(), ConfigScreen.SCREENWIDTH, EaseElasticIn.getInstance())
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                DialogTarget.this.mainGame.mPlayScene.playOrLoadGame();
            }
        };
        this.mRectangle.registerEntityModifier(local2);
    }

    public void loadResource()
    {
        this.mRectangle = new Rectangle(-ConfigScreen.SCREENWIDTH, 0.0F, ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT, this.mainGame.getVertexBufferObjectManager());
        this.mRectangle.setColor(Color.TRANSPARENT);
        this.mainGame.mHud.attachChild(this.mRectangle);
        float f = -100 + ConfigScreen.SCREENWIDTH / 2;
        this.txtLevel = new Text(f, ConfigScreen.SCREENHEIGHT / 2, this.mainGame.getmFont1(), "", 100, this.mainGame.getVertexBufferObjectManager());
        this.txtTarget = new Text(f, 50 + ConfigScreen.SCREENHEIGHT / 2, this.mainGame.getmFont1(), "", 100, this.mainGame.getVertexBufferObjectManager());
        this.mRectangle.attachChild(this.txtLevel);
        this.mRectangle.attachChild(this.txtTarget);
    }

    public void show(int paramInt)
    {
        this.mRectangle.setZIndex(90);
        this.mainGame.mainScene.sortChildren();
        this.txtLevel.setText("   Level " + paramInt);
        this.txtLevel.setScale(1.6F);
        this.mainGame.mPlayScene.getmLevel();
        this.txtTarget.setText("Target $" + this.mLevel.getTarget(paramInt));
        this.txtTarget.setScale(1.6F);
        MoveXModifier local1 = new MoveXModifier(1.5F, -ConfigScreen.SCREENWIDTH, 0.0F, EaseElasticOut.getInstance())
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                DialogTarget.this.mainGame.mPlayScene.hidetargetClear();
                DialogTarget.this.mainGame.mPlayScene.animation1_on();
            }
        };
        this.mRectangle.registerEntityModifier(local1);
    }

    public void unLoadResource()
    {
        this.mainGame.deleteAllChild(this.mRectangle);
    }
}
