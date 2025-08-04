package song.sj.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.sj.dto.LoginDto;
import song.sj.entity.Member;
import song.sj.jwt.JwtTokenProvider;
import song.sj.repository.MemberRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberLoginServiceImpl implements MemberLoginService {

    private final MemberRepository memberRepository;
    private final @Qualifier("rtdb") RedisTemplate<String, Object> redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private String getMemberEmail(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 ID 입니다."));

        return member.getEmail();
    }

    private Member emailPasswordVerification(LoginDto loginDto, Optional<Member> findMember) {

        if (findMember.isEmpty()) {
            throw new IllegalArgumentException("email 또는 password 가 일치하지 않습니다.");
        }

        Member member = findMember.get();

        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("email 또는 password 가 일치하지 않습니다.");
        }

        return member;
    }

    private Map<String, Object> createTokenAndStoringTokenInRedis(Member member) {
        Map<String, Object> loginInfo = new HashMap<>();
        String token = jwtTokenProvider.createToken(member.getId().toString(), member.getRole().toString());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail(), member.getRole().toString());
        redisTemplate.opsForValue().set(member.getEmail(), refreshToken, 100, TimeUnit.DAYS);

        loginInfo.put("id", member.getId());
        loginInfo.put("token", token);
        loginInfo.put("refreshToken", refreshToken);
        return loginInfo;
    }

    @Override
    public Map<String, Object> login(LoginDto loginDto) {


        Optional<Member> findMember = memberRepository.findByEmail(loginDto.getEmail());

        Member member = emailPasswordVerification(loginDto, findMember);

        return createTokenAndStoringTokenInRedis(member);
    }

    @Override
    public void logout(Long memberId) {

        String redisKey = getMemberEmail(memberId);

        if (redisTemplate.hasKey(redisKey)) {
            redisTemplate.delete(redisKey);
            log.info("로그아웃 - redis 에서 refresh token 삭제: {}", redisKey);
        }
    }
}
