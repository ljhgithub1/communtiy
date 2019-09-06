package life.langteng.community.dto;

import lombok.Data;

@Data
public class FileUploadDTO {

    /**
     * 状态码 0 表示上传失败，1 表示上传成功
     */
    private Integer success;
    /**
     * 提示的信息，上传成功或上传失败及错误信息等。
     */
    private String message;

    /**
     * 图片地址
     */
    private String url;



    public static FileUploadDTO fail(){
        FileUploadDTO dto = new FileUploadDTO();
        dto.setSuccess(0);
        dto.setMessage("图片上传失败");
        dto.setUrl("");
        return dto;
    }

    public static FileUploadDTO success(String url){
        FileUploadDTO dto = new FileUploadDTO();
        dto.setSuccess(1);
        dto.setMessage("图片上传成功");
        dto.setUrl(url);
        return dto;
    }




}
