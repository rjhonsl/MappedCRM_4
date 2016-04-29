package com.santeh.rjhonsl.samplemap.DBase;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.santeh.rjhonsl.samplemap.Utils.Helper;


public class GPSQuery {

	private static final String LOGTAG = "GPS";
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase db;

	/********************************************
	 * 				DEFAULTS					*
	 ********************************************/
	public GPSQuery(Context context){
		//Log.d("DBSource", "db connect");
		dbhelper = new GPSHelper(context);
		//opens the db connection
	}

	public void open(){
		//Log.d("DBSource", "db open");
		if (db==null){
			db = dbhelper.getWritableDatabase();
		}else{
			if (!db.isOpen()){
				db = dbhelper.getWritableDatabase();
			}
		}


	}
	public void close(){
		//Log.d("DBSource", "db close");
		dbhelper.close();
	}



	/********************************************
	 * 				INSERTS						*
	 * 	returns index/rowNum of inserted values *
	 ********************************************/
	public void insertUserAccountInfo(int userid, int userlvl, String firstname, String lastname, String username, String password, String deviceID, String dateAdded, int isActive){
		ContentValues values = new ContentValues();
		values.put(GPSHelper.CL_USERS_ID, userid);
		values.put(GPSHelper.CL_USERS_userlvl, userlvl);
		values.put(GPSHelper.CL_USERS_lastName, lastname);
		values.put(GPSHelper.CL_USERS_firstName, firstname);
		values.put(GPSHelper.CL_USERS_username, username);
		values.put(GPSHelper.CL_USERS_password, password);
		values.put(GPSHelper.CL_USERS_deviceid, deviceID);
		values.put(GPSHelper.CL_USERS_dateAdded, dateAdded );
		values.put(GPSHelper.CL_USERS_isactive, isActive);
		db.insert(GPSHelper.TBLUSERS, null, values);
	}

	public long insertUserActivityData(int userid, String actiondone, String lat, String lng, String dateTime, String actionType){
		ContentValues values = new ContentValues();
		values.put(GPSHelper.CL_USER_ACTIVITY_USERID, userid);
		values.put(GPSHelper.CL_USER_ACTIVITY_ACTIONDONE, actiondone);
		values.put(GPSHelper.CL_USER_ACTIVITY_LAT, lat);
		values.put(GPSHelper.CL_USER_ACTIVITY_LNG, lng);
		values.put(GPSHelper.CL_USER_ACTIVITY_DATETIME, dateTime);
		values.put(GPSHelper.CL_USER_ACTIVITY_ACTIONTYPE, actionType);
		values.put(GPSHelper.CL_USER_ACTIVITY_isPosted, "0");
		return  db.insert(GPSHelper.TBLUSER_ACTIVITY, null, values);
	}

	public long insertWeeklyUpdates(String abw, String remakrs, String pondid, String dateAddedToDB, String survivalRate){

		ContentValues values = new ContentValues();
		values.put(GPSHelper.CL_WEEKLY_UPDATES_CURRENT_ABW, abw);
		values.put(GPSHelper.CL_WEEKLY_UPDATES_REMARKS, remakrs);
		values.put(GPSHelper.CL_WEEKLY_UPDATES_PONDID, pondid);
		values.put(GPSHelper.CL_WEEKLY_UPDATES_CURRENT_SURVIVALRATE, survivalRate);
		values.put(GPSHelper.CL_WEEKLY_UPDATES_DATEADDED, dateAddedToDB);
		values.put(GPSHelper.CL_WEEKLY_UPDATES_isposted, 0);

		return  db.insert(GPSHelper.TBLPOND_WeeklyUpdates, null, values);
	}

	public long insertPondData(String pondid, String specie, String sizeofStock, String survivalRate, String dateStocked, String quantity, String area, String culturesystem, String remarks, String customerid) {

		ContentValues values = new ContentValues();
		values.put(GPSHelper.CL_POND_PID, pondid);
		values.put(GPSHelper.CL_POND_specie, specie);
		values.put(GPSHelper.CL_POND_sizeofStock, sizeofStock);
		values.put(GPSHelper.CL_POND_survivalrate, survivalRate);
		values.put(GPSHelper.CL_POND_dateStocked, dateStocked);
		values.put(GPSHelper.CL_POND_quantity, quantity);
		values.put(GPSHelper.CL_POND_area, area);
		values.put(GPSHelper.CL_POND_culturesystem, culturesystem);
		values.put(GPSHelper.CL_POND_remarks, remarks);
		values.put(GPSHelper.CL_POND_customerId, customerid);
		values.put(GPSHelper.CL_POND_isPosted, 0);
		values.put(GPSHelper.CL_POND_isHarvested, 0);
		values.put(GPSHelper.CL_POND_dateInserted, Helper.convertLongtoDate_DB_Format(System.currentTimeMillis()));
		long id = db.insert(GPSHelper.TBLPOND, null, values);
		String currentTime = Helper.convertLongtoDateTime_DB_Format(System.currentTimeMillis());
		insertWeeklyUpdates(sizeofStock, remarks, id + "", currentTime, survivalRate);
		return id;
	}


	public long insertMainCustomerInformation(int userid, String lname, String mname, String fname, String farmid, String housenumber, String street,
											  String subdivision, String barangay, String city, String province, String birthday, String birthplace, String telephone,
											  String cellphone, String civilstatus, String s_fname, String s_lname, String s_mname, String s_birthday, String housestat,
											  String lat, String lng, String custtype){
		ContentValues values = new ContentValues();

		int customerType = 0;
		if (custtype.equalsIgnoreCase("distributor")){
			customerType = 1;
		}else{
			customerType = 0;
		}

		values.put(GPSHelper.CL_MAINCUSTINFO_AddedBy, userid);
		values.put(GPSHelper.CL_MAINCUSTINFO_LastName, lname);
		values.put(GPSHelper.CL_MAINCUSTINFO_FirstName, fname);
		values.put(GPSHelper.CL_MAINCUSTINFO_MiddleName, mname);
		values.put(GPSHelper.CL_MAINCUSTINFO_FarmId, farmid);
		values.put(GPSHelper.CL_MAINCUSTINFO_HouseNumber, housenumber);
		values.put(GPSHelper.CL_MAINCUSTINFO_Street, street);
		values.put(GPSHelper.CL_MAINCUSTINFO_Subdivision, subdivision);
		values.put(GPSHelper.CL_MAINCUSTINFO_Barangay, barangay);
		values.put(GPSHelper.CL_MAINCUSTINFO_City, city);
		values.put(GPSHelper.CL_MAINCUSTINFO_Province, province);
		values.put(GPSHelper.CL_MAINCUSTINFO_CBirthday, birthday);
		values.put(GPSHelper.CL_MAINCUSTINFO_CBirthPlace, birthplace);
		values.put(GPSHelper.CL_MAINCUSTINFO_Telephone, telephone);
		values.put(GPSHelper.CL_MAINCUSTINFO_Cellphone, cellphone);
		values.put(GPSHelper.CL_MAINCUSTINFO_CivilStatus, civilstatus);
		values.put(GPSHelper.CL_MAINCUSTINFO_S_FirstName, s_fname);
		values.put(GPSHelper.CL_MAINCUSTINFO_S_LastName, s_lname);
		values.put(GPSHelper.CL_MAINCUSTINFO_S_MiddleName, s_mname);
		values.put(GPSHelper.CL_MAINCUSTINFO_S_BirthDay, s_birthday);
		values.put(GPSHelper.CL_MAINCUSTINFO_HouseStatus, housestat);
		values.put(GPSHelper.CL_MAINCUSTINFO_DateAdded, Helper.getDateDBformat());
		values.put(GPSHelper.CL_MAINCUSTINFO_Latitude, lat);
		values.put(GPSHelper.CL_MAINCUSTINFO_Longitude, lng);
		values.put(GPSHelper.CL_MAINCUSTINFO_type, customerType);
		values.put(GPSHelper.CL_MAINCUSTINFO_isposted, 0);

		return  db.insert(GPSHelper.TBLMAINCUSTOMERINFO, null, values);
	}




