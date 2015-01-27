package slashdev.com.popbubble;

import com.slashbase.base.BaseDialog;
import com.slashbase.myinterface.IDialog;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.slashbase.util.UtilLib;
import slashdev.com.popbubble.base.BaseSprite;
import slashdev.com.popbubble.myinterface.IButtonSprite;

public class DialogExitGame
        extends BaseDialog
        implements IButtonSprite
{
    boolean isYes = false;
    IDialog mIDialog = new IDialog()
    {
        public void closeDialog() {}

        public void closeDialogCompleted()
        {
            if (DialogExitGame.this.isYes)
            {
                DialogExitGame.this.mMainGame.finish();
                return;
            }
            DialogExitGame.this.mBaseGame.mainScene.setIgnoreUpdate(false);
        }

        public void loadBackground(boolean paramAnonymousBoolean) {}

        public void loadBackgroundCompleted() {}

        public void notLoadResource() {}

        public void showDialog() {}

        public void showDialogCompleted()
        {
            DialogExitGame.this.isYes = false;
        }
    };
    MainGame mMainGame;
    ITextureRegion mNoTTR;
    ITextureRegion mRateTTR;
    Rectangle mRectangle;
    BaseSprite mSpriteNo;
    BaseSprite mSpriteRate;
    BaseSprite mSpriteYes;
    ITextureRegion mTxtExitTTR;
    ITextureRegion mTxtMessageTTR;
    ITextureRegion mYesTTR;

    public DialogExitGame(MainGame paramMainGame)
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
        this.mNoTTR = this.mBaseGame.loadTextureRegion("button/", "btn_no.png", 283, 72, this.listBTA);
        this.mYesTTR = this.mBaseGame.loadTextureRegion("button/", "btn_yes.png", 283, 72, this.listBTA);
        this.mTxtExitTTR = this.mBaseGame.loadTextureRegion("button/", "txt_exit.png", 141, 41, this.listBTA);
        this.mRateTTR = this.mBaseGame.loadTextureRegion("button/", "btn_rate_min.png", 283, 72, this.listBTA);
        this.mTxtMessageTTR = this.mBaseGame.loadTextureRegion("button/", "txt_message.png", 548, 106, this.listBTA);
    }

    public void loadBackgroundCompleted()
    {
        super.loadBackgroundCompleted();
        Sprite localSprite1 = new Sprite(this.bg.getWidth() / 2.0F - this.mTxtExitTTR.getWidth() / 2.0F, 10.0F, this.mTxtExitTTR, this.mVertexBufferObjectManager);
        this.bg.attachChild(localSprite1);
        Sprite localSprite2 = new Sprite(this.bg.getWidth() / 2.0F - this.mTxtMessageTTR.getWidth() / 2.0F, this.bg.getHeight() / 2.0F - this.mTxtMessageTTR.getHeight(), this.mTxtMessageTTR, this.mVertexBufferObjectManager);
        this.bg.attachChild(localSprite2);
        this.mSpriteYes = new BaseSprite(30.0F, 50.0F + (localSprite2.getY() + localSprite2.getHeight()), this.mYesTTR, this.mVertexBufferObjectManager);
        this.mBaseGame.mHud.registerTouchArea(this.mSpriteYes);
        this.mSpriteYes.setmIButtonSprite(this);
        this.bg.attachChild(this.mSpriteYes);
        this.mSpriteNo = new BaseSprite(this.bg.getWidth() - this.mNoTTR.getWidth() - 30.0F, 50.0F + (localSprite2.getY() + localSprite2.getHeight()), this.mNoTTR, this.mVertexBufferObjectManager);
        this.mSpriteNo.setmIButtonSprite(this);
        this.mBaseGame.mHud.registerTouchArea(this.mSpriteNo);
        this.bg.attachChild(this.mSpriteNo);
        this.mSpriteRate = new BaseSprite(this.bg.getWidth() / 2.0F - this.mRateTTR.getWidth() / 2.0F, 10.0F + (this.mSpriteNo.getY() + this.mSpriteNo.getHeight()), this.mRateTTR, this.mVertexBufferObjectManager);
        this.mSpriteRate.setmIButtonSprite(this);
        this.mBaseGame.mHud.registerTouchArea(this.mSpriteRate);
        this.bg.attachChild(this.mSpriteRate);
        this.mBaseGame.mHud.setOnAreaTouchTraversalFrontToBack();
    }

    public Sprite onClick(Sprite paramSprite)
    {
        if (this.mSpriteYes == paramSprite) {
            closeDialog();
        } else if (this.mSpriteNo == paramSprite) {
            closeDialog();
        } else if (this.mSpriteRate == paramSprite) {
            UtilLib.showDetailApp(this.mMainGame, this.mMainGame.getPackageName());
        }
        return null;
    }

    public Sprite onDown(Sprite paramSprite)
    {
        this.mMainGame.getManagerSound().slectedPlay();
        if (this.mSpriteYes == paramSprite) {
            this.isYes = true;
        }
        return null;
    }

    public Sprite onMove(Sprite paramSprite)
    {
        return null;
    }

    public Sprite onMoveOut(Sprite paramSprite)
    {
        if (this.mSpriteYes == paramSprite) {
            this.isYes = false;
        }
        return null;
    }

    public Sprite onUp(Sprite paramSprite)
    {
        return null;
    }
}
