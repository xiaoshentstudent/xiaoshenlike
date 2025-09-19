package com.xiaoshen.xiaoshenlike.controller;

import com.xiaoshen.xiaoshenlike.common.BaseResponse;
import com.xiaoshen.xiaoshenlike.common.ResultUtils;
import com.xiaoshen.xiaoshenlike.model.domain.User;
import com.xiaoshen.xiaoshenlike.service.SingInService;
import com.xiaoshen.xiaoshenlike.service.UserService;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.implementation.bind.annotation.Pipe;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sign")
public class SignController {

	@Resource
	private UserService userService;

	@Resource
	private SingInService singInService;

	@PostMapping("/do")
	public boolean doSing(HttpServletRequest request) {
		User user = userService.getLoginUser(request);
		return singInService.addUserSingIn(user.getId());
	}

	/**
	 * 获取用户签到记录
	 *
	 * @param year    年份（为空表示当前年份）
	 * @param request
	 * @return 签到记录映射
	 */
	@GetMapping("/get/sign_in")
	public BaseResponse<Map<LocalDate, Boolean>> getUserSignInRecord(Integer year, HttpServletRequest request) {
		// 必须要登录才能获取
		User loginUser = userService.getLoginUser(request);
		Map<LocalDate, Boolean> userSignInRecord = singInService.getUserSignInRecord(loginUser.getId(), year);
		return ResultUtils.success(userSignInRecord);
	}

	/**
	 * 获取用户签到记录
	 *
	 * @param year    年份（为空表示当前年份）
	 * @param request
	 * @return 签到记录映射
	 */
	@GetMapping("/get/sign_in_cache")
	public BaseResponse<Map<LocalDate, Boolean>> getUserSignInRecordAsCache(Integer year, HttpServletRequest request) {
		// 必须要登录才能获取
		User loginUser = userService.getLoginUser(request);
		Map<LocalDate, Boolean> userSignInRecord = singInService.getUserSignInRecord(loginUser.getId(), year);
		return ResultUtils.success(userSignInRecord);
	}

	/**
	 * 获取用户签到记录
	 *
	 * @param year    年份（为空表示当前年份）
	 * @param request
	 * @return 签到记录映射
	 */
	@GetMapping("/get/sign_in_result")
	public BaseResponse<List<Integer>> getUserSignInRecordAsResult(Integer year, HttpServletRequest request) {
		// 必须要登录才能获取
		User loginUser = userService.getLoginUser(request);
		List<Integer> userSignInRecordToResult = singInService.getUserSignInRecordToResult(loginUser.getId(), year);
		return ResultUtils.success(userSignInRecordToResult);
	}


}
