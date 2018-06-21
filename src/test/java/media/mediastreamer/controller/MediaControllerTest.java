package media.mediastreamer.controller;

import media.mediastreamer.form.UploadForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MediaControllerTest {

    @Mock
    private FactoryBean<UploadForm> uploadFormFactory;

    @InjectMocks
    private MediaController mediaController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mediaController).build();
    }

    @Test
    public void index() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("media/index"));
    }

    @Test
    public void renderUpload() throws Exception {
        when(uploadFormFactory.getObject()).thenReturn(mock(UploadForm.class));

        mockMvc.perform(get("/upload"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("uploadForm"))
                .andExpect(view().name("media/upload"));
    }

    @Test
    public void upload() throws Exception {
        mockMvc.perform(multipart("/upload")
                .file(getMockMultipartFile())
            ).andExpect(status().isOk())
            .andExpect(view().name("media/upload"));

    }

    private MockMultipartFile getMockMultipartFile() {
        return new MockMultipartFile("video", "video.mp4", "video/mp4", "video".getBytes());
    }
}