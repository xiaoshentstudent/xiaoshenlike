package com.xiaoshen.xiaoshenlike.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoshen.xiaoshenlike.constant.UserConstant;
import com.xiaoshen.xiaoshenlike.model.domain.User;
import com.xiaoshen.xiaoshenlike.service.UserService;
import com.xiaoshen.xiaoshenlike.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
* @author 小申同学
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-09-18 17:59:14
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

	@Override
	public User getLoginUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(UserConstant.LOGIN_USER);
	}


	}




