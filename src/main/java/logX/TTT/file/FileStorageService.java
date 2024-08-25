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

    private final String uploadDir = "src/main/resources/static/images";

    public String storeFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String newFilename = timestamp + "_" + originalFilename;

        Path filePath = Paths.get(uploadDir, newFilename);
        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath);

        // 이미지 파일이 저장된 경로에서 '/images/' URL을 반환
        return "/images/" + newFilename;
    }
}