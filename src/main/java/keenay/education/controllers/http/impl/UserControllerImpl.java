package keenay.education.controllers.http.impl;

import jakarta.validation.Valid;
import keenay.education.controllers.http.UserController;
import keenay.education.dto.auth.LoginDTO;
import keenay.education.dto.auth.RegisterAdminDTO;
import keenay.education.dto.auth.RegisterCustomerDTO;
import keenay.education.dto.auth.RegisterSellerDTO;
import keenay.education.dto.security.JwtAutorizeToken;
import keenay.education.repository.UserRepository;
import keenay.education.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;


@RestController
@Slf4j
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @PostMapping("/admin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterAdminDTO registerAdminDTO) throws AuthenticationException {
        return ResponseEntity.ok(userService.registerAdmin(registerAdminDTO));
    }

    @Override
    @PostMapping("/customer")
    public ResponseEntity<String> registerCustomer(@Valid @RequestBody RegisterCustomerDTO registerCustomerDTO) throws AuthenticationException {
        return ResponseEntity.ok(userService.registerCustomer(registerCustomerDTO));
    }

    @Override
    @PostMapping("/seller")
    public ResponseEntity<String> registerSeller(@Valid @RequestBody RegisterSellerDTO registerSellerDTO) throws AuthenticationException {
        return ResponseEntity.ok(userService.registerSeller(registerSellerDTO));
    }

    @Override
    @PostMapping("/sign/in")
    public ResponseEntity<JwtAutorizeToken> signIn(@Valid @RequestBody LoginDTO loginDTO) throws AuthenticationException {
        return ResponseEntity.ok(userService.singIn(loginDTO));
    }
}
