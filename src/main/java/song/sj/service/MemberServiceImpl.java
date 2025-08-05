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

    public Member getFindMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("없는 email 입니다."));
    }

    @Transactional
    public void updateMember(String email, UpdateMemberDto dto) {

        Member findMember = getFindMember(email);

        findMember.changeUsername(dto.getUsername());
        findMember.changePassword(dto.getNewPassword());
        findMember.changeAddress(dto.getAddress());
    }

    @Transactional
    public void updateShopMember(String email, UpdateShopMemberDto dto) {

        Member findMember = getFindMember(email);

        findMember.changeUsername(dto.getUsername());
        findMember.changePassword(dto.getNewPassword());
        findMember.changeBusinessRegistrationNumber(dto.getBusinessRegistrationNumber());
        findMember.changeAddress(dto.getAddress());
    }

    @Transactional
    public void deleteMember(String email, String password) {

        Member findMember = getFindMember(email);

        if (findMember.getPassword().equals(password)) {
            memberRepository.delete(findMember);
        } else {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    public MemberInfo findMember(String email) {
        Member member = getFindMember(email);

        if (member.getRole().equals(Role.ROLE_MEMBER))
            return new MemberSearchDto(member.getId(), member.getUsername(), member.getEmail(), member.getAddress());


        if (member.getRole().equals(Role.ROLE_SHOP))
            return new ShopMemberSearchDto(member.getId(), member.getUsername(), member.getEmail(), member.getBusinessRegistrationNumber(), member.getAddress());

        return null;
    }
}
