package slashdev.com.popbubble.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.slashbase.mylog.Log;
import java.util.ArrayList;

import slashdev.com.popbubble.object.Coin;

public class Database
        extends SQLiteOpenHelper
{
    private static final String NAME_DB = "popwhat.db";
    private static final int VERSION = 1;
    public final String COIN = "COIN";
    public final String ID = "ID";
    public final String NAME = "NAME";
    public final String TABLE_COIN = "TABLE_COIN";
    boolean isAddDataDefault = true;
    int limitRow = 15;
    private SQLiteDatabase mSQLiteDatabase = null;

    public Database(Context paramContext)
    {
        super(paramContext, NAME_DB, null, 1);
    }

    private void updateCOIN(Coin paramCoin, int paramInt)
    {
        if (this.mSQLiteDatabase.isOpen())
        {
            ContentValues localContentValues = new ContentValues();
            localContentValues.put("NAME", paramCoin.getName());
            localContentValues.put("COIN", Integer.valueOf(paramCoin.getCoin()));
            this.mSQLiteDatabase.update("TABLE_COIN", localContentValues, "ID=" + paramInt, null);
        }
    }

    public void addCOIN(Coin paramCoin)
    {
        int i;
        if (this.mSQLiteDatabase.isOpen())
        {
            i = checkIsInsert(paramCoin);
            if (i == -10)
            {
                ContentValues localContentValues = new ContentValues();
                localContentValues.put("NAME", paramCoin.getName());
                localContentValues.put("COIN", Integer.valueOf(paramCoin.getCoin()));
                this.mSQLiteDatabase.insert("TABLE_COIN", null, localContentValues);
            }
        }
        else
        {
            return;
        }
        updateCOIN(paramCoin, i);
    }

    public void addDataDefault()
    {
        ArrayList localArrayList = new ArrayList();
        if (!this.isAddDataDefault)
        {
            localArrayList.add("Player 1");
            localArrayList.add("Player 2");
            localArrayList.add("Player 3");
            localArrayList.add("Player 4");
            localArrayList.add("Player 5");
            localArrayList.add("Player 6");
            localArrayList.add("Player 7");
            localArrayList.add("Player 8");
            localArrayList.add("Player 9");
            localArrayList.add("Player 10");
            localArrayList.add("Player 11");
            localArrayList.add("Player 12");
            localArrayList.add("Player 13");
            localArrayList.add("Player 14");
            localArrayList.add("Player 15");
        }
        for (int i = 0;; i++)
        {
            if (i >= this.limitRow) {
                return;
            }
            addCOIN(new Coin((String)localArrayList.get(i), 0 * (i + 1)));
        }
    }

    public int checkIsInsert(Coin paramCoin)
    {
        if (this.mSQLiteDatabase.isOpen())
        {
            Cursor localCursor = getCursorQuery("TABLE_COIN", null, null, null, null, null, "COIN DESC");
            int j = 0;
            // TODO: check this logic
            if (localCursor.getCount() < this.limitRow)
            {
                localCursor.close();
                j = -10;
            } else {
                localCursor.moveToLast();
                int i = localCursor.getInt(localCursor.getColumnIndex("COIN"));
                if (paramCoin.getCoin() > i) {
                    j = localCursor.getInt(localCursor.getColumnIndex("ID"));
                }

                localCursor.close();
            }
            return j;
        }
        return -1;
    }

    public void closeDatabase()
    {
        close();
    }

    public void execSQL(String paramString)
    {
        execSQL(paramString);
    }

    public Cursor getCursorQuery(String paramString1, String[] paramArrayOfString1, String paramString2, String[] paramArrayOfString2, String paramString3, String paramString4, String paramString5)
    {
        return this.mSQLiteDatabase.query(paramString1, paramArrayOfString1, paramString2, paramArrayOfString2, paramString3, paramString4, paramString5);
    }

    public ArrayList<Coin> getListCOIN()
    {
        ArrayList localArrayList = new ArrayList();
        Cursor localCursor;
        if (this.mSQLiteDatabase.isOpen()) {
            localCursor = getCursorQuery("TABLE_COIN", null, null, null, null, null, "COIN DESC");
            while (localCursor.moveToNext()) {
                int i = localCursor.getInt(localCursor.getColumnIndex("COIN"));
                localArrayList.add(new Coin(localCursor.getString(localCursor.getColumnIndex("NAME")), i));
            }
            localCursor.close();
        }
        return localArrayList;
    }

    public void logList(int paramInt1, int paramInt2)
    {
        ArrayList localArrayList = getListCOIN();
        for (int i = 0;; i++)
        {
            if (i >= localArrayList.size()) {
                return;
            }
            Coin localCoin = (Coin)localArrayList.get(i);
            Log.e("", "Name " + localCoin.getName() + " - COIN = " + localCoin.getCoin());
        }
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
        paramSQLiteDatabase.execSQL("CREATE TABLE TABLE_COIN ( ID INTEGER PRIMARY KEY ,NAME TEXT NOT NULL,COIN INTEGER NOT NULL);");
        Log.e("", "Database onCreate");
        this.isAddDataDefault = false;
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS TABLE_COIN");
        onCreate(paramSQLiteDatabase);
        Log.e("", "Database onUpgrade");
    }

    public void openDatabase()
    {
        this.mSQLiteDatabase = getWritableDatabase();
        addDataDefault();
    }
}
