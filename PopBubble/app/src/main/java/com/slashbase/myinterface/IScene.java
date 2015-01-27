package com.slashbase.myinterface;

import org.andengine.entity.scene.Scene;

public abstract interface IScene
{
    public abstract void doLoadBackgorund();

    public abstract void onAttached(Scene paramScene);

    public abstract void onDetached(Scene paramScene);

    public abstract void onLoadResource();

    public abstract void onLoadResourceDoBackgorund();

    public abstract void onLoadResourceDoBackgorundCompleted();

    public abstract void unLoadResource();
}
