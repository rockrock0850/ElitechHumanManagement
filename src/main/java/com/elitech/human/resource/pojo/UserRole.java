package com.elitech.human.resource.pojo;
// Generated Nov 6, 2017 5:40:29 PM by Hibernate Tools 4.0.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserRole generated by hbm2java
 */
@Entity
@Table(name="user_role"
    ,schema="permission"
)
public class UserRole  implements java.io.Serializable {


     private String roleId;
     private String roleName;
     private String status;
     private String createUser;
     private Date createTime;
     private String modifyUser;
     private Date modifyTime;

    public UserRole() {
    }

	
    public UserRole(String roleId, String roleName, String status, String createUser, Date createTime) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.status = status;
        this.createUser = createUser;
        this.createTime = createTime;
    }
    public UserRole(String roleId, String roleName, String status, String createUser, Date createTime, String modifyUser, Date modifyTime) {
       this.roleId = roleId;
       this.roleName = roleName;
       this.status = status;
       this.createUser = createUser;
       this.createTime = createTime;
       this.modifyUser = modifyUser;
       this.modifyTime = modifyTime;
    }
   
     @Id 

    
    @Column(name="role_id", unique=true, nullable=false, length=20)
    public String getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    
    @Column(name="role_name", nullable=false, length=100)
    public String getRoleName() {
        return this.roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    
    @Column(name="status", nullable=false, length=1)
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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


