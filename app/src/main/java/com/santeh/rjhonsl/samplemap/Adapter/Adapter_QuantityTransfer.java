package com.santeh.rjhonsl.samplemap.Adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.R;

import java.util.List;

public class Adapter_QuantityTransfer extends ArrayAdapter<CustInfoObject> {

	Context context;
	LayoutInflater inflater;
	List<CustInfoObject> objArrayList;
	int positions = 0;
	String tag = "CreateNew ArrayAdapter";
	private SparseBooleanArray mSelectedItemsIds;


	public Adapter_QuantityTransfer(Context context, int resourceId, List<CustInfoObject> items) {
		super(context, resourceId, items);
		mSelectedItemsIds = new SparseBooleanArray();
		this.context = context;
		this.objArrayList = items;
//		visiblePosArray = new int[objArrayList.size()];
//		checked = new boolean[objArrayList.size()];
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//		positionArray = new ArrayList<Boolean>(objArrayList.size());
	}

	private class ViewHolder {
		TextView txtcasenumber, txtQuantity, txtSpecies, txtArea;
		LinearLayout weeknoHOlder, wrapper;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		positions = position;

		if (view == null) {

			Log.d(tag, "if null");
			holder = new ViewHolder();

			view = inflater.inflate(R.layout.item_lv_pondinfo, null);

			holder.txtcasenumber = (TextView) view.findViewById(R.id.item_lv_casenumber);
			holder.txtQuantity = (TextView) view.findViewById(R.id.item_lv_quantity);
			holder.txtSpecies = (TextView) view.findViewById(R.id.item_lv_specie);
			holder.txtArea = (TextView) view.findViewById(R.id.item_lv_area);

			holder.wrapper = (LinearLayout) view.findViewById(R.id.ll_item_weekLyReportWrapper);


			holder.weeknoHOlder = (LinearLayout) view.findViewById(R.id.weeknoHOlder);

			view.setTag(holder);
		}
		else
		{
			Log.d(tag, "if not null");
			holder = (ViewHolder) view.getTag();
		}

		holder.txtArea.setText("Area: " + objArrayList.get(positions).getArea()+"");
		holder.txtSpecies.setText("Specie: " + objArrayList.get(positions).getSpecie() + "");
		holder.txtcasenumber.setText("" + objArrayList.get(position).getPondID());
		holder.txtQuantity.setText("Qty:" + objArrayList.get(position).getQuantity());

//		holder.txtrecommended.setVisibility(View.GONE);
//		String feedtype = "";
//		if (objArrayList.get(positions).getCurrentFeedType() == "" || objArrayList.get(positions).getCurrentFeedType().isEmpty() || objArrayList.get(positions).getCurrentFeedType() == null){
//			feedtype = "N/A";
//		}else{
//			feedtype = objArrayList.get(positions).getCurrentFeedType();
//		}
//		holder.txtrecommended.setText("Recommended: " + objArrayList.get(positions).getRecommendedConsumption()+"kg");
//		holder.txtQuantity.setText("Feed Type: " + feedtype +"");
		
		return view;

	}


	@Override
	public void remove(CustInfoObject object) {
		objArrayList.remove(object);
		notifyDataSetChanged();
	}

	public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}

	public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, true);
		else
			mSelectedItemsIds.delete(position);
		notifyDataSetChanged();
	}

}
