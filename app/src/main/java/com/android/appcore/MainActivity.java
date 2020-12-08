package com.android.appcore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sociallogin.Auth;
import com.example.sociallogin.PlatformType;
import com.example.sociallogin.model.UserInfo;

public class MainActivity extends AppCompatActivity {

    private TextView socialToken;
    private TextView userId;
    private TextView userName;
    private TextView userEmail;
    private Button facebookLoginButton;
    private Button googleLoginButton;
    private Button lineLoginButton;
    private Button logoutButton;
    private LinearLayout loginBtnsRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        socialToken = findViewById(R.id.tv_token);
        userId = findViewById(R.id.tv_user_id);
        userName = findViewById(R.id.tv_name);
        userEmail = findViewById(R.id.tv_email);
        facebookLoginButton = findViewById(R.id.btn_facebook_login);
        googleLoginButton = findViewById(R.id.btn_google_login);
        lineLoginButton = findViewById(R.id.btn_line_login);
        logoutButton = findViewById(R.id.btn_logout);
        loginBtnsRootView = findViewById(R.id.login_btns_root_view);

        facebookLoginButton.setOnClickListener(view -> {
            try {
                Auth.login(MainActivity.this , PlatformType.Facebook);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });




        googleLoginButton.setOnClickListener(view -> {
            try {
                Auth.login(MainActivity.this, PlatformType.Google);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        lineLoginButton.setOnClickListener(view -> {
            try {
                Auth.login(MainActivity.this, PlatformType.Line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        logoutButton.setOnClickListener(view -> {
            try {
                Auth.logout();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        initObserver();

//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.main_container, ListFragment.newInstance(null));
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        fragmentTransaction.commit();

    }

    private void initObserver() {
        Auth.getUserInfo().observe(this, userInfo -> {
            if(userInfo.isLogin()) {
                loginBtnsRootView.setVisibility(View.GONE);
                logoutButton.setVisibility(View.VISIBLE);
                logoutButton.setText(userInfo.getPlatformType().name() + "登出");
                socialToken.setText(userInfo.getToken());
                userId.setText(userInfo.getId());
                userName.setText(userInfo.getName());
                userEmail.setText(userInfo.getmEmail());
            } else {
                loginBtnsRootView.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.GONE);
                socialToken.setText("");
                userId.setText("");
                userName.setText("");
                userEmail.setText("");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Auth.onActivityResult(requestCode, resultCode, data);
    }
}
