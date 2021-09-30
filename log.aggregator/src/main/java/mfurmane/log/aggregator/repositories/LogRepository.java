package mfurmane.log.aggregator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mfurmane.log.aggregator.dto.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}
