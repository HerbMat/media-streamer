package media.mediastreamer.service.impl;

import media.mediastreamer.domain.Media;
import media.mediastreamer.domain.MediaType;
import media.mediastreamer.exception.GenericServiceException;
import media.mediastreamer.factory.MediaFactory;
import media.mediastreamer.processor.ImageExtractor;
import media.mediastreamer.repositories.MediaRepository;
import media.mediastreamer.service.FileService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public class DefaultMediaServiceTest {

    @Mock
    private FileService fileService;

    @Mock
    private ImageExtractor imageExtractor;

    @Mock
    private MediaRepository mediaRepository;

    @Mock
    private MediaFactory mediaFactory;

    @InjectMocks
    private DefaultMediaService mediaService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void upload() throws Exception {
        MultipartFile imgFile = mock(MultipartFile.class);
        MultipartFile videoFile = mock(MultipartFile.class);
        when(imgFile.getOriginalFilename()).thenReturn("img.png");
        when(videoFile.getOriginalFilename()).thenReturn("video.mp4");

        when(imageExtractor.extractImage(any(MultipartFile.class))).thenReturn(imgFile);
        Media media = mock(Media.class);
        when(mediaFactory.getObject()).thenReturn(media);
        when(mediaRepository.save(any(Media.class))).thenReturn(Mono.just(media));

        mediaService.upload(videoFile);

        verify(fileService, times(1)).putFile(videoFile);
        verify(fileService, times(1)).putFile(imgFile);
        verify(media, times(1)).setImgName("img.png");
        verify(media, times(1)).setMediaName("video.mp4");
        verify(media, times(1)).setMediaType(MediaType.VIDEO);
        verify(mediaRepository, times(1)).save(media);
    }

    @Test(expected = GenericServiceException.class)
    public void uploadFails() throws GenericServiceException {
        doThrow(GenericServiceException.class).when(fileService).putFile(any());

        mediaService.upload(mock(MultipartFile.class));
    }

    @Test(expected = GenericServiceException.class)
    public void uploadExtactImgFails() throws Exception {
        when(imageExtractor.extractImage(any())).thenThrow(IOException.class);

        mediaService.upload(mock(MultipartFile.class));
    }

    @Test(expected = GenericServiceException.class)
    public void uploadSaveMediaDataFails() throws Exception {
        MultipartFile imgFile = mock(MultipartFile.class);
        MultipartFile videoFile = mock(MultipartFile.class);
        when(imgFile.getOriginalFilename()).thenReturn("img.png");
        when(videoFile.getOriginalFilename()).thenReturn("video.mp4");

        when(imageExtractor.extractImage(any(MultipartFile.class))).thenReturn(imgFile);
        Media media = mock(Media.class);
        when(mediaFactory.getObject()).thenReturn(media);

        when(mediaRepository.save(any())).thenThrow(IllegalArgumentException.class);

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
    public void listFiles() {

        Flux<Media> mediaFlux = mock(Flux.class);
        when(mediaRepository.findAll()).thenReturn(mediaFlux);

        Flux<Media> result = mediaService.listMedias();

        assertThat(result, equalTo(mediaFlux));
    }
}
