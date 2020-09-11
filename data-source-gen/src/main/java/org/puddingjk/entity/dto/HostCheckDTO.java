package org.puddingjk.entity.dto;

import org.puddingjk.entity.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName : PowerCheckDTO
 * @Description :
 * @Author : LuoHongyu
 * @Date: 2020-08-24 13:54
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class HostCheckDTO extends BasePage {
    private String startTime;
    private String endTime;
    private String qrCode;
    private String mnCode;
    private String cpuCode;
}
