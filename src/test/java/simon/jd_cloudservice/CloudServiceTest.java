package simon.jd_cloudservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import simon.jd_cloudservice.entity.File;
import simon.jd_cloudservice.exception.WrongDataException;
import simon.jd_cloudservice.mapper.FileInfoMapper;
import simon.jd_cloudservice.repository.CloudRepository;

import com.google.common.truth.Truth;
import simon.jd_cloudservice.service.CloudServiceImpl;
import simon.jd_cloudservice.service.FileService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {FileInfoMapper.class})

public class CloudServiceTest {
    @MockBean
    private CloudRepository cloudRepository;
    @MockBean
    private FileService fileService;
    @InjectMocks
    private CloudServiceImpl cloudService;
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
        when(cloudRepository.findByFilename(mockedFilename)).thenReturn(Optional.empty());

        Exception ex = Assertions.assertThrows(WrongDataException.class,
                () -> cloudService.deleteFile(mockedFilename));

        Truth.assertThat(ex).hasMessageThat().contains("not exist");
    }

    @Test
    public void testDeleteFileWhenCorrectDataThenDeleteFile() throws IOException {
        when(cloudRepository.findByFilename(mockedFilename)).thenReturn(Optional.of(mockedFileFromDb));

        cloudService.deleteFile(mockedFilename);

        verify(fileService, times(1)).deleteFile(mockedHash);
        verify(cloudRepository, times(1)).delete(mockedFileFromDb);
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
