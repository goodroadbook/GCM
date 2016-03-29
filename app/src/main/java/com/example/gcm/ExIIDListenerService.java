package com.example.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by namjinha on 2016-02-03.
 */
public class ExIIDListenerService extends InstanceIDListenerService
{
    @Override
    public void onTokenRefresh()
    {
        Intent intent = new Intent(this, TokenIntentService.class);
        intent.putExtra(TokenIntentService.COMMAND, TokenIntentService.CMD_REG);
        startService(intent);
    }
}
