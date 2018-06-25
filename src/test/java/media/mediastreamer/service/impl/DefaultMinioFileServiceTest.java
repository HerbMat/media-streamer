package media.mediastreamer.service.impl;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.ErrorResponseException;
import io.minio.messages.Item;
import io.minio.messages.Owner;
import media.mediastreamer.dto.FileResult;
import media.mediastreamer.exception.GenericServiceException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

/**
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public class DefaultMinioFileServiceTest {

    private static final String TEST_BUCKET_NAME = "bucket";

    @Mock
    private MinioClient minioClient;

    @Mock
    private Environment env;

    private DefaultMinioFileService fileService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(env.getProperty(anyString())).thenReturn(TEST_BUCKET_NAME);

        fileService = new DefaultMinioFileService(minioClient, env);
    }

    @Test
    public void createBucket() throws Exception {
        when(minioClient.bucketExists(anyString())).thenReturn(Boolean.FALSE);

        fileService.createBucket("test");

        verify(minioClient, times(1)).bucketExists("test");
        verify(minioClient, times(1)).makeBucket("test");
    }

    @Test
    public void createBucketExists() throws Exception {
        when(minioClient.bucketExists(anyString())).thenReturn(Boolean.TRUE);

        fileService.createBucket("test");

        verify(minioClient, times(1)).bucketExists("test");
        verify(minioClient, never()).makeBucket(anyString());
    }

    @Test(expected = GenericServiceException.class)
    public void createBucketException() throws Exception {
        when(minioClient.bucketExists(anyString())).thenThrow(ErrorResponseException.class);

        fileService.createBucket("test");
    }

    @Test
    public void init() throws Exception {
        when(minioClient.bucketExists(anyString())).thenReturn(Boolean.FALSE);

        fileService.init();

        verify(minioClient, times(1)).bucketExists(TEST_BUCKET_NAME);
        verify(minioClient, times(1)).makeBucket(TEST_BUCKET_NAME);
    }

    @Test
    public void putFile() throws Exception {

        ArgumentCaptor<String> bucketNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> sizeCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> origFileNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> contentTypeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<InputStream> videoFileCaptor = ArgumentCaptor.forClass(InputStream.class);

        fileService.putFile(getMockMultipartFile());

        verify(minioClient, times(1))
                .putObject(
                        bucketNameCaptor.capture(),
                        origFileNameCaptor.capture(),
                        videoFileCaptor.capture(),
                        sizeCaptor.capture(),
                        contentTypeCaptor.capture());

        assertThat(bucketNameCaptor.getValue(), equalTo(TEST_BUCKET_NAME));
        assertThat(sizeCaptor.getValue(), equalTo(5L));
        assertThat(origFileNameCaptor.getValue(), equalTo("video.mp4"));
        assertThat(contentTypeCaptor.getValue(), equalTo("video/mp4"));
        assertThat(videoFileCaptor.getValue().readAllBytes(), equalTo("video".getBytes()));
    }

    @Test(expected = GenericServiceException.class)
    public void putFileException() throws Exception {
        doThrow(IOException.class).when(minioClient)
                .putObject(anyString(), anyString(), any(), anyLong(), anyString());

        fileService.putFile(getMockMultipartFile());

    }

    @Test
    public void getFile() throws Exception {
        ArgumentCaptor<String> bucketNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> fileNameCaptor = ArgumentCaptor.forClass(String.class);
        InputStream fileInputStream = mock(InputStream.class);
        when(minioClient.getObject(anyString(), anyString())).thenReturn(fileInputStream);

        InputStream result = fileService.getFile("video.mp4");

        verify(minioClient, times(1)).getObject(bucketNameCaptor.capture(), fileNameCaptor.capture());

        assertThat(result, equalTo(fileInputStream));
        assertThat(bucketNameCaptor.getValue(), equalTo(TEST_BUCKET_NAME));
        assertThat(fileNameCaptor.getValue(), equalTo("video.mp4"));
    }

    @Test(expected = GenericServiceException.class)
    public void getFileException() throws Exception {
        when(minioClient.getObject(anyString(), anyString())).thenThrow(IOException.class);

        fileService.getFile("video.mp4");
    }

    @Test
    public void listFiles() throws Exception {
        Date date = mock(Date.class);
        Owner owner = mock(Owner.class);
        Iterable<Result<Item>> mockedResults = List.of(
                getMockResult(false, "etag1", date, "name1", 5L, owner, "storageClass1"),
                getMockResult(true, "etag2", date, "name2", 10L, owner, "storageClass2"));

        when(minioClient.listObjects(anyString())).thenReturn(mockedResults);

        Collection<FileResult> fileResults = fileService.listFiles();

        assertThat(fileResults, not(nullValue()));
        assertThat(fileResults, hasSize(2));

        Iterator<Result<Item>> resultIterator = mockedResults.iterator();
        Item item;
        for (FileResult fileResult : fileResults) {
            item = resultIterator.next().get();
            assertThat(fileResult.getEtag(), equalTo(item.etag()));
            assertThat(fileResult.getLastModified(), equalTo(item.lastModified()));
            assertThat(fileResult.getObjectName(), equalTo(item.objectName()));
            assertThat(fileResult.getSize(), equalTo(item.objectSize()));
            assertThat(fileResult.getStorageClass(), equalTo(item.storageClass()));

            assertThat(fileResult.getOwner(), not(nullValue()));
            assertThat(fileResult.getOwner().getId(), equalTo(item.owner().id()));
            assertThat(fileResult.getOwner().getDisplayName(), equalTo(item.owner().displayName()));
        }
    }

    @Test(expected = GenericServiceException.class)
    public void listFilesXmlException() throws Exception {

        when(minioClient.listObjects(anyString())).thenThrow(XmlPullParserException.class);

        fileService.listFiles();

    }

    @Test
    public void listFilesOneFileError() throws Exception {
        Date date = mock(Date.class);
        Owner owner = mock(Owner.class);
        Result<Item> result = mock((Result.class));
        when(result.get()).thenThrow(NoSuchAlgorithmException.class);
        Iterable<Result<Item>> mockedResults = List.of(
                getMockResult(false, "etag1", date, "name1", 5L, owner, "storageClass1"),
                result);

        when(minioClient.listObjects(anyString())).thenReturn(mockedResults);

        Collection<FileResult> fileResults = fileService.listFiles();

        assertThat(fileResults, not(nullValue()));
        assertThat(fileResults, hasSize(1));
    }

    private Result<Item> getMockResult(
            boolean isDir,
            String etag,
            Date lastModified,
            String name,
            long objectSize,
            Owner owner,
            String storageClass) throws Exception {
        Item item = mock(Item.class);
        when(item.isDir()).thenReturn(isDir);
        when(item.etag()).thenReturn(etag);
        when(item.lastModified()).thenReturn(lastModified);
        when(item.objectName()).thenReturn(name);
        when(item.objectSize()).thenReturn(objectSize);
        when(item.owner()).thenReturn(owner);
        when(owner.displayName()).thenReturn("owner");
        when(owner.id()).thenReturn("id");
        when(item.storageClass()).thenReturn(storageClass);

        Result<Item> itemResult = mock(Result.class);
        when(itemResult.get()).thenReturn(item);

        return itemResult;
    }

    private MockMultipartFile getMockMultipartFile() {
        return new MockMultipartFile("video", "video.mp4", "video/mp4", "video".getBytes());
    }
}
