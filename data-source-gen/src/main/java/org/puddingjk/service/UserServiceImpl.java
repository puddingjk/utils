package org.puddingjk.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.puddingjk.entity.User;
import org.puddingjk.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName : UserServiceImpl
 * @Description :
 * @Author : LuoHongyu
 * @Date: 2020-08-24 11:24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService  {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }
}
