package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TenantDto {
    String username;
    String email;
    String name;
}
