package media.mediastreamer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsible for displaying media.
 *
 * @author  Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Controller
@RequestMapping("/")
public class MediaController {

    @GetMapping
    public String index() {
        return "media/index";
    }
}
