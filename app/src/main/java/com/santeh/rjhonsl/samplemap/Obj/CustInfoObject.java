package com.santeh.rjhonsl.samplemap.Obj;

import android.graphics.Bitmap;

public class CustInfoObject {
	
	private int id;
	private int ci_id;
	private int counter;
	private int houseNumber;


	private String latitude;
	private String longtitude;
	private String cust_latitude;
	private String cust_longtitude;


	private String contact_name;
	private String company;
	private String address;
	private String farmname;
	private String farmID;
	private String CustfarmID;
	private String contact_number;
	private String cultureType;
	private String culturelevel;
	private String waterType;
	private String dateAddedToDB;
	private Bitmap bitmap;

	private String dateTime;
	private String assingedArea;
	private String actionType;
	private String farm_addedBy;


	private String username;
	private String deviceid;
	private String firstname;
	private String lastname;
	private String middleName;
	private String birthday;
	private String birthPlace;
	private String telephone;
	private String cellphone;
	private String spouse_fname;
	private String spouse_mname;
	private String spouse_lname;
	private String spouse_birthday;
	private String civilStatus;

	private String accountlevelDescription;
	private int userlevel;
	private int userid;
	private int pondID;
	private int area;
	private int quantity;
	private int sizeofStock;
	private int totalStockOfFarm;
    private int pondIndex;
    private String pondLocalIndex;
	private int currentweekofStock;
	private int startweekofStock;
	private int week;
	private int currentABW;
	private int farmLocalID;


	private int isVisited;
	private int isactive;
	private int isPosted;
	private int isharvested;
	private int isPosted_farm;
	private int isPosted_pond;
	private int isPosted_cust;
	private int isPosted_weekly;


	private String hrv_id;
	private String hrv_pondid;
	private String hrv_casenum;
	private String hrv_specie;
	private String hrv_dateOfHarvest;
	private String hrv_finalABW;
	private String hrv_totalConsumption;
	private String hrv_fcr;
	private String hrv_pricePerKilo;
	private String hrv_totalHarvested;
	private String hrv_isPosted;
	private String hrv_localid;
	private String hrv_dateRecorded;
	private String hrv_dateuploaded;



	private String customerID;
	private String mainCustomerId;
	private String mainCustomer_localid;
	private String specie;
	private String culturesystem;
	private String dateStocked;
	private String remarks;
	private String allSpecie;
	private String currentFeedType;

	private String province;
	private String municipality;
	private String street;
	private String barangay;
	private String city;
	private String subdivision;
	private String houseStatus;
	private String addedBy;
	private String addedBy_farm;
	private String addedBy_cust;
	private String addedBy_pond;
	private String addedBy_weekly;
	private String customerType;
	private String w_update_id;
	private String w_update_currentabw;
	private String w_update_survivalrate;
	private String w_update_remarks;
	private String w_update_pondid;
	private String w_update_dateAdded;
	private String w_update_localid;




	int province_id;
	int municipality_id;
	int municipality_province;



	private String actionDone;
	private String recommendedConsumption;
	private String actualConsumption;

	private double weeklyConsumptionInGrams;
	private String survivalrate_per_pond;

	public CustInfoObject() {
	}



	public String getAssingedArea() {
		return assingedArea;
	}

	public void setAssingedArea(String assingedArea) {
		this.assingedArea = assingedArea;
	}


	public String getSurvivalrate_per_pond() {
		return survivalrate_per_pond;
	}

	public void setSurvivalrate_per_pond(String survivalrate_per_pond) {
		this.survivalrate_per_pond = survivalrate_per_pond;
	}

	public int getIsVisited() {
		return isVisited;
	}

	public void setIsVisited(int isVisited) {
		this.isVisited = isVisited;
	}

	public int getUserlevel() {
		return userlevel;
	}

