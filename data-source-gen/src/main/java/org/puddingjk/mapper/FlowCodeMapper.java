package org.puddingjk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.puddingjk.entity.FlowCode;
import org.springframework.stereotype.Repository;

/**
 * @ClassName : UserMapper
 * @Description :
 * @Author : LuoHongyu
 * @Date: 2020-08-24 11:12
 */
@Repository
public interface FlowCodeMapper extends BaseMapper<FlowCode> {
    int insertReturnId(FlowCode flowCode);
}
