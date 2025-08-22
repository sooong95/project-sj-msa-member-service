package song.sj.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.util.StringUtils;
import song.sj.TimeStamp;
import song.sj.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of = {"id", "username", "businessRegistrationNumber", "email", "password", "role", "address"})
public class Member extends TimeStamp {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String businessRegistrationNumber;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // member, admin

    @Embedded
    @Valid
    private Address address;

    @Builder.Default
    private int balance = 0;
    @Builder.Default
    private int point = 0;

    @Builder.Default
    private int reviewCount = 0;
    @Builder.Default
    private int wishlistCount = 0;

    @Builder.Default
    private List<Long> itemsId = new ArrayList<>();

    // 굳이 member 쪽에서 shop 을 알 필요가 없음
    /*@Builder.Default
    private List<Long> shopIdList = new ArrayList<>();*/

    @Builder.Default
    private List<Long> reviewIdList = new ArrayList<>();

    @Builder.Default
    private List<Long> wishlistsId = new ArrayList<>();

    @Builder.Default
    private List<Long> paymentIdList = new ArrayList<>();

    @Builder.Default
    private List<Long> billIdList = new ArrayList<>();

    public void transPassword(String hashPassword) {
        this.password = hashPassword;
    }

    public void changeUsername(String username) {
        if (StringUtils.hasText(username)) this.username = username;
    }

    public void changePassword(String password) {
        if (StringUtils.hasText(password)) this.password = password;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeAddress(Address address) {
        if (Objects.nonNull(address)) this.address = address;
    }

    public void changeBusinessRegistrationNumber(String businessRegistrationNumber) {
        if (StringUtils.hasText(businessRegistrationNumber))
            this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public void changeRole(Role role) {
        this.role = role;
    }

    public void reviewCount() {
        reviewCount++;
    }

    public void reviewCountReduce() {
        reviewCount--;
    }

    public void wishlistCount() {
        wishlistCount++;
    }

    public void inCreaseBalance(int amount) {
        if (this.balance >= 0) {
            this.balance = amount;
        }
    }

    public void decreaseBalance(int amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        } else {
            throw new RuntimeException("잔고가 부족합니다.");
        }
    }

    public void reward(int amount) {
        double rewardPoint = 0.03;
        if (amount > 0) this.point = (int) Math.ceil(amount * rewardPoint);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(username, member.username) && Objects.equals(email, member.email) && Objects.equals(password, member.password) && role == member.role && Objects.equals(address, member.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, role, address);
    }
}
