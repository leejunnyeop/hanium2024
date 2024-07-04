package com.example.mypet.security.service.custom;

import com.example.mypet.security.domain.oauth.OAuth2UserInfo;
import com.example.mypet.security.domain.users.OAuth2AccessTokenDto;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.domain.users.UsersDto;
import com.example.mypet.security.domain.users.UserMapper;
import com.example.mypet.security.repository.UsersRepository;
import com.example.mypet.security.service.oauthInfoSerivce.OAuth2UserInfoServiceProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsersRepository usersRepository;
    private final OAuth2UserInfoServiceProvider userInfoServiceProvider;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        log.info("CustomOAuth2UserService 시작----------------------------------------------------------------");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = userInfoServiceProvider.getOAuth2UserInfoService(provider).loadUserInfo(oAuth2User);

        UsersDto userDto = processUserRegistration(oAuth2UserInfo, provider, userRequest);


        log.info(" UserInfo 찾기 --------------------------------------------------------------------------- ");

        // 사용자 정보를 포함한 OAuth2User 반환
        DefaultOAuth2User defaultOAuth2User = new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                oAuth2User.getAttributes(),
                "email"
        );

        return defaultOAuth2User;
    }

    private UsersDto processUserRegistration(OAuth2UserInfo oAuth2UserInfo, String provider, OAuth2UserRequest userRequest) {
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();

        // 이메일로 사용자 검색
        Optional<Users> optionalUser = usersRepository.findByEmail(email);
        Users user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get(); // 기존 사용자 반환
            // 기존 사용자의 OAuth2 토큰 갱신
            user.updateTokens(new OAuth2AccessTokenDto(userRequest.getAccessToken()));
        } else {
            // 새로운 사용자 생성 및 저장
            user = Users.createUser(email, name, provider, new OAuth2AccessTokenDto(userRequest.getAccessToken()), userRequest.getClientRegistration().getRegistrationId());
            usersRepository.save(user);
        }

        return UserMapper.toDto(user);
    }
}
