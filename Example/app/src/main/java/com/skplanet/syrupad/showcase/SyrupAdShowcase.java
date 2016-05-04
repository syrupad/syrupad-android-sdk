package com.skplanet.syrupad.showcase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.skplanet.tad.AdSlot;

import java.util.ArrayList;
import java.util.List;

public class SyrupAdShowcase extends AppCompatActivity {

    String mClientId = null;
    public static final String shardClientId = "TAD_CLIENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syrup_ad_showcase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mClientId = mPrefs.getString(shardClientId, null);

        ListView listView = (ListView) findViewById(R.id.listView);
        if (listView != null) {
            ListAdapter listAdapter = new ListAdapter(SyrupAdShowcase.this, generateListItems());
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapt, View view, int position, long id) {
                    ListItem items = (ListItem) adapt.getItemAtPosition(position);
                    if (items != null) {
                        Intent intent = new Intent();
                        if (items.getAdType().equals("SyrupAd중심 Mediation")) {
                            intent.setClass(SyrupAdShowcase.this, DisplayAdViewMediation.class);
                        } else if (items.getAdSlot() == AdSlot.FLOATING ) {
                            intent.setClass(SyrupAdShowcase.this, DisplayAdFloating.class);
                        } else if (items.getAdSlot() == AdSlot.NATIVE ) {
                            intent.setClass(SyrupAdShowcase.this, DisplayAdNative.class);
                        } else {
                            intent.setClass(SyrupAdShowcase.this, DisplayAdView.class);
                        }

                        if (mClientId != null && !TextUtils.isEmpty(mClientId)) {
                            intent.putExtra("adClientId", mClientId);
                        } else {
                            intent.putExtra("adClientId", items.getAdClientId());
                        }
                        intent.putExtra("adSlot", items.getAdSlot());
                        intent.putExtra("adIcon", items.getAdIcon());
                        intent.putExtra("adType", items.getAdType());
                        intent.putExtra("adSize", items.getAdSize());
                        intent.putExtra("isXml", items.isXml());
                        startActivity(intent);
                    }
                }
            });
        }
    }


    /**
     * Generates a list of ListItem objects from the ad icons, types, sizes
     * constants arrays. This list will become the input to the ListAdapter.
     */
    private List<ListItem> generateListItems() {
        List<ListItem> listItems = new ArrayList<ListItem>();

        // All four arrays must have the same length to continue.
        for (int index = 0; index < Constants.AD_SAMPLES.length; index++) {
            ListItem listItem = new ListItem();
            listItem.setAdClientId(Constants.AD_SAMPLES[index].cliendId);
            listItem.setAdSlot(Constants.AD_SAMPLES[index].slot);
            listItem.setAdIcon(Constants.AD_SAMPLES[index].icon);
            listItem.setAdType(Constants.AD_SAMPLES[index].type);
            listItem.setAdSize(Constants.AD_SAMPLES[index].size);
            listItem.setXml(Constants.AD_SAMPLES[index].isXml);
            listItems.add(listItem);
        }

        return listItems;
    }
}
