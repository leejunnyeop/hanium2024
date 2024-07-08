package com.example.mypet.security.service.oauthInfoSerivce;

import com.example.mypet.security.domain.users.OAuth2AccessTokenDto;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.repository.UsersRepository;
import com.example.mypet.service.KmsService;
import com.example.mypet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MongoOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private final UsersRepository usersRepository;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final KmsService kmsService;
    private final WalletService walletService;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        Optional<Users> optionalUser = usersRepository.findByEmail(principalName);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
            if (clientRegistration != null) {
                OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(
                        user.getAccessToken().getTokenType(), user.getAccessToken().getTokenValue(), user.getAccessToken().getIssuedAt(), user.getAccessToken().getExpiresAt(), user.getAccessToken().getScopes());
                OAuth2AuthorizedClient client = new OAuth2AuthorizedClient(
                        clientRegistration,
                        principalName,
                        oAuth2AccessToken,
                        user.getRefreshToken()
                );
                return (T) client;
            }
        }
        return null;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication authentication) {
        log.info("---saveAuthorizedClient----");
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        // 지갑 생성
        WalletService.Wallet wallet = null;
        try {
            wallet = walletService.createWallet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // kms 를 통한 암호화
        String encryptedPrivateKey = kmsService.encrypt(wallet.getPrivateKey());

        Optional<Users> optionalUser = usersRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.updateTokens(new OAuth2AccessTokenDto(authorizedClient.getAccessToken()));
            usersRepository.save(user);
        } else {
            Users user = Users.createUser(email, authentication.getName(), authorizedClient.getClientRegistration().getRegistrationId(), new OAuth2AccessTokenDto(authorizedClient.getAccessToken()), authorizedClient.getClientRegistration().getRegistrationId());
            user.setCryptoWallet(wallet.getAddress(), wallet.getPublicKey(),encryptedPrivateKey);
            usersRepository.save(user);
        }
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        Optional<Users> optionalUser = usersRepository.findByEmail(principalName);
        optionalUser.ifPresent(usersRepository::delete);
    }
}
