package com.example.gcm.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBManager
{
    private final String DB_NAME		= "db_test.db";
    private final int DB_VERSION	= 1;

    private Context mContext			= null;
    private OpenHelper mOpener		= null;
    private SQLiteDatabase mDbController	= null;

    private class OpenHelper extends SQLiteOpenHelper
    {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase aDb)
        {
            aDb.execSQL(DBSqlData.SQL_DB_CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
        {
        }
    }

    public DBManager(Context aContext)
    {
        this.mContext = aContext;
        this.mOpener = new OpenHelper(mContext, DB_NAME,
        null, DB_VERSION);
    }

    public void dbOpen()
    {
        this.mDbController = mOpener.getWritableDatabase();
    }

    public void dbClose()
    {
        this.mDbController.close();
    }

    public void insertData(String aSql, PushMsgData aCData)
    {
        String[] sqlData = aCData.getCDataArray();
        this.mDbController.execSQL(aSql, sqlData);
    }

    public void selectAll(String aSql, ArrayList<PushMsgData> aCDataList)
    {
        Cursor results = this.mDbController.rawQuery(aSql, null);
        results.moveToFirst();

        while(!results.isAfterLast())
        {
            PushMsgData cData = new PushMsgData(
                    results.getString(1));
            aCDataList.add(cData);
            results.moveToNext();
        }
        results.close();
    }
}
