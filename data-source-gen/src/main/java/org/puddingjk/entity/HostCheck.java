package org.puddingjk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName : PowerCheck
 * @Description : 电源生产检验
 * @Author : LuoHongyu
 * @Date: 2020-08-24 13:31
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("host_check")
public class HostCheck extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String qrCode;
    private String cpuCode;
    private String mnCode;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date associatedTime;
    private String checkUserId;
    private String checkUser;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date factoryTime;
    private Integer deviceStatus;

}
