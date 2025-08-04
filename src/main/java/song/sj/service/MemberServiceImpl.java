package song.sj.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.sj.dto.member.*;
import song.sj.dto.UpdateMemberDto;
import song.sj.dto.UpdateShopMemberDto;
import song.sj.entity.Member;
import song.sj.enums.Role;
import song.sj.repository.MemberRepository;
import song.sj.service.toEntity.ToMember;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    // requestHeader 로 코드 교체 해야함
    private Member getFindMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
    }

    @Transactional
    public void memberSave(MemberJoinDto dto) {

        if (checkDuplicateEmail(dto.getEmail())) {
            throw new IllegalArgumentException("중복된 email 입니다.");
        }

        Member member = ToMember.toMemberEntity(dto);
        member.changeRole(Role.ROLE_MEMBER);
        member.transPassword(passwordEncoder.encode(dto.getPassword()));

        memberRepository.save(member);
    }

    @Transactional
    public void shopMemberSave(ShopMemberJoinDto dto) {

        if (checkDuplicateEmail(dto.getEmail())) {
            throw new IllegalArgumentException("중복된 email 입니다.");
        }

        Member shopMember = ToMember.toShopMemberEntity(dto);
        shopMember.changeRole(Role.ROLE_SHOP);
        shopMember.transPassword(passwordEncoder.encode(dto.getPassword()));

        memberRepository.save(shopMember);
    }

    private boolean checkDuplicateEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public Member findMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("없는 email 입니다."));
    }

    @Transactional
    public void updateMember(Long memberId, UpdateMemberDto dto) {

        Member findMember = getFindMember(memberId);

        findMember.changeUsername(dto.getUsername());
        findMember.changePassword(dto.getNewPassword());
        findMember.changeAddress(dto.getAddress());
    }

    @Transactional
    public void updateShopMember(Long memberId, UpdateShopMemberDto dto) {

        Member findMember = getFindMember(memberId);

        findMember.changeUsername(dto.getUsername());
        findMember.changePassword(dto.getNewPassword());
        findMember.changeBusinessRegistrationNumber(dto.getBusinessRegistrationNumber());
        findMember.changeAddress(dto.getAddress());
    }

    @Transactional
    public void deleteMember(Long memberId, String password) {

        Member findMember = getFindMember(memberId);

        if (findMember.getPassword().equals(password)) {
            memberRepository.delete(findMember);
        } else {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    public MemberInfo findMember(Long memberId) {
        Member member = getFindMember(memberId);

        if (member.getRole().equals(Role.ROLE_MEMBER))
            return new MemberSearchDto(member.getId(), member.getUsername(), member.getEmail(), member.getAddress());


        if (member.getRole().equals(Role.ROLE_SHOP))
            return new ShopMemberSearchDto(member.getId(), member.getUsername(), member.getEmail(), member.getBusinessRegistrationNumber(), member.getAddress());

        return null;
    }
}
