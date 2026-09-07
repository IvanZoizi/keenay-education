package keenay.education.service.impl;

import keenay.education.entity.Users;
import keenay.education.exception.errors.AuthorizationException;
import keenay.education.exception.errors.EntityNotFoundException;
import keenay.education.repository.UserRepository;
import keenay.education.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl {

    private final UserRepository usersRepository;

    public CustomUserDetail getUserByEmail(String email) {
        Users user = usersRepository.findByEmailWithRoles(email)
                .orElseThrow(() -> new AuthorizationException("User not found"));

        return new CustomUserDetail(user);
    }
}
