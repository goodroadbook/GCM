package com.example.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by namjinha on 2016-02-03.
 */
public class TokenIntentService extends IntentService
{
    public static final String COMMAND = "COMMAND";
    public static final int CMD_REG = 0;
    public static final int CMD_DEL = 1;

    public static final String TOKEN_REG = "TOKEN_REG";
    public static final String TOKEN_DEL = "TOKEN_DEL";
    private final String SEVER_URL =
            "http://192.168.0.41:8080/AppServer/updatetoken.jsp";

    private String mGcmToken = null;

    public TokenIntentService()
    {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        int command = intent.getIntExtra(COMMAND, CMD_REG);

        switch(command)
        {
            case CMD_REG:
                createToken();
                sendRegTokenToServer(getReqUrl(TOKEN_REG, mGcmToken));
                break;
            case CMD_DEL:
                removeToken();
                sendRegTokenToServer(getReqUrl(TOKEN_DEL, getPreference()));
                break;
            default:
                break;
        }
    }

    /**
     * InstanceID로 IID를 만들고, 토큰 및 preference에 저장한다.
     */
    private void createToken()
    {
        InstanceID instanceID = InstanceID.getInstance(this);
        try
        {
            mGcmToken = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.i("Registration Token", mGcmToken);
            setPreference(mGcmToken);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * InstanceID로 토큰과 IID를 삭제하고 Preference 값을 초기화 한다.
     */
    private void removeToken()
    {
        try
        {
            InstanceID.getInstance(this).deleteToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            InstanceID.getInstance(this).deleteInstanceID();

            removePreference(TOKEN_REG);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 서버 URL과 전송할 정보를 붙인다.(GET 방식 사용)
     */
    private String getReqUrl(String aKey, String aToken)
    {
        return SEVER_URL + "?" + "CMD=" + aKey + "&" + aKey + "=" + aToken;
    }

    /**
     * URL과 전송할 데이터를 이용하여 HTTP로 App Server에 요청한다.
     */
    private void sendRegTokenToServer(String aUrl)
    {
        final String HTTP_REQ_METHOD = "GET";
        final int HTTP_CONN_TIMEOUT_MSEC = 20000;

        InputStream in	= null;

        try
        {
            URL url = new URL(aUrl);
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(HTTP_REQ_METHOD);
            connection.setConnectTimeout(HTTP_CONN_TIMEOUT_MSEC);
            connection.setReadTimeout(HTTP_CONN_TIMEOUT_MSEC);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode < 200 || responseCode >= 300)
            {
                return;
            }
            else
            {
                in =  connection.getInputStream();
            }
        }
        catch(Exception e)
        {
            ;
        }
        finally
        {
            try
            {
                if(null != in)
                {
                    in.close();
                }
            }
            catch(Exception e)
            {
                ;
            }
        }
    }

    /**
     * Preference 에서 저장된 토큰을 가져온다.
     */
    private String getPreference()
    {
        SharedPreferences prefdefault = PreferenceManager.
                getDefaultSharedPreferences(this);
        return prefdefault.getString(TokenIntentService.TOKEN_REG,
                null);
    }

    /**
     * 토큰 정보를 저장한다.
     */
    private void setPreference(String aToken)
    {
        SharedPreferences prefdefault =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefdefault.edit();
        edit.putString(TOKEN_REG, aToken);
        edit.commit();
    }

    /**
     * Preference의 Key를 삭제한다.
     */
    private void removePreference(String aKey)
    {
        SharedPreferences prefdefault =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefdefault.edit();
        edit.remove(aKey);
        edit.commit();
    }
}
