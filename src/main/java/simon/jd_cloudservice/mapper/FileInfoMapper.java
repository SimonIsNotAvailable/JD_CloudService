package simon.jd_cloudservice.mapper;

import simon.jd_cloudservice.dto.FileInfoDto;
import simon.jd_cloudservice.entity.File;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileInfoMapper {
    FileInfoDto fileToFileInfoDto(File file);
    File fileInfoDTOToFile(FileInfoDto fileInfoDTO);
}