	public long insertMainCustomerInformation_RESTORE(int userid, String lname, String mname, String fname, String farmid, String housenumber, String street,
											  String subdivision, String barangay, String city, String province, String birthday, String birthplace, String telephone,
											  String cellphone, String civilstatus, String s_fname, String s_lname, String s_mname, String s_birthday, String housestat,
											  String lat, String lng, String custtype, int mciID){
		ContentValues values = new ContentValues();

		int customerType = 0;
		if (custtype.equalsIgnoreCase("distributor")){
			customerType = 1;
		}else{
			customerType = 0;
		}


		values.put(GPSHelper.CL_MAINCUSTINFO_AddedBy, userid);
		values.put(GPSHelper.CL_MAINCUSTINFO_LastName, lname);
		values.put(GPSHelper.CL_MAINCUSTINFO_FirstName, fname);
		values.put(GPSHelper.CL_MAINCUSTINFO_MiddleName, mname);
		values.put(GPSHelper.CL_MAINCUSTINFO_FarmId, farmid);
		values.put(GPSHelper.CL_MAINCUSTINFO_HouseNumber, housenumber);
		values.put(GPSHelper.CL_MAINCUSTINFO_Street, street);
		values.put(GPSHelper.CL_MAINCUSTINFO_Subdivision, subdivision);
		values.put(GPSHelper.CL_MAINCUSTINFO_Barangay, barangay);
		values.put(GPSHelper.CL_MAINCUSTINFO_City, city);
		values.put(GPSHelper.CL_MAINCUSTINFO_Province, province);
		values.put(GPSHelper.CL_MAINCUSTINFO_CBirthday, birthday);
		values.put(GPSHelper.CL_MAINCUSTINFO_CBirthPlace, birthplace);
		values.put(GPSHelper.CL_MAINCUSTINFO_Telephone, telephone);
		values.put(GPSHelper.CL_MAINCUSTINFO_Cellphone, cellphone);
		values.put(GPSHelper.CL_MAINCUSTINFO_CivilStatus, civilstatus);
		values.put(GPSHelper.CL_MAINCUSTINFO_S_FirstName, s_fname);
		values.put(GPSHelper.CL_MAINCUSTINFO_S_LastName, s_lname);
		values.put(GPSHelper.CL_MAINCUSTINFO_S_MiddleName, s_mname);
		values.put(GPSHelper.CL_MAINCUSTINFO_S_BirthDay, s_birthday);
		values.put(GPSHelper.CL_MAINCUSTINFO_HouseStatus, housestat);
		values.put(GPSHelper.CL_MAINCUSTINFO_DateAdded, Helper.getDateDBformat());
		values.put(GPSHelper.CL_MAINCUSTINFO_Latitude, lat);
		values.put(GPSHelper.CL_MAINCUSTINFO_Longitude, lng);
		values.put(GPSHelper.CL_MAINCUSTINFO_type, customerType);
		values.put(GPSHelper.CL_MAINCUSTINFO_isposted, 1);
		values.put(GPSHelper.CL_MAINCUSTINFO_ID, mciID);

		return  db.insert(GPSHelper.TBLMAINCUSTOMERINFO, null, values);
	}



	public long insertWeeklyUpdates_RESTORE(String localid, String abw, String remakrs, String pondid, String dateAdded, String survivalRate){

		ContentValues values = new ContentValues();
		values.put(GPSHelper.CL_WEEKLY_UPDATES_ID, localid);
		values.put(GPSHelper.CL_WEEKLY_UPDATES_CURRENT_ABW, abw);
		values.put(GPSHelper.CL_WEEKLY_UPDATES_REMARKS, remakrs);
		values.put(GPSHelper.CL_WEEKLY_UPDATES_PONDID, pondid);
		values.put(GPSHelper.CL_WEEKLY_UPDATES_CURRENT_SURVIVALRATE, survivalRate);
		values.put(GPSHelper.CL_WEEKLY_UPDATES_DATEADDED, dateAdded);
		values.put(GPSHelper.CL_WEEKLY_UPDATES_isposted, 1);

		return  db.insert(GPSHelper.TBLPOND_WeeklyUpdates, null, values);
	}


	public long insertHarvestInfo_RESTORE(String pondid, String casenumber, String specie, String dateofharvest, String finalabw, String totalconsumption, String fcr, String priceperkilo
			, String totalharvest, String localid, String dateinserted){

		ContentValues values = new ContentValues();
		values.put(GPSHelper.CL_HRV_ID, localid);
		values.put(GPSHelper.CL_HRV_PONDID, pondid);
		values.put(GPSHelper.CL_HRV_CASENUM, casenumber);
		values.put(GPSHelper.CL_HRV_SPECIES, specie);
		values.put(GPSHelper.CL_HRV_DATEOFHARVEST, dateofharvest);
		values.put(GPSHelper.CL_HRV_FINALABW, finalabw);
		values.put(GPSHelper.CL_HRV_TOTAL_CONSUMPTION, totalconsumption);
		values.put(GPSHelper.CL_HRV_FCR, fcr);
		values.put(GPSHelper.CL_HRV_PRICEPERKILO, priceperkilo);
		values.put(GPSHelper.CL_HRV_TOTALHARVEST, totalharvest);
		values.put(GPSHelper.CL_HRV_DATE_INSERTED, dateinserted);
		values.put(GPSHelper.CL_HRV_ISPOSTED, 1);

		return  db.insert(GPSHelper.TBL_HARVESTINFO, null, values);
	}

	public long insertPondData_RESTORE(String localid, String pondid, String specie, String sizeofStock, String survivalRate, String dateStocked, String quantity, String area, String culturesystem, String remarks, String customerid, int isHarvested, String dateInserted) {

		ContentValues values = new ContentValues();
		values.put(GPSHelper.CL_POND_INDEX, localid );
		values.put(GPSHelper.CL_POND_PID, pondid);
		values.put(GPSHelper.CL_POND_specie, specie);
		values.put(GPSHelper.CL_POND_sizeofStock, sizeofStock);
		values.put(GPSHelper.CL_POND_survivalrate, survivalRate);
		values.put(GPSHelper.CL_POND_dateStocked, dateStocked);
		values.put(GPSHelper.CL_POND_quantity, quantity);
		values.put(GPSHelper.CL_POND_area, area);
		values.put(GPSHelper.CL_POND_culturesystem, culturesystem);
		values.put(GPSHelper.CL_POND_remarks, remarks);
		values.put(GPSHelper.CL_POND_customerId, customerid);
		values.put(GPSHelper.CL_POND_isPosted, 1);
		values.put(GPSHelper.CL_POND_isHarvested, isHarvested);
		values.put(GPSHelper.CL_POND_dateInserted, dateInserted);

		return db.insert(GPSHelper.TBLPOND, null, values);
	}



	public long insertFarmInformation(String latitude, String longitude, String contactName, String company, String address,
									  String farmname, String farmid, String contactnumber, String cultureType, String cultureLevel, String waterType, String dateAdded,
									  String userID){

		ContentValues values = new ContentValues();
//		values.put(GPSHelper.CL_FarmInfo_ID, customerid);
		values.put(GPSHelper.CL_FARMINFO_LAT, latitude);
		values.put(GPSHelper.CL_FARMINFO_LNG, longitude);
		values.put(GPSHelper.CL_FARMINFO_CONTACT_NAME, contactName);
		values.put(GPSHelper.CL_FARMINFO_COMPANY, company);
		values.put(GPSHelper.CL_FARMINFO_FARM_ADDRESS, address);
		values.put(GPSHelper.CL_FARMINFO_FARM_NAME, farmname);
		values.put(GPSHelper.CL_FARMINFO_FARM_ID, farmid);
		values.put(GPSHelper.CL_FARMINFO_C_NUMBER, contactnumber);
		values.put(GPSHelper.CL_FARMINFO_CULTYPE, cultureType);
		values.put(GPSHelper.CL_FARMINFO_CULTlVL, cultureLevel);
		values.put(GPSHelper.CL_FARMINFO_WATTYPE, waterType);
		values.put(GPSHelper.CL_FARMINFO_dateAdded, dateAdded);
		values.put(GPSHelper.CL_FARMINFO_addedby, userID);
		values.put(GPSHelper.CL_FARMINFO_IsPosted, 0);

		return  db.insert(GPSHelper.TBLFARMiNFO, null, values);
	}


	public long insertFarmInformation_RESTORE(String latitude, String longitude, String contactName, String company, String address,
									  String farmname, String farmid, String contactnumber, String cultureType, String cultureLevel, String waterType, String dateAdded,
									  String userID, int id){

		ContentValues values = new ContentValues();
		values.put(GPSHelper.CL_FarmInfo_ID, id);
		values.put(GPSHelper.CL_FARMINFO_LAT, latitude);
		values.put(GPSHelper.CL_FARMINFO_LNG, longitude);
		values.put(GPSHelper.CL_FARMINFO_CONTACT_NAME, contactName);
		values.put(GPSHelper.CL_FARMINFO_COMPANY, company);
		values.put(GPSHelper.CL_FARMINFO_FARM_ADDRESS, address);
		values.put(GPSHelper.CL_FARMINFO_FARM_NAME, farmname);
		values.put(GPSHelper.CL_FARMINFO_FARM_ID, farmid);
		values.put(GPSHelper.CL_FARMINFO_C_NUMBER, contactnumber);
		values.put(GPSHelper.CL_FARMINFO_CULTYPE, cultureType);
		values.put(GPSHelper.CL_FARMINFO_CULTlVL, cultureLevel);
		values.put(GPSHelper.CL_FARMINFO_WATTYPE, waterType);
		values.put(GPSHelper.CL_FARMINFO_dateAdded, dateAdded);
		values.put(GPSHelper.CL_FARMINFO_addedby, userID);
		values.put(GPSHelper.CL_FARMINFO_IsPosted, 1);

		return  db.insert(GPSHelper.TBLFARMiNFO, null, values);
	}




