package org.puddingjk.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @ClassName : User
 * @Description :
 * @Author : LuoHongyu
 * @Date: 2020-08-24 11:13
 */
@Data
public class User  implements Serializable {

    @Id
    private String id;
    private String name;
}
