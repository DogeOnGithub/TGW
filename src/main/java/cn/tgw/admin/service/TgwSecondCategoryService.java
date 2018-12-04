package cn.tgw.admin.service;

import cn.tgw.admin.mapper.TgwSecondCategoryMapper;
import cn.tgw.admin.model.TgwSecondCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TgwSecondCategoryService {
    @Autowired
    TgwSecondCategoryMapper tgwSecondCategoryMapper;

    public int add(TgwSecondCategory tgwSecondCategory){
       return tgwSecondCategoryMapper.insert(tgwSecondCategory);
    }
}
