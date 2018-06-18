package media.mediastreamer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring server responsible for uploading and displaying medias.
 * Medias are uploaded to database and streamlined to frontend.
 *
 * @author  Mateusz Kozłowski <matikz1110@gmail.com>
 */
@SpringBootApplication
public class MediaStreamerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaStreamerApplication.class, args);
	}
}
