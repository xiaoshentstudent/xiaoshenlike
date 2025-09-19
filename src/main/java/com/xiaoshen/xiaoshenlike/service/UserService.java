package com.xiaoshen.xiaoshenlike.service;

import com.xiaoshen.xiaoshenlike.constant.UserConstant;
import com.xiaoshen.xiaoshenlike.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 小申同学
* @description 针对表【user】的数据库操作Service
* @createDate 2025-09-18 17:59:14
*/
public interface UserService extends IService<User> {


	/**
	 * 获取当前登录用户
	 *
	 * @param request httpServletRequest
	 * @return User
	 */

	User getLoginUser(HttpServletRequest request);

}
