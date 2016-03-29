package com.example.gcm.db;

public class PushMsgData
{
    private String mMessage		= null;

    public PushMsgData(String aMessage)
    {
        this.mMessage = aMessage;
    }

    public String getMessage()
    {
        return this.mMessage;
    }

    public String[] getCDataArray()
    {
        String[] cData = {
                    this.mMessage,
        };
        return cData;
    }
}
