package com.yaktest.gerichte.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String userName;
    private String role;
}
