package com.elitech.human.resource.pojo;
// Generated Nov 6, 2017 5:40:29 PM by Hibernate Tools 4.0.0


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserRoleMappingId generated by hbm2java
 */
@Embeddable
public class UserRoleMappingId  implements java.io.Serializable {


     private String userId;
     private String roleId;

    public UserRoleMappingId() {
    }

    public UserRoleMappingId(String userId, String roleId) {
       this.userId = userId;
       this.roleId = roleId;
    }
   


    @Column(name="user_id", nullable=false, length=20)
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Column(name="role_id", nullable=false, length=20)
    public String getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof UserRoleMappingId) ) return false;
		 UserRoleMappingId castOther = ( UserRoleMappingId ) other; 
         
		 return ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) )
 && ( (this.getRoleId()==castOther.getRoleId()) || ( this.getRoleId()!=null && castOther.getRoleId()!=null && this.getRoleId().equals(castOther.getRoleId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         result = 37 * result + ( getRoleId() == null ? 0 : this.getRoleId().hashCode() );
         return result;
   }   


}


