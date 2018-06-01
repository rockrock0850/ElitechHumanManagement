package com.elitech.human.resource.vo.redis;

import java.util.List;

/**
 * 
 * @create by Adam
 */
public class PermissionsVO {
	
	private List<MenuVO> menus;
	private List<ButtonVO> buttons;

	public List<MenuVO> getMenus () {
		return menus;
	}

	public void setMenus (List<MenuVO> menus) {
		this.menus = menus;
	}

	public List<ButtonVO> getButtons () {
		return buttons;
	}

	public void setButtons (List<ButtonVO> buttons) {
		this.buttons = buttons;
	}
	
}


