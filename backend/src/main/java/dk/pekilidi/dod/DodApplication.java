package dk.pekilidi.dod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableLoadTimeWeaving
@EnableCaching //(mode = AdviceMode.ASPECTJ)
public class DodApplication {

  public static void main(String[] args) {
    SpringApplication.run(DodApplication.class, args);
  }
}


