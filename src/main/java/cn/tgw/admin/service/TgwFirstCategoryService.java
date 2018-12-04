package cn.tgw.admin.service;

import cn.tgw.admin.mapper.TgwFirstCategoryMapper;
import cn.tgw.admin.model.TgwFirstCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TgwFirstCategoryService {

    @Autowired
    TgwFirstCategoryMapper tgwFirstCategoryMapper;


    /**
     * 查询全部一级分类
     * @return
     */
    public List<TgwFirstCategory> allTgwFirstCategory(){

        return tgwFirstCategoryMapper.allTgwFirstCategory();
    }

}
