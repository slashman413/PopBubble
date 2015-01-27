package slashdev.com.popbubble;

import com.slashbase.base.BaseDialog;
import com.slashbase.myinterface.IClose;
import com.slashbase.myinterface.IDialog;
import com.slashbase.myinterface.IHandler;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.slashbase.util.UtilLib;
import slashdev.com.popbubble.base.BaseSprite;
import slashdev.com.popbubble.database.Database;
import slashdev.com.popbubble.myinterface.IButtonSprite;

public class DialogGameOver
        extends BaseDialog
        implements IButtonSprite
{
    int coin = 0;
    boolean isBtnMenuClick = false;
    boolean isBtnNewGameClick = false;
    BaseSprite mBtnMenu;
    BaseSprite mBtnNewGame;
    BaseSprite mBtnRate;
    BaseSprite mBtnShare;
    IDialog mIDialog = new IDialog()
    {
        public void closeDialog() {}

        public void closeDialogCompleted()
        {
            if (DialogGameOver.this.isBtnMenuClick)
            {
                DialogGameOver.this.isBtnMenuClick = false;
                DialogGameOver.this.mBaseGame.mainScene.setIgnoreUpdate(false);
                DialogGameOver.this.mMainGame.mPlayScene.hideAnimation();
                DialogGameOver.this.mMainGame.showMenu();
            }
            if (DialogGameOver.this.isBtnNewGameClick) {
                DialogGameOver.this.isBtnNewGameClick = false;
                DialogGameOver.this.mMainGame.mPlayScene.unLoadResource();
                DialogGameOver.this.mMainGame.showPlayScene(null);
                DialogGameOver.this.mMainGame.mPlayScene.hidetargetClear();
                DialogGameOver.this.mBaseGame.mainScene.setIgnoreUpdate(false);
            }
        }

        public void loadBackground(boolean paramAnonymousBoolean) {}

        public void loadBackgroundCompleted() {}

        public void notLoadResource() {}

        public void showDialog() {}

        public void showDialogCompleted()
        {
//            Database localDatabase = new Database(DialogGameOver.this.mBaseGame);
//            localDatabase.openDatabase();
//            if ((localDatabase.checkIsInsert(new Coin("", DialogGameOver.this.coin)) != -1) && (DialogGameOver.this.coin > 0)) {
//                DialogGameOver.this.showDialogSave(localDatabase);
//            }
        }
    };
    MainGame mMainGame;
    ITextureRegion mMenuTTR;
    ITextureRegion mNewTTR;
    ITextureRegion mRateTTR;
    ITextureRegion mShareTTR;
    ITextureRegion mTxtGameoverTTR;

    public DialogGameOver(MainGame paramMainGame)
    {
        super(paramMainGame);
        this.mMainGame = paramMainGame;
        setmIDialog(this.mIDialog);
        setAlpha(0.5F);
        loadBg("bg/", "bg_dialog.png", 665, 538);
    }

    public void loadBackground(boolean paramBoolean)
    {
        super.loadBackground(paramBoolean);
        this.mRateTTR = this.mBaseGame.loadTextureRegion("button/", "btn_rate_dialogpause.png", 383, 94, this.listBTA);
        this.mNewTTR = this.mBaseGame.loadTextureRegion("button/", "btn_newgame_dialogpause.png", 383, 94, this.listBTA);
        this.mMenuTTR = this.mBaseGame.loadTextureRegion("button/", "btn_menu.png", 383, 94, this.listBTA);
        this.mShareTTR = this.mBaseGame.loadTextureRegion("button/", "btn_share_dialogpause.png", 383, 94, this.listBTA);
        this.mTxtGameoverTTR = this.mBaseGame.loadTextureRegion("button/", "txt_gameover.png", 250, 30, this.listBTA);
    }

    public void loadBackgroundCompleted()
    {
        super.loadBackgroundCompleted();
        Sprite localSprite = new Sprite(this.bg.getWidth() / 2.0F - this.mTxtGameoverTTR.getWidth() / 2.0F, 10.0F, this.mTxtGameoverTTR, this.mVertexBufferObjectManager);
        this.bg.attachChild(localSprite);
        float f = this.bg.getWidth() / 2.0F - this.mNewTTR.getWidth() / 2.0F;
        this.mBtnNewGame = new BaseSprite(f, 80.0F, this.mNewTTR, this.mVertexBufferObjectManager);
        this.mBtnNewGame.setmIButtonSprite(this);
        this.mBaseGame.mHud.registerTouchArea(this.mBtnNewGame);
        this.bg.attachChild(this.mBtnNewGame);
        this.mBtnMenu = new BaseSprite(f, 10.0F + (this.mBtnNewGame.getY() + this.mBtnNewGame.getHeight()), this.mMenuTTR, this.mVertexBufferObjectManager);
        this.mBtnMenu.setmIButtonSprite(this);
        this.mBaseGame.mHud.registerTouchArea(this.mBtnMenu);
        this.bg.attachChild(this.mBtnMenu);
        this.mBtnRate = new BaseSprite(f, 10.0F + (this.mBtnMenu.getY() + this.mBtnMenu.getHeight()), this.mRateTTR, this.mVertexBufferObjectManager);
        this.mBtnRate.setmIButtonSprite(this);
        this.mBaseGame.mHud.registerTouchArea(this.mBtnRate);
        this.bg.attachChild(this.mBtnRate);
        this.mBtnShare = new BaseSprite(f, 10.0F + (this.mBtnRate.getY() + this.mBtnRate.getHeight()), this.mShareTTR, this.mVertexBufferObjectManager);
        this.mBtnShare.setmIButtonSprite(this);
        this.mBaseGame.mHud.registerTouchArea(this.mBtnShare);
        this.bg.attachChild(this.mBtnShare);
        this.mBaseGame.mHud.setOnAreaTouchTraversalFrontToBack();
    }

    public Sprite onClick(Sprite paramSprite)
    {
        if (this.mBtnMenu == paramSprite)
        {
            this.isBtnMenuClick = true;
            closeDialog();
        }
        if (this.mBtnNewGame == paramSprite)
        {
            this.isBtnNewGameClick = true;
            closeDialog();
        }
        else if (this.mBtnRate == paramSprite)
        {
            UtilLib.showDetailApp(this.mMainGame, this.mMainGame.getPackageName());
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

    public void setData(int paramInt)
    {
        this.coin = paramInt;
    }

    public void showDialogSave(final Database paramDatabase)
    {
        IHandler local3 = new IHandler()
        {
            @Override
            public void doWork() {
                new DialogSaveCoin(DialogGameOver.this.mBaseGame, paramDatabase, DialogGameOver.this.coin, new IClose() {
                    @Override
                    public void onClose() {

                    }
                }).show();
            }
        };
        this.mBaseGame.handlerDoWork(local3);
    }
}
