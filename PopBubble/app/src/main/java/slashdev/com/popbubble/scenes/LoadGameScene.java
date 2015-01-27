package slashdev.com.popbubble.scenes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;

import com.slashbase.myconfig.ConfigScreen;
import com.slashbase.myinterface.IClose;
import com.slashbase.myinterface.IDoBackGround;
import com.slashbase.myinterface.IHandler;

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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.FileUtils;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseBackIn;
import org.andengine.util.modifier.ease.EaseBackOut;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import slashdev.com.popbubble.MainGame;
import slashdev.com.popbubble.base.BaseSprite;
import slashdev.com.popbubble.database.DataSaveGame;
import slashdev.com.popbubble.database.SaveGame;
import slashdev.com.popbubble.myinterface.IButtonAnimatedSprite;
import slashdev.com.popbubble.myinterface.IButtonSprite;
import slashdev.com.popbubble.myinterface.ILoadUnLoadResource;
import slashdev.com.popbubble.myinterface.ObjectPopstar;
import slashdev.com.popbubble.object.GlobalConstants;

public class LoadGameScene
        extends ObjectPopstar
        implements IDoBackGround, ILoadUnLoadResource, IButtonSprite, IButtonAnimatedSprite
{
    BaseSprite Sbg;
    BaseSprite[] btnDeleteItem = new BaseSprite[4];
    BaseSprite[] btnImageSave = new BaseSprite[4];
    float distanceRow = 70.0F;
    ITextureRegion ibgmain;
    boolean isLoadCompleted = false;
    boolean isSave = false;
    boolean isShowCompleted = false;
    ITextureRegion mB1;
    ITextureRegion mB2;
    BaseSprite mBg1;
    BaseSprite mBg2;
    ITextureRegion mBgSquareItem;
    BaseSprite mClose = null;
    ITextureRegion mCloseTTR;
    Font mFont;
    IClose mIClose = null;
    ITextureRegion mIconDeleteTTR;
    ITextureRegion[] mImageSave = new ITextureRegion[4];
    ArrayList<BitmapTextureAtlas> mListBitmapTextureAtlas;
    MainGame mMainGame;
    Rectangle mRectangle;
    ITextureRegion mTitleTTR;
    ITextureRegion mTxtSaveTTR;
    VertexBufferObjectManager mVertexBufferObjectManager;

    public LoadGameScene(MainGame paramMainGame)
    {
        super(paramMainGame);
        this.mMainGame = paramMainGame;
        this.mVertexBufferObjectManager = this.mMainGame.getVertexBufferObjectManager();
        this.mListBitmapTextureAtlas = new ArrayList();
    }

    public void addBgItem()
    {
        float f1 = this.mRectangle.getWidth() / 2.0F - 2.0F * this.mBgSquareItem.getWidth() / 2.0F;
        float f2 = this.mRectangle.getHeight() / 2.0F - this.mBgSquareItem.getHeight() / 2.0F;
        this.mBg1 = new BaseSprite(f1, f2, this.mBgSquareItem, this.mVertexBufferObjectManager);
        this.mRectangle.attachChild(this.mBg1);
        this.mBg1.setmIButtonSprite(this);
        this.mMainGame.mainScene.registerTouchArea(this.mBg1);
        Text localText1 = new Text(0.0F, 0.0F, this.mFont, "Empty", this.mVertexBufferObjectManager);
        localText1.setPosition(this.mBg1.getWidth() / 2.0F - localText1.getWidth() / 2.0F, this.mBg1.getHeight() / 2.0F - localText1.getHeight() / 2.0F);
        this.mBg1.attachChild(localText1);
        if (this.mImageSave[0] != null)
        {
            this.btnImageSave[0] = new BaseSprite(0.0F, 0.0F, this.mBg1.getWidth(), this.mBg1.getHeight(), this.mImageSave[0], this.mVertexBufferObjectManager);
            this.btnImageSave[0].setScale(0.83F);
            this.mBg1.attachChild(this.btnImageSave[0]);
            this.btnDeleteItem[0] = new BaseSprite(this.mBg1.getWidth() - this.mIconDeleteTTR.getWidth(), 0.0F, this.mIconDeleteTTR, this.mVertexBufferObjectManager);
            this.btnDeleteItem[0].setmIButtonSprite(this);
            this.mMainGame.mainScene.registerTouchArea(this.btnDeleteItem[0]);
            this.btnImageSave[0].attachChild(this.btnDeleteItem[0]);
        }
        Sprite localSprite1 = new Sprite(0.0F, 0.0F, this.mB1, this.mVertexBufferObjectManager);
        localSprite1.setZIndex(100);
        this.mBg1.attachChild(localSprite1);
        this.mBg2 = new BaseSprite(this.mBg1.getX() + this.mBg1.getWidth(), f2, this.mBgSquareItem, this.mVertexBufferObjectManager);
        this.mRectangle.attachChild(this.mBg2);
        this.mBg2.setmIButtonSprite(this);
        this.mMainGame.mainScene.registerTouchArea(this.mBg2);
        Text localText2 = new Text(0.0F, 0.0F, this.mFont, "Empty", this.mVertexBufferObjectManager);
        localText2.setPosition(this.mBg2.getWidth() / 2.0F - localText2.getWidth() / 2.0F, this.mBg2.getHeight() / 2.0F - localText2.getHeight() / 2.0F);
        this.mBg2.attachChild(localText2);
        if (this.mImageSave[1] != null)
        {
            this.btnImageSave[1] = new BaseSprite(0.0F, 0.0F, this.mBg1.getWidth(), this.mBg1.getHeight(), this.mImageSave[1], this.mVertexBufferObjectManager);
            this.btnImageSave[1].setScale(0.83F);
            this.mBg2.attachChild(this.btnImageSave[1]);
            this.btnDeleteItem[1] = new BaseSprite(this.mBg1.getWidth() - this.mIconDeleteTTR.getWidth(), 0.0F, this.mIconDeleteTTR, this.mVertexBufferObjectManager);
            this.btnDeleteItem[1].setmIButtonSprite(this);
            this.mMainGame.mainScene.registerTouchArea(this.btnDeleteItem[1]);
            this.btnImageSave[1].attachChild(this.btnDeleteItem[1]);
        }
        Sprite localSprite2 = new Sprite(0.0F, 0.0F, this.mB2, this.mVertexBufferObjectManager);
        localSprite2.setZIndex(100);
        this.mBg2.attachChild(localSprite2);
        Sprite localSprite3 = new Sprite(this.mBg1.getX(), this.mBg1.getY() - this.mTxtSaveTTR.getHeight() - 10.0F, this.mTxtSaveTTR, this.mVertexBufferObjectManager);
        this.mRectangle.attachChild(localSprite3);
    }

    public void animationBtnPlay(Sprite paramSprite)
    {
        MoveYModifier localMoveYModifier1 = new MoveYModifier(1.5F, paramSprite.getY(), paramSprite.getY() - 80.0F);
        MoveYModifier localMoveYModifier2 = new MoveYModifier(1.5F, paramSprite.getY() - 80.0F, paramSprite.getY());
        ScaleModifier localScaleModifier1 = new ScaleModifier(1.5F, 0.9F, 1.0F, 1.0F, 0.95F);
        ScaleModifier localScaleModifier2 = new ScaleModifier(1.5F, 1.0F, 0.9F, 0.95F, 1.0F);
        paramSprite.registerEntityModifier(new LoopEntityModifier(new ParallelEntityModifier(new IEntityModifier[] { new SequenceEntityModifier(new IEntityModifier[] { localMoveYModifier1, localMoveYModifier2 }), new SequenceEntityModifier(new IEntityModifier[] { localScaleModifier1, localScaleModifier2 }) })));
    }

    public void deleteFile(int paramInt)
    {
        new File(FileUtils.getAbsolutePathOnExternalStorage(this.mMainGame, GlobalConstants.nameImage[paramInt])).exists();
    }

    public void deleteImageSelect(int paramInt)
    {
        if (this.btnImageSave[paramInt] != null)
        {
            this.btnImageSave[paramInt] = ((BaseSprite)this.mMainGame.removeEntity(this.btnImageSave[paramInt]));
            this.btnImageSave[paramInt] = null;
            deleteFile(paramInt);
            this.btnDeleteItem[paramInt] = null;
        }
        new SaveGame(this.mMainGame).resetData(paramInt);
    }

    public IClose getmClose()
    {
        return this.mIClose;
    }

    public void hideAnimation()
    {
        this.isShowCompleted = false;
        MoveModifier local2 = new MoveModifier(1.0F, 0.0F, 0.0F, 0.0F, -this.mRectangle.getHeight(), EaseBackIn.getInstance())
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                if (LoadGameScene.this.mIClose != null) {
                    LoadGameScene.this.mIClose.onClose();
                }
                LoadGameScene.this.unLoadResource();
                LoadGameScene.this.isSave = false;
            }
        };
        this.mRectangle.registerEntityModifier(local2);
    }

    public boolean isSave()
    {
        return this.isSave;
    }

    public void loadResource()
    {
        if (!this.isLoadCompleted)
        {
            this.mRectangle = new Rectangle(0.0F, 0.0F, ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT, this.mVertexBufferObjectManager);
            this.mRectangle.setColor(Color.TRANSPARENT);
            this.ibgmain = this.mainGame.loadTextureRegion("gfx/gfx/", "bg.png", 720, 1280, this.mListBitmapTextureAtlas);
            this.mCloseTTR = this.mMainGame.loadTextureRegion("button/", "btn_close.png", 81, 81, this.mListBitmapTextureAtlas);
            this.mBgSquareItem = this.mMainGame.loadTextureRegion("bg/", "bg_squre_item.png", 350, 600, this.mListBitmapTextureAtlas);
            this.mIconDeleteTTR = this.mMainGame.loadTextureRegion("icon/", "icon_delete.png", 50, 50, this.mListBitmapTextureAtlas);
            this.mB1 = this.mMainGame.loadTextureRegion("ball_loadgame/", "ball1.png", 66, 66, this.mListBitmapTextureAtlas);
            this.mB2 = this.mMainGame.loadTextureRegion("ball_loadgame/", "ball2.png", 66, 66, this.mListBitmapTextureAtlas);
            if (this.isSave) {
                this.mTitleTTR = this.mMainGame.loadTextureRegion("button/", "btn_savegame.png", 501, 122, this.mListBitmapTextureAtlas);
                this.mTxtSaveTTR = this.mMainGame.loadTextureRegion("button/", "txt_save.png", 549, 37, this.mListBitmapTextureAtlas);
            } else {
                this.mTitleTTR = this.mMainGame.loadTextureRegion("button/", "btn_loadgame.png", 501, 122, this.mListBitmapTextureAtlas);
                this.mTxtSaveTTR = this.mMainGame.loadTextureRegion("button/", "txt_load.png", 549, 37, this.mListBitmapTextureAtlas);
            }
        }

        for (int i = 0; i < this.mImageSave.length; i++) {
            String str = FileUtils.getAbsolutePathOnExternalStorage(this.mMainGame, GlobalConstants.nameImage[i]);
            this.mImageSave[i] = this.mMainGame.loadTextureRegion(str, ConfigScreen.SCREENWIDTH_REAL, ConfigScreen.SCREENHEIGHT_REAL, this.mListBitmapTextureAtlas);
        }

        this.mFont = FontFactory.create(this.mMainGame.getFontManager(), this.mMainGame.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, 1), 40.0F, true, -1);
        this.mFont.load();
    }

    public void nextPlay(BaseSprite paramBaseSprite, final int paramInt)
    {
        if (this.btnImageSave[paramInt] != null)
        {
            // TODO: check this logic
            final DataSaveGame dataSaveGame = new SaveGame(LoadGameScene.this.mMainGame).read(paramInt);

            LoadGameScene.this.mMainGame.showPlayScene(new IClose() {
                public void onClose() {
                    LoadGameScene.this.mMainGame.getmPlayScene().setmDataSaveGame(dataSaveGame);
                    hideAnimation();
                }
            });
        } else {
            LoadGameScene.this.mMainGame.showToastMessage("Empty");
        }
    }

    public AnimatedSprite onClick(AnimatedSprite paramAnimatedSprite)
    {
        return null;
    }

    public Sprite onClick(Sprite paramSprite)
    {
        if (paramSprite == this.mClose)
        {
            if (this.isSave) {
//                setmClose(new IClose() {
//                    @Override
//                    public void onClose() {
//                        LoadGameScene.this.mMainGame.showPlayScene(null);
//                    }
//                });
            } else {
                setmClose(new IClose() {
                    @Override
                    public void onClose() {
                        LoadGameScene.this.mMainGame.showMenu();
                    }
                });
            }
            hideAnimation();
            this.mainGame.getManagerSound().slectedPlay();
        }
        if (paramSprite == this.mBg1)
        {
            if (this.isSave) {
                showBgSlected(this.mBg1, 0);
                this.mainGame.getManagerSound().slectedPlay();
            } else {
                nextPlay(this.mBg1, 0);
                this.mainGame.getManagerSound().slectedPlay();
            }
        }
        if (paramSprite == this.mBg2)
        {
            if (this.isSave) {
                showBgSlected(this.mBg2, 1);
                this.mainGame.getManagerSound().slectedPlay();
            } else {
                nextPlay(this.mBg2, 1);
                this.mainGame.getManagerSound().slectedPlay();
            }
        }
        if (paramSprite == this.btnDeleteItem[0]) {
            showDialogConfimDeleteItem(0);
            this.mainGame.getManagerSound().slectedPlay();
        }
        if (paramSprite == this.btnDeleteItem[1])
        {
            showDialogConfimDeleteItem(1);
            this.mainGame.getManagerSound().slectedPlay();
        }
        if (paramSprite == this.btnDeleteItem[2])
        {
            showDialogConfimDeleteItem(2);
            this.mainGame.getManagerSound().slectedPlay();
        }

        return null;
    }

    public void onCompleted()
    {
        if (!this.isLoadCompleted)
        {
            BaseSprite localBaseSprite = new BaseSprite(0.0F, 10.0F, this.mTitleTTR, this.mVertexBufferObjectManager);
            this.mRectangle.attachChild(localBaseSprite);
            this.mClose = new BaseSprite(this.mRectangle.getWidth() - this.mCloseTTR.getWidth() - 10.0F, 10.0F, this.mCloseTTR, this.mVertexBufferObjectManager);
            this.mClose.setmIButtonSprite(this);
            this.mRectangle.attachChild(this.mClose);
            this.mMainGame.mainScene.registerTouchArea(this.mClose);
            addBgItem();
            this.mMainGame.mainScene.attachChild(this.mRectangle);
            this.isLoadCompleted = true;
        }
    }

    public void onDoBackGround(boolean paramBoolean)
    {
        loadResource();
    }

    public AnimatedSprite onDown(AnimatedSprite paramAnimatedSprite)
    {
        this.mMainGame.getManagerSound().slectedPlay();
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

    public void reLoad(int paramInt)
    {
        String str = FileUtils.getAbsolutePathOnExternalStorage(this.mMainGame, GlobalConstants.nameImage[paramInt]);
        this.mImageSave[paramInt] = this.mMainGame.loadTextureRegion(str, ConfigScreen.SCREENWIDTH_REAL, ConfigScreen.SCREENHEIGHT_REAL, this.mListBitmapTextureAtlas);
        this.btnImageSave[paramInt] = new BaseSprite(0.0F, 0.0F, this.mBg1.getWidth(), this.mBg1.getHeight(), this.mImageSave[paramInt], this.mVertexBufferObjectManager);
        this.btnImageSave[paramInt].setScale(0.83F);
        this.btnImageSave[paramInt].setZIndex(1);
        if (paramInt == 0)
        {
            this.mBg1.attachChild(this.btnImageSave[paramInt]);
            this.mBg1.sortChildren();
        }
        if (paramInt == 1)
        {
            this.mBg2.attachChild(this.btnImageSave[paramInt]);
            this.mBg2.sortChildren();
        }
        this.btnDeleteItem[paramInt] = new BaseSprite(this.mBg1.getWidth() - this.mIconDeleteTTR.getWidth(), 0.0F, this.mIconDeleteTTR, this.mVertexBufferObjectManager);
        this.btnDeleteItem[paramInt].setmIButtonSprite(this);
        this.mMainGame.mainScene.registerTouchArea(this.btnDeleteItem[paramInt]);
        this.btnImageSave[paramInt].attachChild(this.btnDeleteItem[paramInt]);
    }

    public void saveFile(int paramInt)
    {
        File localFile1 = new File(FileUtils.getAbsolutePathOnExternalStorage(this.mMainGame, "tmp.png"));
        File localFile2 = null;

        try
        {
            if (localFile1.exists()) {
                localFile2 = new File(FileUtils.getAbsolutePathOnExternalStorage(this.mMainGame, GlobalConstants.nameImage[paramInt]));
                FileUtils.copyFile(localFile1, localFile2);
                this.mMainGame.saveData(paramInt);
            }
        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
        }
    }

    public void setSave(boolean paramBoolean)
    {
        this.isSave = paramBoolean;
    }

    public void setmClose(IClose paramIClose)
    {
        this.mIClose = paramIClose;
    }

    public void showAnimation()
    {
        this.isShowCompleted = false;
        MoveModifier local1 = new MoveModifier(1.0F, 0.0F, 0.0F, -this.mRectangle.getHeight(), 0.0F, EaseBackOut.getInstance())
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                LoadGameScene.this.isShowCompleted = true;
            }
        };
        this.mRectangle.registerEntityModifier(local1);
    }

    public void showBgSlected(BaseSprite paramBaseSprite, int paramInt)
    {
        if (this.btnImageSave[paramInt] == null)
        {
            showDialogConfimSave(paramInt);
            return;
        }
        showDialogConfimDelete(paramInt);
    }

    void showDialogConfimDelete(final int paramInt)
    {
        IHandler local5 = new IHandler()
        {
            IDoBackGround iDoBackGround = new IDoBackGround() {
                @Override
                public void onCompleted() {
                    LoadGameScene.this.mMainGame.showToastMessage("Save success");
                    LoadGameScene.this.mMainGame.dismissLoading();
                }

                @Override
                public void onDoBackGround(boolean paramAnonymousBoolean)
                {
                    LoadGameScene.this.deleteImageSelect(paramInt);
                    LoadGameScene.this.saveFile(paramInt);
                    LoadGameScene.this.reLoad(paramInt);
                }
            };


            @Override
            public void doWork() {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(LoadGameScene.this.mMainGame);
                localBuilder.setTitle("Delete and Override");
                localBuilder.setMessage("Do you want to override this Game?");
                localBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                    {
                        LoadGameScene.this.mMainGame.showLoading();
                        LoadGameScene.this.mMainGame.doBackGround(iDoBackGround);
                    }
                });
                localBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {}
                });
                localBuilder.create().show();
            }
        };
        this.mMainGame.handlerDoWork(local5);
    }

    void showDialogConfimDeleteItem(final int paramInt)
    {
        IHandler local6 = new IHandler()
        {
            public void doWork()
            {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(LoadGameScene.this.mMainGame);
                localBuilder.setTitle("Delete save game");
                localBuilder.setMessage("Do you want to delete this saved Game?");
                localBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    int indexDelete = paramInt;
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                    {
                        LoadGameScene.this.deleteImageSelect(this.indexDelete);
                        LoadGameScene.this.mMainGame.showToastMessage("Delete success");
                    }
                });
                localBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {}
                });
                localBuilder.create().show();
            }
        };
        this.mMainGame.handlerDoWork(local6);
    }

    void showDialogConfimSave(final int paramInt)
    {
        IHandler local3 = new IHandler()
        {
            public void doWork()
            {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(LoadGameScene.this.mMainGame);
                localBuilder.setTitle("Save game");
                localBuilder.setMessage("Do you want to save this Game?");
                localBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    int indexDelete = paramInt;
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                    {
                        LoadGameScene.this.saveFile(indexDelete);
                        LoadGameScene.this.mMainGame.saveData(indexDelete);
                        LoadGameScene.this.reLoad(indexDelete);
                    }
                });
                localBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {}
                });
                localBuilder.create().show();
            }
        };
        this.mMainGame.handlerDoWork(local3);
    }

    public void unLoadResource()
    {
        for (int i = 0;; i++)
        {
            if (i >= this.mListBitmapTextureAtlas.size())
            {
                this.mListBitmapTextureAtlas.clear();
                this.mMainGame.removeEntity(this.mRectangle);
                this.isLoadCompleted = false;
                return;
            }
            ((BitmapTextureAtlas)this.mListBitmapTextureAtlas.get(i)).unload();
        }
    }
}
