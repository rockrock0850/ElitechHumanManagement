package com.elitech.human.resource.controller.base;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.elitech.human.resource.util.LogUtil;
import com.elitech.human.resource.util.PropUtil;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.other.FileVO;

/**
 * 基礎流程控制類別
 * 
 * @create by Adam
 */
public class BaseController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	public <T> Object getQueryCondition (HttpSession session, Class<T> clazz) throws Exception {
		Object condition = session.getAttribute("queryCondition");
		return condition == null ? clazz.newInstance() : condition;
	}
	
	/**
	 * 錯誤訊息管理
	 * 
	 * @create by Adam
	 * @create date: Oct 27, 2017
	 *
	 * @param req
	 * @param exception
	 * @return
	 */
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		String accept = req.getHeader("Content-Type");
		LogicException logicException;
		ModelAndView modelAndView = new ModelAndView();
		
		if (e instanceof LogicException) {
			logicException = (LogicException) e;
		} else {
			Exception exception = (Exception) e;
			String type = exception.getClass().getSimpleName();
			String msg = exception.getMessage();
			String target = LogUtil.getExceptionClazz(exception);
			
			logicException = new LogicException("99999", type + ": " + msg, target);
			log.error("Print Stack Trace: ", exception);
		}

		if (StringUtils.contains(accept, "json") || StringUtils.contains(accept, "multipart")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(logicException);
		}
		
		modelAndView.addObject("exception", logicException);
		modelAndView.setViewName("common.error");
		
		return modelAndView;
	}

    /**
     * 將前端收到的檔案物件轉成自訂型態的檔案物件
     * 
     * @author Adam Yeh
     * @create date: Mar 3, 2017
     *
     * @param MultipartFile multipartFile
     * @return FileVO
     * @throws Exception 
     */
	protected FileVO toFileVO(MultipartFile multipartFile) throws Exception {
        FileVO fileVO = new FileVO();
        String[] retval = multipartFile.getOriginalFilename().split("\\.");
        String name = retval[0];
        String extension = retval[1];
        byte[] bytes = multipartFile.getBytes();
        String root = PropUtil.getProperty("temp.file");

        // 注意: bytes不會為null, 因為前端一定會要求選擇一個Excel檔案
        String filePath = root + File.separator + extension + File.separator + name + "." + extension;
        File file = new File(filePath);
        FileUtils.writeByteArrayToFile(file, bytes);

        fileVO.setName(name);
        fileVO.setFileBinary(bytes);
        fileVO.setExtension(extension);
        fileVO.setFile(file);

        return fileVO;
    }
	
}
