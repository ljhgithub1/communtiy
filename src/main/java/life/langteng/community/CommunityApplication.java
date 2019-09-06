package life.langteng.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// 扫描指定路径下面的mapper接口
@MapperScan("life.langteng.community.mapper")
@SpringBootApplication
@EnableAspectJAutoProxy
public class CommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}

}
