package media.mediastreamer.controller;

import media.mediastreamer.form.UploadForm;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller responsible for displaying media.
 *
 * @author  Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Controller
public class MediaController {

    private FactoryBean<UploadForm> uploadFormFactory;

    public MediaController(FactoryBean<UploadForm> uploadFormFactory) {
        this.uploadFormFactory = uploadFormFactory;
    }

    /**
     * It is responsible for presenting media index page.
     *
     * @return path to the media index page
     */
    @GetMapping("/")
    public String index() {
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
    public String upload(@ModelAttribute("uploadForm") UploadForm uploadForm, Model model) {
        model.addAttribute("uploadForm", uploadForm);

        return "media/upload";
    }
}
