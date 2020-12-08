# appcore-android

## APP: aqi-app
![](https://media.giphy.com/media/vJedPwjKGBZIN33ZE9/giphy.gif)

- Kotlin (Corutinue)
- MVVM (Livedata / ViewModel)
- Room (Local data storage)
- Retrofit (Network)
- Work Manager (Periodic)
- MPAndroidChart (Bar Chart)
- Material Design (Card)

## Library: Social Login (Facebook, Google, Line)
![](https://media.giphy.com/media/IZKCWonSzrHnrmaGuC/giphy.gif)

How to implement library
- Step.1 Add maven repository url in your root build.gradle:
    	
      allprojects {
        repositories {
          ...
          maven { url  "https://dl.bintray.com/blelalr/repo" }
        }
      }

- Step 2. Add the dependency
    
      implementation 'com.android.appcore:social-login:1.0.2'

Create your own develop project & Follow the instruction setup social login setting
- [Facebook developer](https://developers.facebook.com/)
- [Google console](https://console.cloud.google.com/)
- [Line developer](https://developers.line.biz/)

Add to String.xml (just add what you need.)

    <string name="facebook_app_id">XXXXXXXXXXX</string>
    <string name="fb_login_protocol_scheme">XXXXXXXXXXX</string>
    <string name="google_default_web_client_id">XXXXXXXXXXX</string>
    <string name="line_channel_id">XXXXXXXXXXX</string>  
    
How to use
- Login 
      
        //Use this method when click login button.
        Auth.login(activity, platformType)
        
        //Copy following code to your Activity
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
          super.onActivityResult( requestCode, resultCode, data );
          Auth.onActivityResult(requestCode, resultCode, data)
        }
        
- Logout
    
        Auth.logout()
        
- Observe UserInfo for UI change when UserInfo isLogin change

        Auth.getUserInfo().observe( this, userInfo -> {
            if(userInfo.isLogin()) {
                Log.d("Social Login", "User Login." + userInfo.getId()); //setLoginView();
            } else {
                Log.d("Social Login", "User Logout."); //setLogoutView();
            }
         
        });

- UserInfo Model
        
      boolean isLogin;
      PlatformType platformType;
      String mId;
      String mName;
      String mEmail;
      String mToken;
