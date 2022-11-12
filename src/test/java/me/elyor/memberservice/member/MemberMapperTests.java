package me.elyor.memberservice.member;

import me.elyor.memberservice.global.config.DataSourceConfig;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.BDDAssertions.then;

@MybatisTest
@Import(DataSourceConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberMapperTests {

    @Autowired
    MemberMapper memberMapper;

    @Test
    void whenSaveThenNoError() {
        Member member = buildTestMember("awesoro");
        int affected = memberMapper.save(member);
        assertEquals(1, affected);

        int generatedId = member.getId();
        then( generatedId).isGreaterThanOrEqualTo(1);
    }

    @Test
    void whenExistsByUsernameWithCorrectUsernameThenReturnTrue() {
        String username = "pro1user";
        Member member = buildTestMember(username);
        memberMapper.save(member);
        boolean exists = memberMapper.existsByUsername(username);
        assertTrue(exists);
    }

    @Test
    void whenExistsByUsernameWithNonExistingUsernameThenReturnFalse() {
        boolean exists = memberMapper.existsByUsername("notexisting");
        assertFalse(exists);
    }

    @Test
    void whenFindByIdThenReturnMember() {
        String username = "userundertest";
        Member member = buildTestMember(username);
        memberMapper.save(member);
        int generatedId = member.getId();
        Optional<Member> optionalMember = memberMapper.findById(generatedId);
        assertTrue(optionalMember.isPresent());
        assertEquals(optionalMember.get().getUsername(), username);

        optionalMember = memberMapper.findById(3232); //doesn't exists
        assertFalse(optionalMember.isPresent());
    }

    private Member buildTestMember(String username) {
        return Member.builder()
                .username(username).password("default")
                .email("default@example.com").phoneNumber("010-3121-2131")
                .build();
    }

}
