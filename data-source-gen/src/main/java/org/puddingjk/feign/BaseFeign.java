package org.puddingjk.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "c-service",url = "${desktop.url:null}"
//     ,   configuration = FeignInterceptor.class,
//        fallback = TestService.DefaultFallback.class
)
public interface BaseFeign {

    /***
    * @Param [type]  1电源 2主机
    * @description CPU一键校0接口
     * @author LuoHongyu
    * @date 2020/8/25 14:59
    */
    @GetMapping("/api/Devices/datazl")
    Map<String,Object> toZero(@RequestParam("ip") String ip,@RequestParam("key") String key);
    
    /***
    * @Param [type] 1电源 2主机
    * @description CPU读取接口 
    * @author LuoHongyu
    * @date 2020/8/25 14:58
    */
    @GetMapping("/api/Devices/GetCPU")
    Map<String,Object> cpuRead(@RequestParam("type") Integer type,@RequestParam("ip") String ip,@RequestParam("key") String key);
}
