package com.elitech.human.resource.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elitech.human.resource.dao.jpa.ParamRepo;
import com.elitech.human.resource.pojo.Param;
import com.elitech.human.resource.pojo.ParamId;
import com.elitech.human.resource.vo.other.ParamVO;

/**
 * 取得前端元件參數內容業務邏輯類別
 * 
 * @create by Adam
 */
@Service
public class HtmlElementService {
	
	@Autowired
	private ParamRepo repo;

	public List<ParamVO> findSeletParam (ParamVO param) throws Exception {
		String key = param.getKey();
		List<Param> pojos = repo.findByKey(key);
		List<ParamVO> vos = new ArrayList<>();
		
		for (Param pojo : pojos) {
			ParamVO vo = new ParamVO();
			ParamId id = pojo.getId();
			
			String value = id.getValue();
			String display = pojo.getDisplay();
			
			vo.setValue(value);
			vo.setDisplay(display);
			
			vos.add(vo);
		}
		
		
		return vos;
	}
	
}

