package media.mediastreamer.service.impl;

import lombok.extern.log4j.Log4j2;
import media.mediastreamer.dto.FileResult;
import media.mediastreamer.exception.GenericServiceException;
import media.mediastreamer.service.FileService;
import media.mediastreamer.service.MediaService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link MediaService}
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Service
@Log4j2
public class DefaultMediaService implements MediaService {
    private FileService fileService;

    public DefaultMediaService(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upload(MultipartFile media) throws GenericServiceException {
        fileService.putFile(media);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getFile(String name) throws GenericServiceException {
        return fileService.getFile(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> listFiles() throws GenericServiceException {
        return fileService.listFiles().stream()
                .map(FileResult::getObjectName)
                .collect(Collectors.toSet());
    }

}
