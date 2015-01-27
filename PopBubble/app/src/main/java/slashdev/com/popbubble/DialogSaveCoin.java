package slashdev.com.popbubble;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.slashbase.myinterface.IClose;

import com.slashbase.util.UtilLib;
import slashdev.com.popbubble.database.Database;
import slashdev.com.popbubble.object.Coin;

public class DialogSaveCoin
        extends Dialog
{
    public DialogSaveCoin(final Context paramContext, final Database paramDatabase, final int paramInt, final IClose paramIClose)
    {
        super(paramContext);
        setCancelable(false);
        setTitle("Save name");
        setContentView(2130903077);
        final EditText localEditText = (EditText)findViewById(2131034198);
        ((Button)findViewById(2131034199)).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                String str = localEditText.getText().toString();
                if (str.length() == 0) {
                    str = "Player";
                }
                paramDatabase.addCOIN(new Coin(str, paramInt));
                UtilLib.showToast(paramContext, "Save success");
                DialogSaveCoin.this.dismiss();
                if (paramIClose != null) {
                    paramIClose.onClose();
                }
            }
        });
    }
}