	public long insertHarvestInfo(String pondid, String casenum, String species, String dateofharvest,
								  String finalabw, String totalconsumption, String fcr, String priceperkilo, String totalharvested){

		ContentValues values = new ContentValues();
		values.put(GPSHelper.CL_HRV_PONDID, pondid);
		values.put(GPSHelper.CL_HRV_CASENUM, casenum);
		values.put(GPSHelper.CL_HRV_SPECIES, species);
		values.put(GPSHelper.CL_HRV_DATEOFHARVEST, dateofharvest);
		values.put(GPSHelper.CL_HRV_FINALABW, finalabw);
		values.put(GPSHelper.CL_HRV_TOTAL_CONSUMPTION, totalconsumption);
		values.put(GPSHelper.CL_HRV_FCR, fcr);
		values.put(GPSHelper.CL_HRV_PRICEPERKILO, priceperkilo);
		values.put(GPSHelper.CL_HRV_TOTALHARVEST, totalharvested);
		values.put(GPSHelper.CL_HRV_DATE_INSERTED, Helper.convertLongtoDateTime_DB_Format(System.currentTimeMillis()));
		values.put(GPSHelper.CL_HRV_ISPOSTED,"0");

		return  db.insert(GPSHelper.TBL_HARVESTINFO, null, values);
	}



