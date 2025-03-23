package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(description = "Metadata details for a document")
public class MetaData {
   @Schema(description = "Title of the document", example = "Lease Agreement")
    private String title;

    @Schema(description = "Description of the document", example = "Lease agreement for apartment 101")
    private String description;

    @Schema(description = "Start date of the document validity", example = "2023-01-01")
    private String startDate;

    @Schema(description = "End date of the document validity", example = "2023-12-31")
    private String endDate;
}

