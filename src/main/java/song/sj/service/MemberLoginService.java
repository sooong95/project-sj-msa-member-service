package song.sj.service;

import song.sj.dto.LoginDto;

import java.util.Map;

public interface MemberLoginService {

    Map<String, Object> login(LoginDto loginDto);

    void logout();
}
