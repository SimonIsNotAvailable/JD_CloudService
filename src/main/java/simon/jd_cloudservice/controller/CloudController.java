package simon.jd_cloudservice.controller;
import lombok.*;
import org.springframework.web.bind.annotation.RestController;
import simon.jd_cloudservice.service.CloudService;

@RestController
@RequiredArgsConstructor
public class CloudController {
    private CloudService cloudService;


}