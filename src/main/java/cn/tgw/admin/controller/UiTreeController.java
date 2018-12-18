package cn.tgw.admin.controller;

import cn.tgw.admin.model.TgwManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UiTreeController {

    @RequestMapping("/main/createtree")
    public String createMenu(HttpSession httpSession){
        TgwManager manager = (TgwManager) httpSession.getAttribute("manager");
        String menuJsonArray = "";
        if (manager != null) {
            menuJsonArray="[\n" +
                    "    {\n" +
                    "        \"id\": 1,\n" +
                    "        \"text\": \"<font size='3'>商家信息\",\n" +
                    "        \"state\": \"open\",\n" +
                    "        \"iconCls\": \"icon-userManage\",\n" +
                    "        \"attributes\": {\n" +
                    "            \"authPath\": \"shangjiaxinxi\"\n" +
                    "        }\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"id\": 2,\n" +
                    "        \"text\": \"<font size='3'>商家入驻管理\",\n" +
                    "        \"state\": \"open\",\n" +
                    "        \"iconCls\": \"icon-userManage\",\n" +
                    "        \"attributes\": {\n" +
                    "            \"authPath\": \"shangjiaruzhuguanli\"\n" +
                    "        }\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"id\": 3,\n" +
                    "        \"text\": \"<font size='3'>商品分类管理\",\n" +
                    "        \"state\": \"open\",\n" +
                    "        \"iconCls\": \"icon-userManage\",\n" +
                    "        \"attributes\": {\n" +
                    "            \"authPath\": \"shangpinfenleiguanli\"\n" +
                    "        }\n" +
                    "    }\n" +
                    "]";
        }
        return menuJsonArray;
    }

    @RequestMapping("/usertree/createtree")
    public String usertree(HttpSession httpSession){
        TgwManager manager = (TgwManager) httpSession.getAttribute("manager");
        String menuJsonArray = "";
        if (manager != null) {
            menuJsonArray="[\n" +
                    "    {\n" +
                    "        \"id\": 1,\n" +
                    "        \"text\": \"<font size='3'>用户信息管理\",\n" +
                    "        \"state\": \"open\",\n" +
                    "        \"iconCls\": \"icon-userManage\",\n" +
                    "        \"attributes\": {\n" +
                    "            \"authPath\": \"yonghuxinxiguanli\"\n" +
                    "        }\n" +
                    "    }\n" +
                    "]";
        }
        return menuJsonArray;
    }
}
