package simon.jd_cloudservice.service;

import org.springframework.web.multipart.MultipartFile;
import simon.jd_cloudservice.dto.FileDto;
import simon.jd_cloudservice.dto.FileInfoDto;

import java.util.List;

public interface CloudService {

    void saveFile(String filename, MultipartFile file, String currentUserLogin);

    void deleteFile(String filename, String currentUserLogin);

    FileDto downloadFile(String filename, String currentUserLogin);

    void editFilename(String filename, String newName, String currentUserLogin);

    List<FileInfoDto> getFiles(int limit, String currentUserLogin);
}
