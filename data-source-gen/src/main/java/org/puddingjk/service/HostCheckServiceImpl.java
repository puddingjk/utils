package org.puddingjk.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.puddingjk.common.TableResultResponse;
import org.puddingjk.entity.HostCheck;
import org.puddingjk.entity.dto.HostCheckDTO;
import org.puddingjk.mapper.HostCheckMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : UserServiceImpl
 * @Description :
 * @Author : LuoHongyu
 * @Date: 2020-08-24 11:24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HostCheckServiceImpl extends ServiceImpl<HostCheckMapper, HostCheck> implements HostCheckService {

    @Autowired
    private HostCheckMapper hostCheckMapper;

    @Override
    public TableResultResponse pageList(HostCheckDTO searchObj) {
        Page result = PageHelper.startPage(searchObj.getPage(), searchObj.getLimit());
        List<HostCheck> list =  hostCheckMapper.pageList(searchObj);
        return  new TableResultResponse(result.getTotal(),list);
    }

    @Override
    public List<HostCheck> export(HostCheckDTO searchObj) {
       return hostCheckMapper.pageList(searchObj);
    }

    @Override
    public Boolean validate(Map<String, Object> param) {
        List<String> list = new ArrayList<>();
        Object id = param.get("id");
        Object qrCode = param.get("qrCode");
        Object mnCode = param.get("mnCode");
        Object cpuCode = param.get("cpuCode");
        if(qrCode==null && mnCode==null && cpuCode==null){
            return false;
        }
        HostCheck hostCheck =null;
        if(id!=null){
            hostCheck = hostCheckMapper.selectById(id.toString());
        }
        int count = 0;
        QueryWrapper<HostCheck> queryWrapper = new QueryWrapper<>();
        if(qrCode!=null && StringUtils.isNotBlank(qrCode.toString())){
            if(hostCheck!=null && qrCode.equals(hostCheck.getQrCode())){
                return true;
            }
            queryWrapper.lambda()
                    .eq(HostCheck::getQrCode,qrCode.toString())
                    .eq(HostCheck::getDeleteFlag,0);
            count = this.count(queryWrapper);
        }
        if(mnCode!=null && StringUtils.isNotBlank(mnCode.toString())){
            if(hostCheck!=null && mnCode.equals(hostCheck.getMnCode())){
                return true;
            }
            queryWrapper.lambda()
                    .eq(HostCheck::getMnCode,mnCode.toString())
                    .eq(HostCheck::getDeleteFlag,0);
            count = this.count(queryWrapper);
        }
        if(cpuCode!=null && StringUtils.isNotBlank(cpuCode.toString())){
            if(hostCheck!=null && cpuCode.equals(hostCheck.getCpuCode())){
                return true;
            }
            queryWrapper.lambda()
                    .eq(HostCheck::getCpuCode,cpuCode.toString())
                    .eq(HostCheck::getDeleteFlag,0);
            count = this.count(queryWrapper);
        }
        if(count>0){
            return false;
        }
        return true;
    }
}
