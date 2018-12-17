package cn.tgw.admin.controller;

import cn.tgw.admin.model.TgwFirstCategory;
import cn.tgw.admin.model.TgwSecondCategory;
import cn.tgw.admin.service.TgwFirstCategoryService;
import cn.tgw.admin.service.TgwSecondCategoryService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {

    @Autowired
    TgwFirstCategoryService tgwFirstCategoryService;

    @Autowired
    TgwSecondCategoryService tgwSecondCategoryService;

    @RequestMapping("/category/findFirstCategory")
    public JSONArray findallFirstCategory(){
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("id","");
        jsonObject.put("text","请选择：");
        jsonArray.add(jsonObject);
        List<TgwFirstCategory> tgwFirstCategories = tgwFirstCategoryService.allTgwFirstCategory();
        for (int i = 0; i < tgwFirstCategories.size(); i++) {
            JSONObject json=new JSONObject();
            TgwFirstCategory tgwFirstCategory = tgwFirstCategories.get(i);
            json.put("id",tgwFirstCategory.getId());
            json.put("text",tgwFirstCategory.getCategoryName());
            jsonArray.add(json);
        }
        return jsonArray;
    }

    @RequestMapping("/category/addFirstCategory")
    public Object addFirstCategory(String secName,Integer firstid){
        TgwSecondCategory tgwSecondCategory=new TgwSecondCategory();
        tgwSecondCategory.setFirstCategoryId(firstid);
        tgwSecondCategory.setCategoryName(secName);
        int add = tgwSecondCategoryService.add(tgwSecondCategory);
        Map<String,Object> map=new HashMap<>();
        if (add>0){
            map.put("status",true);
            return map;
        }
        map.put("status",false);
        return map;
    }

    /**
     * @Description:    根据一级商品商品id 返回二级商品数据
     * @Author:         梁智发
     * @CreateDate:     2018/12/14 0014 8:46
     * @UpdateUser:     梁智发
     * @UpdateDate:     2018/12/14 0014 8:46
     * @UpdateRemark:   修改内容
     * @Version:        1.0
     */
    @RequestMapping("/category/findtgwSecondCategoryById")
    public Object findtgwSecondCategory(Integer id){
        List<TgwSecondCategory> tgwSecondCategories = tgwSecondCategoryService.findtgwSecondCategory(id);
        return  tgwSecondCategories;
    }
}
