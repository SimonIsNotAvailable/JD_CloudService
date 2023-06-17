package simon.jd_cloudservice.service;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface FileService {
    public void uploadFile(byte[] content, String hash, String filename) throws IOException;

    public void deleteFile(String hash) throws IOException;

    public Resource downloadFile(String hash);
}
