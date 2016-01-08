package com.skplanet.syrupad.showcase;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.skplanet.tad.AdDialog;
import com.skplanet.tad.AdDialog.DialogType;
import com.skplanet.tad.AdInterstitial;
import com.skplanet.tad.AdInterstitialListener;
import com.skplanet.tad.AdListener;
import com.skplanet.tad.AdRequest.ErrorCode;
import com.skplanet.tad.AdView;

public class DisplayAdView extends Activity implements OnClickListener {
	private AdView mAdView;
	private AdInterstitial mAdInterstitial;

	private String mAdClientId;
	private int mAdSlot;
	private boolean mIsXml;

	/*
	 * TestMode에 사용하는 ClientID
	 * inline - AXT002001
	 * interstitial - AXT0003001, AXT003002
	 * floating - AXT103001
	 * 
	 * 자세한 내용은 개발자 적용가이드 참조 
	 * */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove title and status bar.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mAdView = null;
		mAdInterstitial = null;

		mAdClientId = getIntent().getStringExtra("adClientId");
		mAdSlot = getIntent().getIntExtra("adSlot", 2);
		mIsXml = getIntent().getBooleanExtra("isXml", false);

		// << interstitial banner
		if (mAdSlot == 3) {
			setContentView(R.layout.display_interstitial);
			
			// Context 를 parameter 로 AdInterstitial 객체를 생성합니다.
			this.mAdInterstitial = new AdInterstitial(this);
			
			// 준비 과정에 발급받은 ClientId 를 직접 입력합니다. Ex) AdView.setClientId("AX0000123");
			// TestMode인경우 상단에 명시된 ClientID를 입력합니다. 
			this.mAdInterstitial.setClientId(this.mAdClientId);
			
			// 원하는 크기의 Slot 을 설정합니다. Ex) AdInterstitial.setSlotNo(Slot.INTERSTITIAL);
			this.mAdInterstitial.setSlotNo(this.mAdSlot);

			// TestMode 를 정합니다. true 인경우 test 광고가 수신됩니다.
			this.mAdInterstitial.setTestMode(true);
			
			// 랜딩시 자동 닫기
			this.mAdInterstitial.setAutoCloseAfterLeaveApplication(true);
			
			// 5초후 자동 닫기 
			this.mAdInterstitial.setAutoCloseWhenNoInteraction(false);
			
			//  AdInterstitail 진행 상태를 알 수 있도록 Listener를 등록합니다. 
			this.mAdInterstitial.setListener(mAdInterstitialListener);
			// >>

			// << inline banner(xml)
		} else if (mIsXml) {
			// AdView가 선언된 xml을 setContentView 진행   
			setContentView(R.layout.display_adview_in_xml);
			this.mAdView = (AdView) findViewById(R.id.tadView);
			// >>

			// << inline banner(java)
		} else if (mAdSlot == 2 || mAdSlot == 5 || mAdSlot == 6) {
			setContentView(R.layout.display_ad_view);
			
			// Context 를 parameter 로 AdView 의 객체를 생성합니다.
			this.mAdView = new AdView(this);

			// 준비 과정에 발급받은 ClientId 를 직접 입력합니다. Ex) AdView.setClientId("AX0000123");
			this.mAdView.setClientId(this.mAdClientId);
			
			// 원하는 크기의 Slot 을 설정합니다
			this.mAdView.setSlotNo(this.mAdSlot);

			// 새로운 광고를 요청하는 주기를 입력합니다. 최소값은 15, 최대값은 60 입니다
			this.mAdView.setRefreshInterval(15);
			
			// 광고 View 의 Background 의 사용 유무를 설정합니다.
			this.mAdView.setUseBackFill(false);

			// 새로운 받은 광고가 Display 되는 Animation 효과를 설정합니다.
			this.mAdView.setAnimationType(AdView.AnimationType.SLIDE_FROM_TOP_TO_BOTTOM);

			// TestMode 를 정합니다. true 인경우 test 광고가 수신됩니다.
			this.mAdView.setTestMode(true);

			// AdView 진행 상태를 알 수 있도록 Listener를 등록합니다. 
			this.mAdView.setListener(mAdListener);

			RelativeLayout layout = (RelativeLayout) findViewById(R.id.adViewDisplay);
			if (layout != null) {
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				layout.addView(mAdView, params);
			}
			
			// 광고를 요청합니다.
			try {
				this.mAdView.loadAd(null);
			} catch (Exception e) {
				e.printStackTrace();
			}

			findViewById(R.id.termsBtn).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AdDialog dialog = new AdDialog(DisplayAdView.this);
					if (dialog.canShowTermsDialog(DialogType.TERMS_TYPE)) {
						dialog.showTermsDialog(DialogType.TERMS_TYPE);
					} else {
						Log.v(Constants.SHOWCASE, "dialog.canShowAdDialog() false");
					}
				}
			});
		} 
		// >>
		
	}

	public void onButtonClicked(View view) {

		this.mAdView.stopAd();

		switch (view.getId()) {
		case R.id.animationNone:
			this.mAdView.setAnimationType(AdView.AnimationType.NONE);
			break;
		case R.id.animationFade:
			this.mAdView.setAnimationType(AdView.AnimationType.FADE);
			break;
		case R.id.animationZoom:
			this.mAdView.setAnimationType(AdView.AnimationType.ZOOM);
			break;
		case R.id.animationRotate:
			this.mAdView.setAnimationType(AdView.AnimationType.ROTATE);
			break;
		case R.id.animationSlideRL:
			this.mAdView.setAnimationType(AdView.AnimationType.SLIDE_FROM_RIGHT_TO_LEFT);
			break;
		case R.id.animationSlideLR:
			this.mAdView.setAnimationType(AdView.AnimationType.SLIDE_FROM_LEFT_TO_RIGHT);
			break;
		case R.id.animationSlideBT:
			this.mAdView.setAnimationType(AdView.AnimationType.SLIDE_FROM_BOTTOM_TO_TOP);
			break;
		case R.id.animationSlideTB:
			this.mAdView.setAnimationType(AdView.AnimationType.SLIDE_FROM_TOP_TO_BOTTOM);
			break;
		case R.id.animationFlipH:
			this.mAdView.setAnimationType(AdView.AnimationType.FLIP_HORIZONTAL);
			break;
		case R.id.animationFlipV:
			this.mAdView.setAnimationType(AdView.AnimationType.FLIP_VERTICAL);
			break;
		case R.id.animationRotateH:
			this.mAdView.setAnimationType(AdView.AnimationType.ROTATE3D_180_HORIZONTAL);
			break;
		case R.id.animationRotateV:
			this.mAdView.setAnimationType(AdView.AnimationType.ROTATE3D_180_VERTICAL);
			break;
		}
		try {
			this.mAdView.loadAd(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View view) {
		final int id = view.getId();
		switch (id) {
		case R.id.showInterstitial:
			if (this.mAdInterstitial != null) {
				try {
					this.mAdInterstitial.showAd();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case R.id.loadInterstitial:
			if (this.mAdInterstitial != null) {
				try {
					this.mAdInterstitial.loadAd(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		}
	}

	protected void onDestroy() {
		//더이상 AdView 또는 Interstitial 을 사용하지 않을 때 destoryAd() Method를 호출해야합니다. 
		if (mAdView != null) {
			this.mAdView.destroyAd();
		}
		if (mAdInterstitial != null) {
			this.mAdInterstitial.destroyAd();
		}
		super.onDestroy();
	};

	AdListener mAdListener = new AdListener() {

		@Override
		public void onAdWillLoad() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdWillLoad() called.");
		}

		@Override
		public void onAdResized() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdResized() called.");
			Toast.makeText(DisplayAdView.this, "onAdResized()",	Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdResizeClosed() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdResizeClosed() called.");
			Toast.makeText(DisplayAdView.this, "onAdResizeClosed()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdLoaded() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdLoaded() called.");
		}

		@Override
		public void onAdExpanded() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdExpanded() called.");
			Toast.makeText(DisplayAdView.this, "onAdExpanded()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdExpandClosed() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdExpandClosed() called.");
			Toast.makeText(DisplayAdView.this, "onAdExpandClosed()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdDismissScreen() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdDismissScreen() called.");
			Toast.makeText(DisplayAdView.this, "onAdDismissScreen()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdLeaveApplication() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdLeaveApplication() called.");
			Toast.makeText(DisplayAdView.this, "onAdLeaveApplication()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdPresentScreen() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdPresentScreen() called.");
			Toast.makeText(DisplayAdView.this, "onAdPresentScreen()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdClicked() {
			Log.v(Constants.SHOWCASE, "@AdView - onAdClicked() called.");
			Toast.makeText(DisplayAdView.this, "onAdClicked()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdFailed(ErrorCode arg0) {
			Log.v(Constants.SHOWCASE, "@AdView - onAdFailed() called. ErrorCode + " + arg0);
			Toast.makeText(DisplayAdView.this, "onAdFailed() ErrorCode : " + arg0, Toast.LENGTH_SHORT).show();
		}

	};

	AdInterstitialListener mAdInterstitialListener = new AdInterstitialListener() {
		@Override
		public void onAdWillLoad() {
			Log.v(Constants.SHOWCASE, "@AdInterstitial - onAdWillLoad() called.");
		}

		@Override
		public void onAdLoaded() {
			Log.v(Constants.SHOWCASE, "@AdInterstitial - onAdLoaded() called.");
			Toast.makeText(DisplayAdView.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdDismissScreen() {
			Log.v(Constants.SHOWCASE, "@AdInterstitial - onAdDismissScreen() called.");
			Toast.makeText(DisplayAdView.this, "onAdDismissScreen()", Toast.LENGTH_SHORT).show();
			
		}

		@Override
		public void onAdLeaveApplication() {
			Log.v(Constants.SHOWCASE, "@AdInterstitial - onAdLeaveApplication() called.");
			Toast.makeText(DisplayAdView.this, "onAdLeaveApplication()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdPresentScreen() {
			Log.v(Constants.SHOWCASE, "@AdInterstitial - onAdPresentScreen() called.");
			Toast.makeText(DisplayAdView.this, "onAdPresentScreen()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdFailed(ErrorCode arg0) {
			Log.v(Constants.SHOWCASE, "@AdInterstitial - onAdFailed() called. ErrorCode : "	+ arg0);
			Toast.makeText(DisplayAdView.this, "onAdFailed() ErrorCode : " + arg0, Toast.LENGTH_SHORT).show();
		}

	};
}
