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

public class Adapter_Growouts_PondWeekLyConsumption extends ArrayAdapter<CustInfoObject> {

	Context context;
	LayoutInflater inflater;
	List<CustInfoObject> objArrayList;
	int positions = 0;
	String tag = "CreateNew ArrayAdapter";
	private SparseBooleanArray mSelectedItemsIds;


	public Adapter_Growouts_PondWeekLyConsumption(Context context, int resourceId, List<CustInfoObject> items) {
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
		TextView txtweekno, txtfeedtype, txtabw, txtrecommended, txtremarks;
		LinearLayout weeknoHOlder, wrapper;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		positions = position;

		if (view == null) {

			Log.d(tag, "if null");
			holder = new ViewHolder();

			view = inflater.inflate(R.layout.item_lv_weeklypondsummary, null);

			holder.txtweekno = (TextView) view.findViewById(R.id.itemlv_weeklyreport_pondsummary_weeknum);
			holder.txtfeedtype = (TextView) view.findViewById(R.id.itemlv_weeklyreport_pondsummary_feedtype);
			holder.txtabw = (TextView) view.findViewById(R.id.itemlv_weeklyreport_pondsummary_abw);
			holder.txtrecommended = (TextView) view.findViewById(R.id.itemlv_weeklyreport_pondsummary_recommended);
			holder.txtremarks = (TextView) view.findViewById(R.id.itemlv_weeklyreport_pondsummary_remarks);

			holder.wrapper = (LinearLayout) view.findViewById(R.id.ll_item_weekLyReportWrapper);

//			holder.chkisVisited = (CheckBox) view.findViewById(R.id.chk_weeklyreport_pondsummary_isVisited);

			holder.weeknoHOlder = (LinearLayout) view.findViewById(R.id.weeknoHOlder);

			view.setTag(holder);
		}
		else
		{
			Log.d(tag, "if not null");
			holder = (ViewHolder) view.getTag();
		}

		holder.txtremarks.setText("Remarks: " + objArrayList.get(positions).getRemarks()+"");
		holder.txtabw.setText("ABW: " + objArrayList.get(positions).getSizeofStock() + "");
		holder.txtweekno.setText("" + (objArrayList.size() - positions)); //objArrayList.get(positions).getWeek()+""
		holder.txtrecommended.setVisibility(View.GONE);
		String feedtype = "";
		if (objArrayList.get(positions).getCurrentFeedType() == "" || objArrayList.get(positions).getCurrentFeedType().isEmpty() || objArrayList.get(positions).getCurrentFeedType() == null){
			feedtype = "N/A";
		}else{
			feedtype = objArrayList.get(positions).getCurrentFeedType();
		}
		holder.txtrecommended.setText("Recommended: " + objArrayList.get(positions).getRecommendedConsumption()+"kg");
		holder.txtfeedtype.setText("Feed Type: " + feedtype +"");

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
