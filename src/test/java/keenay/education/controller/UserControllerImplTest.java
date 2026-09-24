package keenay.education.controller;

import keenay.education.controllers.http.impl.UserControllerImpl;
import keenay.education.dto.auth.LoginDTO;
import keenay.education.dto.auth.RegisterAdminDTO;
import keenay.education.dto.auth.RegisterCustomerDTO;
import keenay.education.dto.auth.RegisterSellerDTO;
import keenay.education.dto.security.JwtAutorizeToken;
import keenay.education.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerImpl userController;

    private RegisterAdminDTO adminDto() {
        RegisterAdminDTO dto = new RegisterAdminDTO();
        dto.setEmail("admin@example.com");
        dto.setPassword("password123");
        return dto;
    }

    private RegisterCustomerDTO customerDto() {
        RegisterCustomerDTO dto = new RegisterCustomerDTO();
        dto.setEmail("customer@example.com");
        dto.setPassword("password123");
        dto.setName("Ivan");
        dto.setSurname("Ivanov");
        return dto;
    }

    private RegisterSellerDTO sellerDto() {
        RegisterSellerDTO dto = new RegisterSellerDTO();
        dto.setEmail("seller@example.com");
        dto.setPassword("password123");
        dto.setName("Petr");
        dto.setSurname("Petrov");
        dto.setAddress("Moscow, Red Square 1");
        dto.setInn("1234567890");
        dto.setDescription("Seller of goods");
        return dto;
    }

    private LoginDTO loginDto() {
        LoginDTO dto = new LoginDTO();
        dto.setEmail("user@example.com");
        dto.setPassword("password123");
        return dto;
    }

    @Test
    @DisplayName("Тест registerAdmin")
    public void testRegisterAdminSuccess() throws AuthenticationException {
        when(userService.registerAdmin(any(RegisterAdminDTO.class))).thenReturn("success");

        ResponseEntity<String> response = userController.registerAdmin(adminDto());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(userService).registerAdmin(any(RegisterAdminDTO.class));
    }

    @Test
    @DisplayName("Тест registerAdmin ошибка аутентификации")
    public void testRegisterAdminThrowsAuthenticationException() throws AuthenticationException {
        when(userService.registerAdmin(any(RegisterAdminDTO.class)))
                .thenThrow(new AuthenticationException("Invalid password."));

        assertThrows(AuthenticationException.class,
                () -> userController.registerAdmin(adminDto()));
        verify(userService).registerAdmin(any(RegisterAdminDTO.class));
    }

    @Test
    @DisplayName("Тест registerCustomer")
    public void testRegisterCustomerSuccess() throws AuthenticationException {
        when(userService.registerCustomer(any(RegisterCustomerDTO.class))).thenReturn("success");

        ResponseEntity<String> response = userController.registerCustomer(customerDto());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(userService).registerCustomer(any(RegisterCustomerDTO.class));
    }

    @Test
    @DisplayName("Тест registerCustomer ошибка аутентификации")
    public void testRegisterCustomerThrowsAuthenticationException() throws AuthenticationException {
        when(userService.registerCustomer(any(RegisterCustomerDTO.class)))
                .thenThrow(new AuthenticationException("The role has already been added"));

        assertThrows(AuthenticationException.class,
                () -> userController.registerCustomer(customerDto()));
        verify(userService).registerCustomer(any(RegisterCustomerDTO.class));
    }

    @Test
    @DisplayName("Тест registerSeller")
    public void testRegisterSellerSuccess() throws AuthenticationException {
        when(userService.registerSeller(any(RegisterSellerDTO.class))).thenReturn("success");

        ResponseEntity<String> response = userController.registerSeller(sellerDto());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(userService).registerSeller(any(RegisterSellerDTO.class));
    }

    @Test
    @DisplayName("Тест registerSeller ошибка аутентификации")
    public void testRegisterSellerThrowsAuthenticationException() throws AuthenticationException {
        when(userService.registerSeller(any(RegisterSellerDTO.class)))
                .thenThrow(new AuthenticationException("Invalid password."));

        assertThrows(AuthenticationException.class,
                () -> userController.registerSeller(sellerDto()));
        verify(userService).registerSeller(any(RegisterSellerDTO.class));
    }

    @Test
    @DisplayName("Тест signIn ошибка аутентификации")
    public void testSignInThrowsAuthenticationException() throws AuthenticationException {
        when(userService.singIn(any(LoginDTO.class)))
                .thenThrow(new AuthenticationException("Invalid password."));

        assertThrows(AuthenticationException.class,
                () -> userController.signIn(loginDto()));
        verify(userService).singIn(any(LoginDTO.class));
    }
}