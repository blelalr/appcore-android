package com.example.sociallogin;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.sociallogin.Facebook.FacebookLogin;
import com.example.sociallogin.Google.GoogleLogin;
import com.example.sociallogin.Line.LineLogin;
import com.example.sociallogin.model.UserInfo;

public class Auth {
    private static BaseSocialLogin socialLogin;
    private static MutableLiveData<UserInfo> userInfo;
    public static int GOOGLE_SIGN_IN = 1000;
    public static int LINE_SIGN_IN = 1002;
    public static String TAG = Auth.class.getSimpleName();

    public static void login(FragmentActivity activity, @Nullable PlatformType platformType) throws Exception {
        switch (platformType) {
            case Google:
                if(activity.getString(R.string.google_default_web_client_id).length() == 0) {
                    throw new Error("google_default_web_client_id not found in R.string");
                } else {
                    socialLogin = new GoogleLogin(activity);
                }
                break;
            case Facebook:
                if(activity.getString(R.string.facebook_app_id).length() == 0 || activity.getString(R.string.fb_login_protocol_scheme).length() == 0 ) {
                    throw new Error("facebook_app_id or fb_login_protocol_scheme not found in R.string");
                } else {
                    socialLogin = new FacebookLogin(activity);
                }
                break;
            case Line:
                if(activity.getString(R.string.line_channel_id).length() == 0 ) {
                    throw new Error("line_channel_id not found in R.string");
                } else {
                    socialLogin = new LineLogin(activity);
                }
                break;
        }

        socialLogin.login();
    }

    public static void logout() {
        socialLogin.logout(true);
    }


    public static void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        socialLogin.onActivityResult(requestCode, resultCode, data);
    }

    public static MutableLiveData<UserInfo> getUserInfo() {
        if(userInfo == null ) {
            userInfo = new MutableLiveData<>();
        }
        return userInfo;
    }

    public static void setUserInfo(UserInfo mUserInfo) {
        userInfo.setValue(mUserInfo);
    }

}
