package com.xiaoshen.xiaoshenlike.controller;

import com.xiaoshen.xiaoshenlike.common.BaseResponse;
import com.xiaoshen.xiaoshenlike.common.ResultUtils;
import com.xiaoshen.xiaoshenlike.model.domain.Blog;
import com.xiaoshen.xiaoshenlike.model.vo.BlogVO;
import com.xiaoshen.xiaoshenlike.service.BlogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("blog")
public class BlogController {  
    @Resource
    private BlogService blogService;
  
    @GetMapping("/get")
    public BaseResponse<BlogVO> get(long blogId, HttpServletRequest request) {
        BlogVO blogVO = blogService.getBlogVOById(blogId, request);
        return ResultUtils.success(blogVO);
    }

    @GetMapping("/list")
    public BaseResponse<List<BlogVO>> list(HttpServletRequest request) {
        List<Blog> blogList = blogService.list();
        List<BlogVO> blogVOList = blogService.getBlogVOList(blogList, request);
        return ResultUtils.success(blogVOList);
    }

}
