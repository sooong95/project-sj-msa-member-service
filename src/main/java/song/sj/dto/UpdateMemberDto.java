package song.sj.dto;

import jakarta.persistence.Embedded;
import lombok.Data;
import song.sj.TimeStamp;
import song.sj.entity.Address;

import java.util.Objects;

@Data
public class UpdateMemberDto extends TimeStamp {

    private String username;

    private String currentPassword;
    private String newPassword;

    @Embedded
    private Address address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateMemberDto that = (UpdateMemberDto) o;
        return Objects.equals(username, that.username) && Objects.equals(currentPassword, that.currentPassword) && Objects.equals(newPassword, that.newPassword) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, currentPassword, newPassword, address);
    }
}
