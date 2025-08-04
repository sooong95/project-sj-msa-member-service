package song.sj.dto;

import jakarta.persistence.Embedded;
import lombok.Data;
import lombok.EqualsAndHashCode;
import song.sj.TimeStamp;
import song.sj.entity.Address;

@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateShopMemberDto extends TimeStamp {

    private String username;

    private String currentPassword;
    private String newPassword;

    private String shopName;
    private String businessRegistrationNumber;

    @Embedded
    private Address address;
}
