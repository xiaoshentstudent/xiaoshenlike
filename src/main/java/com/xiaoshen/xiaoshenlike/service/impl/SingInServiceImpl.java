package com.xiaoshen.xiaoshenlike.service.impl;

import com.xiaoshen.xiaoshenlike.constant.RedisConstant;
import com.xiaoshen.xiaoshenlike.service.SingInService;
import jakarta.annotation.Resource;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;

@Service
public class SingInServiceImpl implements SingInService {

	@Resource
	private RedissonClient redissonClient;

	@Override
	public boolean addUserSingIn(long userId) {
		// 获取当前日期
		LocalDate data = LocalDate.now();
		// 得到 redis key
		String key = RedisConstant.getUserSignInRedisKey(data.getYear(), userId);
		// 根据 key 查询 redis
		RBitSet bitSet = redissonClient.getBitSet(key);
		// 获取当前是年份中的第几天
		int day = data.getDayOfYear();
		// 判断当天是否签到
		if (!bitSet.get(day)) {
			// 没有签到则进行签到
			return bitSet.set(day);
		}
		return true;
	}





	@Override
	public Map<LocalDate, Boolean> getUserSignInRecord(long userId, Integer year) {

		if (year == null) {
			LocalDate now = LocalDate.now();
			year = now.getYear();
		}
		// 获取缓存 key
		String key = RedisConstant.getUserSignInRedisKey(year, userId);
		RBitSet bitSet = redissonClient.getBitSet(key);
		// 获取年份中有多少天
		int totalDays = Year.of(year).length();
		// 存储返回结果
		Map<LocalDate, Boolean> result = new LinkedHashMap<>();
		for (int dayOfYear = 1; dayOfYear <= totalDays; dayOfYear++) {
			// 获取当前日期
			LocalDate localDate = LocalDate.ofYearDay(year, dayOfYear);
			boolean b = bitSet.get(dayOfYear);
			if (b) {
				result.put(localDate,b);
			}
		}
		return result;
	}




	// 优化 1 判断是否签到，需要与 Redis 进行 365 次交互，我们在缓存一下 bitmap 的数据

	@Override
	public Map<LocalDate, Boolean> getUserSignInRecordByCache(long userId, Integer year) {

		if (year == null) {
			LocalDate now = LocalDate.now();
			year = now.getYear();
		}
		// 获取缓存 key
		String key = RedisConstant.getUserSignInRedisKey(year, userId);
		RBitSet signInBitSet = redissonClient.getBitSet(key);
		BitSet bitSet = signInBitSet.asBitSet();
		// 获取年份中有多少天
		int totalDays = Year.of(year).length();
		// 存储返回结果
		Map<LocalDate, Boolean> result = new LinkedHashMap<>();

		for (int dayOfYear = 1; dayOfYear <= totalDays; dayOfYear++) {
			// 获取当前日期
			LocalDate localDate = LocalDate.ofYearDay(year, dayOfYear);
			boolean b = bitSet.get(dayOfYear);
			if (b) {
				result.put(localDate,b);
			}
		}
		return result;
	}

	@Override
	public List<Integer> getUserSignInRecordToResult(long userId, Integer year) {
		if (year == null) {
			LocalDate now = LocalDate.now();
			year = now.getYear();
		}
		// 获取缓存 key
		String key = RedisConstant.getUserSignInRedisKey(year, userId);
		RBitSet signInBitSet = redissonClient.getBitSet(key);
		BitSet bitSet = signInBitSet.asBitSet();
		// 获取年份中有多少天
		int totalDays = Year.of(year).length();
		// 存储返回结果
		ArrayList<Integer> result = new ArrayList<>();

		for (int dayOfYear = 1; dayOfYear <= totalDays; dayOfYear++) {
			// 获取当前日期
			boolean b = bitSet.get(dayOfYear);
			if (b) {
				result.add(dayOfYear);
			}
		}
		return result;
	}
}
