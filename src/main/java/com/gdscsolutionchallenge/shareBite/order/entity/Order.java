package com.gdscsolutionchallenge.shareBite.order.entity;

import com.gdscsolutionchallenge.shareBite.audit.Auditable;
import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import com.gdscsolutionchallenge.shareBite.order.state.OrderStatus;
import com.gdscsolutionchallenge.shareBite.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ORDERS")
public class Order extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="POST_ID")
    private Post post;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.REQUESTED;

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void setPost(Post post) {
        this.post = post;
        post.getOrders().add(this);
    }
}
