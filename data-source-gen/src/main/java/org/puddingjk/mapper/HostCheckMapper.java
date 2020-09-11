package org.puddingjk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.puddingjk.entity.HostCheck;
import org.puddingjk.entity.dto.HostCheckDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName : UserMapper
 * @Description :
 * @Author : LuoHongyu
 * @Date: 2020-08-24 11:12
 */
@Repository
public interface HostCheckMapper extends BaseMapper<HostCheck> {

    List<HostCheck> pageList(@Param("obj") HostCheckDTO searchObj);
}
