package simon.jd_cloudservice.entity;

import javax.persistence.*;

import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        schema = "cloud_service",
        indexes = {@Index(columnList = "filename", name = "filename_idx")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {

    @Id
    private String hash;
    private String filename;
    private Long size;
    private LocalDateTime createdTime;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

}