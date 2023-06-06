package simon.jd_cloudservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.org.apache.commons.lang3.ArrayUtils;
import simon.jd_cloudservice.dto.FileDto;
import simon.jd_cloudservice.dto.FileInfoDto;
import simon.jd_cloudservice.entity.File;
import simon.jd_cloudservice.exception.WrongDataException;
import simon.jd_cloudservice.repository.CloudRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudServiceImpl implements CloudService{
    private final CloudRepository cloudRepository;
    private final FileService fileService;
    @Override
    public void saveFile(String filename, MultipartFile file) {
        try{
            if (cloudRepository.findByFilename(filename).isPresent()) {
                throw new WrongDataException(String.format("File %s already exists", filename));
            }

            if (file == null) {
                throw new WrongDataException("File is not attached to request");
            }

            File uploadedFile = createFileInfo(filename, file);

            fileService.uploadFile(file.getBytes(), uploadedFile.getHash(), filename);
            cloudRepository.save(uploadedFile);

        } catch (Exception Exception) {


        }
    }

    private File createFileInfo(String filename, MultipartFile file) throws IOException {
        LocalDateTime createdTime = LocalDateTime.now();

        String hash = UUID.nameUUIDFromBytes(
                ArrayUtils.addAll(file.getBytes(), createdTime.toString().getBytes())).toString();

        return File.builder()
                .hash(hash)
                .filename(filename)
                .size(file.getSize())
                .createdTime(createdTime)
                .build();
    }

    @Override
    public void deleteFile(String filename) {

    }

    @Override
    public FileDto downloadFile(String filename) {
        return null;
    }

    @Override
    public void editFilename(String filename, String newName) {

    }

    @Override
    public List<FileInfoDto> getFiles(int limit) {
        return null;
    }
}
