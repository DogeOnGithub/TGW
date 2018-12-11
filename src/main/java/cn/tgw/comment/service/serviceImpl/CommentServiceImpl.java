package cn.tgw.comment.service.serviceImpl;

import cn.tgw.comment.mapper.CommentMapper;
import cn.tgw.comment.model.Comment;
import cn.tgw.comment.service.CommentService;
import cn.tgw.order.model.Order;
import cn.tgw.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: 张华健
 * @Date: 2018/12/11 11:12
 * @Description:
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private OrderService orderService;

    @Override
    public List<Comment> getAllComments() {
        return commentMapper.selectAllComment();
    }

    @Override
    public Comment getCommentById(int id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Comment> getCommentByUserId(int userId) {
        return commentMapper.selectByUserId(userId);
    }

    @Override
    public List<Comment> getCommentByGoodsId(int goodsId) {
        return commentMapper.selectByGoodsId(goodsId);
    }

    @Override
    public Comment getCommentByOrderId(int orderId) {
        return commentMapper.selectByOrderId(orderId);
    }

    @Override
    public String createComment(int orderId,String commentDesc,Byte commentStars) {
        Order order = orderService.getOrderById(orderId);
        if(order.getSellStatus()!=3){
            return "error" ;
        }
        Comment comment = new Comment();
        comment.setTgwGoodsId(order.getTgwGoodsId());
        comment.setTgwUserId(order.getTgwUserId());
        comment.setTgwOrderId(orderId);
        comment.setCommentDesc(commentDesc);
        comment.setCommentStars(commentStars);
        comment.setCommentTime(new Date());
        int i = commentMapper.insertSelective(comment);
        if(i!=1){
            return "error";
        }
        return "success";
    }

    @Override
    public String deleteComment(int id) {
        int i = commentMapper.deleteByPrimaryKey(id);
        if(i!=1){
            return "error";
        }
        return "success";
    }


}
