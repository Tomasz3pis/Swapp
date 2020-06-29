package com.swapp.application.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private TokenService tokenService;

    private static String hashPassword(String passwordToHash) {
        return BCrypt.hashpw(passwordToHash, BCrypt.gensalt());
    }

    public Optional<UserDetails> authenticateUser(User userToAuthenticate) {
        Optional<User> appUserFromDb = userRepository.findByUserNameIgnoreCase(userToAuthenticate.getUserName());
        if (!appUserFromDb.isPresent()) {
            return Optional.empty();
        }

        User userFromDb = appUserFromDb.get();

        if (!BCrypt.checkpw(userToAuthenticate.getPassword(), userFromDb.getPassword())) {
            return Optional.empty();
        }

        Tokens tokens = tokenService.generateTokens(userFromDb);

        UserDetails userDetails = UserDetails.builder()
                .id(userFromDb.getId())
                .userName(userFromDb.getUserName())
                .firstName(userFromDb.getFirstName())
                .lastName(userFromDb.getLastName())
                .phoneNumber(userFromDb.getPhoneNumber())
                .accessToken(tokens.getAccessToken())
                .refreshToken(tokens.getRefreshToken())
                .build();

        return Optional.of(userDetails);
    }

    public User registerUser(User user) {
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        // to ignore id passed in request
        user.setId(null);

        return userRepository.save(user);
    }

    public boolean isUsernameAlreadyUsed(String username) {
        return userRepository.findByUserNameIgnoreCase(username).isPresent();
    }


}
