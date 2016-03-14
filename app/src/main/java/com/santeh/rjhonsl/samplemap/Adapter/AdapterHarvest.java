package com.santeh.rjhonsl.samplemap.Adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.R;

import java.util.List;

public class AdapterHarvest extends ArrayAdapter<CustInfoObject> {

	Context context;
	LayoutInflater inflater;
	List<CustInfoObject> ItemList;
	int positions = 0;
	String tag = "CreateNew ArrayAdapter";
	private SparseBooleanArray mSelectedItemsIds;

	public AdapterHarvest(Context context, int resourceId, List<CustInfoObject> items) {
		super(context, resourceId, items);
		mSelectedItemsIds = new SparseBooleanArray();
		this.context = context;
		this.ItemList = items;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private class ViewHolder {

		TextView id, pondid, casenum, species, dateofharvest, finalabw, totalConsumption, fcr, priceperkilo, totalHarvested, isPosted, dateRecorded;
		ExpandableLayout expandableLayout;
		ImageView imgarrow;
		LinearLayout llheader, llharvestedWrapper;
	}

	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		positions = position;

		if (view == null) {
			holder = new ViewHolder();

			view = inflater.inflate(R.layout.item_lv_harvested, null);
			holder.species = (TextView) view.findViewById(R.id.item_txt_specie);
			holder.casenum = (TextView) view.findViewById(R.id.item_txt_casenum);
			holder.dateofharvest = (TextView) view.findViewById(R.id.item_txt_dateofharvest);
			holder.finalabw = (TextView) view.findViewById(R.id.item_txt_finalabw);
			holder.totalConsumption = (TextView) view.findViewById(R.id.item_txt_totalconsumed);
			holder.fcr = (TextView) view.findViewById(R.id.item_txt_fcr);
			holder.priceperkilo = (TextView) view.findViewById(R.id.item_txt_priceoperkilo);
			holder.totalHarvested = (TextView) view.findViewById(R.id.item_txt_totalHarvested);
			holder.dateRecorded = (TextView) view.findViewById(R.id.item_txt_daterecorded);
			holder.expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandableLayout);
			holder.llheader = (LinearLayout) view.findViewById(R.id.titleHeader);
			holder.llharvestedWrapper = (LinearLayout) view.findViewById(R.id.ll_item_harvestedwrapper);
			holder.imgarrow = (ImageView) view.findViewById(R.id.item_btn_arrow);


			view.setTag(holder);
		}
		else
		{
			Log.d(tag, "if not null");
			holder = (ViewHolder) view.getTag();
		}

		holder.llheader.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				holder.expandableLayout.toggle();
				if (holder.expandableLayout.isExpanded()) {
					holder.imgarrow.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
				} else {
					holder.imgarrow.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
				}
			}
		});


		holder.species.setText(""+ItemList.get(position).getHrv_specie());
		holder.casenum.setText("Case #"+ItemList.get(position).getHrv_casenum());
		holder.dateofharvest.setText(ItemList.get(position).getHrv_dateOfHarvest());
		holder.finalabw.setText(ItemList.get(position).getHrv_finalABW());
		holder.totalConsumption.setText(ItemList.get(position).getHrv_totalConsumption());
		holder.fcr.setText(ItemList.get(position).getHrv_fcr());
		holder.priceperkilo.setText(ItemList.get(position).getHrv_pricePerKilo());
		holder.totalHarvested.setText(ItemList.get(position).getHrv_totalHarvested());
		holder.dateRecorded.setText(ItemList.get(position).getHrv_dateRecorded());

//		if (ItemList.get(position).getSpecie().equalsIgnoreCase("bangus")) {
//			holder.initialHolder.setBackground(context.getResources().getDrawable(R.drawable.bg_skyblue_oval));
//		}else if (ItemList.get(position).getSpecie().equalsIgnoreCase("tilapia")){
//			holder.initialHolder.setBackground(context.getResources().getDrawable(R.drawable.bg_amber_oval));
//		}else if (ItemList.get(position).getSpecie().equalsIgnoreCase("vannamei")){
//			holder.initialHolder.setBackground(context.getResources().getDrawable(R.drawable.bg_green_oval));
//		}else if (ItemList.get(position).getSpecie().equalsIgnoreCase("prawns")){
//			holder.initialHolder.setBackground(context.getResources().getDrawable(R.drawable.bg_red_oval));
//		}


//		holder.species.setText(ItemList.get(position).getSpecie()+" - "+ ItemList.get(position).getCurrentABW()+"g");
//		holder.quantity.setText("Quantity: "+ItemList.get(position).getQuantity()+"");
//		holder.datestocked.setText("Date Stocked: "+ItemList.get(position).getDateStocked()+"");
//		holder.pondid.setText(ItemList.get(position).getPondID()+"");

		return view;
	}

	@Override
	public void remove(CustInfoObject object) {
		ItemList.remove(object);
		notifyDataSetChanged();
	}

	public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}

	public void removeSelection() {
		mSelectedItemsIds = new SparseBooleanArray();
		notifyDataSetChanged();
	}

	public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, value);
		else
			mSelectedItemsIds.delete(position);
		notifyDataSetChanged();
	}
}
