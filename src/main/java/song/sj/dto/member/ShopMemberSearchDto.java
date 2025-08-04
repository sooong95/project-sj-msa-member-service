package song.sj.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import song.sj.entity.Address;

@Data
@AllArgsConstructor
public class ShopMemberSearchDto implements MemberInfo{

    private Long id;
    private String username;
    private String email;
    private String businessRegistrationNumber;
    private Address address;
}
