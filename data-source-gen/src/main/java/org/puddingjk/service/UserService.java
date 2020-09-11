package org.puddingjk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.puddingjk.entity.User;

import java.util.List;

public interface UserService extends IService<User> {

    List<User> findAllUser();
}
