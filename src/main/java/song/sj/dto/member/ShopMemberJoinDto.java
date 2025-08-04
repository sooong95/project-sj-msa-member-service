package song.sj.dto.member;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import song.sj.entity.Address;
import song.sj.enums.Role;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShopMemberJoinDto {

    @NotEmpty(message = "이름은 필수입니다.")
    private String username;

    @NotEmpty
    private String businessRegistrationNumber;

    @Column(unique = true)
    private String email;

    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // member, admin
    @Embedded
    @Valid
    private Address address;
}