package slashdev.com.popbubble;

import com.slashbase.base.BaseDialog;
import com.slashbase.myinterface.IClose;
import com.slashbase.myinterface.IDialog;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import slashdev.com.popbubble.base.BaseSprite;
import slashdev.com.popbubble.myinterface.IButtonSprite;

public class DialogPause
        extends BaseDialog
        implements IButtonSprite
{
    BaseSprite mBtnMenu;
    BaseSprite mBtnNewGame;
    BaseSprite mBtnResume;
    IClose mIClose;
    IDialog mIDialog = new IDialog()
    {
        public void closeDialog() {}

        public void closeDialogCompleted()
        {
            if (DialogPause.this.newBtnClick)
            {
                DialogPause.this.newBtnClick = false;
                DialogPause.this.mMainGame.mPlayScene.unLoadResource();
                DialogPause.this.mMainGame.showPlayScene(null);
                DialogPause.this.mMainGame.mPlayScene.hidetargetClear();
                DialogPause.this.mBaseGame.mainScene.setIgnoreUpdate(false);
            }
            if (DialogPause.this.resumeBtnClick)
            {
                DialogPause.this.resumeBtnClick = false;
                DialogPause.this.mBaseGame.mainScene.setIgnoreUpdate(false);
            }
            if (DialogPause.this.menuBtnClick) {
                DialogPause.this.menuBtnClick = false;
                DialogPause.this.mBaseGame.mainScene.setIgnoreUpdate(false);
                DialogPause.this.mMainGame.showMenu();
                DialogPause.this.mMainGame.mPlayScene.hidetargetClear();
                DialogPause.this.mMainGame.mPlayScene.hideAnimation();
            }
        }

        public void loadBackground(boolean paramAnonymousBoolean) {}

        public void loadBackgroundCompleted() {}

        public void notLoadResource() {}

        public void showDialog() {}

        public void showDialogCompleted() {}
    };
    MainGame mMainGame;
    ITextureRegion mMenuTTR;
    ITextureRegion mNewTTR;
    ITextureRegion mResumeTTR;
    ITextureRegion mTxtPauseTTR;
    boolean menuBtnClick = false;
    boolean newBtnClick = false;
    boolean rateBtnClick = false;
    boolean resumeBtnClick = false;

    public DialogPause(MainGame paramMainGame)
    {
        super(paramMainGame);
        this.mMainGame = paramMainGame;
        setmIDialog(this.mIDialog);
        setAlpha(0.5F);
        loadBg("bg/", "bg_dialog.png", 665, 538);
    }

    public IClose getmClose()
    {
        return this.mIClose;
    }

    public void loadBackground(boolean paramBoolean)
    {
        super.loadBackground(paramBoolean);
        this.mNewTTR = this.mBaseGame.loadTextureRegion("button/", "btn_newgame_dialogpause.png", 383, 94, this.listBTA);
        this.mResumeTTR = this.mBaseGame.loadTextureRegion("button/", "btn_resume_dialogpause.png", 383, 94, this.listBTA);
        this.mMenuTTR = this.mBaseGame.loadTextureRegion("button/", "btn_menu.png", 383, 94, this.listBTA);
        this.mTxtPauseTTR = this.mBaseGame.loadTextureRegion("button/", "txt_pause.png", 194, 44, this.listBTA);
    }

    public void loadBackgroundCompleted()
    {
        super.loadBackgroundCompleted();
        Sprite localSprite = new Sprite(this.bg.getWidth() / 2.0F - this.mTxtPauseTTR.getWidth() / 2.0F, 10.0F, this.mTxtPauseTTR, this.mVertexBufferObjectManager);
        this.bg.attachChild(localSprite);
        float f = this.bg.getWidth() / 2.0F - this.mResumeTTR.getWidth() / 2.0F;
        this.mBtnResume = new BaseSprite(f, 100.0F, this.mResumeTTR, this.mVertexBufferObjectManager);
        this.mBtnResume.setmIButtonSprite(this);
        this.mBaseGame.mHud.registerTouchArea(this.mBtnResume);
        this.bg.attachChild(this.mBtnResume);
        this.mBtnNewGame = new BaseSprite(f, 10.0F + (this.mBtnResume.getY() + this.mBtnResume.getHeight()), this.mNewTTR, this.mVertexBufferObjectManager);
        this.mBtnNewGame.setmIButtonSprite(this);
        this.mBaseGame.mHud.registerTouchArea(this.mBtnNewGame);
        this.bg.attachChild(this.mBtnNewGame);
        this.mBtnMenu = new BaseSprite(f, 10.0F + (this.mBtnNewGame.getY() + this.mBtnNewGame.getHeight()), this.mMenuTTR, this.mVertexBufferObjectManager);
        this.mBtnMenu.setmIButtonSprite(this);
        this.mBaseGame.mHud.registerTouchArea(this.mBtnMenu);
        this.bg.attachChild(this.mBtnMenu);
        this.mBaseGame.mHud.setOnAreaTouchTraversalFrontToBack();
    }

    public Sprite onClick(Sprite paramSprite)
    {
        if (this.mBtnResume == paramSprite)
        {
            this.resumeBtnClick = true;
            closeDialog();
        }
        else if (this.mBtnNewGame == paramSprite)
        {
            this.newBtnClick = true;
            closeDialog();
        }
        else if (this.mBtnMenu == paramSprite)
        {
            this.menuBtnClick = true;
            closeDialog();
        }
        return null;
    }

    public Sprite onDown(Sprite paramSprite)
    {
        this.mMainGame.getManagerSound().slectedPlay();
        return null;
    }

    public Sprite onMove(Sprite paramSprite)
    {
        return null;
    }

    public Sprite onMoveOut(Sprite paramSprite)
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
}
