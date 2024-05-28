package com.hardeymorlah.Logistics.Model.DTOs;

import com.hardeymorlah.Logistics.Model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private User user;
    private String token;
}
