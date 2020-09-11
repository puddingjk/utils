package org.puddingjk.entity;

import lombok.Data;

/**
 * @ClassName : BasePage
 * @Description : 分页对象
 * @Author : LuoHongyu
 * @Date: 2020-08-25 10:52
 */
@Data
public class BasePage {
    private Integer page;
    private Integer limit;
}
