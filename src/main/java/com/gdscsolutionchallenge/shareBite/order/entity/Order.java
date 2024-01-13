package com.gdscsolutionchallenge.shareBite.order.entity;

import com.gdscsolutionchallenge.shareBite.audit.Auditable;
import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import com.gdscsolutionchallenge.shareBite.order.state.OrderStatus;
import com.gdscsolutionchallenge.shareBite.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    @JoinColumn(name="member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post postId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.REQUESTED;

}
