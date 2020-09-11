package org.puddingjk.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.puddingjk.common.ObjectRestResponse;
import org.puddingjk.common.TableResultResponse;
import org.puddingjk.entity.HostCheck;
import org.puddingjk.entity.dto.HostCheckDTO;
import org.puddingjk.service.HostCheckService;
import org.puddingjk.utils.DateUtils;
import org.puddingjk.utils.GenCode;
import org.puddingjk.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : HostCheckController
 * @Description : 电源生产检验
 * @Author : LuoHongyu
 * @Date: 2020-08-24 13:45
 */
@RestController
@RequestMapping("/hostCheck")
public class HostCheckController {

    @Autowired
    private HostCheckService hostCheckService;

    @PostMapping("/list")
    public TableResultResponse powerCheck(@RequestBody HostCheckDTO searchObj){
        // 精确查询
        return hostCheckService.pageList(searchObj);
    }

    @PostMapping("/add")
    public ObjectRestResponse addHostCheck(@RequestBody HostCheck saveObj){
        try {
            saveObj.setId(UUIDUtils.generate32UUidString());
            hostCheckService.save(saveObj);
            return ObjectRestResponse.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ObjectRestResponse.error();
    }

    @GetMapping("/get/{id}")
    public ObjectRestResponse getHostCheck(@PathVariable("id") String id){
        if(StringUtils.isBlank(id)){
            return ObjectRestResponse.validateError("参数不合法");
        }
        HostCheck byId = hostCheckService.getById(id);
        return ObjectRestResponse.ok(byId);
    }


    @PostMapping("/update")
    public ObjectRestResponse updatePowerCheck(@RequestBody HostCheck saveObj) {
        try {
            // 设置末次更新时间
            saveObj.setUpdateTime(new Date());
            hostCheckService.updateById(saveObj);
            return ObjectRestResponse.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ObjectRestResponse.error();
    }

    @GetMapping("/delete/{id}")
    public ObjectRestResponse deleteHostCheck(@PathVariable("id") String id){
        if(StringUtils.isBlank(id)){
            return ObjectRestResponse.validateError("参数不合法");
        }
        UpdateWrapper<HostCheck> up = new UpdateWrapper();
        up.lambda().set(HostCheck::getDeleteFlag,1).eq(HostCheck::getId,id);
        hostCheckService.update(up);
        return ObjectRestResponse.ok(null);
    }

    @GetMapping("/validate")
    public ObjectRestResponse validate(@RequestParam Map<String, Object> param) {
        Boolean validate = hostCheckService.validate(param);
        if (validate) {
            return ObjectRestResponse.ok(true);
        }
        return ObjectRestResponse.ok(false);
    }
    @PostMapping("/export")
    public void export(@RequestBody HostCheckDTO searchObj, HttpServletResponse response) throws IOException {
        List<HostCheck> hostCheckList = hostCheckService.export(searchObj);
        String[] title = new String[]{"序号", "SN序列号", "CPU序号","MN号","关联时间","检验人员"};
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
        for (HostCheck obj: hostCheckList){//遍历数据插入excel中
            row = sheet.createRow(rows);
            int col = 0;
            row.createCell(col).setCellValue(index);
            row.createCell(col+1).setCellValue(obj.getQrCode());
            row.createCell(col+2).setCellValue(obj.getCpuCode());
            row.createCell(col+3).setCellValue(obj.getMnCode());
            row.createCell(col+4).setCellValue(DateUtils.dateToStr(obj.getAssociatedTime(),DateUtils.YY_MM_DD));
            row.createCell(col+5).setCellValue(obj.getCheckUser());
            rows++;
            index++;
        }
        String fileName = "主机检查";
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

    @GetMapping("/genMn")
    public ObjectRestResponse genMn(String qrCode){
        if(StringUtils.isBlank(qrCode) && qrCode.length()>=9){
            return  ObjectRestResponse.validateError("参数有误");
        }
        String mn = GenCode.genMn(qrCode);
        return ObjectRestResponse.ok(mn);
    }

}