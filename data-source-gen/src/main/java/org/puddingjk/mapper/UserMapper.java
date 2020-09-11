package org.puddingjk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.puddingjk.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName : UserMapper
 * @Description :
 * @Author : LuoHongyu
 * @Date: 2020-08-24 11:12
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    List<User> findAllUser();
}
