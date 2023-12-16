package nikiforov.ruslan.taskmanagementsystem.services;

import nikiforov.ruslan.taskmanagementsystem.dto.AuthRequest;
import nikiforov.ruslan.taskmanagementsystem.dto.AuthResponse;
import nikiforov.ruslan.taskmanagementsystem.dto.RegisterRequest;
import nikiforov.ruslan.taskmanagementsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public AuthenticationManager authenticationManager;

    JwtService jwtService;
    public BCryptPasswordEncoder passwordEncoder;

    public UserService userService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService,
                                 BCryptPasswordEncoder passwordEncoder, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public User signUp(RegisterRequest registerRequest) {
        User user = User.builder().name(registerRequest.getName()).email(registerRequest.getEmail()).
                password(passwordEncoder.encode(registerRequest.getPassword())).build();
        return userService.create(user);

    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                authRequest.getPassword()));
        UserDetails user = userService.loadUserByUsername(authRequest.getEmail());
        String token = jwtService.generateToken(user);
        return AuthResponse.builder().token(token).build();
    }


}
