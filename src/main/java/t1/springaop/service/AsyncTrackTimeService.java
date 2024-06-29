package t1.springaop.service;

import t1.springaop.model.dto.TrackTimeDto;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Интерфейс для асинхронного сохранения времени выполнения методов в бд.
 */
public interface AsyncTrackTimeService {

    /**
     * Асинхронное добавление записи в базу данных.
     *
     * @param trackTime - Дто с данными.
     * @return - уникальный айди.
     */
    CompletableFuture<UUID> createAsyncTrackTime(TrackTimeDto trackTime);
}
