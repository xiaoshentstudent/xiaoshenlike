package com.xiaoshen.xiaoshenlike.service;

import io.swagger.v3.oas.models.security.SecurityScheme;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface SingInService {

	/**
	 * 签到
	 * @param userId 用户id
	 * @return true:签到成功 false:签到失败
	 */
	boolean addUserSingIn(long userId);


	/**
	 * 获取用户某个年份的签到记录
	 *
	 * @param userId 用户 id
	 * @param year   年份（为空表示当前年份）
	 * @return 签到记录映射
	 */
	Map<LocalDate, Boolean> getUserSignInRecord(long userId, Integer year);

	// 优化 1 判断是否签到，需要与 Redis 进行 365 次交互，我们在缓存一下 bitmap 的数据
	Map<LocalDate, Boolean> getUserSignInRecordByCache(long userId, Integer year);

	List<Integer> getUserSignInRecordToResult(long userId, Integer year);

}
