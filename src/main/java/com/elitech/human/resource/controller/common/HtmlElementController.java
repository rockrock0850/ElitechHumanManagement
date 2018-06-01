package com.elitech.human.resource.controller.common;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.elitech.human.resource.controller.base.BaseController;
import com.elitech.human.resource.tx.HtmlElementTx;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.other.ParamVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 取得前端元件參數內容控制類別
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/HtmlElement")
public class HtmlElementController extends BaseController {
	
	@Autowired
	private HtmlElementTx tx;
	
	@PostConstruct
	public void init() {}

	@RequestMapping(value = "/select", method = RequestMethod.POST)
	public ResponseVO select (HttpServletRequest request, @RequestBody ParamVO param) throws Exception {
		List<ParamVO> params = tx.findSeletParam(param);
		return new ResponseVO(params);
	}

	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}