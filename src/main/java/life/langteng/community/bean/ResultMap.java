package life.langteng.community.bean;

import lombok.Data;

import java.util.Map;

@Data
public class ResultMap {

    /**
     * 状态码
     */
    private int code;
    /**
     * 提示
     */
    private String message;
    /**
     * 数据
     */
    private Map<String,Object>  data;


    public ResultMap putData(String key,Object value){
        this.data.put(key,value);
        return this;
    }




}
