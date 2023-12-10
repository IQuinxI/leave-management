package ma.emsi.leavemanagement.controllers.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.leavemanagement.dto.auth.AuthenticationRequest;
import ma.emsi.leavemanagement.dto.auth.AuthenticationResponse;
import ma.emsi.leavemanagement.services.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationService service;
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }


    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request,response);
    }



//    @PostMapping("/login")
//    public Map<String,String>login( @RequestParam String username,@RequestParam String password){
//
//
//        log.info(username +password);
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
//        Instant instant = Instant.now();
//        String scope=authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
//        JwtClaimsSet jwtClaimsSet= JwtClaimsSet.builder()
//                .issuedAt(instant)
//                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
//                .subject(username)
//                .claim("scope",scope)
//                .build();
//
//        JwtEncoderParameters jwtEncoderParameters=
//                JwtEncoderParameters.from(
//                        JwsHeader.with(MacAlgorithm.HS512).build(),
//                        jwtClaimsSet
//                );
//        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
//
//        return Map.of("access-token",jwt);
//    }


}
