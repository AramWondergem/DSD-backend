package com.example.demo.util;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Error response object for handling exceptions globally in project")
public class Error {
    @Schema(description = "message set by developer to be more descriptive")
    String reason = "developer did not set reason for failure";
    @Schema(description = "error message generated by the exception object passed")
    String errorMessage = "no error message was set";
    @ArraySchema(schema = @Schema(description = "stack trace generated by the exception object passed", implementation = StackTraceElement.class))
    StackTraceElement[] stackTrace;

    public Error(@NonNull Exception exception, @NonNull String message) {
        this.errorMessage = exception.getMessage();
        this.stackTrace = exception.getStackTrace();
        this.reason = message;
    }

}
