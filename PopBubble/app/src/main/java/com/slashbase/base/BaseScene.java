package com.slashbase.base;

import com.slashbase.myconfig.ConfigScreen;
import com.slashbase.myinterface.AsyncCallBack;
import com.slashbase.myinterface.AsyncTaskLoader;
import com.slashbase.myinterface.IHandler;
import com.slashbase.myinterface.IScene;
import com.slashbase.mylog.Log;
import java.util.ArrayList;
import java.util.Iterator;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

public class BaseScene
        extends Scene
        implements IScene
{
    public AsyncTaskLoader asyncTaskLoader;
    public Sprite bg;
    public Sprite iconLoading;
    boolean isLoadBg = false;
    public ArrayList<BitmapTextureAtlas> listBTA;
    public BaseGame mBaseGame;
    public IOnSceneTouchListener mIOnSceneTouchListener;
    public IScene mIScene;

    public BaseScene(BaseGame paramBaseGame)
    {
        this.mBaseGame = paramBaseGame;
        this.listBTA = new ArrayList();
    }

    public void doLoadBackgorund()
    {
        if (this.mIScene != null) {
            this.mIScene.doLoadBackgorund();
        }
    }

    public Sprite getIconLoading()
    {
        return this.iconLoading;
    }

    public BaseGame getmBaseGame()
    {
        return this.mBaseGame;
    }

    public IScene getmIScene()
    {
        return this.mIScene;
    }

    public boolean isLoadBg()
    {
        return this.isLoadBg;
    }

    public boolean loadBg(String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
        try
        {
            BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(paramString1);
            BitmapTextureAtlas localBitmapTextureAtlas = new BitmapTextureAtlas(this.mBaseGame.getTextureManager(), paramInt1, paramInt2);
            TextureRegion localTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(localBitmapTextureAtlas, this.mBaseGame, paramString2, 0, 0);
            localBitmapTextureAtlas.load();
            this.bg = new Sprite(ConfigScreen.SCREENWIDTH / 2 - localTextureRegion.getWidth() / 2.0F, ConfigScreen.SCREENHEIGHT / 2 - localTextureRegion.getHeight() / 2.0F, localTextureRegion, this.mBaseGame.getVertexBufferObjectManager());
            attachChild(this.bg);
            this.isLoadBg = true;
            return true;
        }
        catch (Exception localException)
        {
            Log.e("", "e = " + localException.toString());
            this.isLoadBg = false;
        }
        return false;
    }

    public void onAttached(Scene paramScene)
    {
        setOnSceneTouchListener(new IOnSceneTouchListener()
        {
            public boolean onSceneTouchEvent(Scene paramAnonymousScene, TouchEvent paramAnonymousTouchEvent)
            {
                BaseScene.this.sceneTouchEvent(paramAnonymousScene, paramAnonymousTouchEvent);
                return true;
            }
        });
        setTouchAreaBindingOnActionDownEnabled(true);
        setTouchAreaBindingOnActionMoveEnabled(true);
        paramScene.setChildScene(this);
        Log.e("", "onAttached BaseScene = " + getClass());
        if (this.mIScene != null) {
            this.mIScene.onAttached(paramScene);
        }
    }

    public void onDetached(Scene paramScene)
    {
        paramScene.clearChildScene();
        Log.e("", "onDetached BaseScene = " + getClass());
        if (this.mIScene != null) {
            this.mIScene.onDetached(paramScene);
        }
    }

    public void onLoadResource()
    {
        if (this.mIScene != null) {
            this.mIScene.onLoadResource();
        }
    }

    public void onLoadResourceDoBackgorund()
    {
        IHandler local2 = new IHandler()
        {
            public void doWork()
            {
                AsyncTaskLoader localAsyncTaskLoader = BaseScene.this.asyncTaskLoader;
                AsyncCallBack[] arrayOfAsyncCallBack = new AsyncCallBack[1];
                arrayOfAsyncCallBack[0] = new AsyncCallBack(null)
                {
                    public void onComplete()
                    {
                        super.onComplete();
                        BaseScene.this.onLoadResourceDoBackgorundCompleted();
                        if (BaseScene.this.mIScene != null) {
                            BaseScene.this.mIScene.onLoadResourceDoBackgorundCompleted();
                        }
                    }

                    public void workToDo()
                    {
                        super.workToDo();
                        BaseScene.this.doLoadBackgorund();
                        if (BaseScene.this.mIScene != null) {
                            BaseScene.this.mIScene.doLoadBackgorund();
                        }
                    }
                };
                localAsyncTaskLoader.execute(arrayOfAsyncCallBack);
            }
        };
        this.mBaseGame.handlerDoWork(local2);
    }

    public void onLoadResourceDoBackgorundCompleted() {}

    public void sceneTouchEvent(Scene paramScene, TouchEvent paramTouchEvent) {}

    public void setIconLoading(Sprite paramSprite)
    {
        this.iconLoading = paramSprite;
    }

    public void setLoadBg(boolean paramBoolean)
    {
        this.isLoadBg = paramBoolean;
    }

    public void setmBaseGame(BaseGame paramBaseGame)
    {
        this.mBaseGame = paramBaseGame;
    }

    public void setmIScene(IScene paramIScene)
    {
        this.mIScene = paramIScene;
    }

    public void unLoadResource()
    {
        if (this.asyncTaskLoader != null) {
            this.asyncTaskLoader.cancel(true);
        }
        Iterator localIterator;
        if (this.listBTA != null) {
            localIterator = this.listBTA.iterator();
            while(localIterator.hasNext()) {
                ((BitmapTextureAtlas)localIterator.next()).unload();
            }
            this.listBTA.clear();
            this.listBTA = null;
        }
        if (this.mIScene != null) {
            this.mIScene.unLoadResource();
        }
    }
}
