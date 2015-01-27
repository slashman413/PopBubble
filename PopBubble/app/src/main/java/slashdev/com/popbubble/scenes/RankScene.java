package slashdev.com.popbubble.scenes;

import android.graphics.Typeface;
import android.util.Log;

import com.slashbase.myconfig.ConfigScreen;
import com.slashbase.myinterface.IClose;
import com.slashbase.myinterface.IDoBackGround;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseBackIn;

import java.util.ArrayList;
import java.util.Iterator;

import slashdev.com.popbubble.MainGame;
import slashdev.com.popbubble.base.BaseSprite;
import slashdev.com.popbubble.database.Database;
import slashdev.com.popbubble.myinterface.IButtonAnimatedSprite;
import slashdev.com.popbubble.myinterface.IButtonSprite;
import slashdev.com.popbubble.myinterface.ILoadUnLoadResource;
import slashdev.com.popbubble.myinterface.ObjectPopstar;
import slashdev.com.popbubble.object.Coin;

public class RankScene
        extends ObjectPopstar
        implements IDoBackGround, ILoadUnLoadResource, IButtonSprite, IButtonAnimatedSprite
{
    int FONT_SIZE = 40;
    BaseSprite Sbg = null;
    float distanceRow = 15.0F;
    ITextureRegion ibgmain;
    boolean isLoad = false;
    boolean isShowCompleted = false;
    BaseSprite mBg = null;
    ITextureRegion mBgTTR;
    BaseSprite mClose = null;
    ITextureRegion mCloseTTR;
    Rectangle mFillData;
    Font mFont;
    ITextureRegion mHightTTR;
    IClose mIClose = null;
    ArrayList<BitmapTextureAtlas> mListBitmapTextureAtlas;
    ArrayList<BuildableBitmapTextureAtlas> mListBuildableBitmapTextureAtlas;
    MainGame mMainGame;
    Rectangle mRectangle;
    VertexBufferObjectManager mVertexBufferObjectManager;

    public RankScene(MainGame paramMainGame)
    {
        super(paramMainGame);
        this.mMainGame = paramMainGame;
        this.mVertexBufferObjectManager = this.mMainGame.getVertexBufferObjectManager();
        this.mListBitmapTextureAtlas = new ArrayList();
        this.mListBuildableBitmapTextureAtlas = new ArrayList();
        this.mRectangle = new Rectangle(0.0F, 0.0F, ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT, this.mVertexBufferObjectManager);
        this.mRectangle.setColor(Color.TRANSPARENT);
    }

    public void animationBtnPlay(Sprite paramSprite)
    {
        MoveYModifier localMoveYModifier1 = new MoveYModifier(1.5F, paramSprite.getY(), paramSprite.getY() - 80.0F);
        MoveYModifier localMoveYModifier2 = new MoveYModifier(1.5F, paramSprite.getY() - 80.0F, paramSprite.getY());
        ScaleModifier localScaleModifier1 = new ScaleModifier(1.5F, 0.9F, 1.0F, 1.0F, 0.95F);
        ScaleModifier localScaleModifier2 = new ScaleModifier(1.5F, 1.0F, 0.9F, 0.95F, 1.0F);
        paramSprite.registerEntityModifier(new LoopEntityModifier(new ParallelEntityModifier(new IEntityModifier[] { new SequenceEntityModifier(new IEntityModifier[] { localMoveYModifier1, localMoveYModifier2 }), new SequenceEntityModifier(new IEntityModifier[] { localScaleModifier1, localScaleModifier2 }) })));
    }

    public void fillData(BaseSprite paramBaseSprite)
    {
        if (this.mFillData != null) {
            this.mMainGame.removeEntity(this.mFillData);
        }
        this.mFillData = new Rectangle(0.0F, 0.0F, paramBaseSprite.getWidth(), paramBaseSprite.getHeight(), this.mVertexBufferObjectManager);
        this.mFillData.setColor(Color.TRANSPARENT);
        Database localDatabase = new Database(this.mMainGame);
        localDatabase.openDatabase();
        float f = 100.0F;
        ArrayList localArrayList = localDatabase.getListCOIN();
        int i = 0;
        if (i >= localArrayList.size())
        {
            localDatabase.closeDatabase();
            paramBaseSprite.attachChild(this.mFillData);
            Log.e("", "fillData");
            return;
        }
        String str1 = ((Coin)localArrayList.get(i)).getName().toUpperCase();
        int j = ((Coin)localArrayList.get(i)).getCoin();
        Text localText1 = new Text(80.0F, f, this.mFont, i + 1 + " : " + str1, this.mVertexBufferObjectManager);
        Font localFont = this.mFont;
        String str2 = String.valueOf(j);
        VertexBufferObjectManager localVertexBufferObjectManager = this.mVertexBufferObjectManager;
        Text localText2 = new Text(80.0F, f, localFont, str2, localVertexBufferObjectManager);
        // TODO: check this logic
        if (i == 0)
        {
            localText1.setColor(0.9921569F, 0.9098039F, 0.007843138F);
            localText2.setColor(0.9921569F, 0.9098039F, 0.007843138F);
            localText2.setX(this.mFillData.getWidth() - localText2.getWidth() - 30.0F);
            f = f + localText1.getHeight() + this.distanceRow;
            this.mFillData.attachChild(localText1);
            this.mFillData.attachChild(localText2);
            i++;
        }
        if (i != 1) {
            if (i == 2)
            {
                localText1.setColor(0.2509804F, 0.85098F, 1.0F);
                localText2.setColor(0.2509804F, 0.85098F, 1.0F);
                f = 10.0F + (f + localText1.getHeight());
            }
            else
            {
                localText1.setAlpha(0.8F);
                localText2.setAlpha(0.8F);
            }
        }
    }

    public IClose getmClose()
    {
        return this.mIClose;
    }

    public void hideAnimation()
    {
        this.isShowCompleted = false;
        MoveModifier local1 = new MoveModifier(1.0F, 0.0F, 0.0F, 0.0F, -this.mRectangle.getHeight(), EaseBackIn.getInstance())
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                if (RankScene.this.mIClose != null) {
                    RankScene.this.mIClose.onClose();
                }
            }
        };
        this.mRectangle.registerEntityModifier(local1);
    }

    public void loadResource()
    {
        if (!this.isLoad)
        {
            this.mFont = FontFactory.create(this.mMainGame.getFontManager(), this.mMainGame.getTextureManager(), 512, 512, Typeface.create(Typeface.DEFAULT, 1), 35.0F, true, -1);
            this.mFont.load();
            this.mHightTTR = this.mMainGame.loadTextureRegion("button/", "btn_highscore.png", 501, 122, this.mListBitmapTextureAtlas);
            this.mCloseTTR = this.mMainGame.loadTextureRegion("button/", "btn_close.png", 81, 81, this.mListBitmapTextureAtlas);
            this.mBgTTR = this.mMainGame.loadTextureRegion("bg/", "bg_score.png", 676, 1106, this.mListBitmapTextureAtlas);
        }
    }

    public AnimatedSprite onClick(AnimatedSprite paramAnimatedSprite)
    {
        return null;
    }

    public Sprite onClick(Sprite paramSprite)
    {
        setmClose(this.mMainGame.mBtnCloseHighScoreClick);
        hideAnimation();
        return null;
    }

    public void onCompleted()
    {
        if (!this.isLoad)
        {
            BaseSprite localBaseSprite = new BaseSprite(0.0F, 10.0F, this.mHightTTR, this.mVertexBufferObjectManager);
            this.mRectangle.attachChild(localBaseSprite);
            this.mClose = new BaseSprite(this.mRectangle.getWidth() - this.mCloseTTR.getWidth() - 10.0F, 10.0F, this.mCloseTTR, this.mVertexBufferObjectManager);
            this.mClose.setmIButtonSprite(this);
            this.mRectangle.attachChild(this.mClose);
            this.mMainGame.mainScene.registerTouchArea(this.mClose);
            this.mBg = new BaseSprite(this.mRectangle.getWidth() / 2.0F - this.mBgTTR.getWidth() / 2.0F, this.mRectangle.getHeight() - this.mBgTTR.getHeight(), this.mBgTTR, this.mVertexBufferObjectManager);
            this.mRectangle.attachChild(this.mBg);
            fillData(this.mBg);
            this.mMainGame.mainScene.attachChild(this.mRectangle);
            this.isLoad = true;
            return;
        }
        fillData(this.mBg);
    }

    public void onDoBackGround(boolean paramBoolean)
    {
        loadResource();
    }

    public AnimatedSprite onDown(AnimatedSprite paramAnimatedSprite)
    {
        return null;
    }

    public Sprite onDown(Sprite paramSprite)
    {
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
        this.mIClose = paramIClose;
    }

    public void unLoadResource()
    {
        Iterator localIterator = this.mListBitmapTextureAtlas.iterator();
        while (localIterator.hasNext()) {
            ((BitmapTextureAtlas)localIterator.next()).unload();
        }
        this.mListBitmapTextureAtlas.clear();
        this.mRectangle.setPosition(ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT);
//        for (;;)
//        {
//            if (!localIterator.hasNext())
//            {
//                this.mListBitmapTextureAtlas.clear();
//                this.mRectangle.setPosition(ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT);
//                return;
//            }
//            ((BitmapTextureAtlas)localIterator.next()).unload();
//        }
    }
}
