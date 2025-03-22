package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ComplaintRequest {
    @NotBlank(message = "Message cannot be blank")
    @Size(max = 500, message = "Message cannot exceed 500 characters")
    String message;
    @Schema(description = "by default status is set to 'new' no explicit value. may use put to update to either 'Pending','Resolved','New' only, case insensitive")
    @Pattern(regexp = "new|pending|resolved", message = "Invalid status value to update. Allowed values: New, Pending, Resolved")
    String status;
    @Schema(description = "by default status is set to 'new' no explicit value. may use put to update to either 'Pending','Resolved','New' only, case insensitive")
    @Pattern(regexp = "noise|repair|other", message = "Invalid complaint value to set. Allowed values: noise, repair, other")
    String complaintType;

}
