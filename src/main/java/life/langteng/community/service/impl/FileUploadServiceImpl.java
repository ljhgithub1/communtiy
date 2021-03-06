package life.langteng.community.service.impl;

import life.langteng.community.dto.FileUploadDTO;
import life.langteng.community.service.IFileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements IFileUploadService {

    private final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    @Override
    public FileUploadDTO fileUpload(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            logger.info("image upload fail");
            return FileUploadDTO.fail();
        }

        String uuid = UUID.randomUUID().toString().substring(0, 10).replaceAll("-","a");

        String fileName = uuid + "-" + file.getOriginalFilename();

        //  这里有点问题

        String filePath = "E:\\WorkSpace\\study\\Community\\src\\main\\resources\\static\\images\\" + fileName;



        File f = new File(filePath);

        try {
            file.transferTo(f);
        } catch (IOException e) {
            logger.info("图片copy失败",e);
            return FileUploadDTO.fail();
        }
        // 这里必须是相对了classpath路径，不然会报错
        return FileUploadDTO.success("/images/"+fileName);
    }
}
