package simon.jd_cloudservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class FileServiceImpl implements FileService{

    @Value("${storage.path}")
    private String storage;

    @Override
    public void uploadFile(byte[] content, String hash, String filename) throws IOException {
        Path path = Paths.get(storage, hash);
        Path file = Files.createFile(path);

        try (FileOutputStream stream = new FileOutputStream(file.toString())) {
            stream.write(content);
        }
    }

    @Override
    public Resource downloadFile(String hash) {
        Path path = Paths.get(storage, hash);
        return new PathResource(path);
    }
    @Override
    public void deleteFile(String hash) throws IOException {
        Path path = Paths.get(storage, hash);
        Files.delete(path);
    }
}
