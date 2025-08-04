package song.sj.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import song.sj.dto.LoginDto;
import song.sj.service.MemberLoginService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberLoginController {

    private final MemberLoginService memberLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        return new ResponseEntity<>(memberLoginService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("X-User-Id") Long memberId) {
        memberLoginService.logout(memberId);

        return new ResponseEntity<>("정상적으로 로그아웃 되셨습니다.", HttpStatus.OK);
    }
}
