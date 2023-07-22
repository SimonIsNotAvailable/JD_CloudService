package simon.jd_cloudservice.controller;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import simon.jd_cloudservice.dto.FileDto;
import simon.jd_cloudservice.dto.FileInfoDto;
import simon.jd_cloudservice.service.CloudService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CloudController {
    private final CloudService cloudService;

    @PostMapping("/file")
    @ResponseStatus(HttpStatus.OK)
    public void uploadFile(@RequestParam String name, @RequestBody MultipartFile file) {
        cloudService.saveFile(name, file, getCurrentUserLogin());
    }

    @GetMapping("/file")
    @ResponseStatus(HttpStatus.OK)
    public FileDto downloadFile(@RequestParam String name) {
        return cloudService.downloadFile(name, getCurrentUserLogin());
    }

    @DeleteMapping("/file")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFile(@RequestParam String name) {
        cloudService.deleteFile(name, getCurrentUserLogin());
    }

    @PutMapping("/file")
    @ResponseStatus(HttpStatus.OK)
    public void editFile(@RequestParam String name, @RequestBody String newName) {
        cloudService.editFilename(name, newName, getCurrentUserLogin());
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<FileInfoDto> getFilesList(@RequestParam int limit) {
        return cloudService.getFiles(limit, getCurrentUserLogin());
    }

    private String getCurrentUserLogin()  {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}