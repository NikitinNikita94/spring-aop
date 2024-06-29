package t1.springaop.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import t1.springaop.exception.TrackTimeException;
import t1.springaop.model.dto.TrackTimeDto;
import t1.springaop.service.AsyncTrackTimeService;
import t1.springaop.util.TrackTimeProperties;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@Aspect
@RequiredArgsConstructor
public class TrackTimeExecutionAspect {

    private final AsyncTrackTimeService trackTimeService;
    private final TrackTimeProperties trackTimeProperties;

    @Around("@annotation(t1.springaop.aop.annotation.TrackTime)")
    public Object trackTimeExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        if (trackTimeProperties.isLoggingTrackTime()) {
            return addTrackTime(joinPoint);
        }
        return joinPoint.proceed();
    }

    @Around("@annotation(t1.springaop.aop.annotation.TrackAsyncTime)")
    public Object trackTimeExecutionAsyncTime(ProceedingJoinPoint joinPoint) throws Throwable {
        if (trackTimeProperties.isLoggingTrackTime()) {
            return CompletableFuture.supplyAsync(() -> addTrackTime(joinPoint)).join();
        }
        return joinPoint.proceed();
    }

    private Object addTrackTime(ProceedingJoinPoint joinPoint) {
        log.info("Метод с названием={} начал работу.", joinPoint.getSignature().getName());
        StopWatch timeMeter = new StopWatch();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        try {
            timeMeter.start();
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new TrackTimeException("В методе с названием=" + methodName + ", произошла ошибка!!", e);
        } finally {
            timeMeter.stop();
            trackTimeService.createAsyncTrackTime(
                    TrackTimeDto.builder()
                            .className(className)
                            .methodName(methodName)
                            .totalExecutionTime(timeMeter.getTime())
                            .createAt(LocalDate.now())
                            .build()
            );
            log.info("Метод с названием={}, отработал {} ms", methodName, timeMeter.getTime());
        }
    }
}
