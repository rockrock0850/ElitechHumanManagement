package rest;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elitech.human.resource.constant.Api;
import com.elitech.human.resource.util.AesCryptUtil;
import com.elitech.human.resource.util.ApiUtil;
import com.elitech.human.resource.vo.rest.RequestVO;

import common.BaseTest;

/**
 * 
 * @create by Adam
 */
public class LoginRestTest extends BaseTest {

	@Test
	public void addAccount() throws Exception {
		String password = AesCryptUtil.encrypt("elitech07");
		String domain = "http://localhost:8080";
		
		RequestVO jsonObj = new RequestVO(domain);
		jsonObj.put("accountId", "elitech07");
		jsonObj.put("accountName", "員工07");
		jsonObj.put("accountPasswd", password);
		jsonObj.put("email", "adam.yeh@elitechtw01.com");
		jsonObj.put("createUser", "Adam");
		jsonObj.done();
		ApiUtil.call(Api.ADD_ACCOUNT, jsonObj, RequestMethod.POST);
	}

	@Test
	public void editAccountStatus() throws Exception {
		String domain = "http://localhost:8080";
		
		RequestVO jsonObj = new RequestVO(domain);
		jsonObj.put("accountId", "elitech04");
		jsonObj.put("status", "0");
		jsonObj.put("modifyUser", "adam.yeh");
		jsonObj.done();
		ApiUtil.call(Api.EDIT_ACCOUNT, jsonObj, RequestMethod.POST);
	}
	
}


