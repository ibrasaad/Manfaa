package com.v1.manfaa.Service;
import com.v1.manfaa.Api.ApiException;
import com.v1.manfaa.Config.JwtUtil;
import com.v1.manfaa.DTO.In.LoginRequest;
import com.v1.manfaa.DTO.Out.LoginResponse;
import com.v1.manfaa.Model.User;
import com.v1.manfaa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            User user = (User) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(user.getUsername());
            return new LoginResponse(
                    jwt,
                    user.getUsername(),
                    user.getRole(),
                    "Login successful"
            );
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid username or password");
        } catch (Exception e) {
            throw new ApiException("Authentication failed: " + e.getMessage());
        }
    }


    public boolean validateToken(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            return username != null;
        } catch (Exception e) {
            throw new ApiException("Invalid token");
        }
    }
}