package com.elitech.human.resource.vo.redis;

import java.util.List;

/**
 * 
 * @create by Adam
 */
public class MenuVO {

    private String menuId;
    private String menuName;
    private String PMenuId;
    private String status;
    private String icon;
    private Long seq;
	private List<MenuVO> subMenus;
	private List<FunctionVO> functions;

	public List<FunctionVO> getFunctions () {
		return functions;
	}

	public void setFunctions (List<FunctionVO> functions) {
		this.functions = functions;
	}

	public String getMenuId () {
		return menuId;
	}

	public void setMenuId (String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName () {
		return menuName;
	}

	public void setMenuName (String menuName) {
		this.menuName = menuName;
	}

	public String getPMenuId () {
		return PMenuId;
	}

	public void setPMenuId (String pMenuId) {
		PMenuId = pMenuId;
	}

	public String getStatus () {
		return status;
	}

	public void setStatus (String status) {
		this.status = status;
	}

	public Long getSeq () {
		return seq;
	}

	public void setSeq (Long seq) {
		this.seq = seq;
	}

	public List<MenuVO> getSubMenus () {
		return subMenus;
	}

	public void setSubMenus (List<MenuVO> subMenus) {
		this.subMenus = subMenus;
	}

	public String getIcon () {
		return icon;
	}

	public void setIcon (String icon) {
		this.icon = icon;
	}
	
}