	/********************************************
	 * 				VALIDATIONS					*
	 ********************************************/
	public int selectUserinDB(String userID){
		String query = "SELECT * FROM "+ GPSHelper.TBLUSERS+" WHERE "+ GPSHelper.CL_USERS_ID+" = ?;";
		String[] params = new String[] {userID};
//		rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}


	/********************************************
	 * 				SELECTS						*
	 ********************************************/
	public Cursor getUserIdByLogin(String username, String password, String deviceid){
		String query = "SELECT * FROM "+ GPSHelper.TBLUSERS+" WHERE "
				+ GPSHelper.CL_USERS_username + " = ? AND "
				+ GPSHelper.CL_USERS_password + " = ? AND "
				+ GPSHelper.CL_USERS_deviceid + " = ? "
				;
		String[] params = new String[] {username, password, deviceid };
		return db.rawQuery(query, params);
	}


	public boolean isFarmnamePost(String clientID){
		boolean isexisting = false;
		String query = "SELECT * FROM "+ GPSHelper.TBLFARMiNFO +" WHERE "
				+ GPSHelper.CL_FarmInfo_ID + " = ? "
				+ " AND "
				+ GPSHelper.CL_FARMINFO_IsPosted + " = 1 "
				;


		String[] params = new String[] {clientID};
		Cursor cur = db.rawQuery(query, params);
		if (cur!=null){
			if (cur.getCount() > 0){
				isexisting = true;
			}
		}
		Log.d("isPosted", "" + cur.getCount()+ " " + clientID);
		return  isexisting;
	}





	public String getSQLStringForInsert_UNPOSTED_FARMINFO(Activity activity) {
		String sqlString = "" +
				"INSERT INTO `tblCustomerInfo` (`ci_customerId` , `latitude`, `longtitude`, `contact_name`, `company`, `address` , `farm_name` , `farmid` , `contact_number` , `culture_type` , `culture_level`, `water_type`, `dateAdded`, `addedby`, `lid`) VALUES ";
		String query = "SELECT * FROM " + GPSHelper.TBLFARMiNFO + " WHERE "
				+ GPSHelper.CL_FARMINFO_IsPosted + " = 0 AND " +
				GPSHelper.CL_FARMINFO_addedby + " = " + Helper.variables.getGlobalVar_currentUserID(activity);
		String[] params = new String[]{};
		Cursor cur = db.rawQuery(query, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {

				String contactName = cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_CONTACT_NAME)).replaceAll("'", "\\'");
				String company = cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_COMPANY)).replaceAll("'", "\\'");
				String address = cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_FARM_ADDRESS)).replaceAll("'", "\\'");
				String farmname = cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_FARM_NAME)).replaceAll("'", "\\''");
				String farmid = cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_FARM_ID)).replaceAll("'", "\\'");
				String contactnumber = cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_C_NUMBER)).replaceAll("'", "\\'");
				String cultype = cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_CULTYPE)).replaceAll("'", "\\'");
				String cullvl = cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_CULTlVL)).replaceAll("'", "\\'");
				String wattype = cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_WATTYPE)).replaceAll("'", "\\'");
				String dateadded = cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_dateAdded)).replaceAll("'", "\\'");
				String addedby = cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_addedby)).replaceAll("'", "\\'");

				sqlString = sqlString +
						"( '"+cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_addedby))+"-"+cur.getInt(cur.getColumnIndex(GPSHelper.CL_FarmInfo_ID))+"',  " +
						"'"+cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_LAT))+"',  " +
						"'"+cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_LNG))+"',  " +
						"'"+contactName+"',  " +
						"'"+company+"',  " +
						"'"+address+"',  " +
						"'"+farmname+"',  " +
						"'"+farmid+"',  " +
						"'"+contactnumber+"',  " +
						"'"+cultype+"',  " +
						"'"+cullvl+"',  " +
						"'"+wattype+"',  " +
						"'"+dateadded+"',  " +
						"'"+addedby+"', " +
						"'"+cur.getInt(cur.getColumnIndex(GPSHelper.CL_FarmInfo_ID))+"' ),";
			}
		}

		String strSql = sqlString.substring(0, sqlString.length() - 1);
		strSql = strSql + " " +
				"ON DUPLICATE KEY UPDATE " +
				" 	 latitude = VALUES(latitude), " +
				"    longtitude = VALUES(longtitude), " +
				"    contact_name = VALUES(contact_name), " +
				"    company = VALUES(company), " +
				"    farm_name = VALUES(farm_name), " +
				"    address = VALUES(address), " +
				"    farmid = VALUES(farmid), " +
				"    contact_number = VALUES(contact_number), " +
				"    culture_type = VALUES(culture_type), " +
				"    culture_level = VALUES(culture_level), " +
				"    water_type = VALUES(water_type), " +
				"    dateAdded = VALUES(dateAdded), " +
				"    addedby = VALUES(addedby)";

		return strSql;
	}


	public String getSQLStringForInsert_UNPOSTED_CustomerINFO(Activity activity) {
		String sqlString = "" +
				"INSERT INTO `tblmaincustomerinfo` (`mci_id`, `mci_lname`, `mci_fname`, `mci_mname`, `mci_farmid`, `mci_housenumber`, `mci_street`, `mci_subdivision`, `mci_barangay`, `mci_city`, `mci_province`, `mci_customerbirthday`, `mci_birthplace`, `mci_telephone`, `mci_cellphone`, `mci_civilstatus`, `mci_s_fname`, `mci_s_lname`, `mci_s_mname`, `mci_s_birthday`, `mci_housestatus`, `mci_latitude`, `mci_longitude`, `mci_dateadded`, `mci_addedby`, `mci_lid`, `mci_type`)  VALUES ";
		String query = "SELECT * FROM " + GPSHelper.TBLMAINCUSTOMERINFO + " WHERE "
				+ GPSHelper.CL_MAINCUSTINFO_isposted + " = 0 AND " +
				GPSHelper.CL_MAINCUSTINFO_AddedBy + " = " + Helper.variables.getGlobalVar_currentUserID(activity);

		String[] params = new String[]{};
		Cursor cur = db.rawQuery(query, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String  mci_lname = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_LastName)).replaceAll("'", "\\'");
				String  mci_fname = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_FirstName)).replaceAll("'", "\\'");

				String  mci_mname = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_MiddleName)).replaceAll("'", "\\'"),
						mci_farmid = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_FarmId)).replaceAll("'", "\\'"),
						mci_housenumber = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_HouseNumber)).replaceAll("'", "\\'"),
						mci_street = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Street)).replaceAll("'", "\\'"),
						mci_subdivision = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Subdivision)).replaceAll("'", "\\'"),
						mci_barangay = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Barangay)).replaceAll("'", "\\'"),
						mci_city = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_City)).replaceAll("'", "\\'"),
						mci_province = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Province)).replaceAll("'", "\\'"),
						mci_customerbirthday = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_CBirthday)).replaceAll("'", "\\'"),
						mci_birthplace = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_CBirthPlace)).replaceAll("'", "\\'"),
						mci_telephone = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Telephone)).replaceAll("'", "\\'"),
						mci_cellphone = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Cellphone)).replaceAll("'", "\\'"),
						mci_civilstatus = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_CivilStatus)).replaceAll("'", "\\'"),
						mci_s_fname = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_S_FirstName)).replaceAll("'", "\\'"),
						mci_s_lname = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_S_LastName)).replaceAll("'", "\\'"),
						mci_s_mname = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_S_MiddleName)).replaceAll("'", "\\'"),
						mci_s_birthday = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_S_BirthDay)).replaceAll("'", "\\'"),
						mci_housestatus = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_HouseStatus)).replaceAll("'", "\\'"),
						mci_latitude = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Latitude)).replaceAll("'", "\\'"),
						mci_longitude =  cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_Longitude)).replaceAll("'", "\\'"),
						mci_dateadded =  cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_DateAdded)).replaceAll("'", "\\'"),
						mci_addedby =  cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_AddedBy)).replaceAll("'", "\\'"),
						mci_type = cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_type)).replaceAll("'", "\\'");

				sqlString = sqlString +
						"( '"+cur.getString(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_AddedBy))+"-"+cur.getInt(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_ID))+"',  " +
						"'"+mci_lname+"',  " +
						"'"+mci_fname+"',  " +
						"'"+mci_mname+"',  " +
						"'"+mci_farmid+"',  " +
						"'"+mci_housenumber+"',  " +
						"'"+mci_street+"',  " +
						"'"+mci_subdivision+"',  " +
						"'"+mci_barangay+"',  " +
						"'"+mci_city+"',  " +
						"'"+mci_province+"',  " +
						"'"+mci_customerbirthday+"',  " +
						"'"+mci_birthplace+"',  " +
						"'"+mci_telephone+"',  " +
						"'"+mci_cellphone+"',  " +
						"'"+mci_civilstatus+"',  " +
						"'"+mci_s_fname+"',  " +
						"'"+mci_s_lname+"',  " +
						"'"+mci_s_mname+"',  " +
						"'"+mci_s_birthday+"',  " +
						"'"+mci_housestatus+"',  " +
						"'"+mci_latitude+"',  " +
						"'"+mci_longitude+"',  " +
						"'"+mci_dateadded+"',  " +
						"'"+mci_addedby+"', " +
						"'"+cur.getInt(cur.getColumnIndex(GPSHelper.CL_MAINCUSTINFO_ID))+"', " +
						"'"+mci_type+"' ),";
			}
		}

		String strSql = sqlString.substring(0, sqlString.length() - 1);
		strSql = strSql + " " +
				"ON DUPLICATE KEY UPDATE " +
				" 	 mci_lname = VALUES(mci_lname), " +
				"    mci_fname = VALUES(mci_fname), " +
				"    mci_mname = VALUES(mci_mname), " +
				"    mci_farmid = VALUES(mci_farmid), " +
				"    mci_housenumber = VALUES(mci_housenumber), " +
				"    mci_street = VALUES(mci_street), " +
				"    mci_subdivision = VALUES(mci_subdivision), " +
				"    mci_barangay = VALUES(mci_barangay), " +
				"    mci_city = VALUES(mci_city), " +
				"    mci_province = VALUES(mci_province), " +
				"    mci_customerbirthday = VALUES(mci_customerbirthday), " +
				"    mci_birthplace = VALUES(mci_birthplace), " +
				"    mci_telephone = VALUES(mci_telephone), " +
				"    mci_cellphone = VALUES(mci_cellphone), " +
				"    mci_civilstatus = VALUES(mci_civilstatus), " +
				"    mci_s_fname = VALUES(mci_s_fname), " +
				"    mci_s_lname = VALUES(mci_s_lname), " +
				"    mci_s_mname = VALUES(mci_s_mname), " +
				"    mci_s_birthday = VALUES(mci_s_birthday), " +
				"    mci_housestatus = VALUES(mci_housestatus), " +
				"    mci_latitude = VALUES(mci_latitude), " +
				"    mci_longitude = VALUES(mci_longitude), " +
				"    mci_dateadded = VALUES(mci_dateadded), " +
				"    mci_addedby = VALUES(mci_addedby), " +
				"    mci_lid = VALUES(mci_lid), " +
				"    mci_type = VALUES(mci_type)";

		return strSql;
	}



	public String getUserIdOfPond(String pondid) {
		String addedby = "";
		String query =
				"SELECT tblCustomerInfo.addedby FROm tblPond \n" +
				"INNER JOIN tblCustomerInfo ON tblCustomerInfo.ci_customerid = tblPond.customerId \n" +
				"WHERE tblPond.id = ?;";

		String[] params = new String[] {pondid};
		Cursor cur = db.rawQuery(query, params);
		if (cur.getCount()> 0){
			while (cur.moveToNext()) {
				addedby = cur.getString(cur.getColumnIndex(GPSHelper.CL_FARMINFO_addedby));
			}
		}else {
			addedby = "0";
		}
		return addedby;
	}

	public String getSQLStringForInsert_UNPOSTED_POND(Activity activity) {
		String sqlString = " " +
				"INSERT INTO `tblPond` (`id`, `pondid`, `specie`, `sizeofStock`, `survivalrate`, `dateStocked`, `quantity`, `area`, `culturesystem`, `remarks`, `customerId`, `p_lid`, `isharvested`, "+
				GPSHelper.CL_POND_dateInserted+", "+ GPSHelper.CL_POND_dateuploaded+") VALUES  ";
		String query = "SELECT * FROM " + GPSHelper.TBLPOND + " WHERE "
				+ GPSHelper.CL_POND_isPosted + " = 0 ";
		String[] params = new String[]{};
		Cursor cur = db.rawQuery(query, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String tempid = getUserIdOfPond( cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_INDEX))+"") + "-" + cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_INDEX));
				String id = tempid.replaceAll("'", "\\'");
				String pondid = cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_PID)).replaceAll("'", "\\'");
				String specie = cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_specie)).replaceAll("'", "\\'");
				String sizeofStock = cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_sizeofStock)).replaceAll("'", "\\'");
				String survivalrate = cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_survivalrate)).replaceAll("'", "\\'");
				String dateStocked = cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_dateStocked)).replaceAll("'", "\\'");
				String quantity = cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_quantity)).replaceAll("'", "\\'");
				String area = cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_area)).replaceAll("'", "\\'");
				String culturesystem = cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_culturesystem)).replaceAll("'", "\\'");
				String remarks = cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_remarks)).replaceAll("'", "\\'");
				String customerId = getUserIdOfPond( cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_INDEX))+"") + "-" + cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_customerId)).replaceAll("'", "\\'");
				String plid = cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_INDEX)).replaceAll("'", "\\'");
				String isHarvested = cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_isHarvested)).replaceAll("'", "\\'");
				String dateInserted = cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_dateInserted)).replaceAll("'", "\\'");
				String dateuploaded = Helper.convertLongtoDate_DB_Format(System.currentTimeMillis());

				sqlString = sqlString +
						"( '" + id + "',  " +
						"'"+pondid+"', " +
						"'"+specie+"', " +
						"'"+sizeofStock+"', " +
						"'"+survivalrate+"', " +
						"'"+dateStocked+"', " +
						"'"+quantity+"', " +
						"'"+area+"', " +
						"'"+culturesystem+"', " +
						"'"+remarks+"', " +
						"'"+customerId+"', " +
						"'"+plid+"', " +
						"'"+isHarvested+"', " +
						"'"+dateInserted+"', " +
						"'" +dateuploaded+ "' ),";
			}
		}


		String strSql = sqlString.substring(0, sqlString.length() - 1);
		strSql = strSql + " " +
				" ON DUPLICATE KEY UPDATE " +
				" 	 pondid = VALUES(pondid), " +
				"    specie = VALUES(specie), " +
				"    sizeofStock = VALUES(sizeofStock), " +
				"    survivalrate = VALUES(survivalrate), " +
				"    dateStocked = VALUES(dateStocked), " +
				"    quantity = VALUES(quantity), " +
				"    area = (area), " +
				"    culturesystem = VALUES(culturesystem), " +
				"    remarks = VALUES(remarks), " +
				"    customerId = VALUES(customerId), " +
				"    isharvested = VALUES(isharvested), " +
				"    "+ GPSHelper.CL_POND_dateInserted+" = VALUES("+ GPSHelper.CL_POND_dateInserted+"), " +
				"    "+ GPSHelper.CL_POND_dateuploaded+" = VALUES("+ GPSHelper.CL_POND_dateuploaded+"), " +
				"    p_lid = VALUES(p_lid)";

		return strSql;
	}




	public String getSQLStringForInsert_UNPOSTED_WEEKLY() {
		String sqlString = " " +
				"INSERT INTO `tblpond_weeklyupdates` (`wu_id`, `wu_currentabw`,`wu_survivalRate`, `wu_remakrs`, `wu_pondid`, `wu_dateAdded`, `wu_lid`) VALUES ";
		String query = "SELECT * FROM " + GPSHelper.TBLPOND_WeeklyUpdates + " WHERE "
				+ GPSHelper.CL_WEEKLY_UPDATES_isposted+ " = 0 ";
		String[] params = new String[]{};
		Cursor cur = db.rawQuery(query, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String tempid = getUserIdOfPond(cur.getInt(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_PONDID))+"") + "-" + cur.getInt(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_ID));
				String wu_id = tempid.replaceAll("'", "\\'");
				String wu_currentabw = cur.getString(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_CURRENT_ABW)).replaceAll("'", "\\'");
				String wu_remakrs = cur.getString(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_REMARKS)).replaceAll("'", "\\'");
				String wu_survivalRate = cur.getString(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_CURRENT_SURVIVALRATE)).replaceAll("'", "\\'");
				String wu_pondid =  getUserIdOfPond(cur.getInt(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_PONDID))+"") + "-" +cur.getString(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_PONDID)).replaceAll("'", "\\'");
				String wu_dateAdded = cur.getString(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_DATEADDED)).replaceAll("'", "\\'");
				String wu_lid = cur.getString(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_ID)).replaceAll("'", "\\'");
				sqlString = sqlString +
						"( '" + wu_id + "',  " +
						"'"+wu_currentabw+"', " +
						"'"+wu_survivalRate+"', " +
						"'"+wu_remakrs+"', " +
						"'"+wu_pondid+"', " +
						"'"+wu_dateAdded+"', " +
						"'" +wu_lid+ "' ),";
			}
		}

		String strSql = sqlString.substring(0, sqlString.length() - 1);
		strSql = strSql + " " +
				" ON DUPLICATE KEY UPDATE " +
				" 	 wu_id = VALUES(wu_id), " +
				"    wu_currentabw = VALUES(wu_currentabw), " +
				"    wu_survivalRate = VALUES(wu_survivalRate), " +
				"    wu_remakrs = VALUES(wu_remakrs), " +
				"    wu_pondid = VALUES(wu_pondid), " +
				"    wu_dateAdded = VALUES(wu_dateAdded), " +
				"    wu_lid = VALUES(wu_lid)";

		return strSql;
	}



	public String getSQLStringForInsert_UNPOSTED_HARVESTINFO() {
		String sqlString = " " +
				"INSERT INTO `"+ GPSHelper.TBL_HARVESTINFO+"` " +
				"(`"+ GPSHelper.CL_HRV_ID+"`, " +
				"`"+ GPSHelper.CL_HRV_PONDID+"`," +
				"`"+ GPSHelper.CL_HRV_CASENUM+"`," +
				"`"+ GPSHelper.CL_HRV_SPECIES+"`," +
				"`"+ GPSHelper.CL_HRV_DATEOFHARVEST +"`," +
				"`"+ GPSHelper.CL_HRV_FINALABW+"`," +
				"`"+ GPSHelper.CL_HRV_TOTAL_CONSUMPTION+"`," +
				"`"+ GPSHelper.CL_HRV_FCR+"`," +
				"`"+ GPSHelper.CL_HRV_PRICEPERKILO+"`," +
				"`"+ GPSHelper.CL_HRV_TOTALHARVEST+"`," +
				"`"+ GPSHelper.CL_HRV_LocalId+"`," +
				"`"+ GPSHelper.CL_HRV_DATE_INSERTED+"`," +
				"`"+ GPSHelper.CL_HRV_DateUploaded+"`) VALUES ";

		String query = " SELECT * FROM "+ GPSHelper.TBL_HARVESTINFO+" INNER JOIN " + GPSHelper.TBLPOND +
				" ON " + GPSHelper.CL_HRV_PONDID + " = "+ GPSHelper.CL_POND_INDEX+" " +
				" WHERE " + GPSHelper.CL_HRV_ISPOSTED+ " = '0' ";


		String[] params = new String[]{};
		Cursor cur = db.rawQuery(query, null);
		Log.d("SQL_STRING", "after raw query " + query);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				Log.d("SQL_STRING", "on curmove next. " +cur.getCount());
				String tempid = getUserIdOfPond(cur.getInt(cur.getColumnIndex(GPSHelper.CL_HRV_PONDID)) + "") + "-" + cur.getInt(cur.getColumnIndex(GPSHelper.CL_HRV_ID));
				String hrv_id = tempid.replaceAll("'", "\\'");
				String hrv_pondID = cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_PONDID)).replaceAll("'", "\\'");
				String hrv_caseNumber = cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_CASENUM)).replaceAll("'", "\\'");
				String hrv_species = cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_SPECIES)).replaceAll("'", "\\'");
				String hrv_dateOfHarvest = cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_DATEOFHARVEST)).replaceAll("'", "\\'");
				String hrv_finalabw = cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_FINALABW)).replaceAll("'", "\\'");
				String hrv_totalConsumption = cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_TOTAL_CONSUMPTION)).replaceAll("'", "\\'");
				String hrv_fcr = cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_FCR)).replaceAll("'", "\\'");
				String hrv_pricePerKilo = cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_PRICEPERKILO)).replaceAll("'", "\\'");
				String hrv_totalHarvested = cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_TOTALHARVEST)).replaceAll("'", "\\'");
				String hrv_localid = cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_ID)).replaceAll("'", "\\'");
				String hrv_dateInserted = cur.getString(cur.getColumnIndex(GPSHelper.CL_HRV_DATE_INSERTED)).replaceAll("'", "\\'");
				String hrv_dateUploaded = Helper.convertLongtoDateTime_DB_Format(System.currentTimeMillis());


				sqlString = sqlString +
						"( '" + hrv_id + "',  " +
						"'" + hrv_pondID + "', " +
						"'" + hrv_caseNumber + "', " +
						"'" + hrv_species + "', " +
						"'" + hrv_dateOfHarvest + "', " +
						"'" + hrv_finalabw + "', " +
						"'" + hrv_totalConsumption + "', " +
						"'" + hrv_fcr + "', " +
						"'" + hrv_pricePerKilo + "', " +
						"'" + hrv_totalHarvested + "', " +
						"'" + hrv_localid + "', " +
						"'" + hrv_dateInserted + "', " +
						"'" + hrv_dateUploaded + "' ),";
			}
		}

		String strSql = sqlString.substring(0, sqlString.length() - 1);
		strSql = strSql + " " +
				" ON DUPLICATE KEY UPDATE " +
				" 	 "+ GPSHelper.CL_HRV_ID+" = VALUES("+ GPSHelper.CL_HRV_ID+"), " +
				"    "+ GPSHelper.CL_HRV_PONDID+" = VALUES("+ GPSHelper.CL_HRV_PONDID+"), " +
				"    "+ GPSHelper.CL_HRV_CASENUM+" = VALUES("+ GPSHelper.CL_HRV_CASENUM+"), " +
				"    "+ GPSHelper.CL_HRV_SPECIES+" = VALUES("+ GPSHelper.CL_HRV_SPECIES+"), " +
				"    "+ GPSHelper.CL_HRV_DATEOFHARVEST+" = VALUES("+ GPSHelper.CL_HRV_DATEOFHARVEST+"), " +
				"    "+ GPSHelper.CL_HRV_FINALABW+" = VALUES("+ GPSHelper.CL_HRV_FINALABW+"), " +
				"    "+ GPSHelper.CL_HRV_TOTAL_CONSUMPTION+" = VALUES("+ GPSHelper.CL_HRV_TOTAL_CONSUMPTION+"), " +
				"    "+ GPSHelper.CL_HRV_FCR+" = VALUES("+ GPSHelper.CL_HRV_FCR+"), " +
				"    "+ GPSHelper.CL_HRV_PRICEPERKILO+" = VALUES("+ GPSHelper.CL_HRV_PRICEPERKILO+"), " +
				"    "+ GPSHelper.CL_HRV_TOTALHARVEST+" = VALUES("+ GPSHelper.CL_HRV_TOTALHARVEST+"), " +
				"    "+ GPSHelper.CL_HRV_LocalId+" = VALUES("+ GPSHelper.CL_HRV_LocalId+"), " +
				"    "+ GPSHelper.CL_HRV_DATE_INSERTED+" = VALUES("+ GPSHelper.CL_HRV_DATE_INSERTED+"), " +
				"    "+ GPSHelper.CL_HRV_DateUploaded+" = VALUES("+ GPSHelper.CL_HRV_DateUploaded+")";

		return strSql;
	}


	public String getSQLStringForInsert_UNPOSTED_USERACTIVITY() {
		String sqlString = "" +
				"INSERT INTO `tbluser_activity` (`user_act_id`, `user_act_userid`, `user_act_actiondone`, `user_act_latitude`, `user_act_longitude`, `user_act_datetime`, `user_act_actiontype`) VALUES ";
		String query = "SELECT * FROM " + GPSHelper.TBLUSER_ACTIVITY + " WHERE "
				+ GPSHelper.CL_USER_ACTIVITY_isPosted+ " = 0 ";

		String[] params = new String[]{};
		Cursor cur = db.rawQuery(query, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String actionDone = cur.getString(cur.getColumnIndex(GPSHelper.CL_USER_ACTIVITY_ACTIONDONE)).replaceAll("'","\\'");

				String user_act_id = "";
				String user_act_userid = cur.getString(cur.getColumnIndex(GPSHelper.CL_USER_ACTIVITY_USERID)) + "";
				String user_act_actiondone = actionDone.replace("'", "\\'");
				String user_act_latitude = cur.getString(cur.getColumnIndex(GPSHelper.CL_USER_ACTIVITY_LAT)).replaceAll("'", "\\'");
				String user_act_longitude = cur.getString(cur.getColumnIndex(GPSHelper.CL_USER_ACTIVITY_LNG)).replaceAll("'", "\\'");
				String user_act_datetime = cur.getString(cur.getColumnIndex(GPSHelper.CL_USER_ACTIVITY_DATETIME)).replaceAll("'", "\\'");
				String user_act_actiontype = cur.getString(cur.getColumnIndex(GPSHelper.CL_USER_ACTIVITY_ACTIONTYPE)).replaceAll("'", "\\'");
				sqlString = sqlString +
						"( '',  " +
						"'" + user_act_userid + "', " +
						"'" + user_act_actiondone + "', " +
						"'" + user_act_latitude + "', " +
						"'" + user_act_longitude + "', " +
						"'" + user_act_datetime + "', " +
						"'" + user_act_actiontype + "' ),";
			}
		}

		return sqlString.substring(0, sqlString.length() - 1);
	}




	public int getFarmInfo_notPosted_Count(Activity activity){
		String query = "SELECT * FROM "+ GPSHelper.TBLFARMiNFO+" WHERE "
				+ GPSHelper.CL_FARMINFO_IsPosted + " = 0 AND "
				+ GPSHelper.CL_FARMINFO_addedby + " = " + Helper.variables.getGlobalVar_currentUserID(activity)
				;
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}


	public int getCustInfo_notPosted_Count(Activity activity){
		String query = "SELECT * FROM "+ GPSHelper.TBLMAINCUSTOMERINFO+" WHERE "
				+ GPSHelper.CL_MAINCUSTINFO_isposted+ " = 0 AND "
				+ GPSHelper.CL_MAINCUSTINFO_AddedBy + " = " + Helper.variables.getGlobalVar_currentUserID(activity)
				;
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}


	public int getPond_notPosted_Count(Activity activity){
		String query = "SELECT * FROM "+ GPSHelper.TBLPOND+" WHERE "
				+ GPSHelper.CL_POND_isPosted+ " = 0 ";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getWeeklyPosted_notPosted_Count(Activity activity){
		String query = "SELECT * FROM "+ GPSHelper.TBLPOND_WeeklyUpdates+" WHERE "
				+ GPSHelper.CL_WEEKLY_UPDATES_isposted+ " = 0 ";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getHarvestPosted_notPosted_Count(Activity activity){
		String query = "SELECT * FROM "+ GPSHelper.TBL_HARVESTINFO + " WHERE "
				+ GPSHelper.CL_HRV_ISPOSTED+ " = '0'";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getUserActivity_notPosted_Count(Activity activity){
		String query = "SELECT * FROM "+ GPSHelper.TBLUSER_ACTIVITY+" WHERE "
				+ GPSHelper.CL_USER_ACTIVITY_isPosted+ " = 0 ";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}



	public int getHarvestInfo_notPosted_Count(Activity activity){
		String query = "SELECT * FROM "+ GPSHelper.TBL_HARVESTINFO+" WHERE "
				+ GPSHelper.CL_HRV_ISPOSTED+ " = '0' "
				;
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}


	public Cursor getAll_FARMINFO_LEFTJOIN_PONDINFO_LEFTJOIN_CUSTOMERINFO(String userid){
		String query = "SELECT \n" +
				"tblCustomerInfo.*, \n" +
				"tblPond.*, \n" +
				"tblmaincustomerinfo.*, \n" +
				"SUM(tblPond.quantity) AS Totalquantity, \n" +

				"(SELECT DISTINCT GROUP_CONCAT( DISTINCT tblPond.specie ) \n" +
				"FROM tblPond WHERE tblPond.customerId = tblCustomerInfo.ci_customerId ORDER BY tblPond.specie ASC) AS allSpecie \n" +

				"FROM tblCustomerInfo \n" +

				"LEFT JOIN  tblPond \n" +
				"ON tblPond.customerId = tblCustomerInfo.ci_customerId \n" +

				"LEFT JOIN  tblmaincustomerinfo \n" +
				"ON tblCustomerInfo.farmid = tblmaincustomerinfo.mci_farmid \n"+

				"WHERE tblCustomerInfo.addedby = ? \n" +

				"GROUP BY "+ GPSHelper.CL_FarmInfo_ID +"  \n" +
				"ORDER BY "+ GPSHelper.CL_FarmInfo_ID +"  ASC;"
				;
		String[] params =  new String[] {userid};
		return db.rawQuery(query, params);
	}


	public Cursor getFARM_POND_CUSTOMER_BY_FARMID(String userid, String farmid){
		String query = "SELECT \n" +
				"tblCustomerInfo.*, \n" +
				"tblPond.*, \n" +
				"tblmaincustomerinfo.*, \n" +
				"SUM(tblPond.quantity) AS Totalquantity, \n" +

				"(SELECT DISTINCT GROUP_CONCAT( DISTINCT tblPond.specie ) \n" +
				"FROM tblPond WHERE tblPond.customerId = tblCustomerInfo.ci_customerId ORDER BY tblPond.specie ASC) AS allSpecie \n" +

				"FROM tblCustomerInfo \n" +

				"LEFT JOIN  tblPond \n" +
				"ON tblPond.customerId = tblCustomerInfo.ci_customerId \n" +

				"LEFT JOIN  tblmaincustomerinfo \n" +
				"ON tblCustomerInfo.farmid = tblmaincustomerinfo.mci_farmid \n"+

				"WHERE tblCustomerInfo.addedby = ? \n" +
				"AND tblCustomerInfo.farmid = ? \n" +

				"GROUP BY "+ GPSHelper.CL_FarmInfo_ID +"  \n" +
				"ORDER BY "+ GPSHelper.CL_FarmInfo_ID +"  ASC;"
				;
		String[] params =  new String[] {userid, farmid};
		return db.rawQuery(query, params);
	}

	public Cursor getCUST_LOCATION_BY_ASSIGNED_AREA(String userid){
		String query =
				"Select tblmaincustomerinfo.* from tblmaincustomerinfo \n" +
				"WHERE tblmaincustomerinfo.mci_addedby= ? ;";

		String[] params =  new String[] {userid};
		return db.rawQuery(query, params);
	}


	public Cursor getLocal_PondWeeklyUpdates(String pondid){
		String query =
				"SELECT tblpond_weeklyupdates.* FROM tblpond_weeklyupdates \n" +
						"WHERE tblpond_weeklyupdates.wu_pondid = ?" +
						"ORDER BY tblpond_weeklyupdates.wu_id DESC ;";

		String[] params =  new String[] {pondid};
		return db.rawQuery(query, params);
	}


	public Cursor getCUST_LOCATION_BY_indexID(String index){
		String query = "Select tblmaincustomerinfo.* from tblmaincustomerinfo " +
						"WHERE tblmaincustomerinfo.mci_id= "+index+";";

		String[] params =  new String[] {index};
		return db.rawQuery(query, null);
	}

	public boolean isFarmIDexisting(String farmid){
		boolean isexisting = false;
		String query = "SELECT * FROM "+ GPSHelper.TBLMAINCUSTOMERINFO +" WHERE "
				+ GPSHelper.CL_MAINCUSTINFO_FarmId + " = ? "
				;
		String[] params = new String[] {farmid};
		Cursor cur = db.rawQuery(query, params);
		if (cur!=null){
			if (cur.getCount() > 0){
				isexisting = true;
			}
		}
		return  isexisting;
	}
	public Cursor getLocal_PondsByFarmIndex(String farmid){
		dbhelper.getWritableDatabase();
		String query =
		"SELECT *  FROM `tblPond` " +
				"INNER JOIN tblpond_weeklyupdates ON tblPond.id = tblpond_weeklyupdates.wu_pondid " +
				"WHERE `customerId`= ? " +
				"GROUP BY tblPond.id " +
				"ORDER BY CAST(`tblPond`.`pondid` AS SIGNED)  ASC";

//		"SELECT * FROM `tblPond` WHERE `customerId`= ?\n" +
//				"ORDER BY CAST(`tblPond`.`pondid` AS SIGNED)  ASC";
		String[] params = new String[] {farmid};
		return db.rawQuery(query, params);
	}

	public Cursor getLocal_NotPondsByFarmIndex(String farmid){
		dbhelper.getWritableDatabase();
		String query =
				"SELECT *  FROM `tblPond` " +
						"INNER JOIN tblpond_weeklyupdates ON tblPond.id = tblpond_weeklyupdates.wu_pondid " +
						"WHERE `customerId`= ? AND "+ GPSHelper.CL_POND_isHarvested + " = 0 "+
						"GROUP BY tblPond.id " +
						"ORDER BY CAST(`tblPond`.`pondid` AS SIGNED)  ASC";

		String[] params = new String[] {farmid};
		return db.rawQuery(query, params);
	}



	public int getUser_Count() {
		String query = "SELECT * FROM "+ GPSHelper.TBLUSERS+";";
		String[] params = new String[] {};
//		rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}



	public Cursor getAllHarvestInfo() {
		String query = "SELECT * FROM "+ GPSHelper.TBL_HARVESTINFO+" INNER JOIN " + GPSHelper.TBLPOND +
				" ON " + GPSHelper.CL_HRV_PONDID + "="+ GPSHelper.CL_POND_INDEX;
		String[] params = new String[] {};
//		rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});
		Cursor cur = db.rawQuery(query, params);
		return cur;
	}




	public int getTableCount(String tableName) {

		open();
		String query = "SELECT * FROM "+tableName+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);

		return cur.getCount();
	}

	public int getPond_Count() {
		String query = "SELECT * FROM "+ GPSHelper.TBLPOND+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}


	public String getSINGLE_ROWITEM(String tableName, String where, String columnName) {
		String query = "SELECT * FROM " + tableName +" " +
				"WHERE "+where+" " ;
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		Log.d("SINGLE QUERY", cur.getColumnCount() + " " + cur.getCount() );

		String queriedItem = "";
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				queriedItem = cur.getString(cur.getColumnIndex(columnName));
				Log.d("SINGLE QUERY", queriedItem + " " + cur.getCount() );
			}
		}
		return queriedItem;
	}

	public int getArea_Count(){
		String query = "SELECT * FROM "+ GPSHelper.TBLAREA+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getMainCustInfo_Count(){
		String query = "SELECT * FROM "+ GPSHelper.TBLMAINCUSTOMERINFO+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getUserActivity_Count(){
		String query = "SELECT * FROM "+ GPSHelper.TBLUSER_ACTIVITY+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getMunicipality_Count(){
		String query = "SELECT * FROM "+ GPSHelper.TBLAREA_MUNICIPALITY+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getAssigned_Count(){
		String query = "SELECT* FROM "+ GPSHelper.TBLAREA_ASSIGNED+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getWeeklyUpdates_Count(){
		String query = "SELECT* FROM "+ GPSHelper.TBLPOND_WeeklyUpdates+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}

	public int getFarmInfo_Count(){
		String query = "SELECT* FROM "+ GPSHelper.TBLFARMiNFO+";";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur.getCount();
	}


	public String[] getcolumnNames(String tableName){
		open();
		Log.d("UPGRADE", "afteropen create temp table");
		String query = "SELECT * FROM "+tableName;

		String[] params = new String[] {};
		String[] colnames = new String[0];
		Cursor cur = db.rawQuery(query, params);
		if (cur.getCount() > 0){
			colnames = new String[cur.getColumnCount()];
			for (int i = 0; i < cur.getColumnCount() ; i++) {
				colnames[i] = cur.getColumnName(i);
			}
		}
		Log.d("UPGRADE", "afterclose create temp table");
		return colnames;
	}

	public String[] getcolumnDataTypes(String tableName){
		String query = "PRAGMA table_info("+tableName+"); ";

		String[] params = new String[] {};
		String[] colTypes = new String[0];

		Cursor cur = db.rawQuery(query, params);
		if (cur.getCount() > 0){
			colTypes = new String[cur.getCount()];

			int counter = 0;
			while (cur.moveToNext()) {
				colTypes[counter] =
//						cur.getPosition()+"";
						cur.getString(2)+"";
				counter ++;
			}

		}

		return colTypes;
	}



	public String getColumnCount(String tableName){
		String query = "SELECT * FROM "+tableName;

		String[] params = new String[] {};
		String[] colnames;
		Cursor cur = db.rawQuery(query, params);
		if (cur.getCount() > 0){
			colnames = new String[cur.getColumnCount()];
			for (int i = 0; i < cur.getColumnCount()-1 ; i++) {
				colnames[i] = cur.getColumnName(i);
			}
		}

		return cur.getColumnCount()+" ";

	}


	public Cursor getPondOfFarm(String pondindex){

		String query = "SELECT * from tblpond where customerid in  (select customerid from tblpond where id =+'"+pondindex+"') AND id != '"+pondindex+"'";
//				"SELECT * from "+GPSHelper.TBLPOND+" where "+GPSHelper.CL_POND_customerId+
//				" IN (select "+GPSHelper.CL_POND_customerId+" from tblPond where "+GPSHelper.CL_POND_INDEX+"='"+pondindex+"')";
		String[] params = new String[] {};
		Cursor cur = db.rawQuery(query, params);
		return cur;

//		if (cur.getCount() > 0){
//			while (cur.moveToNext()) {
//				CustInfoObject custInfoObject = new CustInfoObject();
//				custInfoObject.setId(cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_INDEX)));
//				custInfoObject.setPondID(cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_PID)));
//				custInfoObject.setSpecie(cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_specie)));
//				custInfoObject.setSizeofStock(cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_sizeofStock)));
//				custInfoObject.setSurvivalrate_per_pond(cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_survivalrate)) + "");
//				custInfoObject.setDateStocked(cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_dateStocked)));
//				custInfoObject.setQuantity(cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_quantity)));
//				custInfoObject.setArea(cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_area)));
//				custInfoObject.setCulturesystem(cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_culturesystem)));
//				custInfoObject.setRemarks(cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_remarks)));
//				custInfoObject.setCustomerID(cur.getString(cur.getColumnIndex(GPSHelper.CL_POND_customerId)));
//				custInfoObject.setIsPosted(cur.getInt(cur.getColumnIndex(GPSHelper.CL_POND_isPosted)));
//				custInfoObject.setCurrentABW(cur.getInt(cur.getColumnIndex(GPSHelper.CL_WEEKLY_UPDATES_CURRENT_ABW)));
//				pondList.add(custInfoObject);
//			}
//		}

	}


	/********************************************
	 * 				UPDATES						*
	 * updates return the number of rows affectd*
	 ********************************************/
	public int updateRowOneUser(String userid, String lvl, String firstname, String lastname, String username, String password, String deviceid, String dateAdded) {
		String where = GPSHelper.CL_USERS_ID + " = " + userid;
		ContentValues newValues = new ContentValues();
		newValues.put(GPSHelper.CL_USERS_userlvl, lvl);
		newValues.put(GPSHelper.CL_USERS_firstName, firstname);
		newValues.put(GPSHelper.CL_USERS_lastName, lastname);
		newValues.put(GPSHelper.CL_USERS_username, username);
		newValues.put(GPSHelper.CL_USERS_password, password);
		newValues.put(GPSHelper.CL_USERS_deviceid, deviceid);
		newValues.put(GPSHelper.CL_USERS_dateAdded, dateAdded);
		return 	db.update(GPSHelper.TBLUSERS, newValues, where, null);
	}


	public int updatePondInfo(String index, String pondid, String specie, String sizeofStock, String survivalRate, String dateStocked, String quantity, String area, String cultureSystem,
							  String remarks) {
//		Log.d("DB", "db updates" + index + " " + pondid + " " + specie + " " + sizeofStock + " " + survivalRate + " " + dateStocked + " " +
//				quantity + " "+ area + " " +cultureSystem + " " + remarks);
		String where = GPSHelper.CL_POND_INDEX + " = " + index;
		ContentValues newValues = new ContentValues();
		newValues.put(GPSHelper.CL_POND_PID, pondid);
		newValues.put(GPSHelper.CL_POND_specie, specie);
		newValues.put(GPSHelper.CL_POND_sizeofStock, sizeofStock);
		newValues.put(GPSHelper.CL_POND_survivalrate, survivalRate);
		newValues.put(GPSHelper.CL_POND_dateStocked, dateStocked);
		newValues.put(GPSHelper.CL_POND_quantity, quantity);
		newValues.put(GPSHelper.CL_POND_area, area);
		newValues.put(GPSHelper.CL_POND_culturesystem, cultureSystem);
		newValues.put(GPSHelper.CL_POND_remarks, remarks);

		return db.update(GPSHelper.TBLPOND, newValues, where, null);
	}


	public int updateRowFarmInfo(String indexid, String contactname, String company, String address, String farmname, String farmid, String contactNumber, String cultSystem,
								 String cultLevel, String WaterType ) {
		String where = GPSHelper.CL_FarmInfo_ID + " = " + indexid;
		ContentValues newValues = new ContentValues();
		newValues.put(GPSHelper.CL_FARMINFO_CONTACT_NAME, contactname);
		newValues.put(GPSHelper.CL_FARMINFO_COMPANY, company);
		newValues.put(GPSHelper.CL_FARMINFO_FARM_ADDRESS, address);
		newValues.put(GPSHelper.CL_FARMINFO_FARM_NAME, farmname);
		newValues.put(GPSHelper.CL_FARMINFO_FARM_ID, farmid);
		newValues.put(GPSHelper.CL_FARMINFO_C_NUMBER, contactNumber);
		newValues.put(GPSHelper.CL_FARMINFO_CULTYPE, cultSystem);
		newValues.put(GPSHelper.CL_FARMINFO_CULTlVL, cultLevel);
		newValues.put(GPSHelper.CL_FARMINFO_WATTYPE, WaterType);
		return 	db.update(GPSHelper.TBLFARMiNFO, newValues, where, null);
	}


	public int updateRowWeeklyUpdates( String indexid, String abw, String remarks) {
		String where = GPSHelper.CL_WEEKLY_UPDATES_ID + " = " + indexid;
		ContentValues newValues = new ContentValues();
		newValues.put(GPSHelper.CL_WEEKLY_UPDATES_CURRENT_ABW, abw);
		newValues.put(GPSHelper.CL_WEEKLY_UPDATES_REMARKS, remarks);
		return 	db.update(GPSHelper.TBLPOND_WeeklyUpdates, newValues, where, null);
	}


	public int updateHarvestInfo( String casenum , String id, String pondid, String dateofharvest, String finalabw,
								  String totalconsumption, String fcr, String priceperkilo,
								  String totalharvested, String isposted, String dateinserted, String species) {

		String where = GPSHelper.CL_HRV_ID + " = " + id;
		ContentValues newValues = new ContentValues();
		newValues.put(GPSHelper.CL_HRV_CASENUM, casenum);
		newValues.put(GPSHelper.CL_HRV_PONDID, pondid);
		newValues.put(GPSHelper.CL_HRV_DATEOFHARVEST, dateofharvest);
		newValues.put(GPSHelper.CL_HRV_FINALABW, finalabw);
		newValues.put(GPSHelper.CL_HRV_TOTAL_CONSUMPTION, totalconsumption);
		newValues.put(GPSHelper.CL_HRV_FCR, fcr);
		newValues.put(GPSHelper.CL_HRV_PRICEPERKILO, priceperkilo);
		newValues.put(GPSHelper.CL_HRV_TOTALHARVEST, totalharvested);
		newValues.put(GPSHelper.CL_HRV_ISPOSTED, isposted);
		newValues.put(GPSHelper.CL_HRV_DATE_INSERTED, dateinserted);
		newValues.put(GPSHelper.CL_HRV_SPECIES, species);

		return 	db.update(GPSHelper.TBL_HARVESTINFO, newValues, where, null);
	}

	public int updateCustomerInfo(String id, String firstname, String lastname, String middleName, String farmID, String houseNumber, String street, String subdivision, String barangay,
								  String city, String province, String birthday, String birthPlace, String spouseBirthday, String telephone, String cellphone, String civilStatus, String spouseFirstName,
								  String spouseMiddleName, String spouseLastName, String custType){
		String where = GPSHelper.CL_MAINCUSTINFO_ID + " = " + id;
		ContentValues newValues = new ContentValues();
		newValues.put(GPSHelper.CL_MAINCUSTINFO_FirstName, firstname );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_LastName, lastname );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_MiddleName, middleName );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_FarmId, farmID );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_HouseNumber, houseNumber );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_Street, street );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_Subdivision, subdivision );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_Barangay, barangay );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_City, city );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_Province, province );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_CBirthday, birthday );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_CBirthPlace, birthPlace );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_Telephone, telephone );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_Cellphone, cellphone );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_CivilStatus, civilStatus );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_S_FirstName, spouseFirstName );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_S_MiddleName, spouseMiddleName );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_S_LastName, spouseLastName );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_S_BirthDay, spouseBirthday );
		newValues.put(GPSHelper.CL_MAINCUSTINFO_type, custType );

		return 	db.update(GPSHelper.TBLMAINCUSTOMERINFO, newValues, where, null);
	}

	public int updateUnPostedToPosted_FARM() {
		String where = GPSHelper.CL_FARMINFO_IsPosted + " = 0";
		ContentValues newValues = new ContentValues();
		newValues.put(GPSHelper.CL_FARMINFO_IsPosted, 1);
		return 	db.update(GPSHelper.TBLFARMiNFO, newValues, where, null);
	}

	public int updateUnPostedToPosted_Cust() {
		String where = GPSHelper.CL_MAINCUSTINFO_isposted + " = 0";
		ContentValues newValues = new ContentValues();
		newValues.put(GPSHelper.CL_MAINCUSTINFO_isposted, 1);
		return 	db.update(GPSHelper.TBLMAINCUSTOMERINFO, newValues, where, null);
	}

	public int updateUnPostedToPosted_POND() {
		String where = GPSHelper.CL_POND_isPosted + " = 0";
		ContentValues newValues = new ContentValues();
		newValues.put(GPSHelper.CL_POND_isPosted, 1);
		return 	db.update(GPSHelper.TBLPOND, newValues, where, null);
	}

	public int updatePondAsHarvested(String pondIndex) {
		String where = GPSHelper.CL_POND_INDEX + " = '"+pondIndex+"'";
		ContentValues newValues = new ContentValues();
		newValues.put(GPSHelper.CL_POND_isHarvested, 1);
		newValues.put(GPSHelper.CL_POND_isPosted, 0);
		return 	db.update(GPSHelper.TBLPOND, newValues, where, null);
	}


	public int updateUnPostedToPosted_WEEKLY() {
		String where = GPSHelper.CL_WEEKLY_UPDATES_isposted + " = 0";
		ContentValues newValues = new ContentValues();
		newValues.put(GPSHelper.CL_WEEKLY_UPDATES_isposted, 1);
		return 	db.update(GPSHelper.TBLPOND_WeeklyUpdates, newValues, where, null);
	}

	public int updateUnPostedToPosted_HarvestInfo() {
		String where = GPSHelper.CL_HRV_ISPOSTED+ " = '0' ";
		ContentValues newValues = new ContentValues();
		newValues.put(GPSHelper.CL_HRV_ISPOSTED, "1");
		Log.d("Harvested", "update if posted");
		return 	db.update(GPSHelper.TBL_HARVESTINFO, newValues, where, null);
	}

	public int updateUnPostedToPosted_USERACTIVITY() {
		String where = GPSHelper.CL_USER_ACTIVITY_isPosted+ " = 0";
		ContentValues newValues = new ContentValues();
		newValues.put(GPSHelper.CL_USER_ACTIVITY_isPosted, 1);
		return 	db.update(GPSHelper.TBLUSER_ACTIVITY, newValues, where, null);
	}

	public int updateOneRow(String tableName, String column, String value, String whereCondition) {
		ContentValues newValues = new ContentValues();
		newValues.put(column, value);
		return 	db.update(tableName, newValues, whereCondition, null);
	}



	public void trasferOldTableToTEMPTable(String oldtable, String newTable){
		String sql = "INSERT INTO "+newTable+" SELECT * FROM "+oldtable+";";
		Log.d("COPY TABLE", sql);
		db.execSQL(sql);
	}

	public void trasferTEMPTableToNewTable(String fromTable, String toTable, String[]tempColumnNames){

		String fromTableCols = "";
		for (int i = 0; i < tempColumnNames.length; i++) {
			fromTableCols = fromTableCols + " " + tempColumnNames[i]+",";
		}

		fromTableCols = fromTableCols.substring(0, fromTableCols.length() - 1);

		String sql = "insert into "+toTable+" ("+fromTableCols+") " +
				"select "+fromTableCols+" " +
				"from "+fromTable+"; ";
		Log.d("COPY TABLE", sql);
		db.execSQL(sql);
	}


	public void createTableExute(String sqlCreate){
		db.execSQL(sqlCreate);
	}

	public void dropTable(String tableName){
		db.execSQL("DROP TABLE IF EXISTS " + tableName);
	}


	/********************************************
	 * 				DELETES						*
	 ********************************************/

	//Deletes row from Contacts
	public boolean deleteRow_CustomerAddress(String rowId) {
		String where = GPSHelper.CL_MAINCUSTINFO_ID + "=" + rowId;
		return db.delete(GPSHelper.TBLMAINCUSTOMERINFO, where, null) != 0;
	}

	public boolean deleteRow_HarvestInfo(String rowId) {
		String where = GPSHelper.CL_HRV_ID + "=" + rowId;
		return db.delete(GPSHelper.TBL_HARVESTINFO, where, null) != 0;
	}

	public boolean deleteRow_FarmInfo(String rowId) {
		String where = GPSHelper.CL_FarmInfo_ID + "='" + rowId+"'";
		return db.delete(GPSHelper.TBLFARMiNFO, where, null) != 0;
	}
	public boolean deleteRow_Weekly(String rowId) {
		String where = GPSHelper.CL_WEEKLY_UPDATES_ID + "=" + rowId;
		return db.delete(GPSHelper.TBLPOND_WeeklyUpdates, where, null) != 0;
	}

	public boolean deleteRow_PondInfo(String rowId) {
		String where = GPSHelper.CL_POND_INDEX + "=" + rowId;
		String where1 = GPSHelper.CL_WEEKLY_UPDATES_PONDID + "=" + rowId;

		boolean isdeleted = db.delete(GPSHelper.TBLPOND, where, null) != 0;
		if (isdeleted) {
			isdeleted = db.delete(GPSHelper.TBLPOND_WeeklyUpdates, where1, null) != 0;
		}
		return isdeleted;

	}


	public void delete_ALL_ITEM_ON_TABLE(String TABLE_NAME) {
		if (getTableCount(TABLE_NAME) > 0) {
			Log.d("RESTORE TABLE SERVER", TABLE_NAME + " delete all items");
			db.execSQL("delete from " + TABLE_NAME);
		}else{
			Log.d("RESTORE TABLE SERVER", TABLE_NAME + " no items");
		}
	}




}
