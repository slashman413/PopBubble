package slashdev.com.popbubble.scenes;

import android.util.Log;

import com.slashbase.myconfig.ConfigScreen;
import com.slashbase.myinterface.IClose;
import com.slashbase.myinterface.IDoBackGround;
import com.slashbase.myinterface.IHandler;
import com.slashbase.util.UtilLib;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackOut;
import org.andengine.util.modifier.ease.EaseCircularIn;
import org.andengine.util.modifier.ease.EaseElasticOut;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import slashdev.com.popbubble.AnimationPopstar;
import slashdev.com.popbubble.object.Data;
import slashdev.com.popbubble.DialogTarget;
import slashdev.com.popbubble.ItemStar;
import slashdev.com.popbubble.MainGame;
import slashdev.com.popbubble.base.BaseSprite;
import slashdev.com.popbubble.database.DataSaveGame;
import slashdev.com.popbubble.level.Level;
import slashdev.com.popbubble.myinterface.IButtonAnimatedSprite;
import slashdev.com.popbubble.myinterface.IButtonSprite;
import slashdev.com.popbubble.myinterface.ILoadUnLoadResource;
import slashdev.com.popbubble.myinterface.ObjectPopstar;
import slashdev.com.popbubble.mylog.Mylog;

public class PlayScene
        extends ObjectPopstar
        implements IDoBackGround, ILoadUnLoadResource, IButtonSprite, IButtonAnimatedSprite
{
    int BONUS = 2000;
    int CONTS = 72;
    int DEMDEL = 0;
    int DIEMITEM = 0;
    int LEVEL = 1;
    int NUMBERSTAR = 4;
    int SCORE = 0;
    int TARGET = 0;
    ArrayList<ItemStar> arrEated = new ArrayList();
    float confItem = ConfigScreen.SCREENWIDTH / 10;
    boolean iCheckLoad = true;
    ITextureRegion iCool;
    ITextureRegion iGood;
    ITextureRegion iSaveBtn;
    ITextureRegion iScore;
    ITextureRegion iSelect;
    ITextureRegion iSpause;
    ITextureRegion iStage;
    ITextureRegion iStarEffect;
    ITextureRegion iStargetClear;
    ITextureRegion ibgGame;
    boolean isClear = true;
    boolean isClickItem = true;
    boolean isTouch = false;
    ITextureRegion itarget;
    AnimationPopstar mAnimationPopstar;
    ArrayList<BuildableBitmapTextureAtlas> mBuildableBitmapTextureAtlas = new ArrayList();
    Data mDataBallTMPRow = null;
    Data mDataBallTMPCol = null;
    DataSaveGame mDataSaveGame;
    DialogTarget mDialogTarget;
    ArrayList<ItemStar> mLastList = new ArrayList();
    Level mLevel;
    ArrayList<BitmapTextureAtlas> mListBitmapTextureAtlas = new ArrayList();
    ArrayList<ItemStar> mList_Anduoc = new ArrayList();
    ArrayList<ItemStar> mList_Check = new ArrayList();
    ArrayList<ItemStar> mList_Doc;
    ArrayList<ITiledTextureRegion> mList_Star = new ArrayList();
    ArrayList<ItemStar> mList_Temp = new ArrayList();
    ArrayList<ITextureRegion> mList_effectStar = new ArrayList();
    Rectangle mRectangle;
    Rectangle mRectangleEffect;
    Rectangle mRectangleMatran;
    VertexBufferObjectManager mVertexBufferObjectManager;
    Mylog mylog;
    boolean playEffect = true;
    Sprite sBggame;
    Sprite sCool;
    Sprite sGood;
    Sprite sLevel;
    ItemStar[][] sPop = (ItemStar[][])Array.newInstance(ItemStar.class, new int[] { 10, 10 });
    Sprite sScore;
    Sprite sSelecter;
    Sprite sTarget;
    Sprite sTargetClear;
    BaseSprite sbtnPause;
    BaseSprite sbtnSave;
    ArrayList<ItemStar> starMove = new ArrayList();
    Text textLv;
    Text textTarget;
    Text txtFail;
    Text txtGiveUp;
    Text txtLevel;
    Text txtTarget;
    Text txt_Bonus;
    Text txt_Level;
    Text txt_Point;
    Text txt_Score;
    Text txt_Target;
    Text txt_TotalLastList;
    Text txt_addBonus;

    public PlayScene(MainGame paramMainGame)
    {
        super(paramMainGame);
        this.mVertexBufferObjectManager = paramMainGame.getVertexBufferObjectManager();
        this.mLevel = new Level();
        this.mAnimationPopstar = new AnimationPopstar(paramMainGame);
        this.mDialogTarget = new DialogTarget(paramMainGame);
        this.mDialogTarget.loadResource();
    }

    public void addBonusText()
    {
        this.txt_addBonus.setText("+" + this.BONUS);
        this.txt_addBonus.setPosition(this.txt_Bonus.getX() + this.txt_Bonus.getWidth(), this.txt_Bonus.getY() + this.txt_Bonus.getHeight());
        this.txt_addBonus.setScale(3.0F);
        MoveModifier local16 = new MoveModifier(1.5F, this.txt_addBonus.getX(), this.sScore.getX() + this.sScore.getWidth() / 2.0F, this.txt_addBonus.getY(), this.sScore.getY() + this.sScore.getHeight())
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                PlayScene localPlayScene = PlayScene.this;
                localPlayScene.SCORE += PlayScene.this.BONUS;
                PlayScene.this.txt_Score.setText(String.valueOf(PlayScene.this.SCORE));
                float f1 = PlayScene.this.sScore.getWidth() / 2.0F - PlayScene.this.txt_Score.getWidth() / 2.0F;
                float f2 = PlayScene.this.sScore.getHeight() / 2.0F - PlayScene.this.txt_Score.getHeight() / 2.0F;
                PlayScene.this.txt_Score.setPosition(f1, f2);
                PlayScene.this.txt_addBonus.setPosition(5000.0F, 5000.0F);
                PlayScene.this.hideBonus();
            }
        };
        this.txt_addBonus.registerEntityModifier(local16);
    }

    public void addCoolEffect()
    {
        float f1 = ConfigScreen.SCREENWIDTH / 2 - this.sCool.getWidth() / 2.0F;
        float f2 = ConfigScreen.SCREENWIDTH / 2 - this.sCool.getHeight() / 3.0F;
        this.sCool.setPosition(f1, f2);
        this.mainGame.getManagerSound().goodAndCool();
        ParallelEntityModifier local3 = new ParallelEntityModifier(new IEntityModifier[] { new AlphaModifier(1.5F, 0.0F, 1.0F), new ScaleModifier(0.5F, 3.0F, 1.0F) })
        {
            public void onModifierFinished(IModifier<IEntity> paramAnonymousIModifier, IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIModifier, paramAnonymousIEntity);
                PlayScene.this.sCool.setPosition(ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT);
            }
        };
        this.sCool.registerEntityModifier(local3);
    }

    public void addEffect(int paramInt, float paramFloat1, float paramFloat2)
    {
        int i = UtilLib.getRandomIndex(5, 10);
        for (int j = 0;; j++)
        {
            if (j >= i) {
                return;
            }
            effectRemoveStar(paramInt, paramFloat1, paramFloat2);
        }
    }

    public void addGoodEffect()
    {
        float f1 = ConfigScreen.SCREENWIDTH / 2 - this.sGood.getWidth() / 2.0F;
        float f2 = ConfigScreen.SCREENWIDTH / 2 - this.sGood.getHeight() / 3.0F;
        this.sGood.setPosition(f1, f2);
        this.mainGame.getManagerSound().goodAndCool();
        ParallelEntityModifier local4 = new ParallelEntityModifier(new IEntityModifier[] { new AlphaModifier(1.5F, 0.0F, 1.0F), new ScaleModifier(0.5F, 3.0F, 1.0F) })
        {
            public void onModifierFinished(IModifier<IEntity> paramAnonymousIModifier, IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIModifier, paramAnonymousIEntity);
                PlayScene.this.sGood.setPosition(ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT);
            }
        };
        this.sGood.registerEntityModifier(local4);
    }

    public void addStar(int paramInt1, int paramInt2, int paramInt3)
    {
        this.sPop[paramInt1][paramInt2] = new ItemStar(paramInt2 * this.confItem, paramInt1 * this.confItem, ((ITiledTextureRegion)this.mList_Star.get(0)).getWidth(), ((ITiledTextureRegion)this.mList_Star.get(0)).getHeight(), (ITiledTextureRegion)this.mList_Star.get(paramInt3), this.mVertexBufferObjectManager)
        {
            public boolean onAreaTouched(TouchEvent paramAnonymousTouchEvent, float paramAnonymousFloat1, float paramAnonymousFloat2)
            {
                if (paramAnonymousTouchEvent.isActionDown()) {
//                    PlayScene.this.isClickItem = true;
                }
                if (paramAnonymousTouchEvent.isActionUp()) {
                    if (PlayScene.this.isClickItem)
                    {
                        if (getCurrentTileIndex() != 1) {
                            setScale(1.0F);
                        }

                        if (PlayScene.this.arrEated.size() > 0) {
                            if (PlayScene.this.arrEated.contains(this)) {
                                PlayScene.this.getScore(PlayScene.this.arrEated);
                                PlayScene.this.isClickItem = false;
                                for (int n = 0; n < PlayScene.this.arrEated.size(); n++) {
                                    Data localData2 = (Data)((ItemStar)PlayScene.this.arrEated.get(n)).getUserData();
                                    int i1 = localData2.getI();
                                    int i2 = localData2.getJ();
                                    PlayScene.this.sPop[i1][i2] = null;
                                }
                                PlayScene.this.deleteS(PlayScene.this.arrEated);
                                return true;
                            } else {
                                if (PlayScene.this.arrEated.size() > 0) {
                                    for (int j = 0; j < PlayScene.this.arrEated.size(); j++) {
                                        Data localData1 = (Data)((ItemStar)PlayScene.this.arrEated.get(j)).getUserData();
                                        int k = localData1.getI();
                                        int m = localData1.getJ();
                                        if (PlayScene.this.sPop[k][m] != null) {
                                            PlayScene.this.sPop[k][m].setCurrentTileIndex(0);
                                            PlayScene.this.sPop[k][m].setUserData(new Data(k, m, localData1.getColor(), false));
                                        }
                                    }
                                }
                                PlayScene.this.arrEated.clear();
                                PlayScene.this.itemCheck(this);
                            }
                        } else {
                            if (PlayScene.this.arrEated.size() > 0) {
                                for (int j = 0; j < PlayScene.this.arrEated.size(); j++) {
                                    Data localData1 = (Data)((ItemStar)PlayScene.this.arrEated.get(j)).getUserData();
                                    int k = localData1.getI();
                                    int m = localData1.getJ();
                                    if (PlayScene.this.sPop[k][m] != null) {
                                        PlayScene.this.sPop[k][m].setCurrentTileIndex(0);
                                        PlayScene.this.sPop[k][m].setUserData(new Data(k, m, localData1.getColor(), false));
                                    }
                                }
                            }
                            PlayScene.this.itemCheck(this);
                        }





                        ///
//                        if (PlayScene.this.arrEated.size() > 0) {
//                            if ((paramAnonymousFloat1 >= 1.0F) && (paramAnonymousFloat1 <= getWidth() - 1.0F) && (paramAnonymousFloat2 >= 1.0F) && (paramAnonymousFloat2 <= getHeight() - 1.0F)) {
//                                for (int n = 0; n < PlayScene.this.arrEated.size(); n++) {
//                                    Data localData2 = (Data)((ItemStar)PlayScene.this.arrEated.get(n)).getUserData();
//                                    int i1 = localData2.getI();
//                                    int i2 = localData2.getJ();
//                                    PlayScene.this.sPop[i1][i2] = null;
//                                }
//                                PlayScene.this.arrEated.clear();
//                                PlayScene.this.itemCheck(this);
//                            } else {
//                                PlayScene.this.getScore(PlayScene.this.arrEated);
//                                PlayScene.this.isClickItem = false;
//                                PlayScene.this.deleteS(PlayScene.this.arrEated);
//                            }
//                        } else {
//                            PlayScene.this.itemCheck(this);
//                            if (PlayScene.this.arrEated.size() > 0) {
//                                for (int j = 0; j < PlayScene.this.arrEated.size(); j++) {
//                                    Data localData1 = (Data)((ItemStar)PlayScene.this.arrEated.get(j)).getUserData();
//                                    int k = localData1.getI();
//                                    int m = localData1.getJ();
//                                    PlayScene.this.sPop[k][m].setCurrentTileIndex(0);
//                                    PlayScene.this.sPop[k][m].setUserData(new Data(k, m, localData1.getColor(), false));
//                                }
//                            }
//                        }




                        ///////

//                        if ((paramAnonymousFloat1 >= 1.0F) && (paramAnonymousFloat1 <= getWidth() - 1.0F) && (paramAnonymousFloat2 >= 1.0F) && (paramAnonymousFloat2 <= getHeight() - 1.0F)) {
//                            if (PlayScene.this.arrEated.size() > 0) {
//                                for (int n = 0; n < PlayScene.this.arrEated.size(); n++) {
//                                    Data localData2 = (Data)((ItemStar)PlayScene.this.arrEated.get(n)).getUserData();
//                                    int i1 = localData2.getI();
//                                    int i2 = localData2.getJ();
//                                    PlayScene.this.sPop[i1][i2] = null;
//                                }
//                                PlayScene.this.arrEated.clear();
//                                PlayScene.this.itemCheck(this);
//                            }
//                        }
//                        if (PlayScene.this.arrEated.size() > 0) {
////                                for (int j = 0; j < PlayScene.this.arrEated.size(); j++) {
////                                    Data localData1 = (Data)((ItemStar)PlayScene.this.arrEated.get(j)).getUserData();
////                                    int k = localData1.getI();
////                                    int m = localData1.getJ();
////                                    PlayScene.this.sPop[k][m].setCurrentTileIndex(0);
////                                    PlayScene.this.sPop[k][m].setUserData(new Data(k, m, localData1.getColor(), false));
////                                }
//                            PlayScene.this.getScore(PlayScene.this.arrEated);
//                            PlayScene.this.isClickItem = false;
//                            PlayScene.this.deleteS(PlayScene.this.arrEated);
//                            for (int n = 0; n < PlayScene.this.arrEated.size(); n++) {
//                                Data localData2 = (Data)((ItemStar)PlayScene.this.arrEated.get(n)).getUserData();
//                                int i1 = localData2.getI();
//                                int i2 = localData2.getJ();
//                                PlayScene.this.sPop[i1][i2] = null;
//                            }
//                            PlayScene.this.arrEated.clear();
//
//                        } else {
//                            PlayScene.this.arrEated.clear();
//                            PlayScene.this.itemCheck(this);
//                            if (PlayScene.this.arrEated.size() > 0) {
//                                for (int j = 0; j < PlayScene.this.arrEated.size(); j++) {
//                                    Data localData1 = (Data)((ItemStar)PlayScene.this.arrEated.get(j)).getUserData();
//                                    int k = localData1.getI();
//                                    int m = localData1.getJ();
//                                    PlayScene.this.sPop[k][m].setCurrentTileIndex(0);
//                                    PlayScene.this.sPop[k][m].setUserData(new Data(k, m, localData1.getColor(), false));
//                                }
//                            }
//                        }


//                        int n = 0;
//                        if (n < PlayScene.this.arrEated.size()) {
//                            while ((!paramAnonymousTouchEvent.isActionMove()) || ((paramAnonymousFloat1 >= 1.0F) && (paramAnonymousFloat1 <= getWidth() - 1.0F) && (paramAnonymousFloat2 >= 1.0F) && (paramAnonymousFloat2 <= getHeight() - 1.0F)))
//                            {
//                                Data localData2 = (Data)((ItemStar)PlayScene.this.arrEated.get(n)).getUserData();
//                                int i1 = localData2.getI();
//                                int i2 = localData2.getJ();
//                                PlayScene.this.sPop[i1][i2] = null;
//                                n++;
//                                if (PlayScene.this.arrEated.size() > 0) {
//                                    for (int j = 0; j < PlayScene.this.arrEated.size(); j++) {
//                                        Data localData1 = (Data)((ItemStar)PlayScene.this.arrEated.get(j)).getUserData();
//                                        int k = localData1.getI();
//                                        int m = localData1.getJ();
//                                        PlayScene.this.sPop[k][m].setCurrentTileIndex(0);
//                                        PlayScene.this.sPop[k][m].setUserData(new Data(k, m, localData1.getColor(), false));
//                                    }
//                                    PlayScene.this.arrEated.clear();
//                                    PlayScene.this.itemCheck(this);
//                                    return true;
//                                }
//                            }
//                        }

                    }
                }
                if (paramAnonymousTouchEvent.isActionMove() && !((paramAnonymousFloat1 >= 1.0F) && (paramAnonymousFloat1 <= getWidth() - 1.0F) && (paramAnonymousFloat2 >= 1.0F) && (paramAnonymousFloat2 <= getHeight() - 1.0F))) {
//                    for (int n = 0; n < PlayScene.this.arrEated.size(); n++) {
//                        Data localData2 = (Data)((ItemStar)PlayScene.this.arrEated.get(n)).getUserData();
//                        int i1 = localData2.getI();
//                        int i2 = localData2.getJ();
//                        PlayScene.this.sPop[i1][i2] = null;
//                    }
                }

                return true;
            }
        };
        this.sPop[paramInt1][paramInt2].setCurrentTileIndex(0);
        this.mainGame.mainScene.registerTouchArea(this.sPop[paramInt1][paramInt2]);
        this.sPop[paramInt1][paramInt2].setUserData(new Data(paramInt1, paramInt2, paramInt3, false));
        moveStar(this.sPop[paramInt1][paramInt2]);
        this.sPop[paramInt1][paramInt2].setZIndex(3);
        this.sPop[paramInt1][paramInt2].sortChildren();
        this.mRectangleMatran.attachChild(this.sPop[paramInt1][paramInt2]);
    }

    public void addStargetClear()
    {
        if (this.isClear)
        {
            float f1 = ConfigScreen.SCREENWIDTH / 2 - this.sTargetClear.getWidth() / 2.0F;
            float f2 = ConfigScreen.SCREENHEIGHT / 2 - this.sTargetClear.getHeight() / 2.0F;
            this.sTargetClear.setPosition(f1, f2);
            this.mainGame.getManagerSound().stargetClear();
            SequenceEntityModifier localSequenceEntityModifier = new SequenceEntityModifier(new IEntityModifier[] { new ParallelEntityModifier(new IEntityModifier[] { new AlphaModifier(1.5F, 0.0F, 1.0F), new ScaleModifier(0.5F, 2.0F, 1.0F) }), new ParallelEntityModifier(new IEntityModifier[] { new MoveModifier(0.5F, this.sTargetClear.getX(), 50.0F + (ConfigScreen.SCREENWIDTH / 2 - this.sTargetClear.getWidth() / 2.0F), this.sTargetClear.getY(), -100.0F), new ScaleModifier(0.5F, 1.0F, 0.3F) }) });
            this.sTargetClear.registerEntityModifier(localSequenceEntityModifier);
            this.isClear = false;
        }
    }

    public void animation1_off(ItemStar paramItemStar)
    {
        paramItemStar.setAlpha(1.0F);
    }

    public void animation1_on()
    {
        SequenceEntityModifier localSequenceEntityModifier = new SequenceEntityModifier(new IEntityModifier[] { new ScaleModifier(1.0F, 2.0F, 1.0F), new LoopEntityModifier(new SequenceEntityModifier(new IEntityModifier[] { new AlphaModifier(0.05F, 1.0F, 0.0F), new AlphaModifier(0.05F, 0.0F, 1.0F) }), 5, new IEntityModifier.IEntityModifierListener()
        {
            public void onModifierFinished(IModifier<IEntity> paramAnonymousIModifier, IEntity paramAnonymousIEntity)
            {
                PlayScene.this.mDialogTarget.hide();
            }

            public void onModifierStarted(IModifier<IEntity> paramAnonymousIModifier, IEntity paramAnonymousIEntity) {}
        }) });
        this.txt_Target.registerEntityModifier(localSequenceEntityModifier);
    }

    public void bonusScore()
    {
        this.BONUS = (-200 + this.BONUS);
        if (this.BONUS >= 0)
        {
            this.txt_Bonus.setText("BONUS " + this.BONUS);
            this.txt_Bonus.setPosition(this.txt_Bonus.getX(), this.txt_Bonus.getY());
        }
    }

    public void capture()
    {
        IClose local9 = new IClose()
        {
            public void onClose()
            {
                IHandler local1 = new IHandler()
                {
                    public void doWork()
                    {
                        PlayScene.this.hidePlayAndShowSaveGame();
                    }
                };
                PlayScene.this.mainGame.handlerDoWork(local1);
            }
        };
        IClose local10 = new IClose()
        {
            public void onClose()
            {
                IHandler local1 = new IHandler()
                {
                    public void doWork()
                    {
                        UtilLib.showToast(PlayScene.this.mainGame, "Save fail");
                    }
                };
                PlayScene.this.mainGame.handlerDoWork(local1);
            }
        };
        this.mainGame.capture(this.mRectangle, "tmp.png", local9, local10);
    }

    public void checkDefine()
    {
        this.mList_Check.clear();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (this.sPop[i][j] != null)
                {
                    Data localData1 = (Data)this.sPop[i][j].getUserData();
                    int k = localData1.getI();
                    int m = localData1.getJ();
                    int n = localData1.getColor();
                    if ((k - 1 > -1) && (this.sPop[(k - 1)][m] != null))
                    {
                        Data localData5 = (Data)this.sPop[(k - 1)][m].getUserData();
                        if ((!localData5.isCheck()) && (localData5.getColor() == n)) {
                            this.mList_Check.add(this.sPop[(k - 1)][m]);
                        }
                    }
                    if ((m - 1 > -1) && (this.sPop[k][(m - 1)] != null))
                    {
                        Data localData4 = (Data)this.sPop[k][(m - 1)].getUserData();
                        if ((!localData4.isCheck()) && (localData4.getColor() == n)) {
                            this.mList_Check.add(this.sPop[k][(m - 1)]);
                        }
                    }
                    if ((m + 1 < 10) && (this.sPop[k][(m + 1)] != null))
                    {
                        Data localData3 = (Data)this.sPop[k][(m + 1)].getUserData();
                        if ((!localData3.isCheck()) && (localData3.getColor() == n)) {
                            this.mList_Check.add(this.sPop[k][(m + 1)]);
                        }
                    }
                    if ((k + 1 < 10) && (this.sPop[(k + 1)][m] != null))
                    {
                        Data localData2 = (Data)this.sPop[(k + 1)][m].getUserData();
                        if ((!localData2.isCheck()) && (localData2.getColor() == n)) {
                            this.mList_Check.add(this.sPop[(k + 1)][m]);
                        }
                    }
                }
            }
        }
        if (this.mList_Check.size() == 0) {
            lastListStar();
        }
    }

    public void checkEat()
    {
        if (this.mList_Anduoc.size() < 2) {
            for (int m = 0; m < this.mList_Anduoc.size(); m++) {
                Data localData2 = (Data)((ItemStar)this.mList_Anduoc.get(m)).getUserData();
                ((ItemStar)this.mList_Anduoc.get(m)).setUserData(new Data(localData2.getI(), localData2.getJ(), localData2.getColor(), false));
            }
        } else {
            synchronized (this.mList_Anduoc)
            {
                for (int i = 0; i < this.mList_Anduoc.size(); i++) {
                    this.mainGame.getManagerSound().touchStar();
                    Data localData1 = (Data)((ItemStar)this.mList_Anduoc.get(i)).getUserData();
                    int j = localData1.getI();
                    int k = localData1.getJ();
                    this.sPop[j][k].setUserData(localData1);
                    this.sPop[j][k].setCurrentTileIndex(1);
                    this.arrEated.add(this.sPop[j][k]);
                    ScaleModifier localScaleModifier = new ScaleModifier(0.2F, 1.1F, 1.0F);
                    this.sPop[j][k].registerEntityModifier(localScaleModifier);
                }
                viewPoint(this.arrEated);
            }
        }
        this.mList_Anduoc.clear();
        this.mList_Temp.clear();
    }

    public boolean checkIsWin()
    {
        return this.SCORE >= this.mLevel.getTarget(this.LEVEL);
    }

    public void checkLoadorNew()
    {
        if (this.mDataSaveGame == null)
        {
            this.mDialogTarget.show(this.LEVEL);
            return;
        }
        if (this.iCheckLoad)
        {
            this.LEVEL = this.mDataSaveGame.getLevel();
            this.SCORE = this.mDataSaveGame.getCoin();
            this.TARGET = this.mLevel.getTarget(this.LEVEL);
            setPositionText(this.txt_Level, this.LEVEL);
            setPositionText(this.txt_Score, this.SCORE);
            setPositionText(this.txt_Target, this.TARGET);
            this.mDialogTarget.show(this.LEVEL);
            this.iCheckLoad = false;
            return;
        }
        this.mDataSaveGame = null;
        this.mDialogTarget.show(this.LEVEL);
    }

    public void delLastItem(final ArrayList<ItemStar> paramArrayList)
    {
        if (paramArrayList.size() != 0)
        {
            hidetargetClear();
            this.DEMDEL = (1 + this.DEMDEL);
            if (this.DEMDEL <= 10)
            {
                bonusScore();
                this.mainGame.getManagerSound().soundEffect();
                final Sprite localSprite2 = (Sprite)paramArrayList.get(0);
                paramArrayList.remove(0);
                localSprite2.registerEntityModifier(new AlphaModifier(0.3F, 1.0F, 0.0F)
                {
                    Data mData = (Data) localSprite2.getUserData();
                    float pX = localSprite2.getX();
                    float pY = localSprite2.getY();

                    protected void onModifierFinished(IEntity paramAnonymousIEntity)
                    {
                        super.onModifierFinished(paramAnonymousIEntity);
                        PlayScene.this.addEffect(mData.getColor(), pX, pY);
                        PlayScene.this.mainGame.removeEntity(localSprite2);
                        PlayScene.this.delLastItem(paramArrayList);
                        PlayScene.this.mainGame.getManagerSound().effectStar();
                    }
                });
            }
            if (this.DEMDEL > 10)
            {
                this.mainGame.getManagerSound().soundEffect();
                final Sprite localSprite1 = (Sprite)paramArrayList.get(0);
                paramArrayList.remove(0);
                localSprite1.registerEntityModifier(new AlphaModifier(0.05F, 1.0F, 0.0F)
                {
                    Data mData = (Data) localSprite1.getUserData();
                    float pX = localSprite1.getX();
                    float pY = localSprite1.getY();

                    protected void onModifierFinished(IEntity paramAnonymousIEntity)
                    {
                        super.onModifierFinished(paramAnonymousIEntity);
//                        PlayScene.this.addEffect(this.val$mData.getColor(), this.val$pX, this.val$pY);
                        PlayScene.this.addEffect(mData.getColor(), pX, pY);
                        PlayScene.this.mainGame.removeEntity(localSprite1);
                        PlayScene.this.delLastItem(paramArrayList);
                        PlayScene.this.mainGame.getManagerSound().effectStar();
                    }
                });
            }
            return;
        }
        paramArrayList.clear();
        addBonusText();
        this.DEMDEL = 0;
    }

    public void deleteS(final ArrayList<ItemStar> paramArrayList)
    {
        if (paramArrayList.size() != 0)
        {
            if (this.playEffect)
            {
                this.playEffect = false;
                this.mainGame.getManagerSound().soundEffect();
                this.mainGame.getManagerSound().playSoundDelay(paramArrayList.size());
            }
            final Sprite localSprite = (Sprite)paramArrayList.get(0);
            paramArrayList.remove(0);
            localSprite.registerEntityModifier(new AlphaModifier(0.05F, 1.0F, 0.0F)
            {
                Data mData = (Data) localSprite.getUserData();
                float pX = localSprite.getX();
                float pY = localSprite.getY();

                protected void onModifierFinished(IEntity paramAnonymousIEntity)
                {
                    super.onModifierFinished(paramAnonymousIEntity);
                    PlayScene.this.addEffect(mData.getColor(), pX, pY);
                    PlayScene.this.mainGame.removeEntity(localSprite);
                    PlayScene.this.deleteS(paramArrayList);
                }
            });
            return;
        }
        paramArrayList.clear();
        this.playEffect = true;
        moveRow();
    }

    public void effectRemoveStar(int paramInt, float paramFloat1, float paramFloat2)
    {
        float f1 = UtilLib.getRandomIndex(-ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENWIDTH);
        float f2 = UtilLib.getRandomIndex(-200, ConfigScreen.SCREENHEIGHT / 2);
        float f3 = UtilLib.getRandomIndex(500, 1000) / 1000.0F;
        final Sprite localSprite = new Sprite(paramFloat1, paramFloat2, (ITextureRegion)this.mList_effectStar.get(paramInt), this.mVertexBufferObjectManager);
        this.mRectangleMatran.attachChild(localSprite);
        localSprite.registerEntityModifier(new ParallelEntityModifier(new IEntityModifier[] { new JumpModifier(f3, paramFloat1, paramFloat1 + f1, paramFloat2, paramFloat2 + f2, UtilLib.getRandomIndex(50, 100)), new ScaleModifier(f3, 1.1F, 0.0F)
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                PlayScene.this.mainGame.removeEntity(localSprite);
            }
        } }));
    }

    public int getLEVEL()
    {
        return this.LEVEL;
    }

    public int getSCORE()
    {
        return this.SCORE;
    }

    public void getScore(ArrayList<ItemStar> paramArrayList)
    {
        this.DIEMITEM = (5 * paramArrayList.size());
        this.SCORE += this.DIEMITEM * paramArrayList.size();
        if (this.SCORE >= this.mLevel.getTarget(this.LEVEL)) {
            addStargetClear();
        }
        if ((paramArrayList.size() >= 10) && (paramArrayList.size() <= 15)) {
            addCoolEffect();
        }
        if (paramArrayList.size() >= 15) {
            addGoodEffect();
        }
        this.txt_Score.setText(String.valueOf(this.SCORE));
        float f1 = this.sScore.getWidth() / 2.0F - this.txt_Score.getWidth() / 2.0F;
        float f2 = this.sScore.getHeight() / 2.0F - this.txt_Score.getHeight() / 2.0F;
        this.txt_Score.setPosition(f1, f2);
    }

    public int getTARGET()
    {
        return this.TARGET;
    }

    public DataSaveGame getmDataSaveGame()
    {
        return this.mDataSaveGame;
    }

    public Level getmLevel()
    {
        return this.mLevel;
    }

    public Rectangle getmRectangle()
    {
        return this.mRectangle;
    }

    public ItemStar[][] getsPop()
    {
        return this.sPop;
    }

    public void hideAnimation()
    {
        MoveXModifier local12 = new MoveXModifier(0.5F, this.mRectangle.getX(), ConfigScreen.SCREENWIDTH)
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
            }
        };
        this.mRectangle.registerEntityModifier(local12);
    }

    public void hideBonus()
    {
        float f = this.mRectangle.getWidth() + this.txt_Bonus.getWidth();
        MoveXModifier local17 = new MoveXModifier(1.0F, this.txt_Bonus.getX(), f)
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                PlayScene.this.nextPlay();
            }
        };
        this.txt_Bonus.registerEntityModifier(local17);
    }

    public void hidePlayAndShowSaveGame()
    {
        AlphaModifier local11 = new AlphaModifier(1.0F, 1.0F, 0.0F)
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                PlayScene.this.mainGame.getmLoadGameScene().setSave(true);
                PlayScene.this.mainGame.getmLoadGameScene().setmClose(PlayScene.this.mainGame.mBtnCloseLoadGameClickSave);
                PlayScene.this.mainGame.showLoadGame();
            }
        };
        this.mRectangle.registerEntityModifier(local11);
    }

    public void hidetargetClear()
    {
        if (!this.isClear)
        {
            this.sTargetClear.setPosition(ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT);
            this.isClear = true;
        }
    }

    public void itemCheck(ItemStar paramItemStar)
    {
        Data localData1 = (Data)paramItemStar.getUserData();
        int i = localData1.getI();
        int j = localData1.getJ();
        int k = localData1.getColor();

        paramItemStar.setUserData(new Data(localData1.getI(), localData1.getJ(), localData1.getColor(), true));
        paramItemStar.setCheck(true);
        this.mList_Anduoc.add(paramItemStar);
        if ((i - 1 > -1) && (this.sPop[(i - 1)][j] != null))
        {
            Data localData5 = (Data)this.sPop[(i - 1)][j].getUserData();
            if ((!localData5.isCheck()) && (localData5.getColor() == k)) {
                this.mList_Temp.add(this.sPop[(i - 1)][j]);
            }
        }
        if ((j - 1 > -1) && (this.sPop[i][(j - 1)] != null))
        {
            Data localData4 = (Data)this.sPop[i][(j - 1)].getUserData();
            if ((!localData4.isCheck()) && (localData4.getColor() == k)) {
                this.mList_Temp.add(this.sPop[i][(j - 1)]);
            }
        }
        if ((j + 1 < 10) && (this.sPop[i][(j + 1)] != null))
        {
            Data localData3 = (Data)this.sPop[i][(j + 1)].getUserData();
            if ((!localData3.isCheck()) && (localData3.getColor() == k)) {
                this.mList_Temp.add(this.sPop[i][(j + 1)]);
            }
        }
        if ((i + 1 < 10) && (this.sPop[(i + 1)][j] != null))
        {
            Data localData2 = (Data)this.sPop[(i + 1)][j].getUserData();
            if ((!localData2.isCheck()) && (localData2.getColor() == k)) {
                this.mList_Temp.add(this.sPop[(i + 1)][j]);
            }
        }
        loopItemCheck();
        checkEat();
    }

    public void lastListStar()
    {
        this.mLastList.clear();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (this.sPop[i][j] != null) {
                    this.mLastList.add(this.sPop[i][j]);
                }
            }
        }
        showBonus(this.mLastList);
    }

    public void loadResource()
    {
        this.mRectangle = new Rectangle(0.0F, 0.0F, ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT, this.mVertexBufferObjectManager);
        this.mRectangle.setColor(Color.TRANSPARENT);
        this.mainGame.mainScene.attachChild(this.mRectangle);
        this.mRectangleEffect = new Rectangle(0.0F, 0.0F, ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT, this.mVertexBufferObjectManager);
        this.mRectangleEffect.setColor(Color.TRANSPARENT);
        this.mRectangle.attachChild(this.mRectangleEffect);
        this.mRectangleEffect.setZIndex(1000);
        this.mRectangleEffect.sortChildren();
        for (int i = 0; i < 5; i++)
        {
            this.mList_Star.add(this.mainGame.loadTiledTextureRegion("gfx/star/", "Star" + (i + 1) + ".png", 155, 78, 2, 1, this.mBuildableBitmapTextureAtlas));
            this.mList_effectStar.add(this.mainGame.loadTextureRegion("gfx/star/", "Explode" + (i + 1) + ".png", 40, 40, this.mListBitmapTextureAtlas));
        }
        this.iStargetClear = this.mainGame.loadTextureRegion("gfx/gfx/", "stargeclear.png", 500, 300, this.mListBitmapTextureAtlas);
        this.iCool = this.mainGame.loadTextureRegion("gfx/gfx/", "cool.png", 400, 300, this.mListBitmapTextureAtlas);
        this.iGood = this.mainGame.loadTextureRegion("gfx/gfx/", "good.png", 400, 300, this.mListBitmapTextureAtlas);
        this.itarget = this.mainGame.loadTextureRegion("gfx/gfx/", "btn_target.png", 350, 90, this.mListBitmapTextureAtlas);
        this.iScore = this.mainGame.loadTextureRegion("gfx/gfx/", "btn_scorestar.png", 400, 90, this.mListBitmapTextureAtlas);
        this.iStage = this.mainGame.loadTextureRegion("gfx/gfx/", "btn_stage.png", 350, 90, this.mListBitmapTextureAtlas);
        this.iSpause = this.mainGame.loadTextureRegion("gfx/gfx/", "btn_pause.png", 106, 106, this.mListBitmapTextureAtlas);
        this.iStarEffect = this.mainGame.loadTextureRegion("gfx/", "Explode1.png", 40, 40, this.mListBitmapTextureAtlas);
        this.iSaveBtn = this.mainGame.loadTextureRegion("gfx/gfx/", "btn_save.png", 106, 106, this.mListBitmapTextureAtlas);
    }

    public void log_List()
    {
        for (int i = 0;; i++)
        {
            if (i >= this.mList_Anduoc.size()) {
                return;
            }
            Data localData = (Data)((ItemStar)this.mList_Anduoc.get(i)).getUserData();
            System.out.println("[" + localData.getI() + "][" + localData.getJ() + "]");
        }
    }

    public void loopItemCheck()
    {
        for (int i = 0; i < this.mList_Temp.size(); i++)
        {
            if ((this.mList_Temp.get(i) != null) && (!((Data)((ItemStar)this.mList_Temp.get(i)).getUserData()).isCheck())) {
                itemCheck((ItemStar)this.mList_Temp.get(i));
            }
        }
    }

    public synchronized void moveCol()
    {
        int i = 0;
        this.mDataBallTMPCol = null;
        this.starMove.clear();
        this.starMove = new ArrayList();
        int j = 0;

        for (j = 0; j < 10; j++) {
            if (this.sPop[9][j] == null) {
                i++;
            } else {
                for (int k = 9; k > -1; k--) {
                    if ((this.sPop[k][j] != null) && (i != 0))
                    {
                        this.sPop[k][j].setMove(i);
                        Log.d("+++moveCol: this.starMove.size() == 0", "this.starMove.add(): i - " + i + ", k - " + k + ", j - " + j);
                        this.starMove.add(this.sPop[k][j]);
                    }
                }
            }
        }

        if (this.starMove.size() == 0) {
            checkDefine();
            Log.d("+++moveCol: this.starMove.size() == 0", "this.isClickItem = true");
            this.isClickItem = true;
        } else {
            this.mDataBallTMPCol = ((Data)((ItemStar)this.starMove.get(-1 + this.starMove.size())).getUserData());
        }

        for (int m = 0; m < this.starMove.size(); m++) {
            ItemStar localItemStar = (ItemStar)this.starMove.get(m);
            Data localData = (Data)localItemStar.getUserData();
            final int n = localData.getI();
            final int i1 = localData.getJ();
            float f = localItemStar.getX();
            localItemStar.registerEntityModifier(new MoveXModifier(0.2F, f, f - 72 * localItemStar.getMove())
            {
                protected void onModifierFinished(IEntity paramAnonymousIEntity)
                {
                    super.onModifierFinished(paramAnonymousIEntity);
                    if ((PlayScene.this.mDataBallTMPCol != null) && (n == PlayScene.this.mDataBallTMPCol.getI()) && (i1 == PlayScene.this.mDataBallTMPCol.getJ()))
                    {
                        Log.d("+++moveCol() - onModifierFinished", "onModifierFinished called");
                        PlayScene.this.mDataBallTMPCol = null;
                        PlayScene.this.setUserDataForCol(PlayScene.this.starMove);
                        checkDefine();
                    }
                    Log.d("+++moveCol() - onModifierFinished()", "this.isClickItem = true");
                    PlayScene.this.isClickItem = true;
                }
            });
        }
    }

    public synchronized void moveRow()
    {
        this.mDataBallTMPRow = null;
        this.starMove.clear();
        this.starMove = new ArrayList();
        int i = 0;

        for (i = 0; i < 10; i++) {
            int j = 0;
            int k = 9;
            for (k = 9; k > -1; k--) {
                if (this.sPop[k][i] == null) {
                    j++;
                } else {
                    if (j != 0) {
                        this.sPop[k][i].setMove(j);
                        this.starMove.add(this.sPop[k][i]);
                    }
                }
            }
        }
        if (this.starMove.size() > 0) {
            this.mDataBallTMPRow = ((Data)((ItemStar)this.starMove.get(-1 + this.starMove.size())).getUserData());
            for (int m = 0; m < this.starMove.size(); m++) {
                ItemStar localItemStar = (ItemStar)this.starMove.get(m);
                Data localData = (Data)localItemStar.getUserData();
                final int n = localData.getI();
                final int i1 = localData.getJ();
                float f1 = localItemStar.getY();
                float f2 = f1 + 72 * localItemStar.getMove();
                localItemStar.registerEntityModifier(new SequenceEntityModifier(new IEntityModifier[] { new MoveYModifier(0.15F, f1, f1 - 20.0F), new MoveYModifier(0.2F, f1, f2)
                {
                    protected void onModifierFinished(IEntity paramAnonymousIEntity)
                    {
                        super.onModifierFinished(paramAnonymousIEntity);
                        if ((PlayScene.this.mDataBallTMPRow != null) && (n == PlayScene.this.mDataBallTMPRow.getI()) && (i1 == PlayScene.this.mDataBallTMPRow.getJ()))
                        {
                            PlayScene.this.mDataBallTMPRow = null;
                            PlayScene.this.setUserDataForRow(PlayScene.this.starMove);
//                            new Thread(new Runnable()
//                            {
//                                public void run()
//                                {
//                                    PlayScene.this.mainGame.handlerDoWork(new IHandler()
//                                    {
//                                        public void doWork()
//                                        {
//                                            PlayScene.this.setUserDataForRow(PlayScene.this.starMove);
//                                        }
//                                    });
//                                }
//                            }).start();
                        }
                    }
                } }));
            }
        } else {
            moveCol();
        }
    }

    public void moveStar(ItemStar paramItemStar)
    {
        paramItemStar.registerEntityModifier(new MoveYModifier(UtilLib.getRandomIndex(100, 1000) / 1000.0F, paramItemStar.getY(), 1280.0F + paramItemStar.getY(), EaseCircularIn.getInstance())
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                PlayScene.this.mainGame.getManagerSound().putStar();
            }
        });
    }

    public void nextPlay()
    {
        this.BONUS = 2000;
        if (checkIsWin())
        {
            if (this.LEVEL >= 20)
            {
                this.mainGame.showDialogGameOver(this.SCORE);
                return;
            }
            this.LEVEL = (1 + this.LEVEL);
            this.txt_Target.setText(String.valueOf(this.mLevel.getTarget(this.LEVEL)));
            float f1 = 10.0F + (this.sTarget.getWidth() / 2.0F - this.txt_Target.getWidth() / 2.0F);
            float f2 = this.sTarget.getHeight() / 2.0F - this.txt_Target.getHeight() / 2.0F;
            this.txt_Target.setPosition(f1, f2);
            this.txt_Level.setText(String.valueOf(this.LEVEL));
            float f3 = this.sLevel.getWidth() / 2.0F - this.txt_Level.getWidth() / 2.0F;
            float f4 = this.sLevel.getHeight() / 2.0F - this.txt_Level.getHeight() / 2.0F;
            this.txt_Level.setPosition(f3, f4);

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    this.sPop[i][j] = null;
                    this.mainGame.removeEntity(this.sPop[i][j]);
                }
            }
            this.mDialogTarget.show(this.LEVEL);
            return;
        }
        this.mainGame.showDialogGameOver(this.SCORE);
    }

    public AnimatedSprite onClick(AnimatedSprite paramAnimatedSprite)
    {
        return null;
    }

    public Sprite onClick(Sprite paramSprite)
    {
        if (paramSprite == this.sbtnPause)
        {
            this.mainGame.getManagerSound().slectedPlay();
            this.mainGame.getmDialogPause().showDialog();
            this.mainGame.mainScene.setIgnoreUpdate(true);
        }
        if (paramSprite == this.sbtnSave)
        {
            this.mainGame.getManagerSound().slectedPlay();
            hidetargetClear();
            hideAnimation();
            capture();
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

    public void playOrLoadGame()
    {
        if (this.mDataSaveGame == null)
        {
            for (int k = 0; k < 10; k++) {
                for (int m = 0; m < 10; m++) {
                    int n = UtilLib.getRandomIndex(0, this.NUMBERSTAR);
                    if (this.sPop[k][m] != null) {
                        this.mainGame.removeEntity(this.sPop[k][m]);
                    }
                    addStar(k, m, n);
                }
            }
        } else {
            Data[][] arrayOfData = this.mDataSaveGame.getmBall();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (arrayOfData[i][j] != null) {
                        addStar(i, j, arrayOfData[i][j].getColor());
                    }
                }
            }
            this.mDataSaveGame = null;
        }
    }

    public void setLEVEL(int paramInt)
    {
        this.LEVEL = paramInt;
    }

    public void setPositionText(Text paramText, int paramInt)
    {
        if (paramText == this.txt_Score)
        {
            paramText.setText(String.valueOf(paramInt));
            paramText.setPosition(this.sScore.getWidth() / 2.0F - paramText.getWidth() / 2.0F, this.sScore.getHeight() / 2.0F - paramText.getHeight() / 2.0F);
            return;
        }
        if (paramText == this.txt_Level)
        {
            paramText.setText(String.valueOf(paramInt));
            paramText.setPosition(this.sLevel.getWidth() / 2.0F - paramText.getWidth() / 2.0F, this.sLevel.getHeight() / 2.0F - paramText.getHeight() / 2.0F);
            return;
        }
        if (paramText == this.txt_Target) {
            paramText.setText(String.valueOf(paramInt));
            paramText.setPosition(10.0F + (this.sTarget.getWidth() / 2.0F - paramText.getWidth() / 2.0F), this.sTarget.getHeight() / 2.0F - paramText.getHeight() / 2.0F);
        }
    }

    public void setSCORE(int paramInt)
    {
        this.SCORE = paramInt;
    }

    public void setTARGET(int paramInt)
    {
        this.TARGET = paramInt;
    }

    public void setUserDataForCol(ArrayList<ItemStar> paramArrayList)
    {
        for (int i = 0;; i++)
        {
            if (i >= paramArrayList.size())
            {
                checkDefine();
                Log.d("+++setUserDataForCol", "this.isClickItem = true");
                this.isClickItem = true;
                return;
            }
            ItemStar localItemStar = (ItemStar)paramArrayList.get(i);
            Data localData = (Data)localItemStar.getUserData();
            int j = localData.getJ() - localItemStar.getMove();
            int k = localData.getI();
            int m = localData.getJ();
            this.sPop[k][j] = localItemStar;
            this.sPop[k][j].setUserData(localData);
            localData.setJ(j);
            this.sPop[k][m] = null;
        }
    }

    public void setUserDataForRow(ArrayList<ItemStar> paramArrayList)
    {
        for (int i = 0; i < paramArrayList.size(); i++)
        {
            ItemStar localItemStar = (ItemStar)paramArrayList.get(i);
            Data localData = (Data)localItemStar.getUserData();
            int j = localData.getI() + localItemStar.getMove();
            int k = localData.getI();
            int m = localData.getJ();
            this.sPop[j][m] = localItemStar;
            this.sPop[j][m].setUserData(localData);
            localData.setI(j);
            this.sPop[k][m] = null;
        }
        moveCol();
    }

    public void setmDataSaveGame(DataSaveGame paramDataSaveGame)
    {
        this.mDataSaveGame = paramDataSaveGame;
    }

    public void setmLevel(Level paramLevel)
    {
        this.mLevel = paramLevel;
    }

    public void setmRectangle(Rectangle paramRectangle)
    {
        this.mRectangle = paramRectangle;
    }

    public void setsPop(ItemStar[][] paramArrayOfItemStar)
    {
        this.sPop = paramArrayOfItemStar;
    }

    public void show()
    {
        this.sTarget = new Sprite(0.0F, 5.0F, this.itarget, this.mVertexBufferObjectManager);
        this.mRectangle.attachChild(this.sTarget);
        this.sScore = new Sprite(ConfigScreen.SCREENWIDTH / 2 - this.iScore.getWidth() / 2.0F, 20.0F + this.itarget.getHeight(), this.iScore, this.mVertexBufferObjectManager);
        this.mRectangle.attachChild(this.sScore);
        this.sLevel = new Sprite(ConfigScreen.SCREENWIDTH - this.iStage.getWidth() - 2.0F, 5.0F, this.iStage, this.mVertexBufferObjectManager);
        this.mRectangle.attachChild(this.sLevel);
        this.sbtnPause = new BaseSprite(ConfigScreen.SCREENWIDTH - this.iSpause.getWidth() - 2.0F, 10.0F + this.itarget.getHeight(), this.iSpause, this.mVertexBufferObjectManager);
        this.sbtnPause.setmIButtonSprite(this);
        this.mainGame.mainScene.registerTouchArea(this.sbtnPause);
        this.mRectangle.attachChild(this.sbtnPause);
        this.sbtnSave = new BaseSprite(0.0F, 10.0F + this.itarget.getHeight(), this.iSaveBtn, this.mVertexBufferObjectManager);
        this.sbtnSave.setmIButtonSprite(this);
        this.mainGame.mainScene.registerTouchArea(this.sbtnSave);
        this.mRectangle.attachChild(this.sbtnSave);
        this.txt_Target = new Text(-100.0F, -100.0F, this.mainGame.getmFont(), String.valueOf(this.mLevel.getTarget(this.LEVEL)), 20, this.mVertexBufferObjectManager);
        this.txt_Target.setColor(1.0F, 1.0F, 1.0F);
        float f1 = 10.0F + (this.sTarget.getWidth() / 2.0F - this.txt_Target.getWidth() / 2.0F);
        float f2 = this.sTarget.getHeight() / 2.0F - this.txt_Target.getHeight() / 2.0F;
        this.txt_Target.setPosition(f1, f2);
        this.sTarget.attachChild(this.txt_Target);
        this.txt_Level = new Text(-100.0F, -100.0F, this.mainGame.getmFont(), String.valueOf(this.LEVEL), 20, this.mVertexBufferObjectManager);
        this.txt_Level.setColor(1.0F, 1.0F, 1.0F);
        float f3 = this.sLevel.getWidth() / 2.0F - this.txt_Level.getWidth() / 2.0F;
        float f4 = this.sLevel.getHeight() / 2.0F - this.txt_Level.getHeight() / 2.0F;
        this.txt_Level.setPosition(f3, f4);
        this.sLevel.attachChild(this.txt_Level);
        this.txt_Bonus = new Text(ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT / 2, this.mainGame.getmFont(), String.valueOf(this.LEVEL), 20, this.mVertexBufferObjectManager);
        this.txt_Bonus.setColor(1.0F, 1.0F, 1.0F);
        this.mRectangle.attachChild(this.txt_Bonus);
        this.txt_TotalLastList = new Text(ConfigScreen.SCREENWIDTH, 10.0F + (ConfigScreen.SCREENHEIGHT / 2 + this.txt_Bonus.getHeight()), this.mainGame.getmFont(), "", 20, this.mVertexBufferObjectManager);
        this.txt_TotalLastList.setColor(1.0F, 1.0F, 1.0F);
        this.mRectangle.attachChild(this.txt_TotalLastList);
        this.txt_Score = new Text(-100.0F, -100.0F, this.mainGame.getmFont(), "0", 20, this.mVertexBufferObjectManager);
        this.txt_Score.setText(String.valueOf(this.SCORE));
        this.txt_Score.setColor(1.0F, 1.0F, 1.0F);
        float f5 = this.sScore.getWidth() / 2.0F - this.txt_Score.getWidth() / 2.0F;
        float f6 = this.sScore.getHeight() / 2.0F - this.txt_Score.getHeight() / 2.0F;
        this.txt_Score.setPosition(f5, f6);
        this.sScore.attachChild(this.txt_Score);
        this.txt_Point = new Text(-100.0F, -100.0F, this.mainGame.getmFont1(), "", 100, this.mVertexBufferObjectManager);
        this.txt_Point.setColor(1.0F, 1.0F, 1.0F);
        this.mRectangle.attachChild(this.txt_Point);
        this.txt_addBonus = new Text(-100.0F, -100.0F, this.mainGame.getmFont1(), "", 100, this.mVertexBufferObjectManager);
        this.txt_addBonus.setColor(1.0F, 1.0F, 1.0F);
        this.mRectangle.attachChild(this.txt_addBonus);
        this.txt_addBonus.setZIndex(101);
        this.txt_addBonus.sortChildren();
        float f7 = -100 + ConfigScreen.SCREENWIDTH / 2;
        this.txtLevel = new Text(f7, 300.0F, this.mainGame.getmFont(), "", 100, this.mainGame.getVertexBufferObjectManager());
        this.txtTarget = new Text(f7, 400.0F, this.mainGame.getmFont(), "", 100, this.mainGame.getVertexBufferObjectManager());
        this.sTargetClear = new BaseSprite(ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT, this.iStargetClear, this.mVertexBufferObjectManager);
        this.mainGame.mHud.attachChild(this.sTargetClear);
        this.sCool = new BaseSprite(ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT, this.iCool, this.mVertexBufferObjectManager);
        this.mRectangle.attachChild(this.sCool);
        this.sGood = new BaseSprite(ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT, this.iGood, this.mVertexBufferObjectManager);
        this.mRectangle.attachChild(this.sGood);
        this.mRectangleMatran = new Rectangle(0.0F, -720.0F, ConfigScreen.SCREENWIDTH, 720 + ConfigScreen.SCREENHEIGHT, this.mVertexBufferObjectManager);
        this.mRectangle.attachChild(this.mRectangleMatran);
        this.mRectangleMatran.setColor(Color.TRANSPARENT);
        this.mRectangleMatran.setZIndex(1);
        this.mRectangleMatran.sortChildren();
        this.mylog = new Mylog();
        checkLoadorNew();
    }

    public void showAnimation()
    {
        MoveXModifier localMoveXModifier = new MoveXModifier(1.0F, this.mRectangle.getX(), 0.0F, EaseBackOut.getInstance());
        this.mRectangle.registerEntityModifier(localMoveXModifier);
    }

    public void showBonus(final ArrayList<ItemStar> paramArrayList)
    {
        this.txt_Bonus.setText("BONUS 2000");
        float f = this.mRectangle.getWidth() / 2.0F - this.txt_Bonus.getWidth() / 2.0F;
        MoveXModifier local13 = new MoveXModifier(1.0F, this.txt_Bonus.getX(), f, EaseElasticOut.getInstance())
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                PlayScene.this.delLastItem(paramArrayList);
            }
        };
        this.txt_Bonus.registerEntityModifier(local13);
    }

    public void unLoadResource()
    {
        Iterator localIterator1 = this.mListBitmapTextureAtlas.iterator();
        Iterator localIterator2 = this.mBuildableBitmapTextureAtlas.iterator();

        while (localIterator1.hasNext()) {
            ((BitmapTextureAtlas)localIterator1.next()).unload();
        }
        this.mListBitmapTextureAtlas.clear();

        while (localIterator2.hasNext()) {
            ((BuildableBitmapTextureAtlas)localIterator2.next()).unload();
        }
        this.mBuildableBitmapTextureAtlas.clear();

        this.mRectangle.setPosition(ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT);
    }

    public void viewPoint(ArrayList<ItemStar> paramArrayList)
    {
        this.DIEMITEM = (5 * paramArrayList.size());
        float f = -150 + ConfigScreen.SCREENWIDTH / 2;
        this.txt_Point.setPosition(f, 200.0F);
        this.txt_Point.setText(this.arrEated.size() + " Star " + this.DIEMITEM * this.arrEated.size() + " Points");
        this.txt_Point.setColor(1.0F, 1.0F, 1.0F);
        ScaleModifier localScaleModifier = new ScaleModifier(0.5F, 0.0F, 1.0F);
        this.txt_Point.registerEntityModifier(localScaleModifier);
    }
}
