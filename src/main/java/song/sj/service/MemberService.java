package song.sj.service;

import song.sj.dto.UpdateMemberDto;
import song.sj.dto.UpdateShopMemberDto;
import song.sj.dto.member.*;
import song.sj.entity.Member;

public interface MemberService {

    void memberSave(MemberJoinDto dto);

    void shopMemberSave(ShopMemberJoinDto dto);

    void updateMember(String email, UpdateMemberDto dto);

    void updateShopMember(String email, UpdateShopMemberDto dto);

    void deleteMember(String email, String password);

    MemberInfo findMember(String email);
}
