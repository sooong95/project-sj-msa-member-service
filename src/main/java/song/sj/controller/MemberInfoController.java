package song.sj.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import song.sj.dto.Result;
import song.sj.dto.UpdateMemberDto;
import song.sj.dto.member.MemberSearchDto;
import song.sj.service.MemberQueryService;
import song.sj.service.MemberServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memberInfo")
public class MemberInfoController {

    private final MemberServiceImpl memberService;
    private final MemberQueryService memberQueryService;

    @PatchMapping
    public ResponseEntity<String> updateMember(@RequestHeader("X-User-Id") String email, @RequestBody @Valid UpdateMemberDto updateMemberDto) {

        memberService.updateMember(email, updateMemberDto);
        return new ResponseEntity<>("회원 정보가 수정 되었습니다.", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMember(@RequestHeader("X-User-Id") String email, String password) {

        memberService.deleteMember(email, password);

        return new ResponseEntity<>("회원 탈퇴 완료", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<MemberSearchDto> findMember(@RequestHeader("X-User-Id") String email) {
        return new ResponseEntity<>((MemberSearchDto) memberService.findMember(email), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Result<List<MemberSearchDto>>> findMembers() {

        return new ResponseEntity<>(memberQueryService.findMembers(), HttpStatus.OK);
    }
}
