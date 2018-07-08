package media.mediastreamer.multipart;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Implementation of {@link MultipartFile} for images.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public class MultipartImage implements MultipartFile {
    private String fileName;
    private String contentType;
    private long size;
    private InputStream imageStream;

    public MultipartImage(String fileName, String contentType, long size, InputStream imageStream) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
        this.imageStream = imageStream;
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return FileCopyUtils.copyToByteArray(imageStream);
    }

    @Override
    public InputStream getInputStream() {
        return imageStream;
    }

    @Override
    public void transferTo(File dest) throws IOException {
        FileCopyUtils.copy(imageStream, Files.newOutputStream(dest.toPath()));
    }
}
