package simon.jd_cloudservice.service;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface FileService {
    void uploadFile(byte[] content, String hash, String filename) throws IOException;

    void deleteFile(String hash) throws IOException;

    Resource downloadFile(String hash);
}
