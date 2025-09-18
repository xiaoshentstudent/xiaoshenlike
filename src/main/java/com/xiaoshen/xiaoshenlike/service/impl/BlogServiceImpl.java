package com.xiaoshen.xiaoshenlike.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoshen.xiaoshenlike.model.domain.Blog;
import com.xiaoshen.xiaoshenlike.service.BlogService;
import com.xiaoshen.xiaoshenlike.mapper.BlogMapper;
import org.springframework.stereotype.Service;

/**
* @author 小申同学
* @description 针对表【blog】的数据库操作Service实现
* @createDate 2025-09-18 17:59:14
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

}




