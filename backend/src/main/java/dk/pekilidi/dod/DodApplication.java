package dk.pekilidi.dod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DodApplication {

/*
  @Order(Ordered.HIGHEST_PRECEDENCE)
  private class RetryableDataSourceBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      if (bean instanceof DataSource) {
        bean = new RetryableDataSource((DataSource) bean);
      }
      return bean;
    }
  }
*/

  public static void main(String[] args) {
    SpringApplication.run(DodApplication.class, args);
  }

/*
  @Bean
  public BeanPostProcessor dataSouceWrapper() {
    return new RetryableDataSourceBeanPostProcessor();
  }
*/
}


/*
class RetryableDataSource extends AbstractDataSource {

  private DataSource delegate;

  public RetryableDataSource(DataSource delegate) {
    this.delegate = delegate;
  }

  @Override
  @Retryable(maxAttempts = 10, backoff = @Backoff(multiplier = 2.3, maxDelay = 30000))
  public Connection getConnection() throws SQLException {
    return delegate.getConnection();
  }

  @Override
  @Retryable(maxAttempts = 10, backoff = @Backoff(multiplier = 2.3, maxDelay = 30000))
  public Connection getConnection(String username, String password) throws SQLException {
    return delegate.getConnection(username, password);
  }
}
*/

