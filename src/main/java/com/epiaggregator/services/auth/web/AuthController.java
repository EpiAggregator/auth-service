package com.epiaggregator.services.auth.web;

import com.epiaggregator.services.auth.model.ApiError;
import com.epiaggregator.services.auth.model.Token;
import com.epiaggregator.services.auth.model.TokenRepository;
import com.epiaggregator.services.auth.util.PBKDF2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1")
public class AuthController {
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UsersService usersService;
    private PBKDF2 hasher = new PBKDF2();

    @RequestMapping(method = RequestMethod.POST, path = "/token")
    public ResponseEntity<AuthenticateResponse> authenticate(@Validated @RequestBody AuthenticateRequest req) throws UnauthorizedException {
        GetUserResponse getUserResponse = usersService.getUserByEmail(req.getEmail());
        System.out.println(getUserResponse.getId());
        if (!hasher.authenticate(req.getPassword().toCharArray(), getUserResponse.getPassword())) {
            throw new UnauthorizedException();
        }
        String token = hasher.hash(getUserResponse.getPassword().toCharArray());
        tokenRepository.save(new Token(new ObjectId(getUserResponse.getId()), token, LocalDateTime.now().plusDays(30), "Bearer"));

        return new ResponseEntity<>(new AuthenticateResponse(token,Duration.ofDays(30),"Bearer"), HttpStatus.OK);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    ResponseEntity<ApiError> handleUnauthorizedException(UnauthorizedException exception) {
        return new ResponseEntity<>(new ApiError("wrong combination email/password"), HttpStatus.UNAUTHORIZED);
    }
}
