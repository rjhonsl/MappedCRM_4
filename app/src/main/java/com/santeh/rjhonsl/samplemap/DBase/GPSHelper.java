package com.santeh.rjhonsl.samplemap.DBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GPSHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "DB_GPS";
	private static final String DATABASE_NAME = "local.db";
	//each time you change data structure, you must increment this by 1
	private static final int DATABASE_VERSION = 27;
	Context context;


	public static String DATE = "DATE", TEXT = "TEXT", INTEGER = "INTEGER", DOUBLE = "DOUBLE", DATETIME = "DATETIME",
			PRIMARY_AUTOINCRE = "PRIMARY KEY AUTOINCREMENT", BOOLEAN = "BOOLEAN", TEMP = "TMP";

	//Reference for tblmaincustomerinfo
	public static final String TBLMAINCUSTOMERINFO 				= "tblmaincustomerinfo";

	public static final String CL_MAINCUSTINFO_ID 				= "mci_id"; //Column name for all ID in every table
	public static final String CL_MAINCUSTINFO_LastName 		= "mci_lname"; //1
	public static final String CL_MAINCUSTINFO_FirstName 		= "mci_fname"; //2
	public static final String CL_MAINCUSTINFO_MiddleName 		= "mci_mname"; //3
	public static final String CL_MAINCUSTINFO_FarmId			= "mci_farmid"; //4
	public static final String CL_MAINCUSTINFO_HouseNumber 		= "mci_housenumber";//5
	public static final String CL_MAINCUSTINFO_Street 			= "mci_street"; //6
	public static final String CL_MAINCUSTINFO_Subdivision 		= "mci_subdivision"; //7
	public static final String CL_MAINCUSTINFO_Barangay 		= "mci_barangay"; //8
	public static final String CL_MAINCUSTINFO_City 			= "mci_city"; //9
	public static final String CL_MAINCUSTINFO_Province 		= "mci_province"; //10
	public static final String CL_MAINCUSTINFO_CBirthday 		= "mci_customerbirthday"; //11
	public static final String CL_MAINCUSTINFO_CBirthPlace 		= "mci_birthplace"; //12
	public static final String CL_MAINCUSTINFO_Telephone 		= "mci_telephone"; //13
	public static final String CL_MAINCUSTINFO_Cellphone 		= "mci_cellphone"; //14
	public static final String CL_MAINCUSTINFO_CivilStatus 		= "mci_civilstatus"; //15
	public static final String CL_MAINCUSTINFO_S_FirstName 		= "mci_s_fname"; //16
	public static final String CL_MAINCUSTINFO_S_LastName 		= "mci_s_lname"; //17
	public static final String CL_MAINCUSTINFO_S_MiddleName 	= "mci_s_mname"; //18
	public static final String CL_MAINCUSTINFO_S_BirthDay 		= "mci_s_birthday"; //19
	public static final String CL_MAINCUSTINFO_Latitude 		= "mci_latitude"; //20
	public static final String CL_MAINCUSTINFO_HouseStatus 		= "mci_housestatus"; //21
	public static final String CL_MAINCUSTINFO_Longitude 		= "mci_longitude"; //22
	public static final String CL_MAINCUSTINFO_DateAdded 		= "mci_dateadded"; //23
	public static final String CL_MAINCUSTINFO_AddedBy			= "mci_addedby"; //24
	public static final String CL_MAINCUSTINFO_isposted			= "mci_isposted"; //25
	public static final String CL_MAINCUSTINFO_type				= "mci_type"; //26

	public static final String[] ALL_KEY_MAINCUSTOMERINFO 		= new String[]{CL_MAINCUSTINFO_ID, CL_MAINCUSTINFO_LastName, CL_MAINCUSTINFO_FirstName, CL_MAINCUSTINFO_MiddleName,
			CL_MAINCUSTINFO_FarmId, CL_MAINCUSTINFO_HouseNumber, CL_MAINCUSTINFO_Street, CL_MAINCUSTINFO_Subdivision, CL_MAINCUSTINFO_Barangay, CL_MAINCUSTINFO_City, CL_MAINCUSTINFO_Province,
			CL_MAINCUSTINFO_CBirthday, CL_MAINCUSTINFO_CBirthPlace, CL_MAINCUSTINFO_Telephone, CL_MAINCUSTINFO_Cellphone, CL_MAINCUSTINFO_CivilStatus, CL_MAINCUSTINFO_S_FirstName,
			CL_MAINCUSTINFO_S_LastName, CL_MAINCUSTINFO_S_MiddleName, CL_MAINCUSTINFO_S_BirthDay, CL_MAINCUSTINFO_HouseStatus, CL_MAINCUSTINFO_Latitude, CL_MAINCUSTINFO_Longitude,
			CL_MAINCUSTINFO_DateAdded, CL_MAINCUSTINFO_AddedBy, CL_MAINCUSTINFO_isposted, CL_MAINCUSTINFO_type};
	public static final String[] ALL_KEY_MAINCUSTOMERINFO_DATAPROP =
			new String[]{
			INTEGER + " " + PRIMARY_AUTOINCRE, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT,
			TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT};


	//reference for tblarea
	public static final String TBLAREA 				= "tblarea";

	public static final String CL_AREA_ID			= "area_id";
	public static final String CL_AREA_DESCRIPTION	= "area_description";
	public static final String[] ALL_KEY_AREA		= new String[]{CL_AREA_ID, CL_AREA_DESCRIPTION};

	// reference for tblarea_assigned
	public static final String TBLAREA_ASSIGNED			= "tblarea_assigned";

	public static final String CL_ASSIGNED_ID			= "assigned_id";
	public static final String CL_ASSIGNED_USERID 		= "assigned_userid";
	public static final String CL_ASSIGNED_AREA 		= "assigned_area";
	public static final String CL_ASSIGNED_MUNICIPALITY	= "assigned_municpality";
	public static final String[] ALL_KEY_ASSIGNED 	= new String[]{CL_ASSIGNED_ID, CL_ASSIGNED_USERID, CL_ASSIGNED_AREA, CL_ASSIGNED_MUNICIPALITY};



	//reference for tblarea_municipality
	public static final String TBLAREA_MUNICIPALITY	= "tblarea_municipality";

	public static final String CL_MUNICIPALITY_ID			= "municipality_id";
	public static final String CL_MUNICIPALITY_DESCRIPTION	= "municipality_description";
	public static final String CL_MUNICIPALITY_PROVINCE		= "municipality_province";
	public static final String[] ALL_KEY_MUNICIPALITY		= new String[]{CL_MUNICIPALITY_ID, CL_MUNICIPALITY_DESCRIPTION, CL_MUNICIPALITY_PROVINCE};


	//reference for tblarea
	public static final String TBLFARMiNFO 				= "tblCustomerInfo";

	public static final String CL_FarmInfo_ID 			= "ci_customerId";
	public static final String CL_FARMINFO_LAT			= "latitude";
	public static final String CL_FARMINFO_LNG			= "longtitude";
	public static final String CL_FARMINFO_CONTACT_NAME	= "contact_name";
	public static final String CL_FARMINFO_COMPANY		= "company";
	public static final String CL_FARMINFO_FARM_ADDRESS	= "address";
	public static final String CL_FARMINFO_FARM_NAME	= "farm_name";
	public static final String CL_FARMINFO_FARM_ID		= "farmid";
	public static final String CL_FARMINFO_C_NUMBER		= "contact_number";
	public static final String CL_FARMINFO_CULTYPE		= "culture_type";
	public static final String CL_FARMINFO_CULTlVL		= "culture_level";
	public static final String CL_FARMINFO_WATTYPE		= "water_type";
	public static final String CL_FARMINFO_dateAdded	= "dateAdded";
	public static final String CL_FARMINFO_addedby		= "addedby";
	public static final String CL_FARMINFO_IsPosted		= "isposted";

	public static final String[] ALL_KEY_fARM		= new String[]{CL_FarmInfo_ID, CL_FARMINFO_LAT,CL_FARMINFO_LNG,CL_FARMINFO_CONTACT_NAME, CL_FARMINFO_COMPANY, CL_FARMINFO_FARM_ADDRESS
	,CL_FARMINFO_FARM_NAME, CL_FARMINFO_FARM_ID, CL_FARMINFO_C_NUMBER, CL_FARMINFO_CULTYPE, CL_FARMINFO_CULTlVL, CL_FARMINFO_WATTYPE,CL_FARMINFO_dateAdded,  CL_FARMINFO_addedby, CL_FARMINFO_IsPosted};
	public static final String[] ALL_KEY_FARM_DATAPROP =
			new String[]{INTEGER + " " + PRIMARY_AUTOINCRE, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT};


	//reference for tblPond
	public static final String TBLPOND 					= "tblPond";

	public static final String CL_POND_INDEX			= "id";
	public static final String CL_POND_PID				= "pondid";
	public static final String CL_POND_specie			= "specie";
	public static final String CL_POND_sizeofStock		= "sizeofStock";
	public static final String CL_POND_survivalrate		= "survivalrate";
	public static final String CL_POND_dateStocked		= "dateStocked";
	public static final String CL_POND_quantity			= "quantity";
	public static final String CL_POND_area				= "area";
	public static final String CL_POND_culturesystem	= "culturesystem";
	public static final String CL_POND_remarks			= "remarks";
	public static final String CL_POND_customerId		= "customerId";
	public static final String CL_POND_isPosted			= "p_isposted";
	public static final String CL_POND_isHarvested		= "p_isharvested";
	public static final String CL_POND_dateInserted		= "p_dateinserted";
	public static final String CL_POND_dateuploaded 	= "p_uploaded";//only in web
	public static final String CL_POND_localID			= "p_localid";//onlu in web
	public static final String[] ALL_KEY_POND			= new String[]{CL_POND_INDEX, CL_POND_PID, CL_POND_specie, CL_POND_sizeofStock, CL_POND_survivalrate,
			CL_POND_dateStocked, CL_POND_quantity, CL_POND_area, CL_POND_culturesystem, CL_POND_remarks, CL_POND_customerId, CL_POND_isPosted, CL_POND_isHarvested,
			CL_POND_dateInserted};
	public static final String[] ALL_KEY_POND_DATAPROP 	= new String[]{
			INTEGER + " " + PRIMARY_AUTOINCRE, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT
	};


	public static final String TBLPOND_WeeklyUpdates 	= "tblpond_weeklyupdates";

	public static final String CL_WEEKLY_UPDATES_ID				= "wu_id";
	public static final String CL_WEEKLY_UPDATES_CURRENT_ABW	= "wu_currentabw";
	public static final String CL_WEEKLY_UPDATES_CURRENT_SURVIVALRATE	= "wu_survivalRate";
	public static final String CL_WEEKLY_UPDATES_REMARKS		= "wu_remakrs";
	public static final String CL_WEEKLY_UPDATES_PONDID			= "wu_pondid";
	public static final String CL_WEEKLY_UPDATES_DATEADDED 		= "wu_dateAdded";
	public static final String CL_WEEKLY_UPDATES_isposted		= "wu_isposted";
	public static final String[] ALL_KEY_WEEKLY_UPDATES			= new String[]{CL_WEEKLY_UPDATES_ID, CL_WEEKLY_UPDATES_CURRENT_ABW, CL_WEEKLY_UPDATES_CURRENT_SURVIVALRATE, CL_WEEKLY_UPDATES_REMARKS,CL_WEEKLY_UPDATES_PONDID,
			CL_WEEKLY_UPDATES_DATEADDED, CL_WEEKLY_UPDATES_isposted};
	public static final String[] ALL_KEY_WEEKLY_UPDATES_DATAPROP= new String[]{
			INTEGER + " " + PRIMARY_AUTOINCRE, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT
	};


	//UNUSED COLUMNS
	public static final String TBL_USERS_SUBINFO = "user_subinfo_";
	public static final String CL_USI_ID 				= "user_subinfo_id";
	public static final String CL_USI_UID 				= "user_subinfo_uid";
	public static final String CL_USI_BDAY 				= "user_subinfo_birthday";
	public static final String CL_USI_PROFILE_IMGURL 	= "user_subinfo_profileurl";
	public static final String CL_USI_AREA_ASSIGNED 	= "user_subinfo_areaassigned";
	public static final String CL_USI_CURRENT_ADDRESS 	= "user_subinfo_currentaddress";

	public static final String TBL_FEED_MAIN = "feed_main_";
	public static final String CL_FM_id 		= "feed_main_id";
	public static final String CL_FM_UID 		= "feed_main_uid";
	public static final String CL_FM_DATE 		= "feed_main_date";
	public static final String CL_FM_LocLat 	= "feed_main_loclat";
	public static final String CL_FM_LocLong 	= "feed_main_loclong";
	public static final String CL_FM_fetchAt	= "feed_main_fetch_at";
	public static final String CL_FM_SeenState	= "feed_main_seen_state";

	public static final String TBL_FEEDTYPE = "feed_type_";
	public static final String CL_FT_ID 	= "feed_type_id";
	public static final String CL_FT_DESC 	= "feed_type_description";

	public static final String TBL_FeedContents 	= "feedcontents_";
	public static final String CL_FC_ID					= "feedcontents_id";
	public static final String CL_FC_MAINID				= "feedcontents_mainid";
	public static final String CL_FC_TYPE				= "feedcontents_type";
	public static final String CL_FC_DESC				= "feedcontents_description";
	public static final String CL_FC_IMAGEURL			= "feedcontents_imageurl";
	public static final String CL_FC_EVENT				= "feedcontents_event";
	public static final String CL_FC_FILEURL			= "feedcontents_fileurl";
	public static final String CL_FC_FetchAT			= "feedcontents_fetchat";

	public static final String TBL_FEED_COMMENTS = "feedcomments_";
	public static final String CL_FCOMM_ID				= "feedcomments_id";
	public static final String CL_FCOMM_MAINID			= "feedcomments_mainid";
	public static final String CL_FCOMM_UID				= "feedcomments_uid";
	public static final String CL_FCOMM_CONTENT			= "feedcomments_content";
	public static final String CL_FCOMM_DATECOMMENTED	= "feedcomments_datecommented";
	public static final String CL_FCOMM_loclat			= "feedcomments_loclat";
	public static final String CL_FCOMM_locLong			= "feedcomments_loclong";
	public static final String CL_FCOMM_fetchAt			= "feedcomments_fetchat";

	public static final String TBL_FEED_SUBCOMM 		= "feedsubcomments_";
	public static final String CL_FSUBCOMM_ID 				= "feedsubcomments_id";
	public static final String CL_FSUBCOMM_COMMENTID 		= "feedsubcomments_commentid";
	public static final String CL_FSUBCOMM_UID 				= "feedsubcomments_uid";
	public static final String CL_FSUBCOMM_CONTENT			= "feedsubcomments_content";
	public static final String CL_FSUBCOMM_DATECOMM			= "feedsubcomments_datecommented";
	public static final String CL_FSUBCOMM_loclat			= "feedsubcomments_loclat";
	public static final String CL_FSUBCOMM_locLong			= "feedsubcomments_loclong";
	public static final String CL_FSUBCOMM_fetchedAt		= "feedsubcomments_fetchat";



	//TBLUSERS
	public static final String TBLUSERS = "tblusers";
	public static final String CL_USERS_ID				= "users_id";
	public static final String CL_USERS_userlvl			= "userlvl";
	public static final String CL_USERS_firstName		= "users_firstname";
	public static final String CL_USERS_lastName		= "users_lastname";
	public static final String CL_USERS_username		= "users_username";
	public static final String CL_USERS_password		= "users_password";
	public static final String CL_USERS_deviceid		= "users_device_id";
	public static final String CL_USERS_dateAdded		= "dateAdded";
	public static final String CL_USERS_isactive		= "isactive";
	public static final String[] ALL_KEY_USERS	= new String[]{CL_USERS_ID, CL_USERS_userlvl, CL_USERS_firstName, CL_USERS_lastName, CL_USERS_username,
			CL_USERS_password, CL_USERS_deviceid, CL_USERS_dateAdded , CL_USERS_isactive};
	public static final String[] ALL_KEY_USERS_DATAPROP = new String[]{
			INTEGER + " " + PRIMARY_AUTOINCRE, INTEGER, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT
	};

	//reference for tbluseractivity
	public static final String TBLUSER_ACTIVITY 				= "tbluser_activity";

	public static final String CL_USER_ACTIVITY_ID			= "user_act_id";
	public static final String CL_USER_ACTIVITY_USERID		= "user_act_userid";
	public static final String CL_USER_ACTIVITY_ACTIONDONE	= "user_act_actiondone";
	public static final String CL_USER_ACTIVITY_LAT			= "user_act_latitude";
	public static final String CL_USER_ACTIVITY_LNG			= "user_act_longitude";
	public static final String CL_USER_ACTIVITY_DATETIME	= "user_act_datetime";
	public static final String CL_USER_ACTIVITY_ACTIONTYPE	= "user_act_actiontype";
	public static final String CL_USER_ACTIVITY_isPosted	= "user_act_isposted";
	public static final String[] ALL_KEY_USERACTIVITY		= new String[]{CL_USER_ACTIVITY_ID, CL_USER_ACTIVITY_USERID, CL_USER_ACTIVITY_ACTIONDONE,
			CL_USER_ACTIVITY_LAT, CL_USER_ACTIVITY_LNG, CL_USER_ACTIVITY_DATETIME, CL_USER_ACTIVITY_ACTIONTYPE, CL_USER_ACTIVITY_isPosted};

	public static final String[] ALL_KEY_USERACTIVITY_DATAPROP = new String[]{
			INTEGER + " " + PRIMARY_AUTOINCRE, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT
	};



	public static final String TBL_HARVESTINFO 				= "tblharvest_info";
	public static final String CL_HRV_ID 					= "hrv_id";
	public static final String CL_HRV_PONDID 				= "hrv_pondid";
	public static final String CL_HRV_CASENUM 				= "hrv_casenum";
	public static final String CL_HRV_SPECIES 				= "hrv_species";
	public static final String CL_HRV_DATEOFHARVEST = "hrv_dateofharvest";
	public static final String CL_HRV_FINALABW				= "hrv_finalabw";
	public static final String CL_HRV_TOTAL_CONSUMPTION	 	= "hrv_totalconsumption";
	public static final String CL_HRV_FCR	 				= "hrv_fcr";
	public static final String CL_HRV_PRICEPERKILO	 		= "hrv_priceperkilo";
	public static final String CL_HRV_TOTALHARVEST		 	= "hrv_totalharvested";
	public static final String CL_HRV_ISPOSTED		 		= "hrv_isposted";
	public static final String CL_HRV_DATE_INSERTED		 	= "hrv_date_inserted";
	public static final String CL_HRV_LocalId	 			= "hrv_localid"; //only exist in web
	public static final String CL_HRV_DateUploaded 			= "hrv_dateuploaded"; //only exist in web
	public static final String[] ALL_KEY_HARVESTINFO = new String[]{
			CL_HRV_ID, CL_HRV_PONDID, CL_HRV_CASENUM, CL_HRV_SPECIES, CL_HRV_DATEOFHARVEST, CL_HRV_FINALABW, CL_HRV_TOTAL_CONSUMPTION, CL_HRV_FCR, CL_HRV_PRICEPERKILO, CL_HRV_TOTALHARVEST, CL_HRV_ISPOSTED, CL_HRV_DATE_INSERTED};

	public static final String[] ALL_KEY_HARVESTEDINFO_DATAPROP = new String[]{INTEGER + " " + PRIMARY_AUTOINCRE, TEXT, TEXT, TEXT,TEXT,TEXT,TEXT,TEXT,TEXT,TEXT,TEXT,TEXT};



	public static final String TBL_Pond_QH = "TBL_P_QHANDLER";
	public static final String CL_P_QH_ID								= "PQH_ID";
	public static final String CL_P_QH_QUANTITY							= "PQH_QUANTITY";
	public static final String CL_P_QH_MATHOP							= "PQH_MATHOP";
	public static final String CL_P_QH_FROM								= "PQH_FROM_ID";
	public static final String CL_P_QH_TO								= "PQH_TO_ID";
	public static final String CL_P_QH_TRANSDATE						= "PQH_TRANSDATE";
																		//BELOW ONLY EXIST IN WEB
	public static final String CL_P_QH_DATEUPLOADED						= "PQH_DATEUPLOADED";
	public static final String CL_P_QH_LOCALID							= "PQH_LOCALID";
	public static final String[] ALL_KEY_POND_QH = new String[]{
			CL_P_QH_ID, CL_P_QH_QUANTITY, CL_P_QH_MATHOP, CL_P_QH_FROM, CL_P_QH_TO, CL_P_QH_TRANSDATE
	};
	public static final String[] ALL_KEY_POND_QH_DATAPROP = new String[]{INTEGER +" "+PRIMARY_AUTOINCRE, INTEGER, TEXT, TEXT, TEXT, TEXT};


	public static final String TBL_Pond_FQ 			= "TBL_P_FINALQUANTITY"; //TBLFinalQuantity
	public static final String CL_P_FQuantity_ID 			= "PFQ_ID";
	public static final String CL_P_FQuantity_HandlerID 	= "PFQ_HANDLER_ID";
	public static final String CL_P_FQuantity_QUANTITY 		= "PFQ_QUANTITY";
	public static final String CL_P_FQuantity_DATE 			= "PFQ_DATE";
															//BELOW EXIST ONLY IN WEB DB
	public static final String CL_P_FQuantity_DATEUPLOADED 	= "PFQ_DATEUPLOADED";
	public static final String CL_P_FQuantity_LOCALID 		= "PFQ_LOCALID";
	public static final String[] ALL_KEY_POND_FQ = new String[]{ CL_P_FQuantity_ID, CL_P_FQuantity_HandlerID, CL_P_FQuantity_QUANTITY, CL_P_FQuantity_DATE };
	public static final String[] ALL_KEY_POND_FQ_DATAPROP = new String[]{INTEGER +" "+PRIMARY_AUTOINCRE, INTEGER, INTEGER, TEXT};



	/** STORED CREDS **/
	public static final String TBL_TMP_CRED =  "tbltemp";
	public static final String CL_TMP_id 				=  "tmp_id";
	public static final String CL_TMP_currentuserID 	=  "tmp_userid";
	public static final String CL_TMP_currentuserLvl			=  "tmp_userlvl";
	public static final String CL_TMP_isactive 			=  "tmp_isactive";
	public static final String CL_TMP_UserName 			=  "tmp_username";
	public static final String CL_TMP_UserFirstname 	=  "tmp_firstname";
	public static final String CL_TMP_UserLastname		=  "tmp_lastname";
	public static final String CL_TMP_Password			=  "tmp_password";
	public static final String CL_TMP_dateAddedToDB		=  "tmp_dateAddedToDB";
	public static final String CL_TMP_assignedArea		=  "tmp_assignedArea";
	public static final String CL_TMP_deviceID			=  "tmp_deviceid";
	public static final String CL_TMP_isloggedout		=  "tmp_isloggedOut";

	public static final String[] ALL_DATATYPE_TMP= new String[]{INTEGER + " " + PRIMARY_AUTOINCRE, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT};
	public static final String[] ALL_KEY_TMP = new String[]{CL_TMP_id, CL_TMP_currentuserID,
			CL_TMP_currentuserLvl, CL_TMP_isactive, CL_TMP_UserName, CL_TMP_UserLastname, CL_TMP_UserFirstname,
			CL_TMP_Password, CL_TMP_dateAddedToDB, CL_TMP_assignedArea, CL_TMP_deviceID, CL_TMP_isloggedout};




	//////////////////////////////////////////////////////////////////
	///////////// STRINGS FOR CREATING AND UPDATING TABLE ////////////
	//////////////////////////////////////////////////////////////////
	//Query to create tables

	private static final String TBL_CREATE_MAINCUSTOMERINFO =
			"CREATE TABLE " + TBLMAINCUSTOMERINFO + " " +
					"(" +
					CL_MAINCUSTINFO_ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_MAINCUSTINFO_LastName 	+ " TEXT, " +
					CL_MAINCUSTINFO_FirstName 	+ " TEXT, " +
					CL_MAINCUSTINFO_MiddleName 	+ " TEXT, " +
					CL_MAINCUSTINFO_FarmId 		+ " TEXT, " +
					CL_MAINCUSTINFO_HouseNumber + " INTEGER, " +
					CL_MAINCUSTINFO_Street 		+ " TEXT, " +
					CL_MAINCUSTINFO_Subdivision + " TEXT, " +
					CL_MAINCUSTINFO_Barangay 	+ " TEXT, " +
					CL_MAINCUSTINFO_City 		+ " TEXT, " +
					CL_MAINCUSTINFO_Province 	+ " TEXT, " +
					CL_MAINCUSTINFO_CBirthday 	+ " DATE, " +
					CL_MAINCUSTINFO_CBirthPlace + " TEXT, " +
					CL_MAINCUSTINFO_Telephone 	+ " TEXT, " +
					CL_MAINCUSTINFO_Cellphone 	+ " TEXT, " +
					CL_MAINCUSTINFO_CivilStatus + " TEXT, " +
					CL_MAINCUSTINFO_S_FirstName + " TEXT, " +
					CL_MAINCUSTINFO_S_LastName 	+ " TEXT, " +
					CL_MAINCUSTINFO_S_MiddleName + " TEXT, " +
					CL_MAINCUSTINFO_S_BirthDay 	+ " DATE, " +
					CL_MAINCUSTINFO_HouseStatus + " TEXT, " +
					CL_MAINCUSTINFO_Latitude 	+ " TEXT, " +
					CL_MAINCUSTINFO_Longitude 	+ " TEXT, " +
					CL_MAINCUSTINFO_DateAdded 	+ " DATETIME, " +
					CL_MAINCUSTINFO_AddedBy		+ " INTEGER, " +
					CL_MAINCUSTINFO_isposted	+ " INTEGER, " +
					CL_MAINCUSTINFO_type		+ " INTEGER " +
					")";


	private static final String TBL_CREATE_AREA =
			"CREATE TABLE " + TBLAREA + " " +
					"(" +
					CL_AREA_ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_AREA_DESCRIPTION + " TEXT " +
					")";


	private static final String TBL_CREATE_ASSIGNED_AREA =
			"CREATE TABLE " + TBLAREA_ASSIGNED + " " +
					"(" +
					CL_ASSIGNED_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_ASSIGNED_USERID 			+ " INTEGER, " +
					CL_ASSIGNED_AREA 			+ " INTEGER, " +
					CL_ASSIGNED_MUNICIPALITY 	+ " INTEGER " +
					")";

	private static final String TBL_CREATE_MUNICIPALITY =
			"CREATE TABLE " + TBLAREA_MUNICIPALITY + " " +
					"(" +
					CL_MUNICIPALITY_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_ASSIGNED_USERID 			+ " INTEGER, " +
					CL_MUNICIPALITY_DESCRIPTION + " TEXT, " +
					CL_MUNICIPALITY_PROVINCE 	+ " INTEGER " +
					")";


	private static final String TBL_CREATE_FARMINFO =
			"CREATE TABLE " + TBLFARMiNFO + " " +
					"(" +
					CL_FarmInfo_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_FARMINFO_LAT 			+ " TEXT, " +
					CL_FARMINFO_LNG 			+ " TEXT, " +
					CL_FARMINFO_CONTACT_NAME	+ " TEXT, " +
					CL_FARMINFO_COMPANY			+ " TEXT, " +
					CL_FARMINFO_FARM_ADDRESS	+ " TEXT, " +
					CL_FARMINFO_FARM_NAME		+ " TEXT, " +
					CL_FARMINFO_FARM_ID			+ " TEXT, " +
					CL_FARMINFO_C_NUMBER		+ " TEXT, " +
					CL_FARMINFO_CULTYPE			+ " TEXT, " +
					CL_FARMINFO_CULTlVL			+ " TEXT, " +
					CL_FARMINFO_WATTYPE			+ " TEXT, " +
					CL_FARMINFO_dateAdded		+ " DATE, " +
					CL_FARMINFO_addedby			+ " INTEGER, " +
					CL_FARMINFO_IsPosted		+ " INTEGER " +
					")";

	private static final String TBL_CREATE_POND =
			"CREATE TABLE " + TBLPOND + " " +
					"(" +
					CL_POND_INDEX				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_POND_PID 				+ " INTEGER, " +
					CL_POND_specie 				+ " TEXT, " +
					CL_POND_sizeofStock 		+ " TEXT, " +
					CL_POND_survivalrate 		+ " INTEGER, " +
					CL_POND_dateStocked 		+ " DATE, " +
					CL_POND_quantity 			+ " INTEGER, " +
					CL_POND_area 				+ " INTEGER, " +
					CL_POND_culturesystem 		+ " TEXT, " +
					CL_POND_remarks 			+ " TEXT, " +
					CL_POND_customerId			+ " INTEGER, " +
					CL_POND_isPosted			+ " INTEGER, " +
					CL_POND_isHarvested			+ " INTEGER, " +
					CL_POND_dateInserted		+ " TEXT " +
					")";


	private static final String TBL_CREATE_WEEKLYUPDATES =
			"CREATE TABLE " + TBLPOND_WeeklyUpdates + " " +
					"(" +
					CL_WEEKLY_UPDATES_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_WEEKLY_UPDATES_CURRENT_ABW 		+ " INTEGER, " +
					CL_WEEKLY_UPDATES_CURRENT_SURVIVALRATE 	+ " INTEGER, " +
					CL_WEEKLY_UPDATES_REMARKS 			+ " TEXT, " +
					CL_WEEKLY_UPDATES_PONDID 			+ " TEXT, " +
					CL_WEEKLY_UPDATES_DATEADDED 		+ " INTEGER, " +
					CL_WEEKLY_UPDATES_isposted 			+ " INTEGER " +
					")";


	private static final String TBL_CREATE_USERS =
			"CREATE TABLE " + TBLUSERS + " " +
					"(" +
					CL_USERS_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_USERS_userlvl 		+ " INTEGER, " +
					CL_USERS_firstName 		+ " TEXT, " +
					CL_USERS_lastName 		+ " TEXT, " +
					CL_USERS_username 		+ " TEXT, " +
					CL_USERS_password 		+ " TEXT, " +
					CL_USERS_deviceid 		+ " TEXT, " +
					CL_USERS_dateAdded 		+ " DATE, " +
					CL_USERS_isactive 		+ " INTEGER " +
					")";

	private static final String TBL_CREATE_USERS_ACTIVITY =
			"CREATE TABLE " + TBLUSER_ACTIVITY + " " +
					"(" +
					CL_USER_ACTIVITY_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CL_USER_ACTIVITY_USERID 		+ " INTEGER, " +
					CL_USER_ACTIVITY_ACTIONDONE 	+ " TEXT, " +
					CL_USER_ACTIVITY_LAT 			+ " TEXT, " +
					CL_USER_ACTIVITY_LNG 			+ " TEXT, " +
					CL_USER_ACTIVITY_DATETIME 		+ " DATETIME, " +
					CL_USER_ACTIVITY_ACTIONTYPE 	+ " TEXT, " +
					CL_USER_ACTIVITY_isPosted 		+ " INTEGER" +
					")";


	public static final String createTableString(String tableName, String[] columns, String[] dataProperty){

		String sqlCreate = "CREATE TABLE " + tableName +
				" (";

		for (int i = 0; i < columns.length; i++) {
			sqlCreate = sqlCreate + columns[i] + " " + dataProperty[i] + ", ";
		}

		sqlCreate = sqlCreate.substring(0, sqlCreate.length() - 2);
		sqlCreate = sqlCreate +
				" )";

		return sqlCreate;
	}


	//connects db
	public GPSHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d(LOGTAG, "table " + DATABASE_NAME + " has been opened: " + String.valueOf(ctx));

		context = ctx;
	}

	@Override
	//create tb
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TBL_CREATE_MAINCUSTOMERINFO);
		db.execSQL(TBL_CREATE_FARMINFO);
		db.execSQL(TBL_CREATE_POND);
		db.execSQL(TBL_CREATE_WEEKLYUPDATES);
		db.execSQL(TBL_CREATE_USERS);
		db.execSQL(TBL_CREATE_USERS_ACTIVITY);
		createHarvestinFoTable(db);
		createPondQHTable(db);
		createPondFQTable(db);
		db.execSQL(createTableString(TBL_TMP_CRED,ALL_KEY_TMP, ALL_DATATYPE_TMP));

		Log.d(LOGTAG, "tables has been created: " + String.valueOf(db));
	}

	@Override
	//on update version renew tb
	public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {

		Log.d("UPGRADE", "START ON UPGRADE: from "+oldVersion +" to " + newVersion);


		if(oldVersion < 19)
		{
			// Version 18 added isHarvested COLUMN on TBLPOND
			_db.execSQL("ALTER TABLE " + TBLPOND + " ADD COLUMN "
					+ CL_POND_isHarvested + " TEXT");
		}

		if(oldVersion < 21){
			// Version 20 UPDATE VALUES
			_db.execSQL("DELETE FROM "+TBLPOND+" " +
					"WHERE "+CL_POND_isHarvested+"= 0;");
			_db.execSQL("UPDATE " + TBLPOND + " SET " + CL_POND_isHarvested + " = 0;");
		}

		if (oldVersion < 22) {
			// Version 22 Create Harvest Info
			createHarvestinFoTable(_db);
		}

//		if (oldVersion < 23) {
//			_db.execSQL("ALTER TABLE " + TBL_HARVESTINFO + " ADD COLUMN "
//					+ CL_HRV_DATE_INSERTED + " TEXT");
//		}

		if(oldVersion < 24){
			_db.execSQL("ALTER TABLE " + TBLPOND + " ADD COLUMN "
					+ CL_POND_dateInserted + " TEXT");
			_db.execSQL("UPDATE " + TBLPOND + " SET " + CL_POND_dateInserted + " = 0;");
		}

		if (oldVersion < 25){
			createPondQHTable(_db);
		}

		if (oldVersion < 26){
			createPondFQTable(_db);
		}

		if (oldVersion < 27){
			_db.execSQL(createTableString(TBL_TMP_CRED,ALL_KEY_TMP, ALL_DATATYPE_TMP));
		}
	}

	private void createHarvestinFoTable(SQLiteDatabase _db) {
		String createHarvestInfo = createTableString(TBL_HARVESTINFO, ALL_KEY_HARVESTINFO, ALL_KEY_HARVESTEDINFO_DATAPROP);
		_db.execSQL(createHarvestInfo);
	}

	private void createPondQHTable(SQLiteDatabase db) {
		String createPondQH = createTableString(TBL_Pond_QH, ALL_KEY_POND_QH, ALL_KEY_POND_QH_DATAPROP);
		db.execSQL(createPondQH);
	}

	private void createPondFQTable(SQLiteDatabase db) {
		String createPondFinalQuantity = createTableString(TBL_Pond_FQ, ALL_KEY_POND_FQ, ALL_KEY_POND_FQ_DATAPROP);
		db.execSQL(createPondFinalQuantity);
	}

}
