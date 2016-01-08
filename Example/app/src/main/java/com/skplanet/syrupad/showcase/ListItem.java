package com.skplanet.syrupad.showcase;

/**
 * Represents an item in the list of the main activity.
 */
public class ListItem {
	private String adClientId;
	private int adSlot;
	private int adIcon;
	private String adType;
	private String adSize;
	private boolean isXml;

	public boolean isXml() {
		return isXml;
	}

	public void setXml(boolean isXml) {
		this.isXml = isXml;
	}

	public ListItem() {
		// Empty constructor.
	}

	public String getAdClientId() {
		return this.adClientId;
	}

	public void setAdClientId(String adClientId) {
		this.adClientId = adClientId;
	}

	public int getAdSlot() {
		return this.adSlot;
	}

	public void setAdSlot(int adSlot) {
		this.adSlot = adSlot;
	}

	public int getAdIcon() {
		return this.adIcon;
	}

	public void setAdIcon(int adIcon) {
		this.adIcon = adIcon;
	}

	public String getAdType() {
		return this.adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getAdSize() {
		return this.adSize;
	}

	public void setAdSize(String adSize) {
		this.adSize = adSize;
	}
}
