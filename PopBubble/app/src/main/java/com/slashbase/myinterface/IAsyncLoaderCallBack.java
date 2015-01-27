package com.slashbase.myinterface;

public abstract interface IAsyncLoaderCallBack
{
    public abstract void onCancelled();

    public abstract void onCancelled(boolean paramBoolean);

    public abstract void onComplete();

    public abstract void workToDo();
}
