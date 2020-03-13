# appcore-android
## Setting
How to implement library
- Step.1 Add maven repository url in your root build.gradle:
    	
      allprojects {
        repositories {
          ...
          maven { url  "https://blelalr.bintray.com/maven" }
        }
      }

- Step 2. Add the dependency
    
      implementation 'com.android.appcore:social-login:1.0.0'

Create your own develop project
- [Facebook developer](https://developers.facebook.com/)
- [Google console](https://console.cloud.google.com/)
- [Line developer](https://developers.line.biz/)

Add to gradle.properties 

    facebook_app_id="your_facebook_app_id"
    fb_login_protocol_scheme="your_fb_login_protocol_scheme"
    google_default_web_client_id="your_google_default_web_client_id"
    line_channel_id="your_line_channel_id"

Add to build.gradle
     
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
        Auth.login(FragmentActivity activity, PlatformType platformType)
        
        //Add this method to your Activity
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
          super.onActivityResult( requestCode, resultCode, data );
          Auth.onActivityResult(int requestCode, int resultCode, Intent data)
        }
        
- Logout
    
        Auth.logout()
        
- UserInfo is MutableLiveData<UserInfo> 

        Auth.getUserInfo().observe (...)

- UserInfo Model
        
      boolean isLogin;
      PlatformType platformType;
      String mId;
      String mName;
      String mEmail;
      String mToken;
