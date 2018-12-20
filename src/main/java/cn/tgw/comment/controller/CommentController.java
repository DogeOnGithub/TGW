package cn.tgw.comment.controller;

import cn.tgw.comment.model.Comment;
import cn.tgw.comment.service.CommentService;
import cn.tgw.common.utils.TGWStaticString;
import cn.tgw.user.model.User;
import cn.tgw.user.model.UserDetail;
import cn.tgw.user.service.UserService;
import com.alibaba.druid.sql.visitor.functions.If;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private UserService userService;

    /**
     *
     * 功能描述: 创建评论
     *
     * @param: int orderId, String commentDesc, Byte commentStars
     * @return: Map<String,Object>
     * @auther: 张华健
     * @date:  2018/12/14
     */
    @RequestMapping(value = "xiaojian/createComment",method = RequestMethod.POST)
    public Map<String,Object> createComment(int orderId, String commentDesc, Byte commentStars){
        Map<String,Object> result = new HashMap<>();
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,"参数错误，请重试！");
        String s = commentService.createComment(orderId, commentDesc, commentStars);
        if(!s.equals(TGWStaticString.TGW_RESULT_STATUS_SUCCESS)){
            result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
            result.put(TGWStaticString.TGW_RESULT_MESSAGE,"评论错误，请重试！");
            return result;
        }
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        return result;
    }

    /**
     *
     * 功能描述: 根据评论id查看评论
     *
     * @param: Integer id
     * @return:  Map<String,Object>
     * @auther: 张华健
     * @date:  2018/12/14
     */
    @RequestMapping(value = "xiaojian/getCommentById",method = RequestMethod.GET)
    public Map<String,Object> getCommentById(Integer id){
        Map<String,Object> result = new HashMap<>();
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        Comment commentById = commentService.getCommentById(id);
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        result.put("comment",commentById);
        return result;
    }

    /**
     *
     * 功能描述: 根据订单id查看对此订单的评论
     *
     * @param: Integer orderId
     * @return: Map<String,Object>
     * @auther: 张华健
     * @date:  2018/12/14
     */
    @RequestMapping(value = "xiaojian/getCommentByOrderId",method = RequestMethod.GET)
    public Map<String,Object> getCommentByOrderId(Integer orderId){
        Map<String,Object> result = new HashMap<>();
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        Comment commentByOrderId = commentService.getCommentByOrderId(orderId);
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        result.put("comment",commentByOrderId);
        return result;
    }

    /**
     *
     * 功能描述: 根据团购查找该商品的全部评价
     *
     * @param: Integer goodsId
     * @return: Map<String,Object>
     * @auther: 张华健
     * @date:  2018/12/14
     */
    @RequestMapping(value = "xiaojian/getCommentByGoodsId",method = RequestMethod.GET)
    public Map<String,Object> getCommentByGoodsId(Integer goodsId){
        Map<String,Object> result = new HashMap<>();
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        List<Object> resultList = new ArrayList<>();
        List<Comment> commentByGoodsId = commentService.getCommentByGoodsId(goodsId);
        for(int i = 0;i<commentByGoodsId.size();i++){
            Comment resultComment = commentByGoodsId.get(i);
            Integer tgwUserId = resultComment.getTgwUserId();
            User userById = userService.getUserById(tgwUserId);
            UserDetail userDetailByUserId = userService.getUserDetailByUserId(userById);
            Map<String,Object> Comment = new HashMap<>();
            Comment.put("resultComment",resultComment);
            Comment.put("userDetail",userDetailByUserId);
            resultList.add(Comment);
        }
        result.put("comment",resultList);
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);

        return result;
    }

    /**
     *
     * 功能描述: 根据id删除评论
     *
     * @param: Integer id
     * @return: Map<String,Object>
     * @auther: 张华健
     * @date:  2018/12/14
     */
    @RequestMapping(value = "xiaojian/deleteCommentById",method = RequestMethod.GET)
    public Map<String,Object> deleteCommentById(Integer id){
        Map<String,Object> result = new HashMap<>();
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_FAIL);
        String s = commentService.deleteComment(id);
        if(!s.equals(TGWStaticString.TGW_RESULT_STATUS_SUCCESS)){
            result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_FAIL);
            result.put(TGWStaticString.TGW_RESULT_MESSAGE,"删除失败，请重试");
            return result;
        }
        result.put(TGWStaticString.TGW_RESULT_STATUS,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        result.put(TGWStaticString.TGW_RESULT_MESSAGE,TGWStaticString.TGW_RESULT_STATUS_SUCCESS);
        return result;
    }

}
