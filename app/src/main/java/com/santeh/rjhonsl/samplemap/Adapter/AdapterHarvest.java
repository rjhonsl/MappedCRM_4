package com.santeh.rjhonsl.samplemap.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.santeh.rjhonsl.samplemap.Main.Activity_PondWeeklyConsumption;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
import com.santeh.rjhonsl.samplemap.R;
import com.santeh.rjhonsl.samplemap.Utils.Helper;

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

		TextView id, pondid, casenum, updatehistory ,species, dateofharvest, finalabw, totalConsumption, fcr, priceperkilo, totalHarvested, isPosted, dateRecorded;
		ExpandableLayout expandableLayout;
		ImageView imgarrow;
		LinearLayout llheader, llharvestedWrapper;
	}

	public View getView(final int position, View view, ViewGroup parent) {
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
			holder.updatehistory = (TextView) view.findViewById(R.id.txt_updatehistory);

			holder.expandableLayout.collapse();

			holder.updatehistory.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Helper.toastShort((Activity) context, ""+position);


				}
			});

			holder.imgarrow.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					holder.expandableLayout.toggle();
					holder.expandableLayout.setListener(new ExpandableLayoutListener() {
						@Override
						public void onAnimationStart() {

						}

						@Override
						public void onAnimationEnd() {

						}

						@Override
						public void onPreOpen() {

						}

						@Override
						public void onPreClose() {

						}

						@Override
						public void onOpened() {
							holder.imgarrow.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
						}

						@Override
						public void onClosed() {
							holder.imgarrow.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
						}
					});
				}
			});



			String[] splittedDate = ItemList.get(position).getHrv_dateOfHarvest().split("-");
			String dateofharvestt = Helper.convertDatetoGregorian(Integer.parseInt(splittedDate[0]), (Integer.parseInt(splittedDate[1])), (Integer.parseInt(splittedDate[2])));

			String species = ItemList.get(position).getHrv_specie();
			String casenum = ItemList.get(position).getHrv_casenum();
			String dateofharvest = dateofharvestt;
			String finalabw = ItemList.get(position).getHrv_finalABW();
			String totalConsumption = ItemList.get(position).getHrv_totalConsumption();
			String fcr = ItemList.get(position).getHrv_fcr();
			String priceperkilo = ItemList.get(position).getHrv_pricePerKilo();
			String totalharvested = ItemList.get(position).getHrv_totalHarvested();
			String dateRecorded = Helper.convertLongtoDate_Gregorian(Helper.convertDateTimeStringToMilis_DB_Format(ItemList.get(position).getHrv_dateRecorded()))+"";


			if (priceperkilo.equalsIgnoreCase("null")) {
				priceperkilo = "null";
			}else{
				priceperkilo = "P "+priceperkilo;
			}

			if (totalConsumption.equalsIgnoreCase("null")) {
				totalConsumption = "null";
			}else{
				totalConsumption = totalConsumption+"kg";
			}

			if (totalharvested.equalsIgnoreCase("null")) {
				totalharvested = "null";
			}else{
				totalharvested = totalharvested+"kg";
			}

			if (finalabw.equalsIgnoreCase("null")) {
				finalabw = "null";
			}else{
				finalabw = finalabw+"g";
			}
			if (species.equalsIgnoreCase("null")) {
				species = "null";
			}
			if (casenum.equalsIgnoreCase("null")) {
				casenum = "null";
			}else{
				casenum = "Cs. " + casenum;
			}



			holder.species.setText(species);
			holder.casenum.setText(casenum);
			holder.dateofharvest.setText(dateofharvestt);
			holder.finalabw.setText(finalabw);
			holder.totalConsumption.setText(totalConsumption);
			holder.fcr.setText(fcr);
			holder.priceperkilo.setText(priceperkilo);
			holder.totalHarvested.setText(totalharvested);
			holder.dateRecorded.setText(dateRecorded);

			view.setTag(holder);
		}
		else
		{
			Log.d(tag, "if not null");
			holder = (ViewHolder) view.getTag();
		}



		return view;
	}


	private void startPondUpdatesActivity(int position, List<CustInfoObject> infolist, String farmname, Activity activity) {
		Intent intent = new Intent(activity, Activity_PondWeeklyConsumption.class);
		intent.putExtra("farmname", farmname);
		intent.putExtra("pondid", infolist.get(position).getPondID());
		intent.putExtra("id", infolist.get(position).getId());
		intent.putExtra("specie", infolist.get(position).getSpecie());
		intent.putExtra("abw", infolist.get(position).getSizeofStock());
		intent.putExtra("survivalrate", infolist.get(position).getSurvivalrate_per_pond());
		intent.putExtra("datestocked", infolist.get(position).getDateStocked());
		intent.putExtra("quantity", infolist.get(position).getQuantity());
		intent.putExtra("area", infolist.get(position).getArea());
		intent.putExtra("culturesystem", infolist.get(position).getCulturesystem());
		intent.putExtra("remarks", infolist.get(position).getRemarks());

		intent.putExtra("latitude", infolist.get(position).getLatitude());
		intent.putExtra("longitude", infolist.get(position).getLongtitude());
		activity.startActivity(intent);
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
