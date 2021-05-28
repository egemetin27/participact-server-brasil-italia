package it.unibo.paserver.config;

import br.com.bergmannsoft.config.Config;
import com.jolbox.bonecp.BoneCPDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "it.unibo.paserver.service",
        "it.unibo.paserver.repository",
        "it.unibo.paserver.domain.support",
        "it.unibo.paserver.manteinance",
        "it.unibo.paserver.gamificationlogic",
        "it.unibo.tper.repository",
        "it.unibo.tper.service",
        "it.unibo.tper.mantainance",
        "br.gov.cgu.eouv.domain",
        "br.gov.cgu.eouv.repository",
        "br.gov.cgu.eouv.service",
        "br.com.bergmannsoft.config"})
public class InfrastructureContextConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(InfrastructureContextConfiguration.class);

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Critical error", e);
        }
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public FactoryBean<EntityManagerFactory> entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setJpaVendorAdapter(jpaVendorAdapter());
        return emfb;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        txManager.setDataSource(dataSource);
        return txManager;
    }

    @SuppressWarnings("deprecation")
    @Bean
    public DataSource dataSource() {
        BoneCPDataSource ds = new BoneCPDataSource();
        ds.setIdleConnectionTestPeriod(60, TimeUnit.SECONDS);
        ds.setMaxConnectionAge(240, TimeUnit.SECONDS);
        ds.setPartitionCount(3);
        ds.setMaxConnectionsPerPartition(20);
        ds.setMinConnectionsPerPartition(1);
        ds.setAcquireIncrement(1);
        ds.setStatementsCacheSize(100);
        ds.setReleaseHelperThreads(3);

        // ds.setJdbcUrl("jdbc:postgresql://200.98.147.60/participact_db"/*?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory"*/);
        // ds.setJdbcUrl("jdbc:postgresql://127.0.0.1/participact_db"/*?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory"*/);
        ds.setJdbcUrl(Config.DATABASE_HOST/* ?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory" */);
        ds.setUsername(Config.DATABASE_USER);
        ds.setPassword(Config.DATABASE_PASS);
        return ds;
    }

}
