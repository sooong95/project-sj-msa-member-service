package song.sj.service;

import song.sj.dto.LoginDto;
import song.sj.dto.MemberRefreshTokenDto;

import java.util.Map;

public interface MemberLoginService {

    Map<String, Object> login(LoginDto loginDto);

    void logout(String email);

    Map<String, Object> reissueAccessToken(MemberRefreshTokenDto memberRefreshTokenDto);

}
