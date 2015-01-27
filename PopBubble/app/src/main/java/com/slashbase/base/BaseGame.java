package com.slashbase.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.slashbase.myconfig.ConfigScreen;
import com.slashbase.myinterface.AsyncCallBack;
import com.slashbase.myinterface.AsyncTaskLoader;
import com.slashbase.myinterface.IClose;
import com.slashbase.myinterface.IDoBackGround;
import com.slashbase.myinterface.IHandler;
import com.slashbase.mylog.Log;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.FileBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.File;
import java.util.ArrayList;

public abstract class BaseGame
        extends SimpleBaseGameActivity
{
    public AdView adView = null;
    public AsyncTaskLoader asyncTaskLoader;
    boolean isAdmob = true;
//    boolean isMobilecore = true;
    public boolean isMultiTouch = false;
    public Camera mCamera;
    public Context mContext;
    public EngineOptions mEngineOptions;
    final Handler mHandlerAdmob = new Handler()
    {
        public void handleMessage(Message paramAnonymousMessage)
        {
            if (paramAnonymousMessage.what == 1)
            {
                BaseGame.this.adView.setVisibility(View.VISIBLE);
                return;
            }
            BaseGame.this.adView.setVisibility(View.GONE);
        }
    };
    Handler mHandlerShowAdmob;
    public HUD mHud;
    final Handler mIHandlerHandler = new Handler()
    {
        public void handleMessage(Message paramAnonymousMessage)
        {
            super.handleMessage(paramAnonymousMessage);
            ((IHandler)paramAnonymousMessage.obj).doWork();
        }
    };
    Runnable mRunnableShowAdmob;
    public VertexBufferObjectManager mVertexBufferObjectManager;
    public Scene mainScene;

    private Entity remove(Entity paramEntity)
    {
        if (paramEntity == null) {
            return null;
        }
        try
        {
            Engine.EngineLock localEngineLock = this.mEngine.getEngineLock();
            localEngineLock.lock();
            paramEntity.clearEntityModifiers();
            paramEntity.clearUpdateHandlers();
            paramEntity.detachSelf();
            paramEntity.dispose();
            localEngineLock.unlock();
            return null;
        }
        catch (Exception localException)
        {
            Log.e("", "remove e = " + localException.toString());
        }
        return null;
    }

//    private void showOfferwallBase()
//    {
//        if (!this.isMobilecore) {
//            return;
//        }
//    }

    public void addAdmob(int paramInt, View paramView, AdListener paramAdListener, String paramString)
    {
        if (!this.isAdmob) {
            return;
        }
        LinearLayout localLinearLayout = (LinearLayout)paramView.findViewById(paramInt);

        this.adView = new AdView(this);
        this.adView.setAdSize(AdSize.BANNER);
        this.adView.setAdUnitId(paramString);
        AdRequest localAdRequest = new AdRequest.Builder().build();
        this.adView.loadAd(localAdRequest);
        this.adView.setAdListener(paramAdListener);
        localLinearLayout.addView(this.adView);
    }

    public void addAdmob(String paramString, int paramInt)
    {
        if (!this.isAdmob) {
            return;
        }
        FrameLayout localFrameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams localLayoutParams1 = new FrameLayout.LayoutParams(-1, -1);
        this.adView = new AdView(this);
        this.adView.setAdSize(AdSize.BANNER);
        this.adView.setAdUnitId(paramString);
        this.adView.refreshDrawableState();
        this.adView.setVisibility(View.VISIBLE);
        FrameLayout.LayoutParams localLayoutParams2 = new FrameLayout.LayoutParams(-2, -2, paramInt | 0x1);
        AdRequest localAdRequest = new AdRequest.Builder().build();
        this.adView.loadAd(localAdRequest);
        this.mRenderSurfaceView = new RenderSurfaceView(this);
        this.mRenderSurfaceView.setRenderer(this.mEngine, this);
        // TODO: check this if worked
        FrameLayout.LayoutParams localLayoutParams3 = SimpleBaseGameActivity.createSurfaceViewLayoutParams();//new FrameLayout.LayoutParams(SimpleBaseGameActivity.createSurfaceViewLayoutParams());
        localFrameLayout.addView(this.mRenderSurfaceView, localLayoutParams3);
        localFrameLayout.addView(this.adView, localLayoutParams2);
        setContentView(localFrameLayout, localLayoutParams1);
    }

    public void clearEntity(final Sprite paramSprite, final IClose paramIClose)
    {
        this.mEngine.runOnUpdateThread(new Runnable()
        {
            public void run()
            {
                if (paramSprite != null)
                {
                    paramSprite.clearEntityModifiers();
                    paramSprite.setRotation(0.0F);
                    paramSprite.setAlpha(1.0F);
                    paramSprite.setScale(1.0F);
                    Log.e("", "resetBall ok");
                    if (paramIClose != null) {
                        paramIClose.onClose();
                    }
                }
            }
        });
    }

    public abstract void createResources();

    public abstract void createScene();

    public void deleteAllChild(Entity paramEntity)
    {
        for (int i = 0;; i++)
        {
            if (i >= paramEntity.getChildCount()) {
                return;
            }
            removeEntity((Entity)paramEntity.getChildByIndex(i));
        }
    }

    public void detachSelfOnScene(final Sprite paramSprite)
    {
        this.mEngine.runOnUpdateThread(new Runnable()
        {
            public void run()
            {
                if (!paramSprite.detachSelf()) {
                    Log.e("", "detachSelf = Không thành công");
                }
            }
        });
    }

    public void detachSelfOnScene(final Text paramText)
    {
        this.mEngine.runOnUpdateThread(new Runnable()
        {
            public void run()
            {
                if (!paramText.detachSelf()) {
                    Log.e("", "detachSelf = Không thành công");
                }
            }
        });
    }

    public void doBackGround(final IDoBackGround paramIDoBackGround)
    {
        handlerDoWork(new IHandler()
        {
            public void doWork()
            {
                BaseGame.this.asyncTaskLoader = new AsyncTaskLoader();
                AsyncTaskLoader localAsyncTaskLoader = BaseGame.this.asyncTaskLoader;
                AsyncCallBack[] arrayOfAsyncCallBack = new AsyncCallBack[1];
                arrayOfAsyncCallBack[0] = new AsyncCallBack(null)
                {
                    public void onCancelled()
                    {
                        super.onCancelled();
                    }

                    public void onCancelled(boolean paramAnonymous2Boolean)
                    {
                        super.onCancelled(paramAnonymous2Boolean);
                    }

                    public void onComplete()
                    {
                        super.onComplete();
                        paramIDoBackGround.onCompleted();
                    }

                    public void workToDo()
                    {
                        super.workToDo();
                        paramIDoBackGround.onDoBackGround(BaseGame.this.asyncTaskLoader.isCancelled());
                    }
                };
                localAsyncTaskLoader.execute(arrayOfAsyncCallBack);
            }
        });
    }

    public Scene getMainScene()
    {
        return this.mainScene;
    }

    public Camera getmCamera()
    {
        return this.mCamera;
    }

    public Context getmContext()
    {
        return this.mContext;
    }

    public EngineOptions getmEngineOptions()
    {
        return this.mEngineOptions;
    }

    public HUD getmHud()
    {
        return this.mHud;
    }

    public void handlerDoWork(IHandler paramIHandler)
    {
        Message localMessage = this.mIHandlerHandler.obtainMessage(0, paramIHandler);
        this.mIHandlerHandler.sendMessage(localMessage);
    }

    public void iniConfigScreen()
    {
        ConfigScreen.ini(this);
    }

    public boolean isAdmob()
    {
        return this.isAdmob;
    }

//    public boolean isMobilecore()
//    {
//        return this.isMobilecore;
//    }

    public boolean isMultiTouch()
    {
        return this.isMultiTouch;
    }

    public boolean loadBg(String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
        try
        {
            BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(paramString1);
            BitmapTextureAtlas localBitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), paramInt1, paramInt2, TextureOptions.BILINEAR);
            TextureRegion localTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(localBitmapTextureAtlas, this, paramString2, 0, 0);
            localBitmapTextureAtlas.load();
            Sprite localSprite = new Sprite(ConfigScreen.SCREENWIDTH / 2 - localTextureRegion.getWidth() / 2.0F, ConfigScreen.SCREENHEIGHT / 2 - localTextureRegion.getHeight() / 2.0F, localTextureRegion, getVertexBufferObjectManager());
            this.mainScene.attachChild(localSprite);
            return true;
        }
        catch (Exception localException)
        {
            Log.e("", "loadBg flase e = " + localException.toString());
        }
        return false;
    }

    public ITextureRegion loadTextureRegion(String paramString, int paramInt1, int paramInt2, ArrayList<BitmapTextureAtlas> paramArrayList)
    {
        try
        {
            File localFile = new File(paramString);
            boolean bool = localFile.exists();
            Object localObject = null;
            if (bool)
            {
                BitmapTextureAtlas localBitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), paramInt1, paramInt2, TextureOptions.BILINEAR);
                FileBitmapTextureAtlasSource localFileBitmapTextureAtlasSource = FileBitmapTextureAtlasSource.create(localFile);
                getTextureManager().loadTexture(localBitmapTextureAtlas);
                paramArrayList.add(localBitmapTextureAtlas);
                TextureRegion localTextureRegion = TextureRegionFactory.createFromSource(localBitmapTextureAtlas, localFileBitmapTextureAtlasSource, 0, 0, false);
                localObject = localTextureRegion;
            }
            return (ITextureRegion) localObject;
        }
        catch (Exception localException)
        {
            Log.e("", "loadTextureRegion flase e = " + localException.toString());
        }
        return null;
    }

    public ITextureRegion loadTextureRegion(String paramString1, String paramString2, int paramInt1, int paramInt2, ArrayList<BitmapTextureAtlas> paramArrayList)
    {
        try
        {
            BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(paramString1);
            BitmapTextureAtlas localBitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), paramInt1, paramInt2, TextureOptions.BILINEAR);
            TextureRegion localTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(localBitmapTextureAtlas, this, paramString2, 0, 0);
            localBitmapTextureAtlas.load();
            paramArrayList.add(localBitmapTextureAtlas);
            return localTextureRegion;
        }
        catch (Exception localException)
        {
            Log.e("", "loadTextureRegion flase e = " + localException.toString());
        }
        return null;
    }

    public TiledTextureRegion loadTiledTextureRegion(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, ArrayList<BuildableBitmapTextureAtlas> paramArrayList)
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(paramString1);
        BuildableBitmapTextureAtlas localBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(getTextureManager(), paramInt1 + 10, paramInt2 + 10, TextureOptions.BILINEAR);
        TiledTextureRegion localTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(localBuildableBitmapTextureAtlas, this, paramString2, paramInt3, paramInt4);
        try
        {
            localBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder(0, 0, 1));
            localBuildableBitmapTextureAtlas.load();
            paramArrayList.add(localBuildableBitmapTextureAtlas);
            return localTiledTextureRegion;
        }
        catch (ITextureAtlasBuilder.TextureAtlasBuilderException localTextureAtlasBuilderException)
        {
            Log.e("", "e = " + localTextureAtlasBuilderException.toString());
        }
        return null;
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
//        if (this.isMobilecore) {
//            new Thread(new Runnable()
//            {
//                public void run()
//                {
//                    BaseGame.this.handlerDoWork(new IHandler()
//                    {
//                        public void doWork()
//                        {
//                        }
//                    });
//                }
//            }).start();
//        }
        this.asyncTaskLoader = new AsyncTaskLoader();
        this.mVertexBufferObjectManager = getVertexBufferObjectManager();
    }

    public EngineOptions onCreateEngineOptions()
    {
        iniConfigScreen();
        this.mHud = new HUD();
        Log.e("", "ConfigScreen.SCREENWIDTH = " + ConfigScreen.SCREENWIDTH + " ConfigScreen.SCREENHEIGHT = " + ConfigScreen.SCREENHEIGHT);
        this.mCamera = new Camera(0.0F, 0.0F, ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT);
        if (ConfigScreen.mRatioResolutionPolicy != null) {
            this.mEngineOptions = new EngineOptions(true, ConfigScreen.mScreenOrientation, ConfigScreen.mRatioResolutionPolicy, this.mCamera);
        } else {
            this.mEngineOptions = new EngineOptions(true, ConfigScreen.mScreenOrientation, new FillResolutionPolicy(), this.mCamera);
        }
        this.mEngineOptions.getAudioOptions().setNeedsSound(true);
        this.mEngineOptions.getAudioOptions().setNeedsMusic(true);
        this.mEngineOptions.getTouchOptions().setNeedsMultiTouch(this.isMultiTouch);
        this.mEngineOptions.getRenderOptions().setDithering(true);
        this.mEngineOptions.getRenderOptions().setMultiSampling(true);

        return this.mEngineOptions;
    }

    protected void onCreateResources()
    {
        createResources();
    }

    protected Scene onCreateScene()
    {
        this.mainScene = new Scene();
        this.mCamera.setHUD(this.mHud);
        createScene();
        return this.mainScene;
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if (this.adView != null) {
            this.adView.destroy();
        }
    }

    public void onPause()
    {
        super.onPause();
        if (this.mEngine.isRunning()) {
            this.mEngine.stop();
        }
    }

    public void onResume()
    {
        super.onResume();
        if (!this.mEngine.isRunning()) {
            this.mEngine.start();
        }
    }

    protected void onSetContentView()
    {
        super.onSetContentView();
    }

    public void onStart()
    {
//        EasyTracker.getInstance().activityStart(this);
        super.onStart();
    }

    public void onStop()
    {
//        EasyTracker.getInstance().activityStop(this);
        super.onStop();
    }

    public Entity removeEntity(final Entity paramEntity)
    {
        this.mEngine.runOnUpdateThread(new Runnable()
        {
            public void run()
            {
                BaseGame.this.remove(paramEntity);
            }
        });
        return null;
    }

    public Entity removeEntity(final Entity paramEntity, final IClose paramIClose)
    {
        this.mEngine.runOnUpdateThread(new Runnable()
        {
            public void run()
            {
                BaseGame.this.remove(paramEntity);
                if (paramIClose != null) {
                    paramIClose.onClose();
                }
            }
        });
        return null;
    }

    public void removeShowAdmobHandlerDelay()
    {
        if ((this.adView == null) || (!this.isAdmob)) {}
        while ((this.mHandlerShowAdmob == null) || (this.mRunnableShowAdmob == null)) {
            return;
        }
        this.mHandlerShowAdmob.removeCallbacks(this.mRunnableShowAdmob);
    }

    public void setAdmob(boolean paramBoolean)
    {
        this.isAdmob = paramBoolean;
    }

    public void setMainScene(Scene paramScene)
    {
        this.mainScene = paramScene;
    }

