package media.mediastreamer;

import io.minio.MinioClient;
import media.mediastreamer.configuration.properties.MinioBuckets;
import media.mediastreamer.repositories.MediaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class MediaStreamerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@TestConfiguration
	@EnableConfigurationProperties(MinioBuckets.class)
	public static class TestMinioConfiguration {

		@Bean
		public MinioClient minioClient() {
			return mock(MinioClient.class);
		}

		@Bean
		public MediaRepository mediaRepository() {
			return mock(MediaRepository.class);
		}
	}
}
