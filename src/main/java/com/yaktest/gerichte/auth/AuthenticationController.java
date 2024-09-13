package com.yaktest.gerichte.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest request){
        AuthenticationResponse response = authenticationService.register(request);
            return ResponseEntity.ok(response);
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/authentication")
    public String test() {
        return "Hello World!";
    }

}
