package slashdev.com.popbubble.scenes;

import com.slashbase.myconfig.ConfigScreen;
import com.slashbase.myinterface.IClose;
import com.slashbase.myinterface.IDoBackGround;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import java.util.ArrayList;
import java.util.Iterator;

import com.slashbase.util.UtilLib;

import slashdev.com.popbubble.MainGame;
import slashdev.com.popbubble.base.BaseAnimatedSprite;
import slashdev.com.popbubble.base.BaseSprite;
import slashdev.com.popbubble.myinterface.IButtonAnimatedSprite;
import slashdev.com.popbubble.myinterface.IButtonSprite;
import slashdev.com.popbubble.myinterface.ILoadUnLoadResource;
import slashdev.com.popbubble.myinterface.ObjectPopstar;

public class MenuScene
        extends ObjectPopstar
        implements ILoadUnLoadResource, IDoBackGround, IButtonSprite, IButtonAnimatedSprite
{
    Sprite Sbg;
    BaseSprite Scontin;
    BaseSprite Shighscore;
    Sprite Slogo;
    BaseSprite Snewgame;
    BaseSprite btnMore;
    BaseSprite btnRate;
    BaseSprite btnShare;
    BaseAnimatedSprite btnSound;
    float distanceButton = 30.0F;
    ITextureRegion ibgmain;
    ITextureRegion icontin;
    ITextureRegion ihighscore;
    ITextureRegion ilogo;
    ITextureRegion inewgame;

    boolean isShowCompleted = false;
    ArrayList<BuildableBitmapTextureAtlas> mBuildableBitmapTextureAtlas;
    IClose mClose;
    ArrayList<BitmapTextureAtlas> mListBitmapTextureAtlas = new ArrayList();
    ITextureRegion mMoreTTR;
    ITextureRegion mRateTTR;
    Rectangle mRectangle;
    ITextureRegion mShareTTR;
    TiledTextureRegion mSoundTTR;
    ITextureRegion mStarTTR;
    TimerHandler mTimerHandlerAddStar;
    VertexBufferObjectManager mVertexBufferObjectManager;

    public MenuScene(MainGame paramMainGame)
    {
        super(paramMainGame);
        this.mVertexBufferObjectManager = paramMainGame.getVertexBufferObjectManager();
        this.mBuildableBitmapTextureAtlas = new ArrayList();
    }

    public boolean isShowCompleted() {
        return isShowCompleted;
    }

    void addButonBottom()
    {
        float f = this.mRectangle.getHeight() - this.mMoreTTR.getHeight() - 50.0F;
        this.btnShare = new BaseSprite(this.mRectangle.getWidth() - this.mShareTTR.getWidth() - 50.0F, f, this.mShareTTR, this.mVertexBufferObjectManager);
        this.btnShare.setmIButtonSprite(this);
        this.mRectangle.attachChild(this.btnShare);
        this.mainGame.mainScene.registerTouchArea(this.btnShare);
        this.btnRate = new BaseSprite(this.btnShare.getX() - this.mRateTTR.getWidth() - 50.0F, f, this.mRateTTR, this.mVertexBufferObjectManager);
        this.btnRate.setmIButtonSprite(this);
        this.mRectangle.attachChild(this.btnRate);
        this.mainGame.mainScene.registerTouchArea(this.btnRate);
        this.btnMore = new BaseSprite(this.btnRate.getX() - this.mMoreTTR.getWidth() - 50.0F, f, this.mMoreTTR, this.mVertexBufferObjectManager);
        this.btnMore.setmIButtonSprite(this);
        this.mRectangle.attachChild(this.btnMore);
        this.mainGame.mainScene.registerTouchArea(this.btnMore);
        this.btnSound = new BaseAnimatedSprite(50.0F, f + 10.0F, this.mSoundTTR, this.mVertexBufferObjectManager);
        this.btnSound.setmIButtonAnimatedSprite(this);
        this.mRectangle.attachChild(this.btnSound);
        this.mainGame.mainScene.registerTouchArea(this.btnSound);
        changeStatusSound(this.btnSound, false);
    }

    public void addStar()
    {
        stopAddStar();
        this.mTimerHandlerAddStar = new TimerHandler(0.1F, true, new ITimerCallback()
        {
            public void onTimePassed(TimerHandler paramAnonymousTimerHandler)
            {
                float f = UtilLib.getRandomIndex(150, 500);
                final Sprite localSprite = new Sprite(f, 150.0F, MenuScene.this.mStarTTR, MenuScene.this.mVertexBufferObjectManager);
                MenuScene.this.mRectangle.attachChild(localSprite);
                localSprite.registerEntityModifier(new JumpModifier(1.0F, f, UtilLib.getRandomIndex(0, (int)MenuScene.this.mRectangle.getWidth()), 150.0F, 500.0F, UtilLib.getRandomIndex(200, 500))
                {
                    protected void onModifierFinished(IEntity paramAnonymous2IEntity)
                    {
                        super.onModifierFinished(paramAnonymous2IEntity);
                        MenuScene.this.mainGame.removeEntity(localSprite);
                    }
                });
            }
        });
        this.mainGame.mainScene.registerUpdateHandler(this.mTimerHandlerAddStar);
    }

    public void changeStatusSound(AnimatedSprite paramAnimatedSprite, boolean paramBoolean)
    {
        if (paramBoolean)
        {
            if (this.mainGame.getManagerSound().isSound())
            {
                paramAnimatedSprite.setCurrentTileIndex(1);
                this.mainGame.getManagerSound().setSound(false);
                return;
            }
            paramAnimatedSprite.setCurrentTileIndex(0);
            this.mainGame.getManagerSound().setSound(true);
            return;
        }
        if (this.mainGame.getManagerSound().isSound())
        {
            paramAnimatedSprite.setCurrentTileIndex(0);
            return;
        }
        paramAnimatedSprite.setCurrentTileIndex(1);
    }

    public IClose getmClose()
    {
        return this.mClose;
    }

    public void hideAnimation()
    {
        if (this.mClose != null) {
            this.mClose.onClose();
        }
        stopAddStar();
        this.isShowCompleted = true;
    }

    public void loadResource()
    {
        this.mRectangle = new Rectangle(0.0F, 0.0F, ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT, this.mVertexBufferObjectManager);
        this.mRectangle.setColor(Color.TRANSPARENT);
        this.mainGame.mainScene.attachChild(this.mRectangle);
        this.ibgmain = this.mainGame.loadTextureRegion("gfx/gfx/", "bg.png", 720, 1280, this.mListBitmapTextureAtlas);
        this.ilogo = this.mainGame.loadTextureRegion("gfx/gfx/", "logo.png", 720, 550, this.mListBitmapTextureAtlas);
        this.inewgame = this.mainGame.loadTextureRegion("gfx/gfx/", "btn_newgame.png", 452, 152, this.mListBitmapTextureAtlas);
        this.icontin = this.mainGame.loadTextureRegion("gfx/gfx/", "btn_continue.png", 452, 152, this.mListBitmapTextureAtlas);
        this.ihighscore = this.mainGame.loadTextureRegion("gfx/gfx/", "btn_topplay.png", 452, 152, this.mListBitmapTextureAtlas);
        this.mMoreTTR = this.mainGame.loadTextureRegion("icon/", "icon_more.png", 106, 122, this.mListBitmapTextureAtlas);
        this.mRateTTR = this.mainGame.loadTextureRegion("icon/", "icon_rate.png", 129, 125, this.mListBitmapTextureAtlas);
        this.mShareTTR = this.mainGame.loadTextureRegion("icon/", "icon_share.png", 116, 119, this.mListBitmapTextureAtlas);
        this.mSoundTTR = this.mainGame.loadTiledTextureRegion("icon/", "sound_on_off.png", 200, 100, 2, 1, this.mBuildableBitmapTextureAtlas);
        this.mStarTTR = this.mainGame.loadTextureRegion("icon/", "star6.png", 58, 54, this.mListBitmapTextureAtlas);
    }

    public AnimatedSprite onClick(AnimatedSprite paramAnimatedSprite)
    {
        if (paramAnimatedSprite == this.btnSound) {
            changeStatusSound(this.btnSound, true);
        }
        return null;
    }

    public Sprite onClick(Sprite paramSprite)
    {
        if (paramSprite == this.Snewgame)
        {
            setmClose(this.mainGame.mBtnPlayClick);
            unLoadResource();
            hideAnimation();
        }
        if (paramSprite == this.Scontin)
        {
            setmClose(this.mainGame.mBtnLoadGameClick);
            unLoadResource();
            hideAnimation();
        }
        else if (paramSprite == this.Shighscore)
        {
            setmClose(this.mainGame.mBtnHighSocreClick);
            unLoadResource();
            hideAnimation();
        }
        else if (paramSprite == this.btnMore)
        {
//                this.mainGame.showMoreApp();
        }
        else if (paramSprite == this.btnRate)
        {
            this.mainGame.showRateApp();
        }
        else if (paramSprite == this.btnShare)
        {
            this.mainGame.showRateApp();
        }
        return null;
    }

    public void onCompleted()
    {
        show();
    }

    public void onDoBackGround(boolean paramBoolean)
    {
        loadResource();
    }

    public AnimatedSprite onDown(AnimatedSprite paramAnimatedSprite)
    {
        this.mainGame.getManagerSound().slectedPlay();
        return null;
    }

    public Sprite onDown(Sprite paramSprite)
    {
        this.mainGame.getManagerSound().slectedPlay();
        return null;
    }

    public AnimatedSprite onMove(AnimatedSprite paramAnimatedSprite)
    {
        return null;
    }

    public Sprite onMove(Sprite paramSprite)
    {
        return null;
    }

    public AnimatedSprite onMoveOut(AnimatedSprite paramAnimatedSprite)
    {
        return null;
    }

    public Sprite onMoveOut(Sprite paramSprite)
    {
        return null;
    }

    public AnimatedSprite onUp(AnimatedSprite paramAnimatedSprite)
    {
        return null;
    }

    public Sprite onUp(Sprite paramSprite)
    {
        return null;
    }

    public void setmClose(IClose paramIClose)
    {
        this.mClose = paramIClose;
    }

    public void show()
    {
        this.Slogo = new Sprite(ConfigScreen.SCREENWIDTH / 2 - this.ilogo.getWidth() / 2.0F, 50.0F, this.ilogo, this.mVertexBufferObjectManager);
        this.mRectangle.attachChild(this.Slogo);
        this.Snewgame = new BaseSprite(ConfigScreen.SCREENWIDTH / 2 - this.inewgame.getWidth() / 2.0F, ConfigScreen.SCREENHEIGHT / 2 - this.inewgame.getHeight() / 2.0F, this.inewgame, this.mVertexBufferObjectManager);
        this.Snewgame.setmIButtonSprite(this);
        this.mRectangle.attachChild(this.Snewgame);
        this.mainGame.mainScene.registerTouchArea(this.Snewgame);
        float f1 = this.Snewgame.getY() + this.Snewgame.getHeight() + this.distanceButton;
        this.Scontin = new BaseSprite(ConfigScreen.SCREENWIDTH / 2 - this.icontin.getWidth() / 2.0F, f1, this.icontin, this.mVertexBufferObjectManager);
        this.Scontin.setmIButtonSprite(this);
        this.mRectangle.attachChild(this.Scontin);
        this.mainGame.mainScene.registerTouchArea(this.Scontin);
        float f2 = this.Scontin.getY() + this.Scontin.getHeight() + this.distanceButton;
        this.Shighscore = new BaseSprite(ConfigScreen.SCREENWIDTH / 2 - this.ihighscore.getWidth() / 2.0F, f2, this.ihighscore, this.mVertexBufferObjectManager);
        this.Shighscore.setmIButtonSprite(this);
        this.mRectangle.attachChild(this.Shighscore);
        this.mainGame.mainScene.registerTouchArea(this.Shighscore);
        addButonBottom();
        showAnimation();
    }

    public void showAnimation()
    {
        this.isShowCompleted = false;
        addStar();
    }

    public void stopAddStar()
    {
        if (this.mTimerHandlerAddStar != null) {
            this.mainGame.mainScene.unregisterUpdateHandler(this.mTimerHandlerAddStar);
        }
    }

    public void unLoadResource()
    {
        Iterator localIterator1 = this.mListBitmapTextureAtlas.iterator();
        Iterator localIterator2 = this.mBuildableBitmapTextureAtlas.iterator();

        while (localIterator1.hasNext())
        {
            ((BitmapTextureAtlas)localIterator1.next()).unload();
        }
        this.mListBitmapTextureAtlas.clear();

        while (localIterator2.hasNext())
        {
            ((BuildableBitmapTextureAtlas)localIterator2.next()).unload();
        }
        this.mBuildableBitmapTextureAtlas.clear();

        this.mRectangle.setPosition(ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT);
    }
}
