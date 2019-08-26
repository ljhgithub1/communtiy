package life.langteng.community.service.impl;

import life.langteng.community.dto.GithutUser;
import life.langteng.community.entity.User;
import life.langteng.community.mapper.UserMapper;
import life.langteng.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;



    @Override
    public User githubUser2User(GithutUser githutUser) {

        if(githutUser == null){
            return null;
        }

        User user = new User();
        user.setAccount(githutUser.getLogin());
        user.setName(githutUser.getName());
        user.setIntro(githutUser.getBio());

        user.setToken(UUID.randomUUID().toString());
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setAvatarUrl(githutUser.getAvatar_url());

        insertUser(user);

        return user;
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public User getUserByToken(String token) {
        return userMapper.getUserByToken(token);
    }
}
