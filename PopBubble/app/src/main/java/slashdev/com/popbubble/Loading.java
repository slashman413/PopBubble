package slashdev.com.popbubble;

import com.slashbase.myconfig.ConfigScreen;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

import java.util.ArrayList;

import slashdev.com.popbubble.myinterface.ILoading;
import slashdev.com.popbubble.myinterface.ObjectPopstar;

public class Loading
        extends ObjectPopstar
{
    ITextureRegion bg1;
    ITextureRegion bg2;
    Sprite iconLoading;
    ILoading mILoading;
    ITextureRegion mIconLoadingTTR;
    ArrayList<BitmapTextureAtlas> mListBitmapTextureAtlas;
    Rectangle mRectangleBottom;
    Rectangle mRectangleLoading;
    Rectangle mRectangleTop;
    float pDuration = 0.2F;
    float pHeight = 0.0F;
    Sprite sBg1;
    Sprite sBg2;

    public Loading(MainGame paramMainGame)
    {
        super(paramMainGame);
        this.mRectangleLoading = new Rectangle(0.0F, 0.0F, ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT, paramMainGame.getVertexBufferObjectManager());
        this.mRectangleTop = new Rectangle(0.0F, -this.pHeight, ConfigScreen.SCREENWIDTH, this.pHeight, paramMainGame.getVertexBufferObjectManager());
        this.mRectangleTop.setColor(Color.BLACK);
        this.mRectangleBottom = new Rectangle(0.0F, ConfigScreen.SCREENHEIGHT, ConfigScreen.SCREENWIDTH, this.pHeight, paramMainGame.getVertexBufferObjectManager());
        this.mRectangleBottom.setColor(Color.BLACK);
        this.mRectangleLoading.setZIndex(100);
        this.mRectangleLoading.setColor(Color.TRANSPARENT);
        this.mRectangleLoading.attachChild(this.mRectangleTop);
        this.mRectangleLoading.attachChild(this.mRectangleBottom);
        this.mListBitmapTextureAtlas = new ArrayList();
        this.mIconLoadingTTR = paramMainGame.loadTextureRegion("icon/", "icon_loading1.png", 127, 127, this.mListBitmapTextureAtlas);
        this.iconLoading = new Sprite(this.mRectangleLoading.getWidth() / 2.0F - this.mIconLoadingTTR.getWidth() / 2.0F, this.mRectangleLoading.getHeight() / 2.0F - this.mIconLoadingTTR.getHeight() / 2.0F, this.mIconLoadingTTR, paramMainGame.getVertexBufferObjectManager());
        this.iconLoading.setZIndex(100);
        this.iconLoading.setVisible(false);
        this.iconLoading.setAlpha(0.5F);
        LoopEntityModifier localLoopEntityModifier = new LoopEntityModifier(new RotationModifier(1.0F, 0.0F, 360.0F));
        this.iconLoading.registerEntityModifier(localLoopEntityModifier);
        this.mRectangleLoading.attachChild(this.iconLoading);
        paramMainGame.getmHud().attachChild(this.mRectangleLoading);
    }

    public ILoading getmILoading()
    {
        return this.mILoading;
    }

    public void hideLoading(boolean paramBoolean)
    {
        if (!paramBoolean)
        {
            AlphaModifier local4 = new AlphaModifier(this.pDuration, 1.0F, 0.0F)
            {
                protected void onModifierFinished(IEntity paramAnonymousIEntity)
                {
                    super.onModifierFinished(paramAnonymousIEntity);
                    Loading.this.mILoading.closeLoadingCompleted();
                }
            };
            this.iconLoading.registerEntityModifier(local4);
            return;
        }
        AlphaModifier local5 = new AlphaModifier(this.pDuration, 1.0F, 0.0F)
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                MoveYModifier local1 = new MoveYModifier(Loading.this.pDuration, Loading.this.mRectangleTop.getY(), -Loading.this.pHeight)
                {
                    protected void onModifierFinished(IEntity paramAnonymous2IEntity)
                    {
                        super.onModifierFinished(paramAnonymous2IEntity);
                        if (Loading.this.mILoading != null) {
                            Loading.this.mILoading.closeLoadingCompleted();
                        }
                    }
                };
                Loading.this.mRectangleTop.registerEntityModifier(local1);
                MoveYModifier local2 = new MoveYModifier(Loading.this.pDuration, Loading.this.mRectangleBottom.getY(), ConfigScreen.SCREENHEIGHT)
                {
                    protected void onModifierFinished(IEntity paramAnonymous2IEntity)
                    {
                        super.onModifierFinished(paramAnonymous2IEntity);
                    }
                };
                Loading.this.mRectangleBottom.registerEntityModifier(local2);
            }
        };
        this.iconLoading.registerEntityModifier(local5);
    }

    public void setmILoading(ILoading paramILoading)
    {
        this.mILoading = paramILoading;
    }

    public void showLoading(boolean paramBoolean)
    {
        this.mainGame.getmCamera().setHUD(this.mainGame.getmHud());
        if (!paramBoolean)
        {
            this.pDuration = 0.1F;
            this.iconLoading.setAlpha(0.0F);
            this.iconLoading.setVisible(true);
            AlphaModifier local1 = new AlphaModifier(this.pDuration, 0.0F, 1.0F)
            {
                protected void onModifierFinished(IEntity paramAnonymousIEntity)
                {
                    super.onModifierFinished(paramAnonymousIEntity);
                    if (Loading.this.mILoading != null) {
                        Loading.this.mILoading.showLoadingCompleted();
                    }
                }
            };
            this.iconLoading.registerEntityModifier(local1);
            return;
        }
        this.pDuration = 0.5F;
        MoveYModifier local2 = new MoveYModifier(this.pDuration, this.mRectangleTop.getY(), 0.0F)
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                Loading.this.iconLoading.setAlpha(0.0F);
                Loading.this.iconLoading.setVisible(true);
                AlphaModifier local1 = new AlphaModifier(Loading.this.pDuration, 0.0F, 1.0F)
                {
                    protected void onModifierFinished(IEntity paramAnonymous2IEntity)
                    {
                        super.onModifierFinished(paramAnonymous2IEntity);
                        if (Loading.this.mILoading != null) {
                            Loading.this.mILoading.showLoadingCompleted();
                        }
                    }
                };
                Loading.this.iconLoading.registerEntityModifier(local1);
            }
        };
        this.mRectangleTop.registerEntityModifier(local2);
        MoveYModifier local3 = new MoveYModifier(this.pDuration, this.mRectangleBottom.getY(), this.pHeight)
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
            }
        };
        this.mRectangleBottom.registerEntityModifier(local3);
    }
}
