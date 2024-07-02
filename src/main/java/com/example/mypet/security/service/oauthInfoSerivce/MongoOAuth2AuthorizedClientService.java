package com.example.mypet.security.service.oauthInfoSerivce;

import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MongoOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private final UsersRepository usersRepository;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        Optional<Users> optionalUser = usersRepository.findByEmail(principalName);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
            if (clientRegistration != null) {
                OAuth2AuthorizedClient client = new OAuth2AuthorizedClient(
                        clientRegistration,
                        principalName,
                        user.getAccessToken(),
                        user.getRefreshToken()
                );
                return (T) client;
            }
        }
        return null;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        Optional<Users> optionalUser = usersRepository.findByEmail(principal.getName());
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.updateTokens(authorizedClient.getAccessToken());
            usersRepository.save(user);
        }
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        Optional<Users> optionalUser = usersRepository.findByEmail(principalName);
        optionalUser.ifPresent(usersRepository::delete);
    }
}
