package simon.jd_cloudservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import simon.jd_cloudservice.dto.FileDto;
import simon.jd_cloudservice.dto.FileInfoDto;
import simon.jd_cloudservice.entity.File;
import simon.jd_cloudservice.exception.FileHandlingException;
import simon.jd_cloudservice.exception.WrongDataException;
import simon.jd_cloudservice.mapper.FileInfoMapper;
import simon.jd_cloudservice.repository.CloudRepository;
import simon.jd_cloudservice.repository.UserRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CloudServiceImpl implements CloudService {
    private final CloudRepository cloudRepository;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final FileInfoMapper mapper;

    @Override
    public void saveFile(String filename, MultipartFile file,  String currentUserLogin) {

        try {
            log.info("Checking the existence of file " + filename);
            if (cloudRepository.findByFilename(filename, currentUserLogin).isPresent()) {
                throw new WrongDataException(String.format("File %s already exists", filename));
            }

            if (file == null) {
                throw new WrongDataException("File is not attached to request");
            }

            File uploadedFile = createFileInfo(filename, file, currentUserLogin);
            log.info("Uploading file " + filename);
            fileService.uploadFile(file.getBytes(), uploadedFile.getHash(), filename);
            log.info("Saving file info of " + filename);
            cloudRepository.save(uploadedFile);
            log.info(filename + " File info saved");

        } catch (Exception e) {
            throw new FileHandlingException(e.getMessage());
        }
    }

    private File createFileInfo(String filename, MultipartFile file, String currentUserLogin) throws IOException {
        LocalDateTime createdTime = LocalDateTime.now();

        String hash = UUID.nameUUIDFromBytes(
                ArrayUtils.addAll(file.getBytes(), createdTime.toString().getBytes())).toString();

        return File.builder()
                .hash(hash)
                .filename(filename)
                .size(file.getSize())
                .createdTime(createdTime)
                .user(
                        userRepository
                                .findByLogin(currentUserLogin)
                                .orElseThrow(()-> new WrongDataException("Unable to save. User not found")))
                .build();
    }

    @Override
    public void deleteFile(String filename, String currentUserLogin) {
        File file = getExistingFile(filename, currentUserLogin);

        try {
            log.info("Deleting file " + filename);
            fileService.deleteFile(file.getHash());
            log.info("File deleted " + filename);
            log.info("Deleting file info of " + filename);
            cloudRepository.delete(file);
            log.info("File info deleted " + filename);

        } catch (Exception e) {
            throw new FileHandlingException(e.getMessage());
        }
    }

    private File getExistingFile(String filename, String currentUserLogin) {
        return cloudRepository.findByFilename(filename, currentUserLogin).orElseThrow(
                () -> new WrongDataException("File " + filename + " not exists")
        );
    }

    @Override
    public FileDto downloadFile(String filename, String currentUserLogin) {
        File downloadedFile = getExistingFile(filename, currentUserLogin);

        try {
            log.info("Downloading file " + filename);
            String hash = downloadedFile.getHash();
            Resource content = fileService.downloadFile(hash);
            log.info(filename + " downloaded");

            return FileDto.builder()
                    .hash(hash)
                    .file(content.toString())
                    .build();

        } catch (Exception e) {
            throw new FileHandlingException(e.getMessage());
        }
    }

    @Override
    public void editFilename(String filename, String newName, String currentUserLogin) {
        File editedFile = getExistingFile(filename, currentUserLogin);

        editedFile.setFilename(newName);
        cloudRepository.save(editedFile);
    }

    @Override
    public List<FileInfoDto> getFiles(int limit, String currentUserLogin) {
        log.info("Getting files list");
        return cloudRepository.findAllByUser_Login(currentUserLogin, Pageable.ofSize(limit))
                .map(mapper::fileToFileInfoDto)
                .toList();
    }

}
