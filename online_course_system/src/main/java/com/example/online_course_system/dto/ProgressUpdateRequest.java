// dto/ProgressUpdateRequest.java
package com.example.online_course_system.dto;
import jakarta.validation.constraints.Max; import jakarta.validation.constraints.Min;
import lombok.Getter; import lombok.Setter;
@Getter @Setter
public class ProgressUpdateRequest { @Min(0) @Max(100) private double progress; }
