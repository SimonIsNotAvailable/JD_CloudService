package simon.jd_cloudservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import simon.jd_cloudservice.entity.File;
import simon.jd_cloudservice.exception.WrongDataException;
import simon.jd_cloudservice.repository.CloudRepository;
import simon.jd_cloudservice.service.CloudService;

import com.google.common.truth.Truth;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class CloudServiceTest {

    private CloudRepository cloudRepository;
    private CloudService cloudService;
    private String mockedFilename;
    private File mockedFileFromDb;
    private MockMultipartFile mockedFile;
    private String mockedHash;
    private Resource mockedResource;

    @BeforeEach
    public void setUp() {
        this.mockedFilename = "Test";
        this.mockedHash = "12345";
        this.mockedFile = new MockMultipartFile("Test", new byte[]{1, 2, 3});
        this.mockedFileFromDb = new File("12345", "Test", 100L, LocalDateTime.now());
        this.mockedResource = new PathResource("/test.txt");
    }

    @Test
    public void testSaveFileWhenExistedFilenameThenThrowEx() {
        when(cloudRepository.findByFilename(mockedFilename)).thenReturn(Optional.of(mockedFileFromDb));

        Exception ex = Assertions.assertThrows(WrongDataException.class,
                () -> cloudService.saveFile(mockedFilename, mockedFile));

        Truth.assertThat(ex).hasMessageThat().contains("already exists");
    }

    @Test
    public void testSaveFileWhenFileIsNotAttachedThenThrowEx() {
        when(cloudRepository.findByFilename(mockedFilename)).thenReturn(Optional.of(mockedFileFromDb));

        Exception ex = Assertions.assertThrows(WrongDataException.class,
                () -> cloudService.saveFile(mockedFilename, mockedFile));

        Truth.assertThat(ex).hasMessageThat().contains("already exists");
    }

    @Test
    public void testDeleteFileWhenNotExistingFileThenThrowEx() {

    }

    @Test
    public void testDeleteFileWhenCorrectDataThenDeleteFile(){

    }
    @Test
    public void testDownloadFileWhenNotExistingFileThenThrowEx(){

    }

    @Test
    public void testDownloadFileWhenCorrectDataThenReturnFileDto() {

    }

    @Test
    public void testEditFilenameWhenCorrectDataThenSaveWithNewName() {

    }

}
