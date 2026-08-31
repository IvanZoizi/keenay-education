package keenay.education.controllers.http;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import keenay.education.dto.auth.LoginDTO;
import keenay.education.dto.auth.RegisterAdminDTO;
import keenay.education.dto.auth.RegisterCustomerDTO;
import keenay.education.dto.auth.RegisterSellerDTO;
import keenay.education.dto.security.JwtAutorizeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;

@Tag(name = "Auth Endpoints")
@RequestMapping("/api/v1/auth")
public interface UserController {
    ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterAdminDTO registerAdminDTO) throws AuthenticationException;
    ResponseEntity<String> registerCustomer(@Valid @RequestBody RegisterCustomerDTO registerCustomerDTO) throws AuthenticationException;
    ResponseEntity<String> registerSeller(@Valid @RequestBody RegisterSellerDTO registerSellerDTO) throws AuthenticationException;
    ResponseEntity<JwtAutorizeToken> signIn(@Valid @RequestBody LoginDTO loginDTO) throws AuthenticationException;
//    ResponseEntity<String> register(@RequestParam("file") MultipartFile file);
//    ResponseEntity<JwtAutorizeToken> singIn(@Valid @RequestBody LoginDTO loginDTO) throws AuthenticationException;
//    ResponseEntity<JwtAutorizeToken> refreshToken(@Valid @RequestBody RefreshTokenDTO loginDTO);
//    ResponseEntity<String> acceptRegister(@PathParam("token") String token);
}
