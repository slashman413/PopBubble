package com.slashbase.myinterface;

public abstract interface IDialog
{
    public abstract void closeDialog();

    public abstract void closeDialogCompleted();

    public abstract void loadBackground(boolean paramBoolean);

    public abstract void loadBackgroundCompleted();

    public abstract void notLoadResource();

    public abstract void showDialog();

    public abstract void showDialogCompleted();
}
