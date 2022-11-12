package me.elyor.memberservice.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    @Select("SELECT EXISTS(SELECT 1 FROM member WHERE username = #{username})")
    boolean existsByUsername(String username);

    int save(Member member);

    @Select("SELECT * FROM member WHERE id = #{id}")
    Optional<Member> findById(int id);

}
