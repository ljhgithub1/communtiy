package life.langteng.community;

import life.langteng.community.entity.User;
import life.langteng.community.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

	@Autowired
	private UserMapper mapper;


	@Test
	public void contextLoads() {
		User user = mapper.getUserById(1);

		System.out.println(user);

	}

}
