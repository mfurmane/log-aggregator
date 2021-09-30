package mfurmane.log.aggregator.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = { "mfurmane.log.aggregator" })
@EnableJpaRepositories("mfurmane.log.aggregator.repositories")
@EntityScan("mfurmane.log.aggregator.dto")
public class AppConfig {
	@Autowired
	DataSourceProperties dataSourceProperties;

}