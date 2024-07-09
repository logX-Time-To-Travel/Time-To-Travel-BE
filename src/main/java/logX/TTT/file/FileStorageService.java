package logX.TTT.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String newFilename = filename + "_" + timestamp;

        Path filePath = Paths.get(uploadDir, newFilename);
        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath);
        return filePath.toString();
    }
}
