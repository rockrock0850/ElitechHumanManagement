package com.elitech.human.resource.tx;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elitech.human.resource.service.HtmlElementService;
import com.elitech.human.resource.vo.other.ParamVO;

/**
 * 取得前端元件參數內容交易控制類別
 * 
 * @create by Adam
 */
@Service
public class HtmlElementTx {
	
	@Autowired
	private HtmlElementService service;

	@Transactional
	public List<ParamVO> findSeletParam (ParamVO param) throws Exception {
		return service.findSeletParam(param);
	}

}


