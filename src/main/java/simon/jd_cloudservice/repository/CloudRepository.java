package simon.jd_cloudservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simon.jd_cloudservice.entity.File;

import java.util.Optional;

@Repository
public interface CloudRepository extends JpaRepository<File, String> {
    Optional<File> findByFilename(String filename, String currentUserLogin);
    Slice<File> findAllByUser_Login(String login, Pageable pageable);
}
