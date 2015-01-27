package slashdev.com.popbubble.sound;

import com.slashbase.base.BaseGame;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.util.debug.Debug;

import java.io.IOException;
import java.util.ArrayList;

import slashdev.com.popbubble.SharePref;

public class ManagerSound
{
    ArrayList<Float> a = new ArrayList();
    Sound cantmove;
    Sound destroy;
    Sound effectstar;
    Music goodandcool;
    boolean isSound = true;
    Engine mEngine;
    SharePref mSharePref;
    Sound move;
    boolean musicOff = true;
    Sound putstar;
    Sound slected;
    Sound[] soundEffect = new Sound[14];
    Music stargetclear;
    Sound touchstar;

    private void mPlay(final Sound paramSound)
    {
        if ((paramSound != null) && (this.isSound)) {
            new Thread(new Runnable()
            {
                public void run()
                {
                    paramSound.play();
                }
            }).start();
        }
    }

    public void cantmovePlay()
    {
        mPlay(this.cantmove);
    }

    public void destroyPlay()
    {
        mPlay(this.destroy);
    }

    public void effectStar()
    {
        mPlay(this.effectstar);
    }

    public void goodAndCool()
    {
        if ((this.goodandcool != null) && (this.isSound)) {
            new Thread(new Runnable()
            {
                public void run()
                {
                    ManagerSound.this.goodandcool.play();
                }
            }).start();
        }
    }

    public boolean isSound()
    {
        return this.isSound;
    }

    public void load(Engine paramEngine, BaseGame paramBaseGame)
    {
        this.mSharePref = new SharePref(paramBaseGame);
        this.isSound = this.mSharePref.getBoolean("isSound", true);
        this.mEngine = paramEngine;
        SoundFactory.setAssetBasePath("sound/");
        MusicFactory.setAssetBasePath("sound/");
        try
        {
            for (int i = 0; i < this.soundEffect.length; i++) {
                this.soundEffect[i] = SoundFactory.createSoundFromAsset(paramEngine.getSoundManager(), paramBaseGame, "EliminateEffect" + (i + 1) + ".wav");
                this.soundEffect[i].setVolume(0.5F);
            }
            this.goodandcool = MusicFactory.createMusicFromAsset(paramEngine.getMusicManager(), paramBaseGame, "EffectCheer.wav");
            this.putstar = SoundFactory.createSoundFromAsset(paramEngine.getSoundManager(), paramBaseGame, "EffectFall.wav");
            this.putstar.setVolume(0.02F);
            this.stargetclear = MusicFactory.createMusicFromAsset(paramEngine.getMusicManager(), paramBaseGame, "EffectLevelCleared.wav");
            this.stargetclear.setVolume(0.5F);
            this.touchstar = SoundFactory.createSoundFromAsset(paramEngine.getSoundManager(), paramBaseGame, "touchstar.wav");
            this.cantmove = SoundFactory.createSoundFromAsset(paramEngine.getSoundManager(), paramBaseGame, "cantmove.wav");
            this.destroy = SoundFactory.createSoundFromAsset(paramEngine.getSoundManager(), paramBaseGame, "destroy.wav");
            this.move = SoundFactory.createSoundFromAsset(paramEngine.getSoundManager(), paramBaseGame, "move.wav");
            this.slected = SoundFactory.createSoundFromAsset(paramEngine.getSoundManager(), paramBaseGame, "slected.wav");
            this.musicOff = this.mSharePref.getBoolean("music", true);
        }
        catch (IOException localIOException)
        {
            Debug.e(localIOException);
        }
    }

    public void movePlay()
    {
        mPlay(this.move);
    }

    public void playSoundDelay(int paramInt)
    {
        this.a.clear();
        for (int i = 1; i < paramInt + 1; i++) {
            this.a.add(Float.valueOf((float)(0.044D * i)));
        }
        for (int j = 1; j < paramInt + 1; j++) {
            final int k = j;
            this.mEngine.registerUpdateHandler(new TimerHandler(((Float)this.a.get(j - 1)).floatValue(), new ITimerCallback()
            {
                public void onTimePassed(TimerHandler paramAnonymousTimerHandler)
                {
                    if (k >= 14)
                    {
                        ManagerSound.this.soundEffect13();
                        return;
                    }
                    ManagerSound.this.soundEffect[k].play();
                    ManagerSound.this.soundEffect[k].setVolume(0.5F);
                }
            }));
        }
    }

    public void putStar()
    {
        mPlay(this.putstar);
    }

    public void setSound(boolean paramBoolean)
    {
        this.isSound = paramBoolean;
        this.mSharePref.set("isSound", paramBoolean);
    }

    public void slectedPlay()
    {
        mPlay(this.slected);
    }

    public void soundEffect()
    {
        mPlay(this.soundEffect[0]);
    }

    public void soundEffect13()
    {
        mPlay(this.soundEffect[13]);
    }

    public void stargetClear()
    {
        if ((this.stargetclear != null) && (this.isSound)) {
            new Thread(new Runnable()
            {
                public void run()
                {
                    ManagerSound.this.stargetclear.play();
                }
            }).start();
        }
    }

    public void touchStar()
    {
        mPlay(this.touchstar);
    }
}
