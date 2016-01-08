package com.skplanet.syrupad.showcase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.skplanet.tad.AdListener;
import com.skplanet.tad.AdRequest.ErrorCode;
import com.skplanet.tad.AdSlot;
import com.skplanet.tad.AdView;

public class DisplayAdViewMediation extends Activity{
	
	AdView mAdView;

	ImageView otherView;
	
	RelativeLayout layout;
	
	float mDensity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.display_ad_view_mediation);
		layout = (RelativeLayout) findViewById(R.id.adViewDisplay);

		/* 편의상 타광고 플랫폼을 ImageView로 표현하였습니다.*/
		otherView = new ImageView(DisplayAdViewMediation.this);
		otherView.setBackgroundResource(R.drawable.other_ad_image);
		otherView.setVisibility(View.GONE);
		otherView.setTag("1");
		
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
		mDensity = mDisplayMetrics.density;
		
		// Context 를 parameter 로 AdView 의 객체를 생성합니다.
		this.mAdView = new AdView(this);

		// 준비 과정에 발급받은 ClientId 를 직접 입력합니다. Ex) AdView.setClientId("AX0000123");
		this.mAdView.setClientId("AX000450A");
		
		// 원하는 크기의 Slot 을 설정합니다
		this.mAdView.setSlotNo(AdSlot.BANNER);

		// 새로운 광고를 요청하는 주기를 입력합니다. 최소값은 15, 최대값은 60 입니다
		this.mAdView.setRefreshInterval(15);
		
		// 광고 View 의 Background 의 사용 유무를 설정합니다.
		this.mAdView.setUseBackFill(false);

		// 새로운 받은 광고가 Display 되는 Animation 효과를 설정합니다.
		this.mAdView.setAnimationType(AdView.AnimationType.ZOOM);

		// TestMode 를 정합니다. true 인경우 test 광고가 수신됩니다.
		this.mAdView.setTestMode(false);

		// AdView 진행 상태를 알 수 있도록 Listener를 등록합니다. 
		this.mAdView.setListener(mAdListener);

		// Tad 중심으로 meditaion하기 때문에 최초에 Tad광고를 요청을 합니다. 
		if (layout != null) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			mAdView.setTag("0");
			layout.addView(mAdView, params);
		}
		
		try {
			mAdView.loadAd(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	AdListener mAdListener = new AdListener() {

		@Override
		public void onAdWillLoad() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdWillLoad() called.");
		}

		@Override
		public void onAdResized() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdResized() called.");
			Toast.makeText(DisplayAdViewMediation.this, "onAdResized()",	Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdResizeClosed() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdResizeClosed() called.");
			Toast.makeText(DisplayAdViewMediation.this, "onAdResizeClosed()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdLoaded() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdLoaded() called.");
			
			/* Tad에서 광고를 수신하여 타광고 플랫폼에서 Tad플랫폼으로 전환합니다. */
			if(layout.findViewWithTag("1") != null){
				switchTadView();
			}
		}

		@Override
		public void onAdExpanded() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdExpanded() called.");

			Toast.makeText(DisplayAdViewMediation.this, "onAdExpanded()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdExpandClosed() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdExpandClosed() called.");
			Toast.makeText(DisplayAdViewMediation.this, "onAdExpandClosed()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdDismissScreen() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdDismissScreen() called.");
			Toast.makeText(DisplayAdViewMediation.this, "onAdDismissScreen()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdLeaveApplication() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdLeaveApplication() called.");
			Toast.makeText(DisplayAdViewMediation.this, "onAdLeaveApplication()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdPresentScreen() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdPresentScreen() called.");
			Toast.makeText(DisplayAdViewMediation.this, "onAdPresentScreen()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdClicked() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdClicked() called.");
			Toast.makeText(DisplayAdViewMediation.this, "onAdClicked()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdFailed(ErrorCode arg0) {
			// TODO Auto-generated method stub
			Log.v(Constants.SHOWCASE, "@AdView - onAdFailed() called. ErrorCode + " + arg0);
			
			Toast.makeText(DisplayAdViewMediation.this, "onAdFailed() ErrorCode : " + arg0, Toast.LENGTH_SHORT).show();
			
			/*Tad에 광고가 없음으로 타광고 플랫폼으로 전환합니다. */
			if(arg0.equals(ErrorCode.NO_FILL)) {
				if(layout.findViewWithTag("0") != null){
					switchOtherAdView();
				}
			}
			
		}

	};
	
	public void switchOtherAdView() {
		Toast.makeText(DisplayAdViewMediation.this, "switch OtherAdView", Toast.LENGTH_SHORT).show();
		
		/*
		 * Tad에 광고가 없음으로 타광고 플랫폼으로 전환합니다. 
		 * 
		 *  */
		otherView.setVisibility(View.VISIBLE);
		if (layout != null) {
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(320 * mDensity), (int)(50 * mDensity));
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			layout.removeView(mAdView);
			layout.addView(otherView, params);
		}
	}
	
	public void switchTadView() {
		Toast.makeText(DisplayAdViewMediation.this, "switch SyrupAdView", Toast.LENGTH_SHORT).show();
		otherView.setVisibility(View.GONE);
		if (layout != null) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			layout.addView(mAdView, params);
			layout.removeView(otherView);
		}
	}
	
	public void onButtonClicked(View view) {
		return;
	}
	
	@Override
	protected void onDestroy() {
		//더이상 AdView 또는 Interstitial 을 사용하지 않을 때 destoryAd() Method를 호출해야합니다. 
		if (mAdView != null) {
			this.mAdView.destroyAd();
		}
		super.onDestroy();
	}
}
