package media.mediastreamer.listeners;

import media.mediastreamer.exception.GenericServiceException;
import media.mediastreamer.service.MinioFileService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import static org.mockito.Mockito.*;

/**
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public class ApplicationStartupTest {

    @Mock
    private MinioFileService minioFileService;

    @InjectMocks
    private ApplicationStartup applicationStartup;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void onApplicationEvent() throws GenericServiceException {
        applicationStartup.onApplicationEvent(mock(ApplicationReadyEvent.class));

        verify(minioFileService, times(1)).init();
    }

    @Test(expected = RuntimeException.class)
    public void onApplicationEventException() throws Exception {
        doThrow(GenericServiceException.class).when(minioFileService).init();

        applicationStartup.onApplicationEvent(mock(ApplicationReadyEvent.class));
    }
}
