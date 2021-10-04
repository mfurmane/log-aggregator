package mfurmane.log.aggregator.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mfurmane.log.aggregator.dto.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

	@Query(value = "SELECT log FROM Log log WHERE log.application = :application and ((log.date = :startDate and log.time >= :startTime) or log.date > :startDate) and (log.date < :endDate or (log.date = :endDate and log.time <= :endTime))")
	List<Log> findAllByApplicationAndDatetimeBetween(String application, LocalTime startTime, LocalTime endTime,
			LocalDate startDate, LocalDate endDate, Pageable pageable);

	@Query(value = "SELECT log FROM Log log WHERE log.application = :application and log.date = null and log.time >= :startTime and log.time <= :endTime")
	List<Log> findAllByApplicationAndTimeBetween(String application, LocalTime startTime, LocalTime endTime,
			Pageable pageable);

	@Query(value = "SELECT log FROM Log log WHERE log.application = :application and log.time = null and log.date >= :startDate and log.date <= :endDate")
	List<Log> findAllByApplicationAndDateBetween(String application, LocalDate startDate, LocalDate endDate,
			Pageable pageable);

}
