package com.gdscsolutionchallenge.shareBite.member.repository;

import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(long userId);
    Optional<Member> findByEmail(String email);

}