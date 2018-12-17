package cn.tgw.admin.service;

import cn.tgw.admin.mapper.TgwSecondCategoryMapper;
import cn.tgw.admin.model.TgwSecondCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TgwSecondCategoryService {
    @Autowired
    TgwSecondCategoryMapper tgwSecondCategoryMapper;

    public int add(TgwSecondCategory tgwSecondCategory){

       return tgwSecondCategoryMapper.insert(tgwSecondCategory);
    }

    public List<TgwSecondCategory> findtgwSecondCategory(Integer first_category_id){
        List<TgwSecondCategory> tgwSecondCategories = tgwSecondCategoryMapper.findtgwSecondCategory(first_category_id);
        return  tgwSecondCategories;

    }
}
