package song.sj.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import song.sj.dto.Result;
import song.sj.dto.UpdateShopMemberDto;
import song.sj.dto.member.ShopMemberSearchDto;
import song.sj.service.MemberQueryService;
import song.sj.service.MemberServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/userInfo")
public class ShopMemberRUDController {

    private final MemberServiceImpl memberService;
    private final MemberQueryService memberQueryService;

    @PatchMapping
    public ResponseEntity<String> updateShopMember(@RequestHeader("X-User-Id") String email,
                                                   @RequestBody @Valid UpdateShopMemberDto dto) {

        memberService.updateShopMember(email, dto);
        return new ResponseEntity<>("회원 정보가 수정 되었습니다.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ShopMemberSearchDto> findShopMember(@RequestHeader("X-User-Id") String email) {
        return new ResponseEntity<>((ShopMemberSearchDto) memberService.findMember(email), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Result<List<ShopMemberSearchDto>>> findShopMembers() {

        return new ResponseEntity<>(memberQueryService.findShopMembers(), HttpStatus.OK);
    }
}
