package cn.tgw.comment.model;

import java.util.Date;

public class Comment {
    private Integer id;

    private Integer tgwGoodsId;

    private Integer tgwUserId;

    private String commentDesc;

    private Date commentTime;

    private Byte commentStars;

    private Integer tgwOrderId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTgwGoodsId() {
        return tgwGoodsId;
    }

    public void setTgwGoodsId(Integer tgwGoodsId) {
        this.tgwGoodsId = tgwGoodsId;
    }

    public Integer getTgwUserId() {
        return tgwUserId;
    }

    public void setTgwUserId(Integer tgwUserId) {
        this.tgwUserId = tgwUserId;
    }

    public String getCommentDesc() {
        return commentDesc;
    }

    public void setCommentDesc(String commentDesc) {
        this.commentDesc = commentDesc == null ? null : commentDesc.trim();
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Byte getCommentStars() {
        return commentStars;
    }

    public void setCommentStars(Byte commentStars) {
        this.commentStars = commentStars;
    }

    public Integer getTgwOrderId() {
        return tgwOrderId;
    }

    public void setTgwOrderId(Integer tgwOrderId) {
        this.tgwOrderId = tgwOrderId;
    }
}