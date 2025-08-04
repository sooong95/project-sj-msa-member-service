package song.sj.service;

import song.sj.dto.UpdateMemberDto;
import song.sj.dto.UpdateShopMemberDto;
import song.sj.dto.member.*;
import song.sj.entity.Member;

public interface MemberService {

    void memberSave(MemberJoinDto dto);

    void shopMemberSave(ShopMemberJoinDto dto);

    Member findMember(String email);

    void updateMember(Long memberId, UpdateMemberDto dto);

    void updateShopMember(Long memberId, UpdateShopMemberDto dto);

    void deleteMember(Long memberId, String password);

    MemberInfo findMember(Long memberId);
}
