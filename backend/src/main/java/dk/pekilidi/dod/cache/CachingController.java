package dk.pekilidi.dod.cache;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/caches")
@Slf4j
public class CachingController {

  private final CacheManager cacheManager;

  public CachingController(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  @DeleteMapping("clear")
  public ResponseEntity<Void> clearAllCaches() {
    cacheManager.getCacheNames().forEach(cacheName -> {
      log.info("Clearing cache: {}", cacheName);
      Objects.requireNonNull(cacheManager.getCache(cacheName)).clear();
    });
    return ResponseEntity.ok().build();
  }
}