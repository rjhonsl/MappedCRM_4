package com.santeh.rjhonsl.samplemap.Parsers;

import android.util.Log;

import com.santeh.rjhonsl.samplemap.DBase.GpsSQLiteHelper;
import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjhonsl on 3/21/2016.
 */
public class HarvestInfoParser {
    public static List<CustInfoObject> parseFeed(String content) {

        try {
            JSONArray ar = new JSONArray(content);
            List<CustInfoObject> custInfoObjectList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                CustInfoObject custInfoObject = new CustInfoObject();


                /**
                 * HARVEST INFO
                 * */

                if (obj.has(GpsSQLiteHelper.CL_HRV_ID)){
                    if (!obj.isNull(GpsSQLiteHelper.CL_HRV_ID)) {
                        custInfoObject.setHrv_id(obj.getString(GpsSQLiteHelper.CL_HRV_ID));
                    }else{
                        custInfoObject.setHrv_id(obj.getString("0"));
                    }
                }


                if (obj.has(GpsSQLiteHelper.CL_HRV_PONDID)){
                    if (!obj.isNull(GpsSQLiteHelper.CL_HRV_PONDID)) {
                        custInfoObject.setHrv_pondid(obj.getString(GpsSQLiteHelper.CL_HRV_PONDID));
                    }else{
                        custInfoObject.setHrv_pondid(obj.getString("0"));
                    }
                }


                if (obj.has(GpsSQLiteHelper.CL_HRV_CASENUM)){
                    if (!obj.isNull(GpsSQLiteHelper.CL_HRV_CASENUM)) {
                        custInfoObject.setHrv_casenum(obj.getString(GpsSQLiteHelper.CL_HRV_CASENUM));
                    }else{
                        custInfoObject.setHrv_casenum(obj.getString("0"));
                    }
                }

                if (obj.has(GpsSQLiteHelper.CL_HRV_SPECIES)){
                    if (!obj.isNull(GpsSQLiteHelper.CL_HRV_SPECIES)) {
                        custInfoObject.setHrv_specie(obj.getString(GpsSQLiteHelper.CL_HRV_SPECIES));
                    }else{
                        custInfoObject.setHrv_specie(obj.getString("0"));
                    }
                }

                if (obj.has(GpsSQLiteHelper.CL_HRV_DATEOFHARVEST)){
                    if (!obj.isNull(GpsSQLiteHelper.CL_HRV_DATEOFHARVEST)) {
                        custInfoObject.setHrv_dateOfHarvest(obj.getString(GpsSQLiteHelper.CL_HRV_DATEOFHARVEST));
                    }else{
                        custInfoObject.setHrv_dateOfHarvest(obj.getString("0"));
                    }
                }

                if (obj.has(GpsSQLiteHelper.CL_HRV_FINALABW)){
                    if (!obj.isNull(GpsSQLiteHelper.CL_HRV_FINALABW)) {
                        custInfoObject.setHrv_finalABW(obj.getString(GpsSQLiteHelper.CL_HRV_FINALABW));
                    }else{
                        custInfoObject.setHrv_finalABW(obj.getString("0"));
                    }
                }

                if (obj.has(GpsSQLiteHelper.CL_HRV_TOTAL_CONSUMPTION)){
                    if (!obj.isNull(GpsSQLiteHelper.CL_HRV_TOTAL_CONSUMPTION)) {
                        custInfoObject.setHrv_totalConsumption(obj.getString(GpsSQLiteHelper.CL_HRV_TOTAL_CONSUMPTION));
                    }else{
                        custInfoObject.setHrv_totalConsumption(obj.getString("0"));
                    }
                }

                if (obj.has(GpsSQLiteHelper.CL_HRV_FCR)){
                    if (!obj.isNull(GpsSQLiteHelper.CL_HRV_FCR)) {
                        custInfoObject.setHrv_fcr(obj.getString(GpsSQLiteHelper.CL_HRV_FCR));
                    }else{
                        custInfoObject.setHrv_fcr(obj.getString("0"));
                    }
                }

                if (obj.has(GpsSQLiteHelper.CL_HRV_PRICEPERKILO)){
                    if (!obj.isNull(GpsSQLiteHelper.CL_HRV_PRICEPERKILO)) {
                        custInfoObject.setHrv_pricePerKilo(obj.getString(GpsSQLiteHelper.CL_HRV_PRICEPERKILO));
                    }else{
                        custInfoObject.setHrv_pricePerKilo(obj.getString("0"));
                    }
                }

                if (obj.has(GpsSQLiteHelper.CL_HRV_TOTALHARVEST)){
                    if (!obj.isNull(GpsSQLiteHelper.CL_HRV_TOTALHARVEST)) {
                        custInfoObject.setHrv_totalHarvested(obj.getString(GpsSQLiteHelper.CL_HRV_TOTALHARVEST));
                    }else{
                        custInfoObject.setHrv_totalHarvested(obj.getString("0"));
                    }
                }

                if (obj.has(GpsSQLiteHelper.CL_HRV_LocalId)){
                    if (!obj.isNull(GpsSQLiteHelper.CL_HRV_LocalId)) {
                        custInfoObject.setHrv_localid(obj.getString(GpsSQLiteHelper.CL_HRV_LocalId));
                    }else{
                        custInfoObject.setHrv_localid(obj.getString("0"));
                    }
                }

                if (obj.has(GpsSQLiteHelper.CL_HRV_DATE_INSERTED)){
                    if (!obj.isNull(GpsSQLiteHelper.CL_HRV_DATE_INSERTED)) {
                        custInfoObject.setHrv_dateRecorded(obj.getString(GpsSQLiteHelper.CL_HRV_DATE_INSERTED));
                    }else{
                        custInfoObject.setHrv_dateRecorded(obj.getString("0"));
                    }
                }

                if (obj.has(GpsSQLiteHelper.CL_HRV_DateUploaded)){
                    if (!obj.isNull(GpsSQLiteHelper.CL_HRV_DateUploaded)) {
                        custInfoObject.setHrv_dateuploaded(obj.getString(GpsSQLiteHelper.CL_HRV_DateUploaded));
                    }else{
                        custInfoObject.setHrv_dateuploaded(obj.getString("0"));
                    }
                }

                custInfoObjectList.add(custInfoObject);
            }

            return custInfoObjectList;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("PARSE", "CustAndPondParse error");
            return null;
        }

    }
}