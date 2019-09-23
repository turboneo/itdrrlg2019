package com.itdr.controller.back;

import com.itdr.common.ServerResponse;
import com.itdr.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/manage/category/")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    //根据分类ID查询所有分类
    @RequestMapping("get_deep_category.do")
    private ServerResponse getDeepCategpry(Integer categoryId){
        ServerResponse sr= categoryService.getDeepCategpry(categoryId);
        return sr;
    }
}
