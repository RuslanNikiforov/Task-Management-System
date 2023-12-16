package nikiforov.ruslan.taskmanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nikiforov.ruslan.taskmanagementsystem.dto.AuthRequest;
import nikiforov.ruslan.taskmanagementsystem.dto.AuthResponse;
import nikiforov.ruslan.taskmanagementsystem.dto.RegisterRequest;
import nikiforov.ruslan.taskmanagementsystem.entity.Task;
import nikiforov.ruslan.taskmanagementsystem.entity.User;
import nikiforov.ruslan.taskmanagementsystem.services.AuthenticationService;
import nikiforov.ruslan.taskmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    AuthenticationService authenticationService;

    UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been registered!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Request body is empty or user with this email already" +
                    " exist",
                    content = @Content)})
    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@RequestBody RegisterRequest registerRequest) {
        if (registerRequest != null) {
            if (userService.loadUserByUsername(registerRequest.getEmail()) == null) {
                return ResponseEntity.ok(authenticationService.signUp(registerRequest));
            }
            return ResponseEntity.badRequest().build();

        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Authenticate user and response JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user has authenticated and the token has been sent",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Request body is empty!",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Wrong email and password!",
                    content = @Content)})
    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        if (authRequest != null) {
            return ResponseEntity.ok(authenticationService.authenticate(authRequest));
        }
        return ResponseEntity.badRequest().build();
    }
}
