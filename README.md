#Syrup Ad Android SDK Guide

### 준비사항
1.	Syrup Ad SDK를 통해 광고를 수신하기 위해서는 Android 2.3 이상의 Android 개발 환경이 필요합니다. 자세한 내용은 http://developer.android.com 를 참고하세요.

2.	광고 수신과 정산 목적의 Client ID가 필요합니다. http://www.syrupad.co.kr 에서 매체 등록 후 Client ID를 발급 받으세요.

### Step I.  Syrup Ad SDK integration

Syrup Ad 공식사이트(http://www.syrupad.co.kr) 에서 SyrupAdSDK.jar 파일을 다운로드 받습니다.

A.	Android studio

1. Android project의 Open Module Settings 메뉴를 선택합니다. 
2. 왼쪽 상단에 + 버튼을 눌러 import .JAR/.AAR package를 선택합니다. 
3. 다운로드 받은 SDK를 선택합니다.
4. Application Modules에 Dependencies에 SDK모듈을 추가합니다. 
5. 아래와 같이 dependencies가 추가되었는지 확인합니다. 

```xml
apply plugin: 'com.android.application'
    ...

    dependencies {
        compile project(':SyrupAdSdk_3.x.x')
    }
```
B.	Eclipse

1.	다운받은 SDK 내의 SDK.jar 파일을 자신의 Android 프로젝트에 libs 폴더에 복사합니다.
2.	Package Explorer(패키지 탐색기) 탭에서 프로젝트를 마우스 우측 버튼으로 클릭하여 Properties(속성)를 선택합니다.
3.	좌측 패널에서 Java Build Path를 선택합니다.
4.	기본 창에서 Libraries(라이브러리) 탭을 선택합니다.
5.	Add JARs...를 클릭합니다.
6.	이전에 libs 디렉터리에 복사했던 SyrupAdSDK.jar 파일을 선택합니다.
7.	OK를 클릭하여 파일을 Android 프로젝트에 추가합니다.

### Step II. Google Play services SDK integration

SyrupAd SDK 3.10.0 이후 버전부터는 Google Play services SDK를 참조하여 Google Advertising ID를 추출 하고 있습니다. 아래 가이드를 준수하지 않은 경우 광고 수신이 불가합니다.

A.	Android studio

gradle 파일내 dependencies에 아래 구문을 추가합니다.
```xml
apply plugin: 'com.android.application'
    ...

    dependencies {
        compile 'com.google.android.gms:play-services:8.4.0'
    }
```
자세한 내용은 안드로이드 개발자 가이드(https://developers.google.com/android/guides/setup#add_google_play_services_to_your_project)를 참고하세요.

B.	Eclipse

1.  아래 위치의 Google Play services library project를 자신의 workspace에 import합니다.<android-sdk>/extras/google/google_play_services/libproject/google-play-services_lib/
2.  개발중인 프로젝트의 Properties - Android 설정으로 진입합니다.
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
android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
android:label="Ad Activity"
android:theme="@android:style/Theme.NoTitleBar" />
```
빌드 예정인 Android API Level에 따라 configChanges 옵션을 선택적으로 적용합니다. keyboard, keyboardHidden, orientation, screenLayout, uiMode는 필수이고 API Level 13 이상은 screenSize, smallestScreenSize 속성을 선언하여야 합니다.
또한, Syrup Ad 광고를 배치하고자 하는 Activity 의 configChanges 옵션을 Project 빌드 SDK버전에 따라 아래와 같이 추가 해주어야 합니다.
```xml
<activity
android:name=".YourActivity"
android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"/>
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
C.	Google Play services SDK 설정(android studio 환경에서는 제외)
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
            android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
            android:label="@string/app_name"
            android:theme="@android:style/Theme.NoTitleBar" />
        <activity
            android:name="com.skplanet.tad.AdActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
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

###Step IⅤ. 광고 View 생성 및 적용
Syrup Ad 광고는 크게 배너 광고(inline), 삽입형 광고(interstitial) 그리고 플로팅(floating) 광고로 구분할 수 있습니다.
개발자는 자신이 개발 중인 App내 원하는 크기의 광고를 수신하기 위해 아래 표에 정의된 Slot 중 한 개를 명시하여 광고를 요청하여야 합니다.

배너 광고

| Size (WxH) | Description         |AdSlot Constant  | AdSlot Value|
| ---------- | :------------------ | :-------------  | :---------: |
| 320x50     | Standard Banner     | BANNER          |2            |
| 300x250    | IAB Medium Rectangle| MEDIUM_RECTANGLE|5            |
| 320x100    | Large Banner        | LARGE_BANNER    |6            |

삽입형 광고

| Size (WxH) | Description         |AdSlot Constant  | AdSlot Value|
| ---------- | :------------------ | :-------------  | :---------: |
| fullscreen | Interstitial        | INTERSTITIAL    |3            |

플로팅 광고

| Size (WxH) | Description         |AdSlot Constant  | AdSlot Value|
| ---------- | :------------------ | :-------------  | :---------: |
| 100x100    | Floating            | FLOATING        |103          |

네이티브광고

| Size (WxH) | Description         |AdSlot Constant  | AdSlot Value|
| ---------- | :------------------ | :-------------  | :---------: |
|    n/a     | Native              | NATIVE          |7            |


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
| :--------- | :------------------ |
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
#####Banner Life-cycle
![Image of banner life-cycle](http://syrupad.github.io/syrupad-android-sdk/readme-screenshots/adview_lifecycle.png)
매체의 특성에 맞게 광고를 적용 할 수 있으며 일반적인 적용은 위 그림의 life-cycle을 갖습니다.
  AdListener
Syrup Ad SDK 에서는 개발자에게 광고의 상태를 상세히 알려주고 있습니다. SDK에서 제공하는 AdListener를 이용하면 광고의 수신 및 동작 상태 등의 정보를 알 수 있습니다. AdListener는 setListener() method 호출이 필수적이며 xml상에서 선언만 하는 경우 AdListener를 이용 할 수 없습니다.
단, AdListener는 광고 요청 수신에 필수 항목은 아닙니다.
적용 방식은 아래와 같이 AdListener를 구현하고 AdView 객체에 setListener 메소드를 통해 적용 할 수 있습니다. 적용의 예는 아래와 같습니다.
```java
// AdView 객체에 아래와 같이 Listener를 등록해야만 AdView의 상태를 알 수 있습니다.
adView.setListener(mAdListener);

//AdListener 구현 부 예
private AdListener mAdListener = new AdListener() {

// 수신한 광고를 로딩 할 때 호출이 됩니다.
public void onAdWillLoad() { }

// 광고의 로딩이 완료되었을 때 호출이 됩니다.
public void onAdLoaded() { }

// 사용자에 의해 전체 확장이 일어날 때 호출이 됩니다.
public void onAdExpanded() { }

// 사용자에 의해 전체 확장 화면이 닫힐 때 호출이 됩니다.
public void onAdExpandClosed() { }

// 사용자에 의해 부분 확장이 일어날 때 호출이 됩니다.
public void onAdResized() { }

// 사용자에 의해 부분 확장 화면이 닫힐 때 호출이 됩니다.
public void onAdResizeClosed() { }

// 광고 수신, 로드 과정에서 실패 시 호출되고 자세한 내용은 errorCode를 참조하세요
public void onAdFailed(ErrorCode errorCode) { }

// 광고가 노출되는 애플리케이션 상에 새로운 화면으로 가려질 때에 호출됩니다.
public void onAdPresentScreen () { }

// 광고가 노출되는 애플리케이션 상에 가려졌던 화면이 사라질 때에 호출됩니다.
public void onAdDismissScreen () { }

// 광고의 클릭으로 인해, 다른 애플리케이션이 실행될 때에 호출됩니다.
public void onAdLeaveApplication () { }

// 광고가 클릭되었을 때 호출이 됩니다.
public void onAdClicked () { }
};
```
광고를 요청하더라도 때로는 광고 물량에 따라 광고 없음(NO_FILL) 상태가 될 수 있습니다. 광고 유무를 확인하기 위해서는 onAdFailed()에서 ErrorCode를 확인 하여야 합니다. 광고가 없는 경우에는 SDK 에서 자체적으로 재 요청하며 setRefreshInterval()으로 지정한 refreshInterval에 따라 동작합니다. refreshInterval이 30초인 경우 아래 그림과 같이 동작합니다.
![Image of banner life-cycle](http://syrupad.github.io/syrupad-android-sdk/readme-screenshots/adview_autorefresh.png)
매체에서 SDK에 의해 광고가 클릭되고 다른 App으로 이동하는 시점들을 전달하기 위해 listener에 onAdPresentScreen(), onAdLeaveApplication(), onAdDissmissScreen() 이벤트를 제공합니다.  자세한 내용은 아래 그림을 참조하세요.
![Image of banner life-cycle](http://syrupad.github.io/syrupad-android-sdk/readme-screenshots/adview_landing.png)
Mediation Tip
매체에서 다수의 SDK를 이용하여 mediation을 진행할 때 아래와 같이 진행 바랍니다.
A.	Syrup Ad 기준 스케줄링
SDK 자체적으로 refresh를 하기 때문에 이를 활용하면 쉽게 mediation을 할 수 있습니다.
![Image of banner life-cycle](http://syrupad.github.io/syrupad-android-sdk/readme-screenshots/mediation_autorefresh.png)
loadAd()의 결과로 NO_FILL 상태가 되면 타사 SDK를 동작하여 타사 광고를 노출합니다. 이후 refresh Time이 지나서 Syrup ad SDK에서 광고를 수신하면 타사 광고를 제거합니다. 이 때 loadAd() method는 한번만 호출합니다.
B.	자체 스케줄링
외부 Mediation SDK등으로 자체 스케줄링을 하는 경우 Syrup Ad SDK를 수동모드로 전환 합니다. 수동모드는 refreshInterval을 0으로 설정하여 1회씩 광고를 수신하는 모드입니다. 아래 그림과 같이 Syrup Ad 노출 시점 마다loadAd()를 호출합니다.
![Image of banner life-cycle](http://syrupad.github.io/syrupad-android-sdk/readme-screenshots/mediation_manual.png)

###삽입형 광고
#####AdInterstitial
삽입형 광고는 새로운 창이 열리며 전면에 광고가 보여지는 형태입니다.
```java
// Context를 parameter로 AdInterstitial객체를 생성합니다.
AdInterstitial adInterstitial = new AdInterstitial(this);

// 준비 과정에 발급받은 ClientId를 직접 입력합니다.
adInterstitial.setClientId("AXT003001");

// 원하는 크기의 Slot을 설정합니다.
adInterstitial.setSlotNo(AdSlot.INTERSTITIAL);

// 광고가 노출된 후 5초 동안 사용자의 반응이 없을 경우 광고 창을 자동으로 닫을 것인지를 설정합니다.
adInterstitial.setAutoCloseWhenNoInteraction(false);

// 광고가 노출된 후 랜딩 액션에 인해 다른 App으로 전환 시 광고 창을 자동으로 닫을 것인지를 설정합니다.
adInterstitial.setAutoCloseAfterLeaveApplication(false);
```
삽입형 광고는 광고의 노출시점을 개발자가 직접 제어 하여야 합니다. loadAd() 호출을 통해 광고를 수신한 후, 수신한 광고의 노출을 원하는 적절한 시점에 showAd()를 호출하는 방법을 통해 제어합니다.
setAutoCloseWhenNoInteraction 을 true로 설정한 경우 최소 광고 노출 시간 (5초) 이후에 자동으로 닫힙니다. 기본값은 false입니다.
setAutoCloseWhenNoInteraction 을 true로 설정한 경우 landing이 되어 다른 App으로 이동한 경우 광고가 자동으로 닫힙니다. 기본값은 false입니다.
광고 요청을 하는 방법은 다음과 같습니다.
```java
try {
mAdInterstitial.loadAd(null);
} catch (Exception e) {
e.printStackTrace();
}
```
loadAd()의 매개변수에는 일반적으로 null을 지정합니다.
광고를 수신하는 즉시 광고의 노출을 처리 하고자 한다면 AdInterstitialListener의 onAdLoaded()에서 showAd()를 호출하면 됩니다. 단, 수신 중인 과정에서 Progress UI 처리는 개발자가 직접 처리하여야 합니다.
광고 노출은 다음과 같습니다.
```java
// 수신한 광고가 있는지 확인합니다.
If (adInterstitial.isReady()) {
// 광고 수신 후 광고를 노출합니다.
try {
adInterstitial.showAd();
} catch (Exception e) {
e.printStackTrace();
}
}
```
Activity를 종료하는 시점 혹은 더 이상 Syrup Ad광고를 사용하지 않는 시점에는 destroyAd()를 호출하여 사용중인 자원을 모두 반환할 수 있도록 하여야 합니다. destroyAd()가 호출되면 해당 객체는 다시 사용할 수 없습니다.
```java
@Override
protected void onDestroy() {
    if (adInterstitial!= null) {
        adInterstitial.destroyAd();
    }
    super.onDestroy();
}
```
#####Interstitial Life-Cycle
![Image of banner life-cycle](http://syrupad.github.io/syrupad-android-sdk/readme-screenshots/adinterstitial_lifecycle.png)
interstitial은 일반적으로 그림과 같은 life-cycle을 갖습니다. loadAd()와 showAd()는 개발자가 원하는 시점에 호출이 가능합니다. 단 interstitial 광고 특성상 showAd()로 광고가 사용자에게 노출이 되어야 정산이 가능합니다.

#####AdInterstitialListener
AdInterstitialListener를 이용하면 광고의 수신 상태, 광고 없음 및 광고 닫힘 등의 상태를 알 수 있습니다. AdInterstitialListener는 광고 수신에 필수 항목은 아닙니다.
AdInterstitialListener의 적용 방식은 아래와 같습니다.
```java
// AdInterstitial 객체에 아래와 같이 Listener를 등록해야만 현재 상태를 알 수 있습니다.
adInterstitial.setListener(mAdInterstitialListener);

private AdInterstitialListener mAdInterstitialListener = new AdInterstitialListener(){

// 수신한 광고를 로딩 할 때 호출이 됩니다.
public void onAdWillLoad() { }

// 광고의 로딩이 완료되었을 때 호출이 됩니다.
public void onAdLoaded() { }

// 광고 수신, 로드 과정에서 실패 시 호출되고 자세한 내용은 errorCode를 참조하세요
public void onAdFailed(ErrorCode errorcode) { }

// 삽입형광고가 showAd()가 될 때 호출됩니다
public void onAdPresentScreen () { }

// 삽입형 광고가 닫힐 때 호출됩니다.
public void onAdDismissScreen () { }

// 광고의 클릭으로 인해, 다른 애플리케이션이 실행될 때에 호출됩니다.
public void onAdLeaveApplication () { }
};
```
Interstitial 에서는 광고가 노출 될 때에 onAdPresentScreen()가 발생하고 광고가 닫힐 때 onAdDissmissScreen()이 발생합니다.
![Image of banner life-cycle](http://syrupad.github.io/syrupad-android-sdk/readme-screenshots/adinterstitial_landing.png)

###플로팅 광고(floating)
플로팅 광고는 App화면 내 overlay로 배치되는 아이콘 형태의 광고입니다.
플로팅 광고는 Instance를 생성한 뒤 광고를 요청하여 수신하는 Load 과정, 수신한 광고를 노출하는 Show과정으로 동작합니다. 매체에서 원하는 시점, 원하는 위치에 플로팅 광고를 노출 할 수 있고 모든 과정을 listener를 통해 모니터링 할 수 있습니다.
#####AdFloating
Floating 객체를 생성하는 부분은 아래와 같습니다.

```java
// AdFloating 객체를 생성합니다.
AdFloating mAdFloating = new AdFloating(Context);

// AdFloating 상태를 모니터링 할 listner를 등록합니다. listener에 대한 내용은 아래 참조
mAdFloating.setListener(mListener);

// 할당받은 ClientID를 설정합니다.
mAdFloating.setClientId("AXT103001");

// 할당받은 Slot 번호를 설정합니다.
mAdFloating.setSlotNo(AdSlot.FLOATING);

// 광고를 삽입할 parentView를 설정합니다.
mAdFloating.setParentWindow(getWindow());
```
플로팅 광고 요청은 다음과 같습니다.
```java
// 광고를 요청 합니다. 로드시 설정한 값들이 유효한지 판단한 후 광고를 수신합니다.
// 광고 요청에 대한 결과는 설정한 listener를 통해 알 수 있습니다.
try {
mAdFloating.loadAd(null);
} catch (Exception e) {
e.printStackTrace();
}
```
loadAd()의 매개변수에는 일반적으로 null을 지정합니다.
광고 수신 여부는 AdFloatingListener의 onAdLoaded()를 통해 알 수 있습니다.
플로팅 광고 노출은 다음과 같습니다.
```java
// 수신한 광고가 있는지 여부를 반환합니다.
If (mAdFloating.isReady()) {
// 플로팅 광고를 화면의 지정한 좌표에 노출합니다.
try {
mAdFloating.showAd(xPosition, yPosition);
} catch (Exception e) {
e.printStackTrace();
}
}
```
showAd() 호출 시 parameter로 x, y 좌표를 받습니다. x, y 좌표는 setParentWindow()에 설정한 화면 기준 dip단위로 설정을 합니다. x, y좌표가 화면 해상도에서 벗어난 위치라면 SDK 내부적으로 clipping을 하여 표현합니다.
Tip. 광고 수신과 동시에 바로 노출을 할 경우 AdFloatingListener에 onAdLoaded()에서 showAd()를 호출하면 됩니다.
이미 노출된 플로팅 광고를 이동시킬 수 있습니다.
```java
//현재 노출중인 광고의 위치를 재조정 합니다.
try {
mAdFloating.moveAd(xPosition, yPosition);
} catch (Exception e) {
e.printStackTrace();
}
```

노출 이후 화면 orientation변경 이나 사용자 UI상 이동이 필요하면 x, y 좌표를 지정하여 재조정 할 수 있습니다.
또한 노출중인 플로팅 광고를 닫을 수 있습니다.
```java
//노출중인 광고를 닫습니다.
mAdFloating.closeAd()
```
더 이상 플로팅 광고를 사용하지 않는다면 사용중인 리소스들을 반환하기 위해 아래 함수를 호출합니다.
```java
// 현재 노출중인 광고를 닫고 모든 리소스를 회수한다.
mAdFloating.destroyAd();
```
destroyAd()를 호출한 이후에는 loadAd() 또는 showAd()를 호출 할 수 없고 광고 수신을 하려면 새롭게 객체를 생성하여야 합니다.

#####AdFloatingListener
AdFloatingListener를 이용하면 광고의 수신 상태, 광고 없음 및 광고 닫힘 등의 상태를 알 수 있습니다. AdFloatingListener는 필수 내용입니다.
```java
// AdFloating객체에 아래와 같이 Listener를 등록해야만 현재 상태를 알 수 있습니다.
Adfloating.setListener(mListener);

AdFloatingListener mListener = new AdFloatingListener() {

// 수신한 광고를 로딩 할 때 호출이 됩니다.
public void onAdWillLoad() { }

// 광고의 로딩이 완료되었을 때 호출이 됩니다.
public void onAdLoaded() { }

// 사용자에 의해 전체 확장이 일어날 때 호출이 됩니다.
public void onAdExpanded() { }

// 사용자에 의해 전체 확장 화면이 닫힐 때 호출이 됩니다.
public void onAdExpandClosed() { }

// 사용자에 의해 부분 확장이 일어날 때 호출이 됩니다.
public void onAdResized() { }

// 사용자에 의해 부분 확장 화면이 닫힐 때 호출이 됩니다.
public void onAdResizeClosed() { }

// 광고 수신, 로드 과정에서 실패 시 호출되고 자세한 내용은 errorCode를 참조하세요
public void onAdFailed(ErrorCode errorCode) { }

// 플로팅 광고가 화면에 노출될 때 호출이 됩니다.
public void onAdPresentScreen () { }

// 플로팅 광고가 닫힐 때 호출이 됩니다.
public void onAdDismissScreen () { }

// 광고의 클릭으로 인해, 다른 애플리케이션이 실행될 때에 호출됩니다.
public void onAdLeaveApplication () { }

// 광고가 클릭된 경우 호출이 됩니다.
public void onAdClicked() { }

// 광고가 닫힌 경우 호출이 됩니다.
public void onAdClosed(boolean user) { }
};
```
###네이티브 광고
3종의 광고 타입이 있으며 매체에서 어떤 광고 타입을 수신할지 설정이 가능합니다. 각 광고별 Asset의 종류는 다음과 같습니다. 

#####Content Ad
| Assets       | Example         |
| :----------- | :-------------- |
|Headline (max. 25 chars)	|Lowest mortgage rates|
|Image (1200x627 px)	        |Ad's main image|
|Body (max. 100 chars)          |Your home sweet Brooklyn home - cheaper and sooner than you think!|
|Logo (128x128 px)         	|NY Mortgage Inc.'s logo|
|Call to action (max. 15 chars)|Get a quote|
|Advertiser (max. 25 chars)|NY Mortgage Inc.|
|Click through URL (max. 1024 chars)	|http://www.nymtrust.com/|

#####App Install Ad
| Assets       | Example         |
| :----------- | :-------------- |
|Headline (max. 25 chars)	|Flood-It!|
|Image (1200x627 px)	        |A screenshot from the game Flood-It!|
|Body (max. 100 chars)          |Deceptively simple + tantalizingly challenging = delightfully addictive!|
|App icon (128x128 px)        	|Flood-it! app icon|
|Call to action (max. 15 chars)	|Install|
|Star rating (0 - 5)		|4.5|
|Store (max. 25 chars)		|Google Play|
|Price (max. 15 chars)		|Free|
|Click through URL (max. 1024 chars)|https://play.google.com/store/apps/details?id=com.labpixies.flood|

#####Product Ad
| Assets       | Example         |
| :----------- | :-------------- |
|Headline (max. 25 chars)	|정관장 홍삼정에브리타임 10ml*30|
|Image (1200x627 px)	        |Ad's main image|
|Body (max. 100 chars)          |품격있는 홍삼 선물. 정관장. 금방 품절됩니다! 인기 제품만 골라 7종 모음! 쇼핑백 무조건 동봉!|
|Logo (128x128 px)        	|11st's logo|
|Call to action (max. 15 chars)	|Go to shopping|
|Store (max. 25 chars)		|4.5|
|Price (max. 15 chars)	|₩90,000|
|Sale Price (max. 15 chars)		|₩77,900|
|Click through URL (max. 1024 chars)|http://deal.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=1243296986|

#####LayoutStyle
매체의 Layout형태를 광고 요청 시점에 입력 할 수 있습니다. 

| Laytout ID       | Description         |
| :----------- | :-------------- |
|1|Content Wall|
|2|App Wall|
|3|News Feed|
|4|Chat List|
|5|Carousel|
|6|Content Stream|
|7|Grid adjoining the content|
![Image of Layout Style](http://syrupad.github.io/syrupad-android-sdk/readme-screenshots/ss_layout_01.png)

![Image of Layout Style](http://syrupad.github.io/syrupad-android-sdk/readme-screenshots/ss_layout_02.png)

#####AdNative
AdNative instance를 생성하고 loadAd()를 실행합니다.
loadAd()의 수행결과(광고)는 AdNativeListener, onAdLoaded()를 통해 확인 할 수 있습니다. 
```java
AdNative adNative = new AdNative((Activity)getContext())
.setClientId("AXT999001") // 준비 과정에 발급받은 ClientId를 직접 입력합니다
.setSlotNo(AdSlot.NATIVE) // Slot을 설정합니다. 

.enableContentAd() // Content Ad 상품을 받고자 할 때 설정합니다. 
.enableAppInstallAd()  // App Install Ad 상품을 받고자 할 때 설정합니다. 
.enableProductAd()  // Product Ad 상품을 받고자 할 때 설정합니다. 

.setLayoutStyle(AdNative.LayoutStyle.APP_WALL) //LayoutStyle을 지정합니다. 
.setReturnUrlsForImageAssets(false); // image, icon, logo등의 asset정보를 Url로만 받고자 할때 true로 설정합니다. 

adNative.setListener(new AdNativeListener() {
    @Override
    public void onAdFailed(AdRequest.ErrorCode errorCode) {}
 
    @Override
    public void onAdWillLoad() {}
 
    @Override
    public void onAdLoaded(NativeAd nativeAd) {
    	//loadAd()의 결과로 NativeAd 를 확인 할 수 있습니다. 
    	if(nativeAd instanceof NativeContentAd) {
            //((NativeProductAd) nativeAd).getBody();
            //((NativeProductAd) nativeAd).getPrice();
            //((NativeProductAd) nativeAd).getStore();
            //((NativeProductAd) nativeAd).getSalePrice();
            //((NativeProductAd) nativeAd).getImages();
            //((NativeProductAd) nativeAd).getCallToAction();
            //((NativeProductAd) nativeAd).getHeadline();
            //((NativeProductAd) nativeAd).getLogo();
            
        } else if(nativeAd instanceof NativeAppInstallAd) {
            //((NativeAppInstallAd) nativeAd).getBody();
            //((NativeAppInstallAd) nativeAd).getHeadline();
            //((NativeAppInstallAd) nativeAd).getStarRating();
            //((NativeAppInstallAd) nativeAd).getCallToAction();
            //((NativeAppInstallAd) nativeAd).getImages();
            //((NativeAppInstallAd) nativeAd).getIcon();
            //((NativeAppInstallAd) nativeAd).getPrice();
            //((NativeAppInstallAd) nativeAd).getStore();
            
        } else if (nativeAd instanceof NativeProductAd) {
            // ((NativeProductAd) nativeAd).getBody();
            // ((NativeProductAd) nativeAd).getImages();
            // ((NativeProductAd) nativeAd).getCallToAction();
            // ((NativeProductAd) nativeAd).getHeadline();
            // ((NativeProductAd) nativeAd).getLogo();
            // ((NativeProductAd) nativeAd).getPrice();
            // ((NativeProductAd) nativeAd).getSalePrice();
            // ((NativeProductAd) nativeAd).getStore();
    }
 
    @Override
    public void onAdPresentScreen() {}
 
    @Override
    public void onAdDismissScreen() {}
 
    @Override
    public void onAdLeaveApplication() {}
});
 
try {
    adNative.loadAd();  //광고를 요청합니다. 
} catch (Exception e) {
    e.printStackTrace();
}
```
또한 더이상 네이티브 광고를 사용하지 않을 때 사용중인 리소스들을 반환하기 위해 아래 함수를 호출합니다.
```java
@Override
protected void onDestroy() {
    mAdNative.destroyAd();
    super.onDestroy();
}
```

#####노출과 클릭
네이티브광고는 타광고와는 다르게 노출과 클릭에 대한 API 를 매체에서 직접 호출해야합니다. 

노출과 클릭을 간편하게 적용할 수 있는 bind(), unbind() 메소드를 제공합니다. 
bind()함수를 사용하면 네이티브광고가 적용된 View와 네이티브 광고를 매핑하여 노출, 클릭 API를 자동으로 호출합니다. 
```java
//광고가 사용자의 눈에 노출이 될 때 호출합니다. 
AdNative.bind(view, nativeAd);

//광고가 보이지 않을때 호출합니다. 
AdNative.unbind(view, nativeAd);
```

bind()함수를 사용하지 않는다면 직접 노출과 클릭에 대한 API를 호출해야 합니다. 
```java
//광고가 사용자의 눈에 노출이 될 때 호출합니다. 
nativeAd.impression(context);
            
//광고가 사용자에 의해 Click이 될 때 호출합니다. 
nativeAd.click(context);
```
참고로 하나의 네이티비광고에 대해 복수개의 노출API가 호출되더라도 통계에는 한 개만 집게가 되고 클릭API는 매번 집게가 됩니다. (단 부정Click 제외)

### Step Ⅴ.  사용자 타겟팅 설정(option)
Syrup Ad 에서는 App사용자들에게 맞춤형 광고를 제공하기 위해 아래 두 가지 기능을 제공합니다. 사용자 개인정보가 수집이 되면 반응률이 높은 맞춤형 광고를 받을 수 있습니다.
사용자 정보 전달
개별 앱에서 개인정보 활용에 동의를 받은 경우 해당 정보를 통해 맞춤형 광고를 노출 할 수 있습니다. 
```java
// 개인정보를 전달할 AdRequest instance생성
AdRequest request = new AdRequest();

// 사용자 생년원일 정보를 입력합니다.
request.setBirthday(Calendar.getInstance());

// 사용자의 성별을 입력합니다.
request.setGender(Gender.MALE);

// 사용자의 나이 정보를 입력합니다.
request.setAge(20);

// 관심사 keywords를 저장합니다.
Set keywords = new HashSet<String>();
keywords.add("Game");
keywords.add("RPG");
keywords.add("Action");
request.setKeywords(keywords);

//광고 요청시점에 AdRequest instance를 parameter로 입력합니다.
mAdView.loadAd(request);
```
약관동의 대화상자
Syrup Ad 에서는 직접 개인정보 활용에 관련한 동의를 받고 있습니다. 동의를 받기 위해서는 개발자가 Syrup Ad에서 제공하는 method를 호출하여야 합니다. 
```java
//Dialog instance생성
AdDialog dialog = new AdDialog(this);

//Terms type의 dialog호출
dialog.showTermsDialog(DialogType.TERMS_TYPE);
```
약관동의 대화상자는 기동의자의 경우와 대화상자 최근 노출로부터 15일 미만의 경우 대화상자가 나타나지 않습니다.  약관 대화상자가 노출되는지 여부는 AdDialog 객체의 canShowTermsDialog(DialogType) method를 통해서 판단 할 수 있습니다. 약관동의 대화상자 역시 닫히는 시점에 닫힘 여부를 listener를 통해 알 수 있습니다. listener는 필수 항목은 아닙니다.
```java
// 다이얼 로그 객체 생성
AdDialog dialog = new AdDialog(mainActivity.this);

// canShowAdDialog로 기 동의 자인지 판단
if (dialog.canShowTermsDialog(DialogType.TERMS_TYPE)) {
// true라면 미동의자이며 아래 Dialog를 호출한다.
dialog.showTermsDialog(DialogType.TERMS_TYPE);

	// dialog가 닫힐 때 호출되는 Listener
dialog.setListener(new AdDialogListener() {

	@Override
	public void onDialogClosed() { }
	// Dialog가 닫힘.
});
}
```

###FAQ
Q: 광고가 보이지 않습니다.
A: Syrup Ad에서는 광고의 상태를 Listener를 통해서 알려 주고 있습니다. 현재의 AdView 또는 AdInterstitial의 광고 유무, 오류, 수신 상태를 개발자에게 전달해주고 있으니 Listener를 통해 문제를 파악하시길 바랍니다. 주기적으로 문제가 발생시 Syrup ad 운영센터로 연락주세요.

Q: 광고는 잘 보여지는데 통계가 잡히지 않아요
A: 클릭이나 노출 통계가 개발자 페이지에 표시되기 까지 일정의 시간이 걸립니다. 또는 SDK설정에 TestMode를 설정한 것은 아닌지 확인 바랍니다. TestMode 에서의 노출 클릭 로그는 통계에 포함되지 않습니다.

Q: 빌드시 AndroidManifest.xml의 configChanges 에서 에러가 나옵니다.
A: AndroidManifest.xml내 configChange는 Project API Level 에 따라 선택적으로 적용을 하여야 합니다. 기본적으로 keyboard, keyboardHidden, orientation, screenLayout, uiMode는 필수이고 API Level 13 이상은 screenSize, smallestScreenSize 속성을 선언하여야 합니다.

Q: 프로가드 진행 시 아래와 같은 에러가 나옵니다.
```xml
Proguard returned with error code 1. See console
Warning: com.google.android.gms.common.SupportErrorDialogFragment: can't find referenced method 'void setShowsDialog(boolean)' in class com.google.android.gms.common.SupportErrorDialogFragment
Warning: com.google.android.gms.common.api.zzh: can't find referenced method 'android.support.v4.app.FragmentActivity getActivity()' in class com.google.android.gms.common.api.zzh
Warning: com.google.android.gms.common.api.zzh: can't find referenced method 'android.support.v4.app.LoaderManager getLoaderManager()' in class com.google.android.gms.common.api.zzh
Warning: com.google.android.gms.common.api.zzh: can't find referenced method 'boolean isRemoving()' in class com.google.android.gms.common.api.zzh
Warning: com.google.android.gms.common.api.zzh$zza: can't find referenced method 'void deliverResult(java.lang.Object)' in class com.google.android.gms.common.api.zzh$zza
Warning: com.google.android.gms.common.api.zzh$zza: can't find referenced method 'boolean isAbandoned()' in class com.google.android.gms.common.api.zzh$zza
Warning: com.google.android.gms.common.api.zzh$zza: can't find referenced method 'boolean isStarted()' in class com.google.android.gms.common.api.zzh$zza
Warning: com.google.android.gms.common.api.zzh$zzc: can't find referenced method 'android.support.v4.app.FragmentActivity getActivity()' in class com.google.android.gms.common.api.zzh
```
A: 프로젝트가 참조하는 Google Play services library의 버전이 높기 때문에 android-support-v4.jar파일을 필요로 합니다. 프로젝트내 libs폴더에 해당 파일을 복사바랍니다.

###API Introduction
 첨부된 index.html파일 참조하세요.

###ProGuard 사용
Syrup Ad SDK는 ProGuard를 사용하여 난독화(obfuscated)되어 있습니다. 개발이 완료된 Android 어플리케이션에 ProGuard를 사용할 때 ProGuard 설정에 다음 예외 문을 추가하여야 합니다.
```xml
-keep class com.skplanet.tad.** { *; }
-dontwarn com.skplanet.tad.**

-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
```

추가 정보
SDK 적용에 문의가 있으면 아래 연락처로 문의해 주세요.

| 구분     | 연락처 |
| :-------------  | :--------- |
| 기 술 문 의     | support@syrupad.co.kr  |
|SDK배포/사업문의 |  svcadmin@syrupad.co.kr |
|연락처	          | 1588-7898|
