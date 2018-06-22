package media.mediastreamer.service.impl;

import media.mediastreamer.exception.GenericServiceException;
import media.mediastreamer.service.FileService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

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
}