package t1.springaop.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
public record TrackTimeDto(
        @NotBlank @Size(min = 3, max = 64) String className,
        @NotBlank @Size(min = 3, max = 64) String methodName,
        @PositiveOrZero Long totalExecutionTime,
        @DateTimeFormat(pattern = "YYYY-MM-DD") LocalDate createAt) {
}
