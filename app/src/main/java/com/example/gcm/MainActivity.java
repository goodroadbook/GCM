package com.example.gcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버튼을 제공하여 사용자가 클릭하면 GCM 등록을 하도록 한다.
        Button btnGCMRegister = (Button) findViewById(R.id.btnregid);
        btnGCMRegister.setOnClickListener(this);

        // 버튼을 제공하여 사용자가 클릭하면 GCM 등록을 하도록 한다.
        Button btnGCMRemove = (Button) findViewById(R.id.btnremove);
        btnGCMRemove.setOnClickListener(this);

        Button btnSelect = (Button) findViewById(R.id.btnselect);
        btnSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View aView)
    {
        Intent intent = null;
        String token = getPreference();

        switch (aView.getId())
        {
            case R.id.btnregid:
                // Preference GCM ID를 발급 받은 경우는 사용자에게 이미 등록되었다는
                // 메시지를 보여주도록 한다.
                if (null != token && token.length() != 0)
                {
                    Toast.makeText(MainActivity.this,
                            "이미 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                intent = new Intent(this, TokenIntentService.class);
                intent.putExtra(TokenIntentService.COMMAND, TokenIntentService.CMD_REG);
                startService(intent);
                break;
            case R.id.btnremove:
                if (null == token || token.length() == 0)
                {
                    Toast.makeText(MainActivity.this,
                            "등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent = new Intent(this, TokenIntentService.class);
                intent.putExtra(TokenIntentService.COMMAND, TokenIntentService.CMD_DEL);
                startService(intent);
                break;
            case R.id.btnselect:
                intent = new Intent(this, PushMsgSelectActivity.class);
                startActivity(intent);
                break;
            default:
                break;
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
}


