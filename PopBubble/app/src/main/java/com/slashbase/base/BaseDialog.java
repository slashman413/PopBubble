package com.slashbase.base;

import com.slashbase.myconfig.ConfigScreen;
import com.slashbase.myinterface.AsyncCallBack;
import com.slashbase.myinterface.AsyncTaskLoader;
import com.slashbase.myinterface.IDialog;
import com.slashbase.myinterface.IHandler;
import com.slashbase.mylog.Log;
import java.util.ArrayList;
import java.util.Iterator;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseBackIn;
import org.andengine.util.modifier.ease.EaseBackOut;

public class BaseDialog
        extends Rectangle
        implements IDialog
{
    AsyncTaskLoader asyncTaskLoader;
    public Sprite bg;
    public Sprite iconLoading;
    boolean isHideCompleted = true;
    boolean isLoadBg = false;
    boolean isLoadLoading = false;
    public boolean isLoadResource = false;
    boolean isShowCompleted = false;
    public ArrayList<BitmapTextureAtlas> listBTA;
    public BaseGame mBaseGame;
    public HUD mHud;
    IDialog mIDialog;
    public VertexBufferObjectManager mVertexBufferObjectManager;
    float timeShowHideDialog = 1.0F;

    public BaseDialog(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, VertexBufferObjectManager paramVertexBufferObjectManager)
    {
        super(0.0F, 0.0F, ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT, paramVertexBufferObjectManager);
        setColor(Color.TRANSPARENT);
        ini();
    }

    public BaseDialog(BaseGame paramBaseGame)
    {
        this(0.0F, 0.0F, ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT, paramBaseGame.getVertexBufferObjectManager());
        this.mBaseGame = paramBaseGame;
        this.mHud = paramBaseGame.mHud;
        this.mVertexBufferObjectManager = paramBaseGame.getVertexBufferObjectManager();
    }

    public BaseDialog(BaseGame paramBaseGame, String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
        this(0.0F, 0.0F, ConfigScreen.SCREENWIDTH, ConfigScreen.SCREENHEIGHT, paramBaseGame.getVertexBufferObjectManager());
        this.mBaseGame = paramBaseGame;
        this.mVertexBufferObjectManager = paramBaseGame.getVertexBufferObjectManager();
        this.mHud = paramBaseGame.mHud;
        loadBg(paramString1, paramString2, paramInt1, paramInt2);
    }

    public void closeDialog()
    {
        this.isHideCompleted = false;
        this.isShowCompleted = false;
        if (this.asyncTaskLoader != null) {
            this.asyncTaskLoader.cancel(true);
        }
        ParallelEntityModifier localParallelEntityModifier = new ParallelEntityModifier(new IEntityModifier[] { new MoveYModifier(this.timeShowHideDialog, this.bg.getY(), ConfigScreen.SCREENHEIGHT + this.bg.getHeight(), EaseBackIn.getInstance())
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                BaseDialog.this.mHud.detachChild(BaseDialog.this);
                BaseDialog.this.mHud.setOnSceneTouchListener(null);
                BaseDialog.this.closeDialogCompleted();
                if (BaseDialog.this.mIDialog != null) {
                    BaseDialog.this.mIDialog.closeDialogCompleted();
                }
                BaseDialog.this.isHideCompleted = true;
                BaseDialog.this.isShowCompleted = false;
            }
        } });
        this.bg.registerEntityModifier(localParallelEntityModifier);
        if (this.mIDialog != null) {
            this.mIDialog.closeDialog();
        }
    }

    public void closeDialogCompleted()
    {
        if (this.mIDialog != null) {
            this.mIDialog.closeDialogCompleted();
        }
    }

    public IDialog getmIDialog()
    {
        return this.mIDialog;
    }

    public void ini()
    {
        this.listBTA = new ArrayList();
        this.asyncTaskLoader = new AsyncTaskLoader();
    }

    public boolean isHideCompleted()
    {
        return this.isHideCompleted;
    }

    public boolean isLoadBg()
    {
        return this.isLoadBg;
    }

    public boolean isLoadLoading()
    {
        return this.isLoadLoading;
    }

    public boolean isShowCompleted()
    {
        return this.isShowCompleted;
    }

    public void loadBackground(boolean paramBoolean) {}

    public void loadBackgroundCompleted() {}

    public boolean loadBg(String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
        try
        {
            BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(paramString1);
            BitmapTextureAtlas localBitmapTextureAtlas = new BitmapTextureAtlas(this.mBaseGame.getTextureManager(), paramInt1, paramInt2);
            TextureRegion localTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(localBitmapTextureAtlas, this.mBaseGame, paramString2, 0, 0);
            localBitmapTextureAtlas.load();
            this.bg = new Sprite(ConfigScreen.SCREENWIDTH / 2 - localTextureRegion.getWidth() / 2.0F, -localTextureRegion.getHeight(), localTextureRegion, this.mBaseGame.getVertexBufferObjectManager());
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

    public boolean loadIconLoading(String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
        if (!this.isLoadBg) {
            return false;
        }
        try
        {
            BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(paramString1);
            BitmapTextureAtlas localBitmapTextureAtlas = new BitmapTextureAtlas(this.mBaseGame.getTextureManager(), paramInt1, paramInt2);
            TextureRegion localTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(localBitmapTextureAtlas, this.mBaseGame, paramString2, 0, 0);
            localBitmapTextureAtlas.load();
            this.iconLoading = new Sprite(this.bg.getWidth() / 2.0F - localTextureRegion.getWidth() / 2.0F, this.bg.getHeight() / 2.0F - localTextureRegion.getHeight() / 2.0F, localTextureRegion, this.mBaseGame.getVertexBufferObjectManager());
            this.bg.attachChild(this.iconLoading);
            this.isLoadLoading = true;
            return true;
        }
        catch (Exception localException)
        {
            Log.e("", "e = " + localException.toString());
            this.isLoadLoading = false;
        }
        return false;
    }

    public void notLoadResource()
    {
        this.mIDialog.notLoadResource();
    }

    public void setHideCompleted(boolean paramBoolean)
    {
        this.isHideCompleted = paramBoolean;
    }

    public void setLoadBg(boolean paramBoolean)
    {
        this.isLoadBg = paramBoolean;
    }

    public void setLoadLoading(boolean paramBoolean)
    {
        this.isLoadLoading = paramBoolean;
    }

    public void setShowCompleted(boolean paramBoolean)
    {
        this.isShowCompleted = paramBoolean;
    }

    public void setmIDialog(IDialog paramIDialog)
    {
        this.mIDialog = paramIDialog;
    }

    public void showDialog()
    {
        this.isShowCompleted = false;
        this.isHideCompleted = false;
        if (!this.isLoadBg) {
            return;
        }
        if (this.mIDialog != null) {
            this.mIDialog.showDialog();
        }
        this.mHud.setOnSceneTouchListener(new IOnSceneTouchListener()
        {
            public boolean onSceneTouchEvent(Scene paramAnonymousScene, TouchEvent paramAnonymousTouchEvent)
            {
                Log.d("", "mHud.setOnSceneTouchListener = " + paramAnonymousTouchEvent.getAction());
                return true;
            }
        });
        this.mHud.setTouchAreaBindingOnActionDownEnabled(true);
        this.mHud.setTouchAreaBindingOnActionMoveEnabled(true);
        this.mHud.attachChild(this);
        float f = ConfigScreen.SCREENHEIGHT / 2 - this.bg.getHeight() / 2.0F;
        ParallelEntityModifier localParallelEntityModifier = new ParallelEntityModifier(new IEntityModifier[] { new MoveYModifier(this.timeShowHideDialog, this.bg.getY(), f, EaseBackOut.getInstance())
        {
            protected void onModifierFinished(IEntity paramAnonymousIEntity)
            {
                super.onModifierFinished(paramAnonymousIEntity);
                BaseDialog.this.showDialogCompleted();
            }
        } });
        this.bg.registerEntityModifier(localParallelEntityModifier);
    }

    public void showDialogCompleted()
    {
        if (this.mIDialog != null) {
            this.mIDialog.showDialogCompleted();
        }
        IHandler local4 = new IHandler()
        {
            public void doWork()
            {
                BaseDialog.this.asyncTaskLoader = new AsyncTaskLoader();
                AsyncTaskLoader localAsyncTaskLoader = BaseDialog.this.asyncTaskLoader;
                AsyncCallBack[] arrayOfAsyncCallBack = new AsyncCallBack[1];
                arrayOfAsyncCallBack[0] = new AsyncCallBack(null)
                {
                    public void onComplete()
                    {
                        super.onComplete();
                        if (!BaseDialog.this.isLoadResource) {
                            BaseDialog.this.loadBackgroundCompleted();
                            BaseDialog.this.isLoadResource = true;
                            BaseDialog.this.isShowCompleted = true;
                            BaseDialog.this.isHideCompleted = false;
                        } else {
                            BaseDialog.this.notLoadResource();
                        }
                    }

                    public void workToDo()
                    {
                        super.workToDo();
                        if (!BaseDialog.this.isLoadResource) {
                            BaseDialog.this.loadBackground(BaseDialog.this.asyncTaskLoader.isCancelled());
                        }
                    }
                };
                localAsyncTaskLoader.execute(arrayOfAsyncCallBack);
            }
        };
        this.mBaseGame.handlerDoWork(local4);
    }

    public void unLoadResource()
    {
        Iterator localIterator;
        if (this.listBTA != null) {
            localIterator = this.listBTA.iterator();
            while(localIterator.hasNext()) {
                ((BitmapTextureAtlas)localIterator.next()).unload();
            }
            this.listBTA.clear();
            this.listBTA = null;
            this.isLoadResource = false;
        }
    }
}
