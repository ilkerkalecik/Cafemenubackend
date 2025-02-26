package com.example.cafemenu.controller;

import com.example.cafemenu.entity.LoginRequest;
import io.jsonwebtoken.Jwts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    // Base64-encoded secret key


	// Instead of a plain string, create a SecretKey from your base64-encoded string:
	private static final String SECRET_KEY_BASE64 = "K3UA6aaP2JhUXMS/hY7uGcJmfZK3/eztKP5g80AWVTXY6eSftJb6z1KxJsag91TviigutFM/VWEccfiy4OBqxw==";
	private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY_BASE64));

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	    if ("admin".equals(loginRequest.getUsername()) && "ilkerkalecik".equals(loginRequest.getPassword())) {
	        String token = Jwts.builder()
	                .setSubject(loginRequest.getUsername())
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
	                .signWith(SECRET_KEY)  // using the SecretKey object
	                .compact();

	        Map<String, String> response = new HashMap<>();
	        response.put("token", token);
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body("Invalid username or password");
	    }
	}

}

