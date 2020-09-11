package org.puddingjk.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.puddingjk.common.TableResultResponse;
import org.puddingjk.entity.PowerCheck;
import org.puddingjk.entity.dto.PowerCheckDTO;
import org.puddingjk.mapper.PowerCheckMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class PowerCheckServiceImpl extends ServiceImpl<PowerCheckMapper, PowerCheck> implements PowerCheckService  {

    @Autowired
    private PowerCheckMapper powerCheckMapper;

    @Override
    public TableResultResponse pageList(PowerCheckDTO searchObj) {
        Page result = PageHelper.startPage(searchObj.getPage(), searchObj.getLimit());
        List<PowerCheck> list =  powerCheckMapper.pageList(searchObj);
        return  new TableResultResponse(result.getTotal(),list);
    }

    @Override
    public Boolean validate(Map<String, Object> param) {
        Object id = param.get("id");
        Object qrCode = param.get("qrCode");
        Object snCode = param.get("snCode");
        Object cpuCode = param.get("cpuCode");
        if(qrCode==null && snCode==null && cpuCode==null){
          return false;
        }
        PowerCheck powerCheck =null;
        if(id!=null){
            powerCheck = powerCheckMapper.selectById(id.toString());
        }
        int count = 0;
        QueryWrapper<PowerCheck> queryWrapper = new QueryWrapper<>();
        if(qrCode!=null && StringUtils.isNotBlank(qrCode.toString())){
            if(powerCheck!=null && qrCode.equals(powerCheck.getQrCode())){
                return true;
            }
            queryWrapper.lambda()
                    .eq(PowerCheck::getQrCode,qrCode.toString())
                    .eq(PowerCheck::getDeleteFlag,0);
             count = this.count(queryWrapper);
        }
        if(snCode!=null && StringUtils.isNotBlank(snCode.toString())){
            if(powerCheck!=null && snCode.equals(powerCheck.getSnCode())){
                return true;
            }
            queryWrapper.lambda()
                    .eq(PowerCheck::getSnCode,snCode.toString())
                    .eq(PowerCheck::getDeleteFlag,0);
            count = this.count(queryWrapper);
        }
        if(cpuCode!=null && StringUtils.isNotBlank(cpuCode.toString())){
            if(powerCheck!=null && cpuCode.equals(powerCheck.getCpuCode())){
                return true;
            }
            queryWrapper.lambda()
                    .eq(PowerCheck::getCpuCode,cpuCode.toString())
                    .eq(PowerCheck::getDeleteFlag,0);
            count = this.count(queryWrapper);
        }
        if(count>0){
            return false;
        }
        return true;
    }

    @Override
    public List<PowerCheck> queryList(PowerCheckDTO searchObj) {
        return powerCheckMapper.pageList(searchObj);
    }
}
