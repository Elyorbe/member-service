package me.elyor.memberservice.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(MemberController.PATH)
public class MemberController {

    protected static final String PATH = "/api/v1/members";
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    ResponseEntity<MemberDto.SignupResponse> signup(
            @Valid @RequestBody MemberDto.SignupRequest dto) {
        int id = memberService.signup(dto);
        return ResponseEntity.ok(new MemberDto.SignupResponse(id));
    }

    @GetMapping("/{id}")
    ResponseEntity<MemberDto.DetailedResponse> listOne(@PathVariable Integer id) {
        return ResponseEntity.ok(memberService.findById(id));
    }

}
