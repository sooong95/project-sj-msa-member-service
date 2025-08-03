package song.sj.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import song.sj.dto.LoginDto;
import song.sj.service.MemberLoginService;

@RestController
@RequiredArgsConstructor
public class MemberLoginController {

    private final MemberLoginService memberLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        return new ResponseEntity<>(memberLoginService.login(loginDto), HttpStatus.OK);
    }
}
