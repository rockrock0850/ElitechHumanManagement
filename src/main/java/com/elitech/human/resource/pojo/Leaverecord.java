package com.elitech.human.resource.pojo;
// Generated Dec 25, 2017 2:09:27 PM by Hibernate Tools 4.0.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Leaverecord generated by hbm2java
 */
@Entity
@Table(name="leaverecord"
    ,schema="hr"
)
public class Leaverecord  implements java.io.Serializable {


     private String leaveNo;
     private String empId;
     private String departmentName;
     private String jobtitleName;
     private String leavetypeId;
     private String reason;
     private Date begindate;
     private String beginhour;
     private String beginmin;
     private Date enddate;
     private String endhour;
     private String endmin;
     private long leavedays;
     private double leavehours;
     private String substitute;
     private String document;
     private String process;
     private String approveStatus;
     private Date closeTime;
     private String createUser;
     private Date createTime;
     private String modifyUser;
     private Date modifyTime;

    public Leaverecord() {
    }

	
    public Leaverecord(String leaveNo, String empId, String departmentName, String jobtitleName, String leavetypeId, String reason, Date begindate, String beginhour, String beginmin, Date enddate, String endhour, String endmin, long leavedays, double leavehours, String process, String approveStatus, String createUser, Date createTime) {
        this.leaveNo = leaveNo;
        this.empId = empId;
        this.departmentName = departmentName;
        this.jobtitleName = jobtitleName;
        this.leavetypeId = leavetypeId;
        this.reason = reason;
        this.begindate = begindate;
        this.beginhour = beginhour;
        this.beginmin = beginmin;
        this.enddate = enddate;
        this.endhour = endhour;
        this.endmin = endmin;
        this.leavedays = leavedays;
        this.leavehours = leavehours;
        this.process = process;
        this.approveStatus = approveStatus;
        this.createUser = createUser;
        this.createTime = createTime;
    }
    public Leaverecord(String leaveNo, String empId, String departmentName, String jobtitleName, String leavetypeId, String reason, Date begindate, String beginhour, String beginmin, Date enddate, String endhour, String endmin, long leavedays, double leavehours, String substitute, String document, String process, String approveStatus, Date closeTime, String createUser, Date createTime, String modifyUser, Date modifyTime) {
       this.leaveNo = leaveNo;
       this.empId = empId;
       this.departmentName = departmentName;
       this.jobtitleName = jobtitleName;
       this.leavetypeId = leavetypeId;
       this.reason = reason;
       this.begindate = begindate;
       this.beginhour = beginhour;
       this.beginmin = beginmin;
       this.enddate = enddate;
       this.endhour = endhour;
       this.endmin = endmin;
       this.leavedays = leavedays;
       this.leavehours = leavehours;
       this.substitute = substitute;
       this.document = document;
       this.process = process;
       this.approveStatus = approveStatus;
       this.closeTime = closeTime;
       this.createUser = createUser;
       this.createTime = createTime;
       this.modifyUser = modifyUser;
       this.modifyTime = modifyTime;
    }
   
     @Id 

    
    @Column(name="leave_no", unique=true, nullable=false, length=12)
    public String getLeaveNo() {
        return this.leaveNo;
    }
    
    public void setLeaveNo(String leaveNo) {
        this.leaveNo = leaveNo;
    }

    
    @Column(name="emp_id", nullable=false, length=20)
    public String getEmpId() {
        return this.empId;
    }
    
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    
    @Column(name="department_name", nullable=false, length=50)
    public String getDepartmentName() {
        return this.departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    
    @Column(name="jobtitle_name", nullable=false, length=50)
    public String getJobtitleName() {
        return this.jobtitleName;
    }
    
    public void setJobtitleName(String jobtitleName) {
        this.jobtitleName = jobtitleName;
    }

    
    @Column(name="leavetype_id", nullable=false, length=6)
    public String getLeavetypeId() {
        return this.leavetypeId;
    }
    
    public void setLeavetypeId(String leavetypeId) {
        this.leavetypeId = leavetypeId;
    }

    
    @Column(name="reason", nullable=false, length=100)
    public String getReason() {
        return this.reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="begindate", nullable=false, length=13)
    public Date getBegindate() {
        return this.begindate;
    }
    
    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    
    @Column(name="beginhour", nullable=false, length=2)
    public String getBeginhour() {
        return this.beginhour;
    }
    
    public void setBeginhour(String beginhour) {
        this.beginhour = beginhour;
    }

    
    @Column(name="beginmin", nullable=false, length=2)
    public String getBeginmin() {
        return this.beginmin;
    }
    
    public void setBeginmin(String beginmin) {
        this.beginmin = beginmin;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="enddate", nullable=false, length=13)
    public Date getEnddate() {
        return this.enddate;
    }
    
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    
    @Column(name="endhour", nullable=false, length=2)
    public String getEndhour() {
        return this.endhour;
    }
    
    public void setEndhour(String endhour) {
        this.endhour = endhour;
    }

    
    @Column(name="endmin", nullable=false, length=2)
    public String getEndmin() {
        return this.endmin;
    }
    
    public void setEndmin(String endmin) {
        this.endmin = endmin;
    }

    
    @Column(name="leavedays", nullable=false)
    public long getLeavedays() {
        return this.leavedays;
    }
    
    public void setLeavedays(long leavedays) {
        this.leavedays = leavedays;
    }

    
    @Column(name="leavehours", nullable=false, precision=17, scale=17)
    public double getLeavehours() {
        return this.leavehours;
    }
    
    public void setLeavehours(double leavehours) {
        this.leavehours = leavehours;
    }

    
    @Column(name="substitute", length=20)
    public String getSubstitute() {
        return this.substitute;
    }
    
    public void setSubstitute(String substitute) {
        this.substitute = substitute;
    }

    
    @Column(name="document")
    public String getDocument() {
        return this.document;
    }
    
    public void setDocument(String document) {
        this.document = document;
    }

    
    @Column(name="process", nullable=false, length=10)
    public String getProcess() {
        return this.process;
    }
    
    public void setProcess(String process) {
        this.process = process;
    }

    
    @Column(name="approve_status", nullable=false, length=10)
    public String getApproveStatus() {
        return this.approveStatus;
    }
    
    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="close_time", length=35)
    public Date getCloseTime() {
        return this.closeTime;
    }
    
    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    
    @Column(name="create_user", nullable=false, length=20)
    public String getCreateUser() {
        return this.createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", nullable=false, length=35)
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    @Column(name="modify_user", length=20)
    public String getModifyUser() {
        return this.modifyUser;
    }
    
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modify_time", length=35)
    public Date getModifyTime() {
        return this.modifyTime;
    }
    
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }




}


