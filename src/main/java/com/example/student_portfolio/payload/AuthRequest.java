// payload/AuthRequest.java
package com.example.student_portfolio.payload;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
