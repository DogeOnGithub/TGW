package cn.tgw.comment.controller;

import cn.tgw.comment.service.CommentService;
import cn.tgw.common.utils.TGWStaticString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 张华健
 * @Date: 2018/12/11 16:58
 * @Description:
 */
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "xiaojian/createComment",method = RequestMethod.POST)
    public Map<String,Object> createComment(int orderId, String commentDesc, Byte commentStars){
        Map<String,Object> result = new HashMap<>();
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,"评论错误，请重试！");
        return null;
    }

}
