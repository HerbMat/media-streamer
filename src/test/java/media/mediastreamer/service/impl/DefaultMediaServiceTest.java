package media.mediastreamer.service.impl;

import media.mediastreamer.dto.FileResult;
import media.mediastreamer.exception.GenericServiceException;
import media.mediastreamer.service.FileService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public class DefaultMediaServiceTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private DefaultMediaService mediaService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void upload() throws GenericServiceException {
        mediaService.upload(mock(MultipartFile.class));

        verify(fileService, times(1)).putFile(any(MultipartFile.class));
    }

    @Test(expected = GenericServiceException.class)
    public void uploadFails() throws GenericServiceException {
        doThrow(GenericServiceException.class).when(fileService).putFile(any());

        mediaService.upload(mock(MultipartFile.class));
    }

    @Test
    public void getFile() throws GenericServiceException {
        InputStream inputStream = mock(InputStream.class);
        when(fileService.getFile(anyString())).thenReturn(inputStream);

        InputStream result = mediaService.getFile("test");

        assertThat(result, equalTo(inputStream));
    }

    @Test(expected = GenericServiceException.class)
    public void getFileFails() throws GenericServiceException {
        doThrow(GenericServiceException.class).when(fileService).getFile(anyString());

        mediaService.getFile("name");
    }

    @Test
    public void listFiles() throws GenericServiceException {

        Collection<FileResult> fileResults = List.of(mockFileResult("test.mp4"), mockFileResult("video.mp4"));
        when(fileService.listFiles()).thenReturn(fileResults);

        Collection<String> fileNames = mediaService.listFiles();

        assertThat(fileNames, hasSize(2));
        assertThat(fileNames, hasItem("test.mp4"));
        assertThat(fileNames, hasItem("video.mp4"));
    }

    private FileResult mockFileResult(String name) {
        FileResult fileResult = mock(FileResult.class);
        when(fileResult.getObjectName()).thenReturn(name);

        return fileResult;
    }
}
