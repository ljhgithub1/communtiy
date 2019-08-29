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
        // github 的唯一账号
        user.setAccount(String.valueOf(githutUser.getId()));

        user.setName(githutUser.getName());
        user.setIntro(githutUser.getBio());

        user.setToken(UUID.randomUUID().toString());

        user.setAvatarUrl(githutUser.getAvatar_url());

        // 如果账号存在，那么就修改其他的信息，如:name、bio、avatar_url ...
        createOrUpdate(user);
        //  该 user 会存放到上下文中
        return user;
    }

    /**
     * 新用户就插入信息，老用户就修改信息
     * @param user
     */
    private void createOrUpdate(User user){
        User u = userMapper.getUserByAccount(user.getAccount());
        if(u == null){
            // 插入操作
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());

            userMapper.insertUser(user);
        }else{
            // 更新操作 -- 可能会更新的数据
            u.setToken(user.getToken());
            u.setAvatarUrl(user.getAvatarUrl());
            u.setName(user.getName());
            u.setIntro(user.getIntro());
            u.setGmtModified(System.currentTimeMillis());

            userMapper.updateUserToken(u);
        }
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
