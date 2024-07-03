package com.example.mypet.security.service.tokenService;

import com.example.mypet.security.domain.oauth.OAuth2UserInfo;
import com.example.mypet.security.domain.users.UserMapper;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.domain.users.UsersDto;
import com.example.mypet.security.repository.UsersRepository;
import com.example.mypet.security.service.oauthInfoSerivce.OAuth2UserInfoServiceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService{
    private final UsersRepository usersRepository;
    private final OAuth2UserInfoServiceProvider userInfoServiceProvider;

    @Override
    public UsersDto processUser(OAuth2UserInfo userInfo, String provider) {
        String email = userInfo.getEmail();
        Optional<Users> optionalUser = usersRepository.findByEmail(email);
        Users user = optionalUser.orElseGet(() -> Users.createUser(email, userInfo.getName(), provider));
        if (optionalUser.isEmpty()) {
            usersRepository.save(user);
        }
        return UserMapper.toDto(user);
    }

    @Override
    public OAuth2UserInfo getOAuth2UserInfo(String provider, String accessToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String userInfoUri = getUserInfoUri(provider);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, Map.class);
            return userInfoServiceProvider.getOAuth2UserInfoService(provider).loadUserInfo((OAuth2User) response.getBody());
        } catch (Exception e) {
            throw new IllegalArgumentException("사용자 정보를 가져오는 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    private String getUserInfoUri(String provider) {
        switch (provider) {
            case "google":
                return "https://www.googleapis.com/oauth2/v3/userinfo";
            case "naver":
                return "https://openapi.naver.com/v1/nid/me";
            default:
                throw new IllegalArgumentException("지원되지 않는 제공자입니다: " + provider);
        }
    }


}
