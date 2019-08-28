package life.langteng.community;

import life.langteng.community.dto.QuestionDTO;
import life.langteng.community.entity.User;
import life.langteng.community.mapper.QuestionMapper;
import life.langteng.community.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

	@Autowired
	private UserMapper mapper;

	@Autowired
	private QuestionMapper questionMapper;


	@Test
	public void contextLoads() {
		List<QuestionDTO> questionDTOS = questionMapper.queryQuestionByPage(0, 3);
		System.out.println(questionDTOS.size());
		for (QuestionDTO q:questionDTOS ) {
			System.out.println(q);
		}

	}

}
