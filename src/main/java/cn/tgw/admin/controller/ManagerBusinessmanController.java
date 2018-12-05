package cn.tgw.admin.controller;

import cn.tgw.admin.model.EasyUIDataGridResult;
import cn.tgw.businessman.model.Businessman;
import cn.tgw.businessman.model.BusinessmanDetail;
import cn.tgw.businessman.service.BusinessmanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManagerBusinessmanController {

    @Autowired
    BusinessmanService businessmanService;


    @RequestMapping("/usermanager/findBusinessmansByLikeMobile")
    public EasyUIDataGridResult findBusinessmansByLikeMobile(Integer page,Integer rows,@RequestParam(value = "mobile",required = false,defaultValue = "") String mobile){
        EasyUIDataGridResult easyUIDataGridResult=new EasyUIDataGridResult();
        if (!StringUtils.isEmpty(mobile)){
            mobile="%"+mobile+"%";
        }
        PageInfo<Businessman> pageInfo = businessmanService.findBusinessmansByLikeMobile(page, rows, mobile);

        easyUIDataGridResult.setTotal((int) pageInfo.getTotal());
        easyUIDataGridResult.setRows(pageInfo.getList());

        return easyUIDataGridResult;
    }
    @RequestMapping("/usermanager/findAllDetailsByBusinessmanId")
    public EasyUIDataGridResult findAllDetailsByBusinessmanId(Integer page,Integer rows,@RequestParam(value = "id",required = false) Integer id){
        EasyUIDataGridResult easyUIDataGridResult=new EasyUIDataGridResult();
        PageInfo<BusinessmanDetail> details = businessmanService.findAllDetailsByBusinessmanId(page, rows, id);
        easyUIDataGridResult.setTotal((int) details.getTotal());
        easyUIDataGridResult.setRows(details.getList());
        return easyUIDataGridResult;
    }


}
