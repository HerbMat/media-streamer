package media.mediastreamer.factory;

import media.mediastreamer.domain.Media;
import org.springframework.beans.factory.FactoryBean;

/**
 * Factory for creating {@link Media} objects
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public class MediaFactory implements FactoryBean<Media> {


    /**
     * {@inheritDoc}
     */
    @Override
    public Media getObject() {
        return new Media();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getObjectType() {
        return Media.class;
    }
}
