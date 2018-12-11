package cn.tgw.comment.mapper;

import cn.tgw.comment.model.Comment;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectAllComment();

    List<Comment> selectByUserId(Integer userId);

    List<Comment> selectByGoodsId(Integer goodsId);

    Comment selectByOrderId(Integer orderId);


}