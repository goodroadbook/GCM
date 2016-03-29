package com.example.gcm;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gcm.db.PushMsgData;

import java.util.List;

public class ListArrayAdapter extends ArrayAdapter<PushMsgData>
{
    private ViewHolder mViewHolder = null;
    private LayoutInflater mInflater	= null;

    class ViewHolder
    {
        public TextView message = null;
    }

    public ListArrayAdapter(Context aContext, int aResource,
                            List<PushMsgData> aCData)
    {
        super(aContext, aResource, aCData);

        this.mInflater = (LayoutInflater)
                aContext.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertview,
                        ViewGroup parent)
    {
        View v = convertview;

        if(v == null)
        {
            mViewHolder = new ViewHolder();
            v = mInflater.inflate(R.layout.list_item, null);
            mViewHolder.message = (TextView)
                    v.findViewById(R.id.nametxt);

            v.setTag(mViewHolder);
        }
        else
        {
            mViewHolder = (ViewHolder)v.getTag();
        }

        mViewHolder.message.setText(getItem(position).getMessage());

        return v;
    }

    @Override
    public int getCount()
    {
        return super.getCount();
    }

    @Override
    public PushMsgData getItem(int position)
    {
        return super.getItem(position);
    }
}


