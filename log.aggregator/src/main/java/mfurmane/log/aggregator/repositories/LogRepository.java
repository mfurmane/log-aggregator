package mfurmane.log.aggregator.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mfurmane.log.aggregator.dto.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

	List<Log> findAllByTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

}
