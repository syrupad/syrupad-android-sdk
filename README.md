#Syrup Ad SDK Developer Guide

### 준비사항
1.	Syrup Ad SDK를 통해 광고를 수신하기 위해서는 Android 2.3 이상의 Android 개발 환경이 필요합니다. 자세한 내용은 http://developer.android.com 를 참고하세요.

2.	광고 수신과 정산 목적의 Client ID가 필요합니다. http://www.syrupad.co.kr 에서 매체 등록 후 Client ID를 발급 받으세요.

### Step I.  Syrup Ad SDK integration
1.	Syrup Ad 공식사이트(http://www.syrupad.co.kr) 에서 SyrupAdSDK.jar 파일을 다운로드 받습니다. 
2.	다운받은 SDK 내의 SDK.jar 파일을 자신의 Android 프로젝트에 libs 폴더에 복사합니다.
3.	Package Explorer(패키지 탐색기) 탭에서 프로젝트를 마우스 우측 버튼으로 클릭하여 Properties(속성)를 선택합니다.
4.	좌측 패널에서 Java Build Path를 선택합니다.
5.	기본 창에서 Libraries(라이브러리) 탭을 선택합니다.
6.	Add JARs...를 클릭합니다.
7.	이전에 libs 디렉터리에 복사했던 SyrupAdSDK.jar 파일을 선택합니다.
8.	OK를 클릭하여 파일을 Android 프로젝트에 추가합니다. 

### Step II. Google Play services SDK integration
SyrupAd SDK 3.10.0 이후 버전부터는 Google Play services SDK를 참조하여 Google Advertising ID를 추출 하고 있습니다. 아래 가이드를 준수하지 않은 경우 광고 수신이 불가합니다. 

1.  아래 위치의 Google Play services library project를 자신의 workspace에 import합니다. <android-sdk>/extras/google/google_play_services/libproject/google-play-services_lib/
2.	개발중인 프로젝트의 Properties - Android 설정으로 진입합니다. 
3.	하단에 Library 탭의 Add.. 버튼을 누릅니다. 
4.	1번 단계에 import한 library project를 선택 후 Ok 버튼을 누릅니다. 
5.	아래 그림과 같이 체크표시가 보이면 OK버튼을 누르고 완료합니다.
6.	추가적인 manifest설정은 Step III 에서 설명합니다. 

###Step III. Manifest Setting
Syrup Ad SDK를 적용하기 위해서는 AndroidManifest.xml에 1개의 Activity와 5개의 Permission을 적용하여야 합니다.

A.	Activity설정 
광고 수신을 위해서는 아래의 AdActivity 를 필수로 선언하여야 합니다. 

 ```xml
<activity
android:name="com.skplanet.tad.AdActivity"
android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode
|screenSize|smallestScreenSize"
android:label="Ad Activity"
android:theme="@android:style/Theme.NoTitleBar" />
```

빌드 예정인 Android API Level에 따라 configChanges 옵션을 선택적으로 적용합니다. keyboard, keyboardHidden, orientation, screenLayout, uiMode는 필수이고 API Level 13 이상은 screenSize, smallestScreenSize 속성을 선언하여야 합니다.
또한, Syrup Ad 광고를 배치하고자 하는 Activity 의 configChanges 옵션을 Project 빌드 SDK버전에 따라 아래와 같이 추가 해주어야 합니다.
   ```xml
  <activity
android:name=".YourActivity"
android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode
|screenSize|smallestScreenSize"/>
  ```

B.	Permission설정
광고 수신을 위해서는 아래 5개의 Permission을 선언하여야 합니다. 이중 INTERNET, WRITE_EXTERNAL_STORAGE Permission은 필수 항목입니다. 사용하시는 target SdkVersion =19 이상인 경우 READ_EXTERNAL_STORAGE Permission을 추가합니다.
  ```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
  ```
C.	Google Play services SDK 설정
Google Advertising ID 추출을 위해 아래 코드를 추가합니다. 
 ```xml
 <meta-data android:name="com.google.android.gms.version"
           android:value="@integer/google_play_services_version" />
 ```
D.	BroadcastReceiver 설정
광고 수신을 위해서는 아래의 BroadcastReceiver 를 필수로 선언하여야 합니다. 
```xml
<receiver android:name="com.skplanet.tad.SyrupAdReceiver" >
<intent-filter>
<action android:name="com.skplanet.syrupad.action.SAID_CHANGED" />
</intent-filter>
</receiver>
```
AndroidMenifest.xml Sample Code
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.skplanet.tad.showcase"
    android:versionCode="0"
    android:versionName="1.0" >

    <uses-sdk
        android:minSdkVersion="9"
        android:targetSdkVersion="15" />

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

    <application
        android:icon="@drawable/appicon"
        android:label="@string/app_name" >
        <meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" />

<activity
            android:name=".YourActivity"
            android:configChanges="keyboard|keyboardHidden
|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
            android:label="@string/app_name"
            android:theme="@android:style/Theme.NoTitleBar" />
        <activity
            android:name="com.skplanet.tad.AdActivity"
            android:configChanges="keyboard|keyboardHidden
|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
            android:label="Ad Activity"
            android:theme="@android:style/Theme.NoTitleBar" />

        <receiver android:name="com.skplanet.tad.SyrupAdReceiver" >
            <intent-filter>
                <action android:name="com.skplanet.syrupad.action.SAID_CHANGED" />
            </intent-filter>
        </receiver>
    </application>
</manifest>
```

###Step IⅤ. 광고 View 생성 및 
Syrup Ad 광고는 크게 배너 광고(inline), 삽입형 광고(interstitial) 그리고 플로팅(floating) 광고로 구분할 수 있습니다.
개발자는 자신이 개발 중인 App내 원하는 크기의 광고를 수신하기 위해 아래 표에 정의된 Slot 중 한 개를 명시하여 광고를 요청하여야 합니다.
배너 광고

| Size (WxH) | Description         |AdSlot Constant  | AdSlot Value|
| ---------- | :------------------ | :-------------: | :---------: |
| 320x50     | Standard Banner     | BANNER          |2            |
| 300x250    | IAB Medium Rectangle| MEDIUM_RECTANGLE|5            |
| 320x100    | Large Banner        | LARGE_BANNER    |6            |

삽입형 광고
| Size (WxH) | Description         |AdSlot Constant  | AdSlot Value|
| ---------- | :------------------ | :-------------: | :---------: |
| fullscreen | Interstitial        | INTERSTITIAL    |3            |

플로팅 광고
| Size (WxH) | Description         |AdSlot Constant  | AdSlot Value|
| ---------- | :------------------ | :-------------: | :---------: |
| 100x100    | Floating            | FLOATING        |103          |

###배너 광고
Standard 배너, IAB Medium Rectangle배너, Large 배너는 AdView 클래스를 활용하여 App 개발자가 원하는 위치, 원하는 설정으로 광고View를 적용 할 수 있습니다. 배너 광고 적용에는 xml적용 방식과 java 적용 방식이 있습니다.

#####AdView
A.	xml적용 방식
개발자는 자신이 개발중인 layout xml상에 AdView를 배치 할 수 있습니다.
```xml
<com.skplanet.tad.AdView
android:layout_width="match_parent"
android:layout_height="wrap_content"
clientId="AXT002001" 
slotNo = "2" />
```
AdVeiw객체를 생성할 때 clientId, slotNo의 값은 필수 항목입니다. 그 외 개발자 편의를 위하여 animationType, refreshInterval 등을 제공하고 있습니다.

```xml
<com.skplanet.tad.AdView
android:layout_width="match_parent"
android:layout_height="wrap_content"
clientId="AX0000024"
slotNo = "2"
animationType = "SLIDE_FROM_RIGHT_TO_LEFT"
refreshInterval="30"
useBackFill = "false" />
```
animationType 속성은 배너가 나타날 때 보여지는 animation효과에 대한 설정 할 수 있습니다. 아래 Animation type 별 효과 표를 보고 적합한 animation 효과를 설정하세요. 
refreshInterval 속성은 광고를 갱신하는 주기를 설정 할 수 있습니다. 기본 20초이며 최소 15초에서 최대 60초까지 설정 가능합니다. 또한 0초로 입력한 경우에는 수동모드로 동작하며 광고를 수신합니다. 
useBackFill 속성을 true로 설정하면 각 광고마다 광고주가 설정한 배경색이 그려지고 false 인 경우 투명(0x00000000)으로 나타납니다.
*	xml방식으로 적용을 하는 경우에도 destroy시점에 AdView.destroy()를 호출하여야 합니다. 

| Type       | Description         |
| ---------- | :------------------ |
| NONE                   |효과 없음|
|FADE	                   |Fade 효과|
|ZOOM	                   |Zoom 효과|
|ROTATE                  |회전 효과|
|SLIDE_FROM_RIGHT_TO_LEFT|오른쪽에서 왼쪽으로 나타남|
|SLIDE_FROM_LEFT_TO_RIGHT|왼쪽에서 오른쪽으로 나타남|
|SLIDE_FROM_BOTTOM_TO_TOP|아래에서 위쪽으로 나타남|
|SLIDE_FROM_TOP_TO_BOTTOM|위쪽에서 아래쪽으로 나타남|
|FLIP_HORIZONTAL         |가로로 접기 효과|
|FLIP_VERTICAL           |세로로 접기 효과|
|ROTATE3D_180_HORIZONTAL |가로로 3D 회전 효과|
|ROTATE3D_180_VERTICAL   |세로로 3D 회전 효과|

B.	java 적용 방식 
java code로 적용하는 방식은 xml 적용 방식에 비해 보다 세밀한 제어가 가능합니다. java적용 방식에서는 loadAd()를 호출하여 직접 광고를 요청 하여야 합니다. 광고가 정상적으로 수신되면 자동으로 화면에 노출됩니다.
loadAd()의 매개변수에는 일반적으로 null을 지정합니다.

```java
// Context를 parameter로 AdView의 객체를 생성합니다.
AdView adView = new AdView(this);
 
// 준비 과정에 발급받은 ClientId를 직접 입력합니다
adView.setClientId("AXT002001");
 
// 원하는 크기의 Slot을 설정합니다.
adView.setSlotNo(AdSlot.BANNER);
 
// 새로운 받은 광고가 Display 되는 Animation 효과를 설정합니다.
adView.setAnimationType(AnimationType.ROTATE3D_180_VERTICAL);
 
// 새로운 광고를 요청하는 주기를 입력합니다. 최소값은 15, 최대값은 60입니다
// 0으로 설정한 경우 1번의 광고만 수신합니다. 
adView.setRefreshInterval(20);
 
// 광고View의 Background의 사용 유무를 설정합니다.
adView.setUseBackFill(true);
  
// 광고를 요청합니다.
try {
adView.loadAd(null);
} catch (Exception e) {
e.printStackTrace();
}
```
매체에서 자체적으로 mediation을 구현한 경우 refreshInterval을 0으로 설정하고 원하는 시점 마다 loadAd()를 호출하세요. 짧은 시간 내에 loadAd()를 과도하게 반복 호출하면 반려 사유가 됩니다. 
Syrup Ad광고가 노출된 상태에서 다른 광고 SDK로 전환하는 경우에는 stopAd()를 호출하여야 합니다. 그리고 다시 Syrup Ad광고로 전환하는 경우에는 새로운 AdView 객체를 생성할 필요 없이 loadAd()를 호출하면 새로운 광고를 수신합니다. 참고로, stopAd()를 호출하면 SDK 내부적으로 AdView의 상위 레이아웃으로부터 removeView를 자동 처리합니다.
```java
// 광고 수신을 중단합니다.
adView.stopAd();
```
Activity를 종료하는 시점 혹은 더 이상 Syrup Ad광고를 사용하지 않는 시점에는 destroyAd()를 호출하여 사용중인 자원을 모두 반환할 수 있도록 하여야 합니다. destroyAd()가 호출되면 해당 객체는 다시 사용할 수 없습니다.
```java
@Override
protected void onDestroy() {
    if (adView != null) {
        adView.destroyAd();
    }
    super.onDestroy();
}
```
Banner Life-cycle
![Image of banner life-cycle](http://syrupad.github.io/syrupad-android-sdk/readme-screenshots/adview_lifecycle.png)

