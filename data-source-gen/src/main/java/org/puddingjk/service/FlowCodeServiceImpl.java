package org.puddingjk.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.puddingjk.entity.FlowCode;
import org.puddingjk.mapper.FlowCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName : UserServiceImpl
 * @Description :
 * @Author : LuoHongyu
 * @Date: 2020-08-24 11:24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FlowCodeServiceImpl extends ServiceImpl<FlowCodeMapper, FlowCode> implements FlowCodeService {

    @Autowired
    private FlowCodeMapper flowCodeMapper;

    @Override
    public Integer genFlowCode(FlowCode flowCode) {
        // 先查询末次ID 是否使用
//        QueryWrapper<FlowCode> q = new QueryWrapper<>();
//        q.
//        flowCodeMapper.selectOne();
//        flowCodeMapper.

        flowCodeMapper.insertReturnId(flowCode);
        Integer id = flowCode.getId();
        System.out.println(id);
        return id;
    }
}
