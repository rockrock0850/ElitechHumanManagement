package com.elitech.human.resource.vo.redis;

import java.io.Serializable;

/**
 * 存取登入資訊
 * 
 * @create by Adam
 */
public class RedisVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8326334378746964432L;

	private String accountId;
	private String accountPasswd;
	private String accountName;
	private String appId;
	private PermissionsVO permissions = new PermissionsVO();

	public String getAccountId () {
		return accountId;
	}

	public void setAccountId (String accountId) {
		this.accountId = accountId;
	}

	public String getAccountPasswd () {
		return accountPasswd;
	}

	public void setAccountPasswd (String accountPasswd) {
		this.accountPasswd = accountPasswd;
	}

	public String getAccountName () {
		return accountName;
	}

	public void setAccountName (String accountName) {
		this.accountName = accountName;
	}

	public String getAppId () {
		return appId;
	}

	public void setAppId (String appId) {
		this.appId = appId;
	}

	public PermissionsVO getPermissions () {
		return permissions;
	}

	public void setPermissions (PermissionsVO permissions) {
		this.permissions = permissions;
	}

}
