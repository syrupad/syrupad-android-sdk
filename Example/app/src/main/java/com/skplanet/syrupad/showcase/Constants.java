package com.skplanet.syrupad.showcase;

import com.skplanet.tad.AdSlot;

public final class Constants {

	public static final String SHOWCASE = "Syrup Ad v3.0 Showcase";

	public final class AdTypes {
		public static final String SLOT2 = "[Slot 2]";
		public static final String SLOT3 = "[Slot 3]";
		public static final String SLOT4 = "[Slot 103]";
		public static final String SLOT5 = "[Slot 5]";
		public static final String SLOT6 = "[Slot 6]";
	}

	public static class AdSample {
		public final String type;
		public final int icon;
		public final String size;
		public final String cliendId;
		public final int slot;
		public final boolean isXml;

		private AdSample(String type, int icon, String size, String clientId,
				int slot, boolean isXml) {
			this.type = type;
			this.icon = icon;
			this.size = size;
			this.cliendId = clientId;
			this.slot = slot;
			this.isXml = isXml;
		}
	};
	
	static AdSample[] AD_SAMPLES = new AdSample[] {
		new AdSample("Standard Banner (320x50)",	R.drawable.banner,	AdTypes.SLOT2,	"AXT002001", AdSlot.BANNER, 		false),
		new AdSample("Medium Rectangle (300x250)",	R.drawable.banner,	AdTypes.SLOT5,	"AXT005001", AdSlot.MEDIUM_RECTANGLE, 		false),
		new AdSample("Large Banner (320x100)",		R.drawable.banner,	AdTypes.SLOT6,	"AXT006001", AdSlot.LARGE_BANNER, 		false),
		new AdSample("Interstitial (fullscreen)",	R.drawable.open,	AdTypes.SLOT3,	"AXT003001", AdSlot.INTERSTITIAL, 	false),
		new AdSample("(XML)Standard Banner",		R.drawable.banner,	AdTypes.SLOT2,	"AXT002001", AdSlot.BANNER, 		true),
		new AdSample("Floating Banner (100x100)",	R.drawable.facebook,	AdTypes.SLOT4,	"AXT103001", AdSlot.FLOATING, 		false),
		new AdSample("SyrupAd중심 Mediation",		R.drawable.interstitial,	AdTypes.SLOT2,	"AX000450A", AdSlot.BANNER, 		false)
	};
}
