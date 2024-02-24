package com.gdscsolutionchallenge.shareBite.order.service;

import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;
import com.gdscsolutionchallenge.shareBite.exception.exceptions.ResourceNotFoundException;
import com.gdscsolutionchallenge.shareBite.gcs.service.GcsService;
import com.gdscsolutionchallenge.shareBite.gcs.state.ImageType;
import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import com.gdscsolutionchallenge.shareBite.member.service.MemberService;
import com.gdscsolutionchallenge.shareBite.order.dto.CreateOrderResponseDto;
import com.gdscsolutionchallenge.shareBite.order.dto.FindOrderDto;
import com.gdscsolutionchallenge.shareBite.order.entity.Order;
import com.gdscsolutionchallenge.shareBite.order.repository.OrderRepository;
import com.gdscsolutionchallenge.shareBite.order.state.OrderStatus;
import com.gdscsolutionchallenge.shareBite.post.dto.FindAllPostResponseDto;
import com.gdscsolutionchallenge.shareBite.post.entity.Post;
import com.gdscsolutionchallenge.shareBite.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private MemberService memberService;
    private PostService postService;
    private GcsService gcsService;

    @Transactional
    public CreateOrderResponseDto createOrder(Long memberId, Long postId) {
        Member member = memberService.verifyMember(memberId);
        Post post = postService.verifyPost(postId);

        Order order = new Order();
        order.setMember(member);
        order.setPost(post);

        Long orderId = orderRepository.save(order).getOrderId();

        return new CreateOrderResponseDto(orderId);
    }

    @Transactional
    public void setOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = verifyOrder(orderId);
        order.update(orderStatus);
    }

    public FindOrderDto findOrder(Long orderId) {
        Order order = verifyOrder(orderId);
        Long postId = order.getPost().getPostId();
        Post post = postService.verifyPost(postId);

        List<String> imageUrls = gcsService.findImages(ImageType.POST_IMAGE, String.valueOf(postId));
        String imageUrl = imageUrls.isEmpty() ? "" : imageUrls.get(0);

        return new FindOrderDto(orderId, imageUrl, post.getTitle(), order.getOrderStatus());
    }

    public FindAllPostResponseDto findAllOrder(Long memberId, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAllByMember(memberId, pageable);
        List<FindOrderDto> findOrderDtoList = orderPage.getContent().stream()
                .map(order -> {
                    Long orderId = order.getOrderId();
                    Post post = order.getPost();
                    Long postId = post.getPostId();
                    String postTitle = post.getTitle();

                    List<String> imageUrls = gcsService.findImages(ImageType.POST_IMAGE, String.valueOf(postId));
                    String imageUrl = imageUrls.isEmpty() ? "" : imageUrls.get(0);

                    return new FindOrderDto(orderId, postTitle, imageUrl, order.getOrderStatus());
                }).collect(Collectors.toList());

        return new FindAllPostResponseDto();
    }

    private Order verifyOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NOT_FOUND_ORDER));
    }


}
