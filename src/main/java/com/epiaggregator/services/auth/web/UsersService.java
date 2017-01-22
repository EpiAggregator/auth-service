package com.epiaggregator.services.auth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsersService {
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    public GetUserResponse getUserByEmail(String email) {
        return restTemplate.getForObject("http://users-service/v1/users?email="+email, GetUserResponse.class);
    }
}
