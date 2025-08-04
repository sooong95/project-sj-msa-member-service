package song.sj.dto.member;

import song.sj.entity.Address;

public interface MemberInfo {

    Long getId();

    String getUsername();

    String getEmail();

    Address getAddress();
}
