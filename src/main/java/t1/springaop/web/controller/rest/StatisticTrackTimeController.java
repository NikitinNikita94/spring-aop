package t1.springaop.web.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import t1.springaop.aop.annotation.TrackAsyncTime;
import t1.springaop.aop.annotation.TrackTime;
import t1.springaop.model.dto.TrackTimeDto;
import t1.springaop.service.impl.TrackTimeServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/statistic")
@RequiredArgsConstructor
@Tag(name = "Статистика и создание", description = "Эндпоинты для работы со статистикой и созданию времени методов ")
public class StatisticTrackTimeController {

    private final TrackTimeServiceImpl timeService;

    @GetMapping("/average")
    @Operation(
            summary = "Получить среднее время выполнения методов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Double.class)
                            )
                    )
            }
    )
    @TrackTime
    public Double trackTimeAverage() {
        return timeService.getAverageMethodExecutionTime();
    }

    @GetMapping("/total")
    @Operation(
            summary = "Получить общее время выполнения методов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Long.class)
                            )
                    )
            }
    )
    @TrackTime
    public Long trackTimeTotal() {
        return timeService.getTotalMethodExecutionTime();
    }

    @PostMapping
    @TrackAsyncTime
    @Operation(
            summary = "Создать новый объект время работы метода",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UUID.class)
                            )
                    )
            }
    )
    public UUID createTrackTime(@RequestBody @Validated TrackTimeDto trackTimeDto) {
        return timeService.createAsyncTrackTime(trackTimeDto).join();
    }
}
