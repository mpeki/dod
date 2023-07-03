package dk.pekilidi.dod.cache;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/caches")
@CrossOrigin
@Slf4j
public class CachingController {
  @Autowired
  private CacheManager cacheManager;

  @DeleteMapping("clear")
  public ResponseEntity<Void> clearAllCaches() {
    cacheManager
        .getCacheNames()
        .forEach(cacheName -> {
          log.info("Clearing cache: {}", cacheName);
          Objects.requireNonNull(cacheManager.getCache(cacheName)).clear();
        });
    return ResponseEntity.ok().build();
  }
}