package com.slashbase.myinterface;

import android.os.AsyncTask;

public class AsyncTaskLoader
        extends AsyncTask<AsyncCallBack, Integer, Boolean>
{
    AsyncCallBack[] _params;

    protected Boolean doInBackground(AsyncCallBack... paramVarArgs)
    {
        this._params = paramVarArgs;
        int i = paramVarArgs.length;
        for (int j = 0;; j++)
        {
            if (j >= i) {
                return Boolean.valueOf(true);
            }
            paramVarArgs[j].workToDo();
        }
    }

    protected void onCancelled()
    {
        super.onCancelled();
        int i = this._params.length;
        for (int j = 0;; j++)
        {
            if (j >= i) {
                return;
            }
            this._params[j].onCancelled();
        }
    }

    protected void onCancelled(Boolean paramBoolean)
    {
        super.onCancelled(paramBoolean);
        int i = this._params.length;
        for (int j = 0;; j++)
        {
            if (j >= i) {
                return;
            }
            this._params[j].onCancelled(paramBoolean.booleanValue());
        }
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
        int i = this._params.length;
        for (int j = 0;; j++)
        {
            if (j >= i) {
                return;
            }
            this._params[j].onComplete();
        }
    }
}
