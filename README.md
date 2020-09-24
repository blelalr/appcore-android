# appcore-android

## APP: aqi-app

- Kotlin (Corutinue)
- Mvvm (Livedata / ViewModel)
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
    
      implementation 'com.android.appcore:social-login:1.0.0'

Create your own develop project & Follow the instruction setup social login setting
- [Facebook developer](https://developers.facebook.com/)
- [Google console](https://console.cloud.google.com/)
- [Line developer](https://developers.line.biz/)

Add to gradle.properties 

    facebook_app_id="your_facebook_app_id"
    fb_login_protocol_scheme="your_fb_login_protocol_scheme"
    google_default_web_client_id="your_google_default_web_client_id"
    line_channel_id="your_line_channel_id"

Add to build.gradle: just add what you need.
     
    defaultConfig {
        
        if(project.findProperty("facebook_app_id") == null) {
            throw new FileNotFoundException("facebook_app_id not found in gradle.properties")
        } else {
            resValue("string", "facebook_app_id", (project.findProperty("facebook_app_id")))
        }
        if(project.findProperty("fb_login_protocol_scheme") == null) {
            throw new FileNotFoundException("fb_login_protocol_scheme not found in gradle.properties")
        } else {
            resValue("string", "fb_login_protocol_scheme", (project.findProperty("fb_login_protocol_scheme")))
        }
        if(project.findProperty("google_default_web_client_id") == null) {
            throw new FileNotFoundException("google_default_web_client_id not found in gradle.properties")
        } else {
            resValue("string", "google_default_web_client_id", (project.findProperty("google_default_web_client_id")))
        }
        if(project.findProperty("line_channel_id") == null) {
            throw new FileNotFoundException("line_channel_id not found in gradle.properties")
        } else {
            resValue("string", "line_channel_id", (project.findProperty("line_channel_id")))
        }
        
    }
      
    
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
