package life.langteng.community.utils;

import java.util.UUID;

/**
 * 工具类
 */
public class UUIDUtil {

    /**
     * 获取 36 为 uuid 随机数
     * @return
     */
    public static String uuidString(){
        return UUID.randomUUID().toString();
    }

}
