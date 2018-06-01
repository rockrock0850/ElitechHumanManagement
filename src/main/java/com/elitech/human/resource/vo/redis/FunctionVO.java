package com.elitech.human.resource.vo.redis;

/**
 * 
 * @create by Adam
 */
public class FunctionVO {

    private String functionId;
    private String functionName;
    private String menuId;
    private String status;
    private String namespace;
    private long seq;

	public String getFunctionId () {
		return functionId;
	}

	public void setFunctionId (String functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName () {
		return functionName;
	}

	public void setFunctionName (String functionName) {
		this.functionName = functionName;
	}

	public String getMenuId () {
		return menuId;
	}

	public void setMenuId (String menuId) {
		this.menuId = menuId;
	}

	public String getStatus () {
		return status;
	}

	public void setStatus (String status) {
		this.status = status;
	}

	public String getNamespace () {
		return namespace;
	}

	public void setNamespace (String namespace) {
		this.namespace = namespace;
	}

	public long getSeq () {
		return seq;
	}

	public void setSeq (long seq) {
		this.seq = seq;
	}
	
}


