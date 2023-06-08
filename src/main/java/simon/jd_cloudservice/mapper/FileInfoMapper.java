package simon.jd_cloudservice.mapper;

import simon.jd_cloudservice.dto.FileInfoDto;
import simon.jd_cloudservice.entity.File;

public interface FileInfoMapper {
    FileInfoDto fileToFileInfoDto(File file);

}
