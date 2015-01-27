package slashdev.com.popbubble;

import android.app.ProgressDialog;

import com.slashbase.base.BaseGame;
import com.slashbase.myconfig.ConfigScreen;
import com.slashbase.myinterface.IClose;
import com.slashbase.myinterface.IDoBackGround;
import com.slashbase.myinterface.IHandler;
import com.slashbase.mylog.Log;

import org.andengine.engine.Engine;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.ScreenCapture;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.slashbase.util.UtilLib;
import slashdev.com.popbubble.database.SaveGame;
import slashdev.com.popbubble.myinterface.ILoading;
import slashdev.com.popbubble.scenes.LoadGameScene;
import slashdev.com.popbubble.scenes.MenuScene;
import slashdev.com.popbubble.scenes.PlayScene;
import slashdev.com.popbubble.scenes.RankScene;
import slashdev.com.popbubble.sound.ManagerSound;

public class MainGame
        extends BaseGame
{
    int FONT_SIZE = 50;
    ITextureRegion ibgGame;
    public IClose mBtnCloseHighScoreClick = new IClose()
    {
        public void onClose()
        {
            MainGame.this.showMenu();
        }
    };
    public IClose mBtnCloseLoadGameClick = new IClose()
    {
        public void onClose()
        {
            MainGame.this.showPlayScene(null);
        }
    };
    public IClose mBtnCloseLoadGameClickSave = new IClose()
    {
        public void onClose()
        {
            MainGame.this.mPlayScene.showAnimation();
        }
    };
    public IClose mBtnHighSocreClick = new IClose()
    {
        public void onClose()
        {
            MainGame.this.showHighScore();
        }
    };
    public IClose mBtnLoadGameClick = new IClose()
    {
        public void onClose()
        {
            MainGame.this.showLoadGame();
        }
    };
    public IClose mBtnPlayClick = new IClose()
    {
        public void onClose()
        {
            MainGame.this.showPlayScene(null);
        }
    };
    DialogExitGame mDialogExitGame;
    DialogGameOver mDialogGameOver;
    DialogPause mDialogPause;
    public Font mFont;
    public Font mFont1;
    ArrayList<BitmapTextureAtlas> mListBitmapTextureAtlas = new ArrayList();

    public LoadGameScene getmLoadGameScene() {
        return mLoadGameScene;
    }

    public void setmLoadGameScene(LoadGameScene mLoadGameScene) {
        this.mLoadGameScene = mLoadGameScene;
    }

    public DialogPause getmDialogPause() {
        return mDialogPause;
    }

    public void setmDialogPause(DialogPause mDialogPause) {
        this.mDialogPause = mDialogPause;
    }

    LoadGameScene mLoadGameScene;
    Loading mLoading;
    MenuScene mMenuScene;
    PlayScene mPlayScene;
    ProgressDialog mProgressDialog;
//    RankScene mRankScene;
    ScreenCapture mScreenCapture;
    VertexBufferObjectManager mVertexBufferObjectManager = new VertexBufferObjectManager();
    ManagerSound managerSound;
    Sprite sBggame;

    public void capture(Rectangle paramRectangle, String paramString, final IClose paramIClose1, final IClose paramIClose2)
    {
        showLoading();
        paramRectangle.attachChild(this.mScreenCapture);
        File localFile1 = new File(FileUtils.getAbsolutePathOnExternalStorage(this, ""));
        if (!localFile1.isDirectory()) {
            localFile1.mkdirs();
        }
        String str = FileUtils.getAbsolutePathOnExternalStorage(this, paramString);
        File localFile2 = new File(str);

        try
        {
            if (!localFile2.exists()) {
                localFile2.createNewFile();
            }
            this.mScreenCapture.capture(0, 0, ConfigScreen.SCREENWIDTH_REAL, ConfigScreen.SCREENHEIGHT_REAL, str, new ScreenCapture.IScreenCaptureCallback()
            {
                public void onScreenCaptureFailed(String paramAnonymousString, Exception paramAnonymousException)
                {
                    if (paramIClose2 != null) {
                        paramIClose2.onClose();
                    }
                    MainGame.this.mScreenCapture.detachSelf();
                    MainGame.this.dismissLoading();
                }

                public void onScreenCaptured(String paramAnonymousString)
                {
                    if (paramIClose1 != null) {
                        paramIClose1.onClose();
                    }
                    MainGame.this.mScreenCapture.detachSelf();
                    MainGame.this.dismissLoading();
                }
            });
            return;
        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
        }
    }

    public void createResources()
    {
//        this.mRankScene = new RankScene(this);
        this.ibgGame = loadTextureRegion("gfx/gfx/", "background.png", 720, 1280, this.mListBitmapTextureAtlas);
    }

    public void createScene()
    {
        this.managerSound = new ManagerSound();
        this.managerSound.load(this.mEngine, this);
        this.mDialogPause = new DialogPause(this);
        this.mMenuScene = new MenuScene(this);
        this.mDialogGameOver = new DialogGameOver(this);
        this.mDialogExitGame = new DialogExitGame(this);
        this.mScreenCapture = new ScreenCapture();
        this.mLoadGameScene = new LoadGameScene(this);
        this.mLoading = new Loading(this);
        this.sBggame = new Sprite(0.0F, 0.0F, this.ibgGame, this.mVertexBufferObjectManager);
        this.mainScene.attachChild(this.sBggame);
        showMenu();
        this.mainScene.setTouchAreaBindingOnActionDownEnabled(true);
        this.mainScene.setOnAreaTouchTraversalFrontToBack();
        FontFactory.setAssetBasePath("font/");
        BitmapTextureAtlas localBitmapTextureAtlas1 = new BitmapTextureAtlas(getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        this.mFont = FontFactory.createFromAsset(getFontManager(), localBitmapTextureAtlas1, getAssets(), "canum.ttf", this.FONT_SIZE, true, -1);
        this.mFont.load();
        BitmapTextureAtlas localBitmapTextureAtlas2 = new BitmapTextureAtlas(getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        this.mFont1 = FontFactory.createFromAsset(getFontManager(), localBitmapTextureAtlas2, getAssets(), "canum.ttf", 30.0F, true, -1);
        this.mFont1.load();
    }

    public void dismissLoading()
    {
        handlerDoWork(new IHandler()
        {
            public void doWork()
            {
                if (MainGame.this.mProgressDialog != null)
                {
                    MainGame.this.mProgressDialog.dismiss();
                    MainGame.this.mProgressDialog = null;
                }
            }
        });
    }

    public Engine getEngie()
    {
        return this.mEngine;
    }

    public ManagerSound getManagerSound()
    {
        return this.managerSound;
    }

    public Font getmFont()
    {
        return this.mFont;
    }

    public Font getmFont1()
    {
        return this.mFont1;
    }

    public Loading getmLoading()
    {
        return this.mLoading;
    }

    public MenuScene getmMenuScene()
    {
        return this.mMenuScene;
    }

    public PlayScene getmPlayScene()
    {
        return this.mPlayScene;
    }

    public void iniConfigScreen()
    {
        super.iniConfigScreen();
        ConfigScreen.mScreenOrientation = ScreenOrientation.PORTRAIT_FIXED;
        ConfigScreen.SCREENWIDTH = 720;
        ConfigScreen.SCREENHEIGHT = 1280;
        ConfigScreen.mRatioResolutionPolicy = null;
        Log.setDebug(true);
    }

    public void onBackPressed()
    {
        // TODO: check this logic
//        showOfferwallNotFinish(2);
        if ((!this.mDialogExitGame.isHideCompleted()) || (this.mDialogExitGame.isShowCompleted()) || (!this.mDialogPause.isHideCompleted()) || (this.mDialogPause.isShowCompleted()) || (!this.mDialogGameOver.isHideCompleted()) || (this.mDialogGameOver.isShowCompleted())) {
            return;
        }
        if ((this.mDialogExitGame.isShowCompleted()) && (!this.mDialogExitGame.isHideCompleted())) {
            this.mDialogExitGame.closeDialog();
            return;
        }

        if (this.mMenuScene.isShowCompleted()) {
            this.mainScene.setIgnoreUpdate(true);
            this.mDialogExitGame.showDialog();
        } else {
            this.mainScene.setIgnoreUpdate(true);
            this.mDialogExitGame.showDialog();
        }

//        do
//        {
//            return;
//
//        } while ();

    }

    public void saveData(int paramInt)
    {
        new SaveGame(this).save(paramInt, this.mPlayScene);
    }

    public void setEffectStar(Sprite paramSprite)
    {
        paramSprite.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new IEntityModifier[] { new ScaleModifier(1.0F, 1.0F, 0.9F, 1.0F, 1.1F), new ScaleModifier(1.0F, 0.9F, 1.0F, 1.1F, 1.0F) })));
    }

    public void setManagerSound(ManagerSound paramManagerSound)
    {
        this.managerSound = paramManagerSound;
    }

    public void setmFont(Font paramFont)
    {
        this.mFont = paramFont;
    }

    public void setmFont1(Font paramFont)
    {
        this.mFont1 = paramFont;
    }

    public void setmLoading(Loading paramLoading)
    {
        this.mLoading = paramLoading;
    }

    public void setmMenuScene(MenuScene paramMenuScene)
    {
        this.mMenuScene = paramMenuScene;
    }

    public void setmPlayScene(PlayScene paramPlayScene)
    {
        this.mPlayScene = paramPlayScene;
    }

    public void showDialogGameOver(int paramInt)
    {
        if ((this.mDialogGameOver.isHideCompleted()) && (!this.mDialogGameOver.isShowCompleted()))
        {
            this.mainScene.setIgnoreUpdate(true);
            this.mDialogGameOver.setData(paramInt);
            this.mDialogGameOver.showDialog();
        }
    }

    public void showHighScore()
    {
        

//        ILoading local9 = new ILoading()
//        {
//            public void closeLoadingCompleted()
//            {
////                MainGame.this.showOfferwallNotFinish(2);
//            }
//
//            public void showLoadingCompleted()
//            {
//                IDoBackGround local1 = new IDoBackGround()
//                {
//                    public void onCompleted()
//                    {
//                        MainGame.this.mRankScene.onCompleted();
//                        MainGame.this.mLoading.hideLoading(true);
//                    }
//
//                    public void onDoBackGround(boolean paramAnonymous2Boolean)
//                    {
//                        MainGame.this.mRankScene = null;
//                        MainGame.this.mRankScene = new RankScene(MainGame.this);
//                        MainGame.this.mRankScene.onDoBackGround(paramAnonymous2Boolean);
//                    }
//                };
//                MainGame.this.doBackGround(local1);
//            }
//        };
//        this.mLoading.setmILoading(local9);
//        this.mLoading.showLoading(true);
    }

    public void showLoadGame()
    {
        ILoading local10 = new ILoading()
        {
            public void closeLoadingCompleted()
            {
//                MainGame.this.showOfferwallNotFinish(2);
            }

            public void showLoadingCompleted()
            {
                IDoBackGround local1 = new IDoBackGround()
                {
                    public void onCompleted()
                    {
                        MainGame.this.mLoadGameScene.onCompleted();
                        MainGame.this.mLoading.hideLoading(true);
                    }

                    public void onDoBackGround(boolean paramAnonymous2Boolean)
                    {
                        MainGame.this.mLoadGameScene.onDoBackGround(paramAnonymous2Boolean);
                    }
                };
                MainGame.this.doBackGround(local1);
            }
        };
        this.mLoading.setmILoading(local10);
        this.mLoading.showLoading(true);
    }

    public void showLoading()
    {
        handlerDoWork(new IHandler()
        {
            public void doWork()
            {
                if (MainGame.this.mProgressDialog != null)
                {
                    MainGame.this.mProgressDialog.dismiss();
                    MainGame.this.mProgressDialog = null;
                }
                MainGame.this.mProgressDialog = new ProgressDialog(MainGame.this);
                MainGame.this.mProgressDialog.setCancelable(false);
                MainGame.this.mProgressDialog.setMessage("Loading...");
                MainGame.this.mProgressDialog.show();
            }
        });
    }

    public void showMenu()
    {
        ILoading local7 = new ILoading()
        {
            public void closeLoadingCompleted()
            {
//                MainGame.this.showOfferwallNotFinish(2);
            }

            public void showLoadingCompleted()
            {
                IDoBackGround local1 = new IDoBackGround()
                {
                    public void onCompleted()
                    {
                        MainGame.this.mMenuScene.onCompleted();
                        MainGame.this.mLoading.hideLoading(true);
                    }

                    public void onDoBackGround(boolean paramAnonymous2Boolean)
                    {
                        MainGame.this.mMenuScene = null;
                        MainGame.this.mMenuScene = new MenuScene(MainGame.this);
                        MainGame.this.mMenuScene.onDoBackGround(paramAnonymous2Boolean);
                    }
                };
                MainGame.this.doBackGround(local1);
            }
        };
        this.mLoading.setmILoading(local7);
        this.mLoading.showLoading(true);
    }

    public void showPlayScene(final IClose paramIClose)
    {
        ILoading local8 = new ILoading()
        {
            public void closeLoadingCompleted()
            {
//                MainGame.this.showOfferwallNotFinish(2);
            }

            public void showLoadingCompleted()
            {
                IDoBackGround local1 = new IDoBackGround()
                {
                    IClose mIClose = paramIClose;
                    public void onCompleted()
                    {
                        if (mIClose != null) {
                            mIClose.onClose();
                        }
                        MainGame.this.mPlayScene.onCompleted();
                        MainGame.this.mLoading.hideLoading(true);
                    }

                    public void onDoBackGround(boolean paramAnonymous2Boolean)
                    {
                        MainGame.this.mPlayScene = null;
                        MainGame.this.mPlayScene = new PlayScene(MainGame.this);
                        MainGame.this.mPlayScene.onDoBackGround(paramAnonymous2Boolean);
                    }
                };
                MainGame.this.doBackGround(local1);
            }
        };
        this.mLoading.setmILoading(local8);
        this.mLoading.showLoading(true);
    }

    public void showRateApp()
    {
        handlerDoWork(new IHandler()
        {
            public void doWork()
            {
                UtilLib.showDetailApp(MainGame.this, MainGame.this.getPackageName());
            }
        });
    }

    public void showToastMessage(final String paramString)
    {
        handlerDoWork(new IHandler()
        {
            public void doWork()
            {
                UtilLib.showToast(MainGame.this, paramString);
            }
        });
    }
}
