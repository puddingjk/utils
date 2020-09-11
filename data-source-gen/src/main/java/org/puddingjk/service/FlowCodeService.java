package org.puddingjk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.puddingjk.entity.FlowCode;

public interface FlowCodeService extends IService<FlowCode> {

    /***
    * @Param [flowCode] 
    * @description  新增并返回ID
    * @author LuoHongyu
    * @date 2020/8/28 19:43
    */
    Integer genFlowCode(FlowCode flowCode);
}
