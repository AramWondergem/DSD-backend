package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TenantDTO {
    String username;
    String email;
    String name;
}