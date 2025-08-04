package song.sj;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import song.sj.entity.Address;
import song.sj.entity.Member;
import song.sj.enums.Role;
import song.sj.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (memberRepository.findByEmail("test@gmail.com").isPresent()) return;

        Member member = Member.builder()
                .email("test@gmail.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.ROLE_MEMBER)
                .address(Address.builder()
                        .city("서울시")
                        .street("1번가")
                        .zipcode("ㅇㅇ")
                        .build())
                .build();

        memberRepository.save(member);
    }
}
