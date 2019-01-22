package media.mediastreamer.factory;

import media.mediastreamer.domain.MediaType;
import media.mediastreamer.processor.ImageExtractor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public class DefaultImageExtractorFactoryTest {

    @Mock
    ImageExtractor imageExtractorFromMusic;

    @Mock
    ImageExtractor imageExtractorFromVideo;

    @InjectMocks
    DefaultImageExtractorFactory imageExtractorFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getExtractorForVideoMediaType() {
        ImageExtractor imageExtractor = imageExtractorFactory.getExtractorForMediaType(MediaType.VIDEO);

        assertThat(imageExtractor, equalTo(imageExtractorFromVideo));
    }

    @Test
    public void getExtractorForMusicMediaType() {
        ImageExtractor imageExtractor = imageExtractorFactory.getExtractorForMediaType(MediaType.MUSIC);

        assertThat(imageExtractor, equalTo(imageExtractorFromMusic));
    }
}