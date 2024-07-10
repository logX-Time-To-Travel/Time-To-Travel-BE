package logX.TTT.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/image")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file")MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            String filePath = fileStorageService.storeFile(file);
            response.put("imageURL", filePath);
            return ResponseEntity.ok(response);
        }
        catch(IOException e) {
            response.put("error", "파일 업로드 실패");
            return ResponseEntity.status(500).body(response);
        }
    }
}
