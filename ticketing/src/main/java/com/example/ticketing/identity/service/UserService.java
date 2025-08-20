package com.example.ticketing.identity.service;

import com.example.ticketing.identity.domain.User;
import com.example.ticketing.identity.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository users;

    public Optional<User> findById(Long id) { return users.findById(id); }
}
