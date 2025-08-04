package song.sj.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import song.sj.entity.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchDto implements MemberInfo{

    private Long id;
    private String username;
    private String email;
    private Address address;
}
