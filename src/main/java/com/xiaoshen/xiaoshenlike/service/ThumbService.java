package com.xiaoshen.xiaoshenlike.service;

import com.xiaoshen.xiaoshenlike.model.domain.Thumb;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoshen.xiaoshenlike.model.dto.thumb.DoThumbRequest;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 小申同学
* @description 针对表【thumb】的数据库操作Service
* @createDate 2025-09-18 17:59:14
*/
public interface ThumbService extends IService<Thumb> {

	/**
	 * 点赞
	 * @param doThumbRequest
	 * @param request
	 * @return {@link Boolean }
	 */
	Boolean doThumb(DoThumbRequest doThumbRequest, HttpServletRequest request);


	/**
	 * 取消点赞
	 * @param doThumbRequest
	 * @param request
	 * @return {@link Boolean }
	 */
	Boolean undoThumb(DoThumbRequest doThumbRequest, HttpServletRequest request);


}
