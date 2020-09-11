package org.puddingjk.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.puddingjk.common.ObjectRestResponse;
import org.puddingjk.common.TableResultResponse;
import org.puddingjk.entity.FlowCode;
import org.puddingjk.entity.PowerCheck;
import org.puddingjk.entity.dto.PowerCheckDTO;
import org.puddingjk.feign.BaseFeign;
import org.puddingjk.service.FlowCodeService;
import org.puddingjk.service.PowerCheckService;
import org.puddingjk.utils.DateUtils;
import org.puddingjk.utils.GenCode;
import org.puddingjk.utils.IpUtil;
import org.puddingjk.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : PowerCheckController
 * @Description : 电源生产检验
 * @Author : LuoHongyu
 * @Date: 2020-08-24 13:45
 */
@RestController
@RequestMapping("/powerCheck")
@Slf4j
public class PowerCheckController {

    @Autowired
    private PowerCheckService powerCheckService;

    @Autowired
    private BaseFeign baseFeign;
    @Autowired
    private FlowCodeService flowCodeService;

    @PostMapping("/list")
    public TableResultResponse powerCheck(@RequestBody PowerCheckDTO searchObj) {
        // 精确查询
        return powerCheckService.pageList(searchObj);
    }

    @PostMapping("/add")
    public ObjectRestResponse addPowerCheck(@RequestBody PowerCheck saveObj) {
        try {
            saveObj.setId(UUIDUtils.generate32UUidString());
            // 保存前 更新SN号为已使用状态
//            flowCodeService.updateById(new FlowCode(saveObj.getSnCode()))
            powerCheckService.save(saveObj);
            return ObjectRestResponse.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ObjectRestResponse.error();
    }

    @PostMapping("/update")
    public ObjectRestResponse updatePowerCheck(@RequestBody PowerCheck saveObj) {
        try {
            // 设置末次更新时间
            saveObj.setUpdateTime(new Date());
            powerCheckService.updateById(saveObj);
            return ObjectRestResponse.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ObjectRestResponse.error();
    }

    @GetMapping("/get/{id}")
    public ObjectRestResponse getPowerCheck(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            return ObjectRestResponse.validateError("参数不合法");
        }
        PowerCheck byId = powerCheckService.getById(id);
        return ObjectRestResponse.ok(byId);
    }

    @GetMapping("/delete/{id}")
    public ObjectRestResponse deletePowerCheck(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            return ObjectRestResponse.error();
        }
        UpdateWrapper<PowerCheck> up = new UpdateWrapper();
        up.lambda().set(PowerCheck::getDeleteFlag, 1)
                .eq(PowerCheck::getId, id);
        powerCheckService.update(up);
        return ObjectRestResponse.ok(null);
    }

    @GetMapping("/validate")
    public ObjectRestResponse validate(@RequestParam Map<String, Object> param) {
        Boolean validate = powerCheckService.validate(param);
        if (validate) {
            return ObjectRestResponse.ok(true);
        }
        return ObjectRestResponse.ok(false);
    }

    @PostMapping("/export")
    public void export(@RequestBody PowerCheckDTO searchObj, HttpServletResponse response) throws IOException {
        List<PowerCheck> hostCheckList = powerCheckService.queryList(searchObj);
        String[] title = new String[]{"序号", "二维码", "CPU序号","SN序列号","关联时间","检验人员"};
        XSSFWorkbook workbook = new XSSFWorkbook(); // 新建工作簿对象
        XSSFSheet sheet = workbook.createSheet("UserList");// 创建sheet
        int rowNum = 0;
        Row row =  sheet.createRow(rowNum);// 创建第一行对象,设置表标题
        Cell cell;
        int cellNum = 0;
        for (String name:title){
            cell = row.createCell(cellNum);
            cell.setCellValue(name);
            cellNum++;
        }
        int rows = 1;
        int index = 1;
        for (PowerCheck obj: hostCheckList){//遍历数据插入excel中
            row = sheet.createRow(rows);
            int col = 0;
            row.createCell(col).setCellValue(index);
            row.createCell(col+1).setCellValue(obj.getQrCode());
            row.createCell(col+2).setCellValue(obj.getCpuCode());
            row.createCell(col+3).setCellValue(obj.getSnCode());
            row.createCell(col+4).setCellValue(DateUtils.dateToStr(obj.getAssociatedTime(),DateUtils.YY_MM_DD));
            row.createCell(col+5).setCellValue(obj.getCheckUser());
            rows++;
            index++;
        }
        String fileName = "电源检查";
        OutputStream out =null;
        try {
            out = response.getOutputStream();
            response.reset();
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //默认Excel名称
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            out.flush();
            out.close();
        }
    }

    @GetMapping("/cpuRead")
    public ObjectRestResponse cpuRead(Integer type, HttpServletRequest request) {
        String ipAddr = IpUtil.getIpAddr(request);
        log.info("cpuRead,ip:{}",ipAddr);
        try {
            Map<String, Object> map = baseFeign.cpuRead(type,ipAddr,UUIDUtils.generate32UUidString());
            if (map != null && map.get("cpu") != null && StringUtils.isNotBlank(map.get("cpu").toString())) {
                return ObjectRestResponse.ok(map.get("cpu").toString());
            }
            log.info("type:{},cpu:{}", type, JSONObject.toJSONString(map));
            return ObjectRestResponse.ok(map.get("cpu"));
        } catch (Exception e) {
            log.error("CPU读取失败：{},{}",type, e);
            e.printStackTrace();
        }
        return ObjectRestResponse.ok(null);
    }

    @GetMapping("/toZero")
    public ObjectRestResponse powerToZero(HttpServletRequest request) {
        try {
            String ipAddr = IpUtil.getIpAddr(request);
            log.info("toZero,ip:{}",ipAddr);
            Map<String, Object> map = baseFeign.toZero(ipAddr,UUIDUtils.generate32UUidString());
            if(map!=null && map.get("success")!=null){
                Object success = map.get("success");
                if("true".equals(success)){
                    // 置零成功
                    return ObjectRestResponse.ok(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("置零失败,{}",e);
        }
        return ObjectRestResponse.ok(false);
    }


    // 生成SN流水号 5位数
    @GetMapping("/flowCode")
    public ObjectRestResponse flowCode(){
        try {
            Integer code = flowCodeService.genFlowCode(new FlowCode(0));
            return ObjectRestResponse.ok(GenCode.genFlowCode(code));
        } catch (Exception e) {
            log.error("流水号生成失败",e);
            e.printStackTrace();
        }
        return ObjectRestResponse.error();
    }

    /***
     * @Param
     * @description  预留：年份下拉框
     * @author LuoHongyu
     * @date 2020/8/28 17:59
     */
    public ObjectRestResponse yearCodeSelect(){
        return ObjectRestResponse.ok(null);
    }




}