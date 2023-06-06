package simon.jd_cloudservice.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CloudRepository {
    Optional<Object> findByFilename(String filename);
}