//    public void setMobilecore(boolean paramBoolean)
//    {
//        this.isMobilecore = paramBoolean;
//    }

    public void setMultiTouch(boolean paramBoolean)
    {
        this.isMultiTouch = paramBoolean;
    }

    public void setPositionAdmob(final int paramInt)
    {
        if ((this.adView == null) || (!this.isAdmob)) {
            return;
        }
        handlerDoWork(new IHandler()
        {
            public void doWork()
            {
                FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2, 0x1 | paramInt);
                BaseGame.this.adView.setLayoutParams(localLayoutParams);
            }
        });
    }

    public void setVisiableAdmob(final boolean paramBoolean)
    {
        if ((this.adView == null) || (!this.isAdmob)) {
            return;
        }
        handlerDoWork(new IHandler()
        {
            public void doWork()
            {
                if (paramBoolean)
                {
                    BaseGame.this.mHandlerAdmob.sendEmptyMessage(1);
                    return;
                }
                BaseGame.this.mHandlerAdmob.sendEmptyMessage(0);
            }
        });
    }

    public void setmCamera(Camera paramCamera)
    {
        this.mCamera = paramCamera;
    }

    public void setmContext(Context paramContext)
    {
        this.mContext = paramContext;
    }

    public void setmEngineOptions(EngineOptions paramEngineOptions)
    {
        this.mEngineOptions = paramEngineOptions;
    }

    public void setmHud(HUD paramHUD)
    {
        this.mHud = paramHUD;
    }

//    public void showAdmobHandler(final View paramView, final int paramInt1, final int paramInt2)
//    {
//        if ((this.adView == null) || (!this.isAdmob)) {
//            return;
//        }
//        if (paramView.getVisibility() == View.GONE) {}
//        for (int i = paramInt2;; i = paramInt1)
//        {
//            this.mHandlerShowAdmob = new Handler();
//            this.mRunnableShowAdmob = new Runnable()
//            {
//                public void run()
//                {
//                    if (!BaseGame.this.isFinishing())
//                    {
//                        if (paramView.getVisibility() != View.GONE) {
//                            break label50;
//                        }
//                        paramView.setVisibility(0);
//                    }
//                    for (;;)
//                    {
//                        BaseGame.this.showAdmobHandler(paramView, paramInt1, paramInt2);
//                        return;
//                        label50:
//                        paramView.setVisibility(8);
//                    }
//                }
//            };
//            this.mHandlerShowAdmob.postDelayed(this.mRunnableShowAdmob, i);
//            return;
//        }
//    }
}
