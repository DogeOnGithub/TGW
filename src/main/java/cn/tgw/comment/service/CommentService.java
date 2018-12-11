package cn.tgw.comment.service;

import cn.tgw.comment.mapper.CommentMapper;
import cn.tgw.comment.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: 张华健
 * @Date: 2018/12/11 10:44
 * @Description:
 */
public interface CommentService {

    /**
     *
     * 功能描述: 返回所有评论
     *
     * @param: NULL
     * @return: List<Comment>
     * @auther: 张华健
     * @date:  2018/12/11
     */
    public List<Comment> getAllComments();

    /**
     *
     * 功能描述: 根据id查询评论
     *
     * @param: int id
     * @return: Comment
     * @auther: 张华健
     * @date:  2018/12/11
     */
    public Comment getCommentById(int id);

    /**
     *
     * 功能描述: 根据用户id查询所有该用户的评论
     *
     * @param: int userId
     * @return: List<Comment>
     * @auther: 张华健
     * @date:  2018/12/11
     */
    public List<Comment> getCommentByUserId(int userId);

    /**
     *
     * 功能描述: 根据团购id查询所有用户对该团购的评论
     *
     * @param: int goodsId
     * @return: List<Comment>
     * @auther: 张华健
     * @date:  2018/12/11
     */
    public List<Comment> getCommentByGoodsId(int goodsId);

    /**
     *
     * 功能描述: 根据订单查询用户在该订单下的评论
     *
     * @param: int orderId
     * @return: Comment
     * @auther: 张华健
     * @date:  2018/12/11
     */
    public Comment getCommentByOrderId(int orderId);

    /**
     *
     * 功能描述: 用户根据订单id对订单进行评价
     *
     * @param: int orderId
     * @return:
     * @auther: 张华健
     * @date:  2018/12/11
     */
    public String createComment(int orderId,String commentDesc,Byte commentStars);

    /**
     *
     * 功能描述: 用户根据评论id对评论进行删除（建表没设字段判断状态，直接删除，不做逻辑删除）
     *
     * @param: int orderId
     * @return:
     * @auther: 张华健
     * @date:  2018/12/11
     */
    public String deleteComment(int id);

}
