package com.example.api.web.rest;

import com.example.api.domain.Customer;
import com.example.api.security.JWTUtil;
import com.example.api.service.CustomerService;
import com.example.api.web.dto.JwtRequest;
import com.example.api.web.dto.JwtResponse;
import com.example.api.web.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final JWTUtil jwtUtil;

    private final CustomerService service;

    private static final String CUSTOMER_NOT_FOUND = "Customer not found";

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        Customer customer = service.findCustomerByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));

        final String token = jwtUtil.generateToken(customer.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        Customer customer = service.authenticated();
        String token = jwtUtil.generateToken(customer.getEmail());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

}
