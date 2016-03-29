package com.example.gcm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.gcm.db.DBManager;
import com.example.gcm.db.DBSqlData;
import com.example.gcm.db.PushMsgData;

import java.util.ArrayList;

public class PushMsgSelectActivity extends AppCompatActivity

{
    private ListView mListViewLayout			= null;
    private ListArrayAdapter mListArrayAdapter	= null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_select);

        ArrayList<PushMsgData> cDataList = new ArrayList<PushMsgData>();
        getMessageDBData(cDataList);

        mListArrayAdapter = new ListArrayAdapter(this,
                                                    R.id.list_item,
                                                    cDataList);
        mListViewLayout = (ListView)
        this.findViewById(R.id.listView);
        mListViewLayout.setAdapter(mListArrayAdapter);
    }

    private void getMessageDBData(ArrayList<PushMsgData> aCDataList)
    {
        DBManager dbMgr = new DBManager(this);
        dbMgr.dbOpen();
        dbMgr.selectAll(DBSqlData.SQL_DB_SELECT_ALL, aCDataList);
        dbMgr.dbClose();
    }
}