package com.slashbase.myinterface;

import org.andengine.entity.scene.Scene;

public abstract interface IBaseScene
{
    public abstract void onAttachScene(Scene paramScene);

    public abstract void onDestroyScene();

    public abstract void onDettachScene(Scene paramScene);
}
