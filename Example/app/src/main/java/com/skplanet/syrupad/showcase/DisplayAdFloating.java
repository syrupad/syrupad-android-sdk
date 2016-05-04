package com.skplanet.syrupad.showcase;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.skplanet.tad.AdFloating;
import com.skplanet.tad.AdFloatingListener;
import com.skplanet.tad.AdRequest.ErrorCode;
import com.skplanet.tad.AdSlot;

public class DisplayAdFloating extends Activity {
	
	AdFloating mAdFloating;
	TextView xResult;
	TextView yResult;
	SeekBar xSeek;
	SeekBar ySeek;
	
	int xPosition, yPosition = 0;

	private String mClientId;
	
	
	/*
	 * TestMode에 사용하는 ClientID
	 * inline - AXT002001
	 * interstitial - AXT0003001, AXT003002
	 * floating - AXT103001
	 * 
	 * 자세한 내용은 개발자 적용가이드 참조 
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.adfloating_test_activity);
		
		mClientId = getIntent().getStringExtra("adClientId");
		
		Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		display.getMetrics(mDisplayMetrics);
		float mDensity = mDisplayMetrics.density;
		
		int displayWidth = (int) (mDisplayMetrics.widthPixels / mDensity);
		int displayHeight = (int) (mDisplayMetrics.heightPixels / mDensity);
		
		xResult = (TextView) findViewById(R.id.xResult);
		yResult = (TextView) findViewById(R.id.yResult);
		
		xSeek = ((SeekBar) findViewById(R.id.xseek));
		ySeek = (SeekBar) findViewById(R.id.yseek);

		xSeek.setMax(displayWidth);
		xSeek.setProgress(displayWidth/2);
		xPosition = displayWidth/2;
		xResult.setText(xPosition + "");
		xSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				xPosition = seekBar.getProgress();
				xResult.setText(xPosition + "");
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				xResult.setText(seekBar.getProgress()+"");
				
			}
		});
		ySeek.setMax(displayHeight);
		ySeek.setProgress(displayHeight/2);
		yPosition = displayHeight/2;
		yResult.setText(yPosition + "");
		ySeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				yPosition = seekBar.getProgress();
				yResult.setText(yPosition + "");
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				yResult.setText(seekBar.getProgress()+"");
			}
		});
	}

	public void onClickCreate(View v) {
		
		mAdFloating = new AdFloating(DisplayAdFloating.this);
		mAdFloating.setClientId(mClientId);
		mAdFloating.setSlotNo(AdSlot.FLOATING);
		
		// TestMode여부를 설정합니다. 
		mAdFloating.setTestMode(true);

		// 광고를 삽입할 parentView를 설정합니다. 
		mAdFloating.setParentWindow(getWindow());
		
		// 광고 수신 및 상태를 알 수 있는 Listener를 등록합니다. 
		mAdFloating.setListener(mListener);
	};
	public void onClickLoad(View v) {
		if (mAdFloating != null) {
			// 광고를 요청 합니다. 로드시 설정한 값들이 유효한지 판단한 후 광고를 수신합니다.
			// 광고 요청에 대한 결과는 설정한 listener를 통해 알 수 있습니다. 
			try {
				mAdFloating.loadAd(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	};

	public void onClickShowAd(View v) {
		if (mAdFloating != null) {
			try {
				mAdFloating.showAd(xPosition, yPosition);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public void onClickMoveAd(View v) {
		if (mAdFloating != null) {
			// 플로팅 광고 이동
			try {
				mAdFloating.moveAd(xPosition, yPosition);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public void onClickDestroyAd(View v) {
		if (mAdFloating != null) {
			// 플로팅 광고 종료
			mAdFloating.destroyAd();
		}
	};
	protected void onDestroy() {
		if (mAdFloating != null) {
			mAdFloating.destroyAd();
		}
		super.onDestroy();
	};
	
	AdFloatingListener mListener = new AdFloatingListener() {
		
		@Override
		public void onAdWillLoad() {}
		
		@Override
		public void onAdResized() {}
		
		@Override
		public void onAdResizeClosed() {}
		
		@Override
		public void onAdPresentScreen() {
			Toast.makeText(DisplayAdFloating.this, "onAdPresentScreen()", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onAdLoaded() {
			Toast.makeText(DisplayAdFloating.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onAdLeaveApplication() {
			Toast.makeText(DisplayAdFloating.this, "onAdLeaveApplication()", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onAdExpanded() {}
		
		@Override
		public void onAdExpandClosed() {}
		
		@Override
		public void onAdDismissScreen() {
			Toast.makeText(DisplayAdFloating.this, "onAdDismissScreen()", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdFailed(ErrorCode arg0) {
			Toast.makeText(DisplayAdFloating.this, "onAdFailed(" + arg0 + ")", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdClicked() {
			
		}

		@Override
		public void onAdClosed(boolean arg0) {
			
		}
		
	};
	public void onConfigurationChanged(Configuration newConfig) {
		Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		display.getMetrics(mDisplayMetrics);
		float mDensity = mDisplayMetrics.density;
		
		int displayWidth = (int) (mDisplayMetrics.widthPixels / mDensity);
		int displayHeight = (int) (mDisplayMetrics.heightPixels / mDensity);
		
		xSeek.setMax(displayWidth);
		ySeek.setMax(displayHeight);
		
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	        Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
	    }
	    
		if (mAdFloating != null) {
			try {
				mAdFloating.moveAd(xPosition, yPosition);
			} catch (Exception e) {
			}
		}
		
	    super.onConfigurationChanged(newConfig);
	};
}
