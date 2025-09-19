package com.xiaoshen.xiaoshenlike.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoshen.xiaoshenlike.constant.ThumbConstant;
import com.xiaoshen.xiaoshenlike.exception.BusinessException;
import com.xiaoshen.xiaoshenlike.exception.ErrorCode;
import com.xiaoshen.xiaoshenlike.model.domain.Thumb;
import com.xiaoshen.xiaoshenlike.model.dto.thumb.DoThumbRequest;
import com.xiaoshen.xiaoshenlike.service.BlogService;
import com.xiaoshen.xiaoshenlike.service.ThumbService;
import com.xiaoshen.xiaoshenlike.mapper.ThumbMapper;
import com.xiaoshen.xiaoshenlike.service.UserService;
import com.xiaoshen.xiaoshenlike.model.domain.User;
import com.xiaoshen.xiaoshenlike.model.domain.Blog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
* @author 小申同学
* @description 针对表【thumb】的数据库操作Service实现
* @createDate 2025-09-18 17:59:14
*/
@Service("thumbServiceDB")
@Slf4j
@RequiredArgsConstructor
public class ThumbServiceImpl extends ServiceImpl<ThumbMapper, Thumb> implements ThumbService {

	private final UserService userService;

	private final BlogService blogService;

	private final TransactionTemplate transactionTemplate;

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public Boolean doThumb(DoThumbRequest doThumbRequest, HttpServletRequest request) {
		if (doThumbRequest == null || doThumbRequest.getBlogId() == null) {
			throw new RuntimeException("参数错误");
		}
		User loginUser = userService.getLoginUser(request);
		// 加锁
		synchronized (loginUser.getId().toString().intern()) {

			// 编程式事务
			return transactionTemplate.execute(status -> {
				Long blogId = doThumbRequest.getBlogId();
//				boolean exists = this.lambdaQuery()
//						.eq(Thumb::getUserId, loginUser.getId())
//						.eq(Thumb::getBlogId, blogId)
//						.exists();
				Boolean exists = this.hasThumb(blogId, loginUser.getId());
				if (exists) {
					throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户已点赞");
				}

				boolean update = blogService.lambdaUpdate()
						.eq(Blog::getId, blogId)
						.setSql("thumbCount = thumbCount + 1")
						.update();

				Thumb thumb = new Thumb();
				thumb.setUserId(loginUser.getId());
				thumb.setBlogId(blogId);
				// 更新成功才执行
				boolean success = this.save(thumb);
				if (success) {
					redisTemplate.opsForHash().put(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId().toString(), blogId.toString(), thumb.getId());
				}
				// 更新成功才执行返回
				return success;
			});
		}
	}

	@Override
	public Boolean undoThumb(DoThumbRequest doThumbRequest, HttpServletRequest request) {
		log.info("ThumbServiceImpl.undoThumb");
		if (doThumbRequest == null || doThumbRequest.getBlogId() == null) {
			throw new BusinessException(ErrorCode.SYSTEM_ERROR,"请求参数错误");
		}
		User loginUser = userService.getLoginUser(request);
		// 加锁
		synchronized (loginUser.getId().toString().intern()) {

			// 编程式事务
			return transactionTemplate.execute(status -> {
				Long blogId = doThumbRequest.getBlogId();
//				Thumb thumb = this.lambdaQuery()
//						.eq(Thumb::getUserId, loginUser.getId())
//						.eq(Thumb::getBlogId, blogId)
//						.one();
				Object thumbObj = redisTemplate.opsForHash().get(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId().toString(), blogId.toString());
				if (thumbObj == null) {
					throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户未点赞");
				}
				Long thumbId = Long.valueOf(thumbObj.toString());
				boolean update = blogService.lambdaUpdate()
						.eq(Blog::getId, blogId)
						.setSql("thumbCount = thumbCount - 1")
						.update();
	            // 更新成功才执行
				boolean success =  update && this.removeById(thumbId);
				if (success) {
					redisTemplate.opsForHash().delete(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId(), blogId.toString());
				}
				return success;
			});
		}
	}


	@Override
	public Boolean hasThumb(Long blogId, Long userId) {
		return redisTemplate.opsForHash().hasKey(ThumbConstant.USER_THUMB_KEY_PREFIX + userId , blogId.toString());
	}

}


