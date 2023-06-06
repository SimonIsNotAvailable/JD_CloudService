package simon.jd_cloudservice.service;

import org.springframework.core.io.Resource;

public interface FileService {
    public void uploadFile(byte[] content, String hash, String filename);
    public void deleteFile(String hash);
    public String downloadFile(String hash);
}
