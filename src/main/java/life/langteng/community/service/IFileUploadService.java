package life.langteng.community.service;

import life.langteng.community.dto.FileUploadDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {

    FileUploadDTO fileUpload(MultipartFile file);


}
