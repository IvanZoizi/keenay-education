package keenay.education.service.impl;

import keenay.education.dto.auth.LoginDTO;
import keenay.education.dto.auth.RegisterAdminDTO;
import keenay.education.dto.auth.RegisterCustomerDTO;
import keenay.education.dto.auth.RegisterSellerDTO;
import keenay.education.dto.security.JwtAutorizeToken;
import keenay.education.entity.Customers;
import keenay.education.entity.Roles;
import keenay.education.entity.Sellers;
import keenay.education.entity.Users;
import keenay.education.exception.errors.InternalException;
import keenay.education.repository.CustomersRepository;
import keenay.education.repository.RolesRepository;
import keenay.education.repository.SellersRepository;
import keenay.education.repository.UserRepository;
import keenay.education.security.jwt.JwtService;
import keenay.education.service.UserService;
import keenay.education.utils.UtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final CustomersRepository customersRepository;
    private final SellersRepository sellersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private Users createUser(String email, String password, List<Roles> roles) {
        Users user = new Users();
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    private Customers createCustomers(String name, String surname, Users user) {
        Customers customers = new Customers();
        customers.setName(name);
        customers.setSurname(surname);
        customers.setUser(user);
        return customersRepository.save(customers);
    }

    private Sellers createSellers(String name, String surname, String address, String inn, String description, Users user) {
        Sellers seller = new Sellers();
        seller.setName(name);
        seller.setSurname(surname);
        seller.setAddress(address);
        seller.setInn(inn);
        seller.setDescription(description);
        seller.setUser(user);
        return sellersRepository.save(seller);
    }


    @Override
    public String registerAdmin(RegisterAdminDTO registerAdminDTO) throws AuthenticationException {

        Optional<Users> usersOptional = userRepository.findByEmail(registerAdminDTO.getEmail());
        Roles adminRole = rolesRepository.findByRole("admin").orElseThrow(() -> new InternalException("Role not found"));
        if (usersOptional.isEmpty()) {
            this.createUser(registerAdminDTO.getEmail(),
                    registerAdminDTO.getPassword(),
                    List.of(adminRole));
        } else {
            Users currentUser = usersOptional.get();
            if  (!passwordEncoder.matches(registerAdminDTO.getPassword(), currentUser.getPassword())) {
                throw new AuthenticationException("Invalid password.");
            }
            List<Roles> rolesList = currentUser.getRoles();
            if (UtilsService.in(rolesList, adminRole)) {
                throw new AuthenticationException("The role has already been added");
            }
            rolesList.add(adminRole);
            currentUser.setRoles(List.of(adminRole));
            userRepository.save(currentUser);
        }
        return "success";
    }

    @Override
    public String registerCustomer(RegisterCustomerDTO registerCustomerDTO) throws AuthenticationException {
        Optional<Users> usersOptional = userRepository.findByEmail(registerCustomerDTO.getEmail());
        Roles customerRole = rolesRepository.findByRole("customer").orElseThrow(() -> new InternalException("Role not found"));
        if (usersOptional.isEmpty()) {
            Users user = this.createUser(registerCustomerDTO.getEmail(),
                    registerCustomerDTO.getPassword(),
                    List.of(customerRole));
            this.createCustomers(
                    registerCustomerDTO.getName(),
                    registerCustomerDTO.getSurname(),
                    user
            );

        } else {
            Users currentUser = usersOptional.get();
            if  (!passwordEncoder.matches(registerCustomerDTO.getPassword(), currentUser.getPassword())) {
                throw new AuthenticationException("Invalid password.");
            }
            List<Roles> rolesList = currentUser.getRoles();
            if (UtilsService.in(rolesList, customerRole)) {
                throw new AuthenticationException("The role has already been added");
            }
            rolesList.add(customerRole);
            currentUser.setRoles(List.of(customerRole));
            this.createCustomers(
                    registerCustomerDTO.getName(),
                    registerCustomerDTO.getSurname(),
                    userRepository.save(currentUser)
            );
        }
        return "success";
    }

    @Override
    public String registerSeller(RegisterSellerDTO registerSellerDTO) throws AuthenticationException {
        Optional<Users> usersOptional = userRepository.findByEmail(registerSellerDTO.getEmail());
        Roles sellerRole = rolesRepository.findByRole("customer").orElseThrow(() -> new InternalException("Role not found"));
        if (usersOptional.isEmpty()) {
            Users user = this.createUser(registerSellerDTO.getEmail(),
                    registerSellerDTO.getPassword(),
                    List.of(sellerRole));
            this.createSellers(
                    registerSellerDTO.getName(),
                    registerSellerDTO.getSurname(),
                    registerSellerDTO.getAddress(),
                    registerSellerDTO.getInn(),
                    registerSellerDTO.getDescription(),
                    user
            );

        } else {
            Users currentUser = usersOptional.get();
            if  (!passwordEncoder.matches(registerSellerDTO.getPassword(), currentUser.getPassword())) {
                throw new AuthenticationException("Invalid password.");
            }
            List<Roles> rolesList = currentUser.getRoles();
            if (UtilsService.in(rolesList, sellerRole)) {
                throw new AuthenticationException("The role has already been added");
            }
            rolesList.add(sellerRole);
            currentUser.setRoles(List.of(sellerRole));
            this.createSellers(
                    registerSellerDTO.getName(),
                    registerSellerDTO.getSurname(),
                    registerSellerDTO.getAddress(),
                    registerSellerDTO.getInn(),
                    registerSellerDTO.getDescription(),
                    userRepository.save(currentUser)
            );
        }
        return "success";
    }

    @Override
    public JwtAutorizeToken singIn(LoginDTO loginDTO) throws AuthenticationException {
        Users user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new AuthenticationException("The user with this ID was not found."));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid password.");
        }

        return jwtService.generateAuthToken(user.getEmail());
    }
}
