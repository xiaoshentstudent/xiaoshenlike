package com.xiaoshen.xiaoshenlike.service;

import com.xiaoshen.xiaoshenlike.model.domain.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoshen.xiaoshenlike.model.vo.BlogVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author 小申同学
* @description 针对表【blog】的数据库操作Service
* @createDate 2025-09-18 17:59:14
*/
public interface BlogService extends IService<Blog> {


	/**
	 * 获取文章视图
	 *
	 * @param blogId id
	 * @param request http 请求
	 * @return 文章视图
	 */
	BlogVO getBlogVOById(long blogId, HttpServletRequest request);


	/**
	 * 获取文章视图列表
	 *
	 * @param blogList 文章id列表
	 * @param request http 请求
	 * @return 文章视图列表
	 */
	List<BlogVO> getBlogVOList(List<Blog> blogList, HttpServletRequest request);


}
