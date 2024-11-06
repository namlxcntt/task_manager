package com.lxn.task_manager.auth.services;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistService {
    private final Set<String> blacklist = new HashSet<>();

    public void blacklistToken(String token) {
        blacklist.add(token); // Đánh dấu token là không hợp lệ
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token); // Kiểm tra token có trong blacklist không
    }
}
