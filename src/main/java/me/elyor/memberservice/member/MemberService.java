package me.elyor.memberservice.member;

import me.elyor.memberservice.global.error.exception.ErrorCode;
import me.elyor.memberservice.global.error.exception.GlobalException;
import me.elyor.memberservice.other.EncryptionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private MemberMapper memberMapper;
    private EncryptionService encryptionService;
    private PasswordEncoder passwordEncoder;

    public MemberService(MemberMapper memberMapper, EncryptionService encryptionService,
                         PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.encryptionService = encryptionService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public int signup(MemberDto.SignupRequest dto) {
        String encryptedUsername = encryptionService.encrypt(dto.username);
        checkForUsernameDuplication(encryptedUsername);
        String encryptedPassword = passwordEncoder.encode(dto.password);
        String encryptedEmail = encryptionService.encrypt(dto.email);
        String encryptedPhone = encryptionService.encrypt(dto.phoneNumber);

        Member member = Member.builder()
                .username(encryptedUsername).password(encryptedPassword)
                .email(encryptedEmail).phoneNumber(encryptedPhone).build();

        int affected = memberMapper.save(member);
        if(affected != 1)
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);

        return member.getId();
    }


    private void checkForUsernameDuplication(String encryptedUsername) {
        if(memberMapper.existsByUsername(encryptedUsername))
            throw new GlobalException(ErrorCode.DUPLICATE_USERNAME);
    }

    public MemberDto.DetailedResponse findById(Integer id) {
        Member member = memberMapper.findById(id).orElseThrow(
                () -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND));
        String username = encryptionService.decrypt(member.getUsername());
        String email = encryptionService.decrypt(member.getEmail());
        String phoneNumber = encryptionService.decrypt(member.getPhoneNumber());
        return new MemberDto.DetailedResponse(username, email, phoneNumber);
    }

}
