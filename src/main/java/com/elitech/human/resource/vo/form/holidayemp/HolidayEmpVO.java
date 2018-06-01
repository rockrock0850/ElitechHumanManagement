package com.elitech.human.resource.vo.form.holidayemp;

/**
 * 
 * @create by Adam
 */
public class HolidayEmpVO {

    private String emptype;
    private String leavetypeId;
    private String leaveYear;
    private double years;
    private long leaveDays;
	private String isProportional;
    private String emptypeDisplay;
    private String leaveName;
    private String isYears;

	public String getEmptype () {
		return emptype;
	}

	public void setEmptype (String emptype) {
		this.emptype = emptype;
	}

	public String getLeavetypeId () {
		return leavetypeId;
	}

	public void setLeavetypeId (String leavetypeId) {
		this.leavetypeId = leavetypeId;
	}

	public String getLeaveYear () {
		return leaveYear;
	}

	public void setLeaveYear (String leaveYear) {
		this.leaveYear = leaveYear;
	}

	public double getYears () {
		return years;
	}

	public void setYears (double years) {
		this.years = years;
	}

	public long getLeaveDays () {
		return leaveDays;
	}

	public void setLeaveDays (long leaveDays) {
		this.leaveDays = leaveDays;
	}

	public String getIsProportional () {
		return isProportional;
	}

	public void setIsProportional (String isProportional) {
		this.isProportional = isProportional;
	}

	public String getEmptypeDisplay () {
		return emptypeDisplay;
	}

	public void setEmptypeDisplay (String emptypeDisplay) {
		this.emptypeDisplay = emptypeDisplay;
	}

	public String getLeaveName () {
		return leaveName;
	}

	public void setLeaveName (String leaveName) {
		this.leaveName = leaveName;
	}

	public String getIsYears () {
		return isYears;
	}

	public void setIsYears (String isYears) {
		this.isYears = isYears;
	}

}
