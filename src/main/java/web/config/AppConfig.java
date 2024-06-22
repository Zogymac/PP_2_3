package web.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = "web")
public class AppConfig {

   @Autowired
   private Environment env;

   @Bean
   public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getProperty("db.driver"));
      dataSource.setUrl(env.getProperty("db.url"));
      dataSource.setUsername(env.getProperty("db.username"));
      dataSource.setPassword(env.getProperty("db.password"));
      return dataSource;
   }

//   @Bean
//   public LocalSessionFactoryBean getSessionFactory() {
//      LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
//      factoryBean.setDataSource(getDataSource());
//
//      Properties props=new Properties();
//      props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//      props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//
//      factoryBean.setHibernateProperties(props);
//      factoryBean.setAnnotatedClasses(User.class, Service.class, UserController.class);
//      return factoryBean;
//   }

   @Bean
   public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(getDataSource());
      em.setPackagesToScan("web.model", "web.service", "web.controller");
      em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

      Properties props = new Properties();
      props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
      props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
      em.setJpaProperties(props);

      return em;
   }

//   @Bean
//   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//      em.setDataSource(getDataSource());
//      em.setPackagesToScan("web.model", "web.service", "web.controller");
//      em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//      Properties props = new Properties();
//      props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//      props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//      em.setJpaProperties(props);
//
//      return em;
//   }

   @Bean
   public JpaTransactionManager transactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
//      transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
      transactionManager.setEntityManagerFactory(getEntityManagerFactory().getObject());
//      transactionManager.setEntityManagerFactory(getSessionFactory().getObject());
      return transactionManager;
   }

/*   @Bean
   public HibernateTransactionManager getTransactionManager() {
      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
      transactionManager.setSessionFactory(getSessionFactory().getObject());
      return transactionManager;
   }*/
}
