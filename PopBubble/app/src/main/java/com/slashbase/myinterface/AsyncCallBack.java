package com.slashbase.myinterface;

public class AsyncCallBack
        implements IAsyncLoaderCallBack
{
    Object object;

    public AsyncCallBack(Object paramObject)
    {
        this.object = paramObject;
    }

    public void onCancelled() {}

    public void onCancelled(boolean paramBoolean) {}

    public void onComplete() {}

    public void workToDo() {}
}
