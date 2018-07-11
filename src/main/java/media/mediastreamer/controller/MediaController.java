package media.mediastreamer.controller;

import lombok.extern.log4j.Log4j2;
import media.mediastreamer.exception.GenericServiceException;
import media.mediastreamer.form.UploadForm;
import media.mediastreamer.service.MediaService;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;

/**
 * Controller responsible for displaying media.
 *
 * @author  Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Controller
@Log4j2
public class MediaController {

    private FactoryBean<UploadForm> uploadFormFactory;
    private MediaService mediaService;

    public MediaController(FactoryBean<UploadForm> uploadFormFactory, MediaService mediaService) {
        this.uploadFormFactory = uploadFormFactory;
        this.mediaService = mediaService;
    }

    /**
     * It is responsible for presenting media index page.
     *
     * @return path to the media index page
     */
    @GetMapping("/")
    public String index(Model model) throws GenericServiceException {
        model.addAttribute("medias", mediaService.listMedias().collectList().block());

        return "media/index";
    }

    /**
     * It is responsible for rendering media upload page.
     *
     * @return path to the media upload page
     *
     * @throws Exception
     */
    @GetMapping("/upload")
    public String renderUpload(Model model) throws Exception {
        model.addAttribute("uploadForm", uploadFormFactory.getObject());

        return "media/upload";
    }

    /**
     * It is responsible for rendering media upload page.
     *
     * @param uploadForm object with upload media data
     * @param model holder of model attributes
     *
     * @return path to the media upload page
     */
    @PostMapping("/upload")
    public String upload(@ModelAttribute("uploadForm") UploadForm uploadForm, Model model) throws Exception {
        mediaService.upload(uploadForm.getMediaFile());
        model.addAttribute("uploadForm", uploadFormFactory.getObject());

        return "media/upload";
    }

    /**
     * Returns video with given name.
     *
     * @param name name of video
     *
     * @return stream of found video
     */
    @GetMapping("/video/{name}")
    public StreamingResponseBody getVideo(@PathVariable("name") String name) {
        return outputStream -> {
            try {
                outputStream.write(mediaService.getFile(name).readAllBytes());
            } catch (GenericServiceException e) {
                outputStream.close();
                log.log(Level.ERROR, e.getLocalizedMessage(), e);
            }
        };
    }

    /**
     * Returns image with given name.
     * @param name name of image
     *
     * @return response entity with found image
     *
     * @throws GenericServiceException
     */
    @GetMapping("/img/{name}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable("name") String name) throws GenericServiceException {
        InputStream inputStream = mediaService.getFile(name);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(inputStream));
    }
}
