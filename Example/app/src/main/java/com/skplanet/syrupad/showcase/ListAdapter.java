package com.skplanet.syrupad.showcase;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * This is a ListAdapter that holds a ListItem.
 */
public class ListAdapter extends BaseAdapter {
	private List<ListItem> listItems;
	private static LayoutInflater inflater = null;

	public ListAdapter(Activity activity, List<ListItem> listItems) {
		this.listItems = listItems;
		ListAdapter.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public ListItem getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private static class ViewHolder {
		public ImageView adIcon;
		public TextView adType;
		public TextView adSize;
		public TextView adClientId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (convertView == null) {
			view = inflater.inflate(R.layout.grid_list_layout, null);
			holder = new ViewHolder();
			holder.adIcon = (ImageView) view.findViewById(R.id.adIcon);
			holder.adType = (TextView) view.findViewById(R.id.adType);
			holder.adSize = (TextView) view.findViewById(R.id.adSize);
			holder.adClientId = (TextView) view.findViewById(R.id.adClientId);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		ListItem listItem = this.getItem(position);
		holder.adIcon.setImageResource(listItem.getAdIcon());
		holder.adType.setText(listItem.getAdType());
		holder.adSize.setText(listItem.getAdSize());
		holder.adClientId.setText(listItem.getAdClientId());

		return view;
	}
}
