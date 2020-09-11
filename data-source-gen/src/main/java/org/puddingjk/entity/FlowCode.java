package org.puddingjk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @ClassName : FlowCode
 * @Description : 流水号实体
 * @Author : LuoHongyu
 * @Date: 2020-08-28 19:35
 */
@Data
public class FlowCode {
    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 0未使用 1已使用
    private Integer state;


    public FlowCode() {
    }

    public FlowCode(Integer state) {
        this.state = state;
    }

    public FlowCode(Integer id, Integer state) {
        this.id = id;
        this.state = state;
    }
}
