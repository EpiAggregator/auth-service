package com.epiaggregator.services.auth.web;

import com.epiaggregator.services.auth.model.ApiError;
import com.epiaggregator.services.auth.model.Token;
import com.epiaggregator.services.auth.model.TokenRepository;
import com.epiaggregator.services.auth.util.PBKDF2;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UsersService usersService;
    @Value("${jwt.secretKey}")
    private String base64EncodedSecretKey;
    private PBKDF2 hasher = new PBKDF2();

    @RequestMapping(method = RequestMethod.POST, path = "/token")
    public ResponseEntity<AuthenticateResponse> authenticate(@Validated @RequestBody AuthenticateRequest req) throws UnauthorizedException {
        GetUserResponse getUserResponse = usersService.getUserByEmail(req.getEmail());

        if (!hasher.authenticate(req.getPassword().toCharArray(), getUserResponse.getPassword())) {
            throw new UnauthorizedException();
        }
        String token = hasher.hash(getUserResponse.getPassword().toCharArray());
        tokenRepository.save(new Token(new ObjectId(getUserResponse.getId()), token, LocalDateTime.now().plusDays(30), "Bearer"));

        return new ResponseEntity<>(new AuthenticateResponse(token, Duration.ofDays(30), "Bearer"), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/token/exchange")
    public ResponseEntity<ExchangeResponse> exchange(@Validated @RequestBody ExchangeRequest req) throws UnauthorizedException {
        Token token = tokenRepository.findOneByToken(req.getAccessToken());
        GetUserResponse user = usersService.getUserById(token.getUserId().toHexString());
        String jwtUser = Jwts.builder()
                .setClaims(Jwts.claims()
                        .setAudience(req.getAudience())
                        .setExpiration(new Date(System.currentTimeMillis() + 600 * 1000))
                        .setId(UUID.randomUUID().toString())
                        .setIssuedAt(new Date())
                        .setIssuer("com.epiaggregator.services.auth")
                        .setSubject(user.getId()))
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
                .compact();

        return new ResponseEntity<>(new ExchangeResponse(jwtUser), HttpStatus.OK);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    ResponseEntity<ApiError> handleUnauthorizedException(UnauthorizedException exception) {
        return new ResponseEntity<>(new ApiError("wrong combination email/password"), HttpStatus.UNAUTHORIZED);
    }
}
