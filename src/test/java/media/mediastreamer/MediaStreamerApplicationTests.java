package media.mediastreamer;

import io.minio.MinioClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MediaStreamerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@TestConfiguration
	public static class TestMinioConfiguration {

		@Bean
		public MinioClient minioClient() {
			return mock(MinioClient.class);
		}
	}
}
