package com.skplanet.syrupad.showcase;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skplanet.tad.AdNative;
import com.skplanet.tad.AdNativeListener;
import com.skplanet.tad.AdRequest;
import com.skplanet.tad.AdSlot;
import com.skplanet.tad.NativeAd;
import com.skplanet.tad.NativeAppInstallAd;
import com.skplanet.tad.NativeContentAd;
import com.skplanet.tad.NativeProductAd;

public class DisplayAdNative extends Activity {
	private String mAdClientId;
	private AdNative mAdNative;
	private LinearLayout mResultLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.display_adnative);

		mAdClientId = getIntent().getStringExtra("adClientId");
		mResultLayout = (LinearLayout)findViewById(R.id.native_ad_layout);

		mAdNative = new AdNative(this)
		.setClientId(mAdClientId)
		.setSlotNo(AdSlot.NATIVE)
		.setTestMode(true)
		.setLayoutStyle(AdNative.LayoutStyle.CAROUSEL)
		.setRequestMultipleImages(false)
		.setReturnUrlsForImageAssets(false)
		.setListener(mListener);

		mAdNative.disableAppInstallAd();
		mAdNative.disableContentAd();
		mAdNative.disableProductAd();
	}

	public void onClickCheckProduct(View v) {
		if(((CheckBox)v).isChecked())
			mAdNative.enableProductAd();
		else
			mAdNative.disableProductAd();
	}

	public void onClickCheckInstall(View v) {
		if(((CheckBox)v).isChecked())
			mAdNative.enableAppInstallAd();
		else
			mAdNative.disableAppInstallAd();
	}

	public void onClickCheckContent(View v) {
		if(((CheckBox)v).isChecked())
			mAdNative.enableContentAd();
		else
			mAdNative.disableContentAd();
	}

	public void onClickNativeLoad(View v) {
		mResultLayout.removeAllViews();
		mAdNative.loadAd();
	}

	AdNativeListener mListener = new AdNativeListener() {
		@Override
		public void onAdFailed(AdRequest.ErrorCode errorCode) {
			Toast.makeText(DisplayAdNative.this, "onAdFailed" + errorCode.toString(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdWillLoad() {

		}

		@Override
		public void onAdLoaded(NativeAd nativeAd) {
			View resultView = View.inflate(DisplayAdNative.this, R.layout.display_adnative_item, null);
			TextView headline = (TextView) resultView.findViewById(R.id.headline);
			TextView body = (TextView) resultView.findViewById(R.id.body);
			TextView sponserText = (TextView) resultView.findViewById(R.id.sponsor);

			ImageView thumbnail = (ImageView) resultView.findViewById(R.id.logo);
			ImageView mainImage = (ImageView) resultView.findViewById(R.id.image);
			Button actionButton = (Button) resultView.findViewById(R.id.call_to_action);

			RatingBar ratingBar = (RatingBar) resultView.findViewById(R.id.ratingbar);
			if(nativeAd instanceof NativeContentAd) {
				headline.setText(((NativeContentAd) nativeAd).getHeadline());
				body.setText(((NativeContentAd) nativeAd).getBody());
				thumbnail.setImageDrawable(((NativeContentAd) nativeAd).getLogo().getDrawable());
				mainImage.setImageDrawable(((NativeContentAd) nativeAd).getImages().get(0).getDrawable());
				actionButton.setText(((NativeContentAd) nativeAd).getCallToAction());
				sponserText.setText("@powered by \n" + ((NativeContentAd) nativeAd).getAdvertiser());

				ratingBar.setVisibility(View.GONE);
			} else if (nativeAd instanceof NativeAppInstallAd) {
				headline.setText(((NativeAppInstallAd) nativeAd).getHeadline());
				body.setText(((NativeAppInstallAd) nativeAd).getBody());
				thumbnail.setImageDrawable(((NativeAppInstallAd) nativeAd).getIcon().getDrawable());
				mainImage.setImageDrawable(((NativeAppInstallAd) nativeAd).getImages().get(0).getDrawable());
				actionButton.setText(((NativeAppInstallAd) nativeAd).getCallToAction());
				sponserText.setText("@powered by \n" + ((NativeAppInstallAd) nativeAd).getStore());

				ratingBar.setVisibility(View.VISIBLE);
				ratingBar.setRating(((NativeAppInstallAd) nativeAd).getStarRating().floatValue());
			} else {
				headline.setText(((NativeProductAd) nativeAd).getHeadline());
				body.setText(((NativeProductAd) nativeAd).getBody());
				thumbnail.setImageDrawable(((NativeProductAd) nativeAd).getLogo().getDrawable());
				mainImage.setImageDrawable(((NativeProductAd) nativeAd).getImages().get(0).getDrawable());
				actionButton.setText(((NativeProductAd) nativeAd).getCallToAction());
				sponserText.setText("@powered by \n" + ((NativeProductAd) nativeAd).getStore());

				ratingBar.setVisibility(View.GONE);
			}

			AdNative.bind(resultView, nativeAd);

			mResultLayout.addView(resultView);
		}

		@Override
		public void onAdPresentScreen() {

		}

		@Override
		public void onAdDismissScreen() {

		}

		@Override
		public void onAdLeaveApplication() {

		}

		@Override
		public void onAdClicked() {

		}
	};
}
