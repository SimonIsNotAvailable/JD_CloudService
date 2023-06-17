package simon.jd_cloudservice.mapper;

import org.springframework.stereotype.Component;
import simon.jd_cloudservice.dto.FileInfoDto;
import simon.jd_cloudservice.entity.File;

@Component()
public interface FileInfoMapper {
    FileInfoDto fileToFileInfoDto(File file);
}
