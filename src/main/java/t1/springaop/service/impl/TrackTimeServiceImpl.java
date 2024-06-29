package t1.springaop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import t1.springaop.data.repository.TrackTimeRepository;
import t1.springaop.mapper.TrackTimeMapper;
import t1.springaop.model.dto.TrackTimeDto;
import t1.springaop.model.entity.TrackTimeEntity;
import t1.springaop.service.AsyncTrackTimeService;
import t1.springaop.service.StatisticTrackTimeService;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.LongStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrackTimeServiceImpl implements AsyncTrackTimeService, StatisticTrackTimeService {

    /**
     * Переменная для работы с репозиторием
     */
    private final TrackTimeRepository trackTimeRepository;

    /**
     * Асинхронное добавление записи в базу данных.
     *
     * @param trackTime - Дто с данными.
     * @return - уникальный айди.
     */
    @Override
    @Transactional
    public CompletableFuture<UUID> createAsyncTrackTime(TrackTimeDto trackTime) {
        return CompletableFuture.supplyAsync(() -> insertTrackTime(trackTime));
    }

    /**
     * Метод возвращает среднее время выполнения всех методов в бд методов.
     *
     * @return - среднее время выполнения
     */
    @Override
    public Double getAverageMethodExecutionTime() {
        return trackTimeRepository.findAll()
                .stream()
                .flatMapToLong(trackTimeEntity -> LongStream.of(trackTimeEntity.getTotalExecutionTime()))
                .average()
                .orElseThrow();
    }

    /**
     * Метод возвращает общее время выполнения всех методов в бд.
     *
     * @return - общее время выполнения
     */
    @Override
    public Long getTotalMethodExecutionTime() {
        return trackTimeRepository.findAll()
                .stream()
                .mapToLong(TrackTimeEntity::getTotalExecutionTime)
                .sum();
    }

    /**
     * Метод по созданию записи в бд об исполняемом методе.
     *
     * @param createTrackTime - дто с информацией о методе.
     */
    private UUID insertTrackTime(TrackTimeDto createTrackTime) {
        return Optional.of(createTrackTime)
                .map(TrackTimeMapper.INSTANCE::toEntity)
                .map(trackTimeRepository::save)
                .map(TrackTimeEntity::getId)
                .orElseThrow();
    }
}
