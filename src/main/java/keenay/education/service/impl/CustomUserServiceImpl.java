package keenay.education.service.impl;

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
        return new CustomUserDetail(usersRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("The user with this ID was not found.")));
    }
}
