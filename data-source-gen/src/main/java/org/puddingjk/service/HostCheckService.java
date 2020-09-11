package org.puddingjk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.puddingjk.common.TableResultResponse;
import org.puddingjk.entity.HostCheck;
import org.puddingjk.entity.dto.HostCheckDTO;

import java.util.List;
import java.util.Map;

public interface HostCheckService extends IService<HostCheck> {

    TableResultResponse pageList(HostCheckDTO searchObj);

    List<HostCheck> export(HostCheckDTO searchObj);

    Boolean validate(Map<String, Object> param);
}
