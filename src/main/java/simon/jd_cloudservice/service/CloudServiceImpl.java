package simon.jd_cloudservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.org.apache.commons.lang3.ArrayUtils;
import simon.jd_cloudservice.dto.FileDto;
import simon.jd_cloudservice.dto.FileInfoDto;
import simon.jd_cloudservice.entity.File;
import simon.jd_cloudservice.exception.FileHandlingException;
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

        // TODO : add logging

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

        } catch (Exception e) {
            throw new FileHandlingException(e.getMessage());
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
    public void deleteFile(String filename)  {
        File file = getExistingFile(filename);

        try {
            fileService.deleteFile(file.getHash());
            cloudRepository.delete(file);

        } catch (Exception e){
            throw new FileHandlingException(e.getMessage());
        }
    }

    private File getExistingFile(String filename) {
        return cloudRepository.findByFilename(filename).orElseThrow(
                () -> new WrongDataException("File " + filename + "not exists")
        ) ;
    }

    @Override
    public FileDto downloadFile(String filename) {
        File downloadedFile = getExistingFile(filename);

        try{
            String hash = downloadedFile.getHash();
            Resource content = fileService.downloadFile(hash);

        return FileDto.builder()
                .hash(hash)
                .file(content.toString())
                .build();

        } catch (Exception e) {
            throw new FileHandlingException(e.getMessage());
        }
    }

    @Override
    public void editFilename(String filename, String newName) {
        File editedFile = getExistingFile(filename);

        editedFile.setFilename(newName);
        cloudRepository.save(editedFile);
    }

    @Override
    public List<FileInfoDto> getFiles(int limit) {
        return null;
    }
}
