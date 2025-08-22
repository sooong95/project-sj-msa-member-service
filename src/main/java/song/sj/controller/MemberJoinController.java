package song.sj.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import song.sj.dto.member.MemberJoinDto;
import song.sj.dto.member.ShopMemberJoinDto;
import song.sj.service.MemberServiceImpl;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/join")
public class MemberJoinController {

    private final MemberServiceImpl memberService;

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody MemberJoinDto memberJoinDto) {

        memberService.memberSave(memberJoinDto);

        return new ResponseEntity<>("MEMBER 가입 성공!", CREATED);
    }

    @PostMapping("/shop-member/join")
    public ResponseEntity<String> saveShop(@Valid @RequestBody ShopMemberJoinDto shopJoinDto) {

        memberService.shopMemberSave(shopJoinDto);

        return new ResponseEntity<>("SHOP MEMBER 가입 성공", CREATED);
    }
}
