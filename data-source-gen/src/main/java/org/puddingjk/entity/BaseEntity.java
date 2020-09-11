package org.puddingjk.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName : BaseEntity
 * @Description :
 * @Author : LuoHongyu
 * @Date: 2020-08-24 13:36
 */
@Data
public class BaseEntity {

    public Date createTime = new Date();
    public Integer deleteFlag = 0;
    public Date updateTime;

}
