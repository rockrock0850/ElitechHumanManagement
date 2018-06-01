package com.elitech.human.resource.vo.form.location;

/**
 * 
 * @create by Adam
 */
public class LocationVO {

	private String locationId;
	private String locationName;
	private String pm;
	private String sales;
    private String workingTime;
    private String offTime;
    private double lunchTime;

	public String getLocationId () {
		return locationId;
	}

	public void setLocationId (String locationId) {
		this.locationId = locationId;
	}

	public String getLocationName () {
		return locationName;
	}

	public void setLocationName (String locationName) {
		this.locationName = locationName;
	}

	public String getPm () {
		return pm;
	}

	public void setPm (String pm) {
		this.pm = pm;
	}

	public String getSales () {
		return sales;
	}

	public void setSales (String sales) {
		this.sales = sales;
	}

	public String getWorkingTime () {
		return workingTime;
	}

	public void setWorkingTime (String workingTime) {
		this.workingTime = workingTime;
	}

	public String getOffTime () {
		return offTime;
	}

	public void setOffTime (String offTime) {
		this.offTime = offTime;
	}

	public double getLunchTime () {
		return lunchTime;
	}

	public void setLunchTime (double lunchTime) {
		this.lunchTime = lunchTime;
	}

}
