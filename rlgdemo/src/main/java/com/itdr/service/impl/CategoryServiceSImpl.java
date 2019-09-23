package com.itdr.service.impl;

import com.itdr.common.ServerResponse;
import com.itdr.mapper.CategoryMapper;
import com.itdr.pojo.Category;
import com.itdr.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceSImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    //根据分类ID查询所有分类
    @Override
    public ServerResponse getDeepCategpry(Integer categoryId) {
        if (categoryId==null||categoryId<0){
            return ServerResponse.defeatedRS("非法的参数!");
        }
        List<Integer> li=new ArrayList<>();
        li.add(categoryId);
        getAll(categoryId,li);
        ServerResponse sr = ServerResponse.successRS(li);
        return sr;
    }

    private void getAll(Integer pid, List<Integer> list) {

        List<Category>li=categoryMapper.selectByParentId(pid);

        if (li!=null&&li.size()!=0){
            for (Category categorys : li) {
                list.add(categorys.getId());
                getAll(categorys.getId(),list);
            }
        }
    }
}
