package com.elitech.human.resource.pojo;
// Generated Nov 24, 2017 4:39:19 PM by Hibernate Tools 4.0.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Department generated by hbm2java
 */
@Entity
@Table(name="department"
    ,schema="base"
)
public class Department  implements java.io.Serializable {


     private String departmentId;
     private String departmentName;
     private String parentDepartmentId;
     private String departmentManager;
     private String createUser;
     private Date createTime;
     private String modifyUser;
     private Date modifyTime;

    public Department() {
    }

	
    public Department(String departmentId, String departmentName, String parentDepartmentId, String createUser, Date createTime) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.parentDepartmentId = parentDepartmentId;
        this.createUser = createUser;
        this.createTime = createTime;
    }
    public Department(String departmentId, String departmentName, String parentDepartmentId, String departmentManager, String createUser, Date createTime, String modifyUser, Date modifyTime) {
       this.departmentId = departmentId;
       this.departmentName = departmentName;
       this.parentDepartmentId = parentDepartmentId;
       this.departmentManager = departmentManager;
       this.createUser = createUser;
       this.createTime = createTime;
       this.modifyUser = modifyUser;
       this.modifyTime = modifyTime;
    }
   
     @Id 

    
    @Column(name="department_id", unique=true, nullable=false)
    public String getDepartmentId() {
        return this.departmentId;
    }
    
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    
    @Column(name="department_name", nullable=false)
    public String getDepartmentName() {
        return this.departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    
    @Column(name="parent_department_id", nullable=false)
    public String getParentDepartmentId() {
        return this.parentDepartmentId;
    }
    
    public void setParentDepartmentId(String parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId;
    }

    
    @Column(name="department_manager", length=20)
    public String getDepartmentManager() {
        return this.departmentManager;
    }
    
    public void setDepartmentManager(String departmentManager) {
        this.departmentManager = departmentManager;
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

