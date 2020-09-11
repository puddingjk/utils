package org.puddingjk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.puddingjk.common.TableResultResponse;
import org.puddingjk.entity.PowerCheck;
import org.puddingjk.entity.dto.PowerCheckDTO;

import java.util.List;
import java.util.Map;

public interface PowerCheckService extends IService<PowerCheck> {

    TableResultResponse pageList(PowerCheckDTO searchObj);

    Boolean validate(Map<String, Object> param);

    List<PowerCheck> queryList(PowerCheckDTO searchObj);
}
