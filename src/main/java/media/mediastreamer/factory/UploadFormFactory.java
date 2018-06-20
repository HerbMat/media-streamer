package media.mediastreamer.factory;

import media.mediastreamer.form.UploadForm;
import org.springframework.beans.factory.FactoryBean;

/**
 * Factory for creating {@link UploadForm} objects
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public class UploadFormFactory  implements FactoryBean<UploadForm> {


    /**
     * {@inheritDoc}
     */
    @Override
    public UploadForm getObject() throws Exception {
        return new UploadForm();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getObjectType() {
        return UploadForm.class;
    }
}