	public void setUserlevel(int userlevel) {
		this.userlevel = userlevel;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAccountlevelDescription() {
		return accountlevelDescription;
	}

	public void setAccountlevelDescription(String accountlevelDescription) {
		this.accountlevelDescription = accountlevelDescription;
	}


	public double getWeeklyConsumptionInGrams() {
		return weeklyConsumptionInGrams;
	}

	public void setWeeklyConsumptionInGrams(double weeklyConsumptionInGrams) {
		this.weeklyConsumptionInGrams = weeklyConsumptionInGrams;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}


	public int getStartweekofStock() {
		return startweekofStock;
	}

	public void setStartweekofStock(int startweekofStock) {
		this.startweekofStock = startweekofStock;
	}


	public String getRecommendedConsumption() {
		return recommendedConsumption;
	}

	public void setRecommendedConsumption(String recommendedConsumption) {
		this.recommendedConsumption = recommendedConsumption;
	}

	public String getActualConsumption() {
		return actualConsumption;
	}

	public void setActualConsumption(String actualConsumption) {
		this.actualConsumption = actualConsumption;
	}




	public String getCurrentFeedType() {
		return currentFeedType;
	}

	public void setCurrentFeedType(String currentFeedType) {
		this.currentFeedType = currentFeedType;
	}

	public int getCurrentweekofStock() {
		return currentweekofStock;
	}

	public void setCurrentweekofStock(int currentweekofStock) {
		this.currentweekofStock = currentweekofStock;
	}


	public String getAllSpecie() {
		return allSpecie;
	}

	public void setAllSpecie(String allSpecie) {
		this.allSpecie = allSpecie;
	}


	public int getTotalStockOfFarm() {
		return totalStockOfFarm;
	}

	public void setTotalStockOfFarm(int totalStockOfFarm) {
		this.totalStockOfFarm = totalStockOfFarm;
	}



	public int getCi_id() {
		return ci_id;
	}

	public void setCi_id(int ci_id) {
		this.ci_id = ci_id;
	}


	public int getPondIndex() {
        return pondIndex;
    }

    public void setPondIndex(int pondIndex) {
        this.pondIndex = pondIndex;
    }

	public int getSizeofStock() {
		return sizeofStock;
	}

	public void setSizeofStock(int sizeofStock) {
		this.sizeofStock = sizeofStock;
	}

	public int getPondID() {
		return pondID;
	}

	public void setPondID(int pondID) {
		this.pondID = pondID;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getSpecie() {
		return specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}

	public String getCulturesystem() {
		return culturesystem;
	}

	public void setCulturesystem(String culturesystem) {
		this.culturesystem = culturesystem;
	}

	public String getDateStocked() {
		return dateStocked;
	}

	public void setDateStocked(String dateStocked) {
		this.dateStocked = dateStocked;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_number() {
		return contact_number;
	}

	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}

	public String getCulturelevel() {
		return culturelevel;
	}

	public void setCulturelevel(String culturelevel) {
		this.culturelevel = culturelevel;
	}

	public String getCultureType() {
		return cultureType;
	}

	public void setCultureType(String cultureType) {
		this.cultureType = cultureType;
	}

	public String getDateAddedToDB() {
		return dateAddedToDB;
	}

	public void setDateAddedToDB(String dateAddedToDB) {
		this.dateAddedToDB = dateAddedToDB;
	}

	public String getFarmID() {
		return farmID;
	}

	public void setFarmID(String farmID) {
		this.farmID = farmID;
	}

	public String getFarmname() {
		return farmname;
	}

	public void setFarmname(String farmname) {
		this.farmname = farmname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	public String getWaterType() {
		return waterType;
	}

	public void setWaterType(String waterType) {
		this.waterType = waterType;
	}

	public String getActionDone() {
		return actionDone;
	}

	public void setActionDone(String actionDone) {
		this.actionDone = actionDone;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String provice) {
		this.province = provice;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public int getProvince_id() {
		return province_id;
	}

	public void setProvince_id(int province_id) {
		this.province_id = province_id;
	}

	public int getMunicipality_id() {
		return municipality_id;
	}

	public void setMunicipality_id(int municipality_id) {
		this.municipality_id = municipality_id;
	}

	public int getMunicipality_province() {
		return municipality_province;
	}

	public void setMunicipality_province(int municipality_province) {
		this.municipality_province = municipality_province;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBarangay() {
		return barangay;
	}

	public void setBarangay(String barangay) {
		this.barangay = barangay;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSubdivision() {
		return subdivision;
	}

	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}

	public String getSpouse_fname() {
		return spouse_fname;
	}

	public void setSpouse_fname(String spouse_fname) {
		this.spouse_fname = spouse_fname;
	}

	public String getSpouse_mname() {
		return spouse_mname;
	}

	public void setSpouse_mname(String spouse_mname) {
		this.spouse_mname = spouse_mname;
	}

	public String getSpouse_lname() {
		return spouse_lname;
	}

	public void setSpouse_lname(String spouse_lname) {
		this.spouse_lname = spouse_lname;
	}

	public String getCivilStatus() {
		return civilStatus;
	}

	public void setCivilStatus(String civilStatus) {
		this.civilStatus = civilStatus;
	}

	public String getSpouse_birthday() {
		return spouse_birthday;
	}

	public void setSpouse_birthday(String spouse_birthday) {
		this.spouse_birthday = spouse_birthday;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(String houseStatus) {
		this.houseStatus = houseStatus;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getCust_latitude() {
		return cust_latitude;
	}

	public void setCust_latitude(String cust_latitude) {
		this.cust_latitude = cust_latitude;
	}

	public String getCust_longtitude() {
		return cust_longtitude;
	}

	public void setCust_longtitude(String cust_longtitude) {
		this.cust_longtitude = cust_longtitude;
	}

	public String getMainCustomerId() {
		return mainCustomerId;
	}

	public void setMainCustomerId(String mainCustomerId) {
		this.mainCustomerId = mainCustomerId;
	}

	public int getIsactive() {
		return isactive;
	}

	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceId(String deviceid) {
		this.deviceid = deviceid;
	}

	public int getIsPosted() {
		return isPosted;
	}

	public void setIsPosted(int isPosted) {
		this.isPosted = isPosted;
	}

	public String getCustfarmID() {
		return CustfarmID;
	}

	public void setCustfarmID(String custfarmID) {
		CustfarmID = custfarmID;
	}

	public int getIsPosted_weekly() {
		return isPosted_weekly;
	}

	public void setIsPosted_weekly(int isPosted_weekly) {
		this.isPosted_weekly = isPosted_weekly;
	}

	public int getIsPosted_cust() {
		return isPosted_cust;
	}

	public void setIsPosted_cust(int isPosted_cust) {
		this.isPosted_cust = isPosted_cust;
	}

	public int getIsPosted_pond() {
		return isPosted_pond;
	}

	public void setIsPosted_pond(int isPosted_pond) {
		this.isPosted_pond = isPosted_pond;
	}

	public int getIsPosted_farm() {
		return isPosted_farm;
	}

	public void setIsPosted_farm(int isPosted_farm) {
		this.isPosted_farm = isPosted_farm;
	}

	public String getAddedBy_weekly() {
		return addedBy_weekly;
	}

	public void setAddedBy_weekly(String addedBy_weekly) {
		this.addedBy_weekly = addedBy_weekly;
	}

	public String getAddedBy_pond() {
		return addedBy_pond;
	}

	public void setAddedBy_pond(String addedBy_pond) {
		this.addedBy_pond = addedBy_pond;
	}

	public String getAddedBy_cust() {
		return addedBy_cust;
	}

	public void setAddedBy_cust(String addedBy_cust) {
		this.addedBy_cust = addedBy_cust;
	}

	public String getAddedBy_farm() {
		return addedBy_farm;
	}

	public void setAddedBy_farm(String addedBy_farm) {
		this.addedBy_farm = addedBy_farm;
	}

	public int getCurrentABW() {
		return currentABW;
	}

	public void setCurrentABW(int currentABW) {
		this.currentABW = currentABW;
	}

	public int getFarmLocalID() {
		return farmLocalID;
	}

	public void setFarmLocalID(int farmLocatID) {
		this.farmLocalID = farmLocatID;
	}

	public String getFarm_addedBy() {
		return farm_addedBy;
	}

	public void setFarm_addedBy(String farm_addedBy) {
		this.farm_addedBy = farm_addedBy;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getMainCustomer_localid() {
		return mainCustomer_localid;
	}

	public void setMainCustomer_localid(String mainCustomer_localid) {
		this.mainCustomer_localid = mainCustomer_localid;
	}

	public String getPondLocalIndex() {
		return pondLocalIndex;
	}

	public void setPondLocalIndex(String pondLocalIndex) {
		this.pondLocalIndex = pondLocalIndex;
	}

	public String getW_update_id() {
		return w_update_id;
	}

	public void setW_update_id(String w_update_id) {
		this.w_update_id = w_update_id;
	}

	public String getW_update_localid() {
		return w_update_localid;
	}

	public void setW_update_localid(String w_update_localid) {
		this.w_update_localid = w_update_localid;
	}

	public String getW_update_currentabw() {
		return w_update_currentabw;
	}

	public void setW_update_currentabw(String w_update_currentabw) {
		this.w_update_currentabw = w_update_currentabw;
	}

	public String getW_update_survivalrate() {
		return w_update_survivalrate;
	}

	public void setW_update_survivalrate(String w_update_survivalrate) {
		this.w_update_survivalrate = w_update_survivalrate;
	}

	public String getW_update_remarks() {
		return w_update_remarks;
	}

	public void setW_update_remarks(String w_update_remarks) {
		this.w_update_remarks = w_update_remarks;
	}

	public String getW_update_pondid() {
		return w_update_pondid;
	}

	public void setW_update_pondid(String w_update_pondid) {
		this.w_update_pondid = w_update_pondid;
	}

	public String getW_update_dateAdded() {
		return w_update_dateAdded;
	}

	public void setW_update_dateAdded(String w_update_dateAdded) {
		this.w_update_dateAdded = w_update_dateAdded;
	}

	public String getHrv_id() {
		return hrv_id;
	}

	public void setHrv_id(String hrv_id) {
		this.hrv_id = hrv_id;
	}

	public String getHrv_pondid() {
		return hrv_pondid;
	}

	public void setHrv_pondid(String hrv_pondid) {
		this.hrv_pondid = hrv_pondid;
	}

	public String getHrv_casenum() {
		return hrv_casenum;
	}

	public void setHrv_casenum(String hrv_casenum) {
		this.hrv_casenum = hrv_casenum;
	}

	public String getHrv_specie() {
		return hrv_specie;
	}

	public void setHrv_specie(String hrv_specie) {
		this.hrv_specie = hrv_specie;
	}

	public String getHrv_dateOfHarvest() {
		return hrv_dateOfHarvest;
	}

	public void setHrv_dateOfHarvest(String hrv_dateOfHarvest) {
		this.hrv_dateOfHarvest = hrv_dateOfHarvest;
	}

	public String getHrv_finalABW() {
		return hrv_finalABW;
	}

	public void setHrv_finalABW(String hrv_finalABW) {
		this.hrv_finalABW = hrv_finalABW;
	}

	public String getHrv_totalConsumption() {
		return hrv_totalConsumption;
	}

	public void setHrv_totalConsumption(String hrv_totalConsumption) {
		this.hrv_totalConsumption = hrv_totalConsumption;
	}

	public String getHrv_fcr() {
		return hrv_fcr;
	}

	public void setHrv_fcr(String hrv_fcr) {
		this.hrv_fcr = hrv_fcr;
	}

	public String getHrv_pricePerKilo() {
		return hrv_pricePerKilo;
	}

	public void setHrv_pricePerKilo(String hrv_pricePerKilo) {
		this.hrv_pricePerKilo = hrv_pricePerKilo;
	}

	public String getHrv_totalHarvested() {
		return hrv_totalHarvested;
	}

	public void setHrv_totalHarvested(String hrv_totalHarvested) {
		this.hrv_totalHarvested = hrv_totalHarvested;
	}

	public String getHrv_isPosted() {
		return hrv_isPosted;
	}

	public void setHrv_isPosted(String hrv_isPosted) {
		this.hrv_isPosted = hrv_isPosted;
	}

	public String getHrv_dateRecorded() {
		return hrv_dateRecorded;
	}

	public void setHrv_dateRecorded(String hrv_dateRecorded) {
		this.hrv_dateRecorded = hrv_dateRecorded;
	}

	public int getIsharvested() {
		return isharvested;
	}

	public void setIsharvested(int isharvested) {
		this.isharvested = isharvested;
	}

	public String getHrv_localid() {
		return hrv_localid;
	}

	public void setHrv_localid(String hrv_localid) {
		this.hrv_localid = hrv_localid;
	}

	public String getHrv_dateuploaded() {
		return hrv_dateuploaded;
	}

	public void setHrv_dateuploaded(String hrv_dateuploaded) {
		this.hrv_dateuploaded = hrv_dateuploaded;
	}
}
