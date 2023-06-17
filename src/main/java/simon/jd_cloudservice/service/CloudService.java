package simon.jd_cloudservice.service;

import org.springframework.web.multipart.MultipartFile;
import simon.jd_cloudservice.dto.FileDto;
import simon.jd_cloudservice.dto.FileInfoDto;

import java.io.IOException;
import java.util.List;

public interface CloudService {

    void saveFile(String filename, MultipartFile file);

    void deleteFile(String filename);

    FileDto downloadFile(String filename);

    void editFilename(String filename, String newName);

    List<FileInfoDto> getFiles(int limit);
}
