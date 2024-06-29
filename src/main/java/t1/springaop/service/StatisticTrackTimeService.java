package t1.springaop.service;

/**
 * Интерфейс для работы со статистикой выполнения методов.
 */
public interface StatisticTrackTimeService {

    /**
     * Метод возвращает среднее время выполнения всех методов в бд методов.
     *
     * @return - среднее время выполнения
     */
    Double getAverageMethodExecutionTime();

    /**
     * Метод возвращает общее время выполнения всех методов в бд.
     *
     * @return - общее время выполнения
     */
    Long getTotalMethodExecutionTime();
}
