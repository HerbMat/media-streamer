package media.mediastreamer.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

/**
 * Object for containing upload media data.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Data
public class UploadForm {

    @NotEmpty
    private MultipartFile mediaFile;
}
