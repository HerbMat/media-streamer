package media.mediastreamer.processor.impl;

import de.odysseus.ithaka.audioinfo.AudioInfo;
import de.odysseus.ithaka.audioinfo.mp3.ID3v2Exception;
import de.odysseus.ithaka.audioinfo.mp3.MP3Exception;
import de.odysseus.ithaka.audioinfo.mp3.MP3Info;
import lombok.extern.log4j.Log4j2;
import media.mediastreamer.exception.GenericServiceException;
import media.mediastreamer.exception.NoAudioInfoException;
import media.mediastreamer.multipart.MultipartImage;
import media.mediastreamer.processor.ImageExtractor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Default implementation of {@link ImageExtractor} for png images.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Log4j2
public class MusicToPNGImageExtractor implements ImageExtractor {

    @Override
    public MultipartFile extractImage(MultipartFile file) throws IOException, GenericServiceException {
        AudioInfo audioInfo;
        try {
            audioInfo = new MP3Info(new BufferedInputStream(new ByteArrayInputStream(file.getInputStream().readAllBytes())), file.getSize());
        } catch (ID3v2Exception|MP3Exception e) {
            log.log(Level.ERROR, e.getLocalizedMessage());
            throw new GenericServiceException(e.getLocalizedMessage(), e);
        }
        byte[] cover = audioInfo.getCover();
        if(ArrayUtils.isEmpty(cover)) {
            throw new NoAudioInfoException("There are no Tag 3v2 information");
        }

        return new MultipartImage(FilenameUtils.removeExtension(file.getOriginalFilename()) + ".png", MediaType.IMAGE_PNG_VALUE, cover.length, new ByteArrayInputStream(cover));
    }
}
