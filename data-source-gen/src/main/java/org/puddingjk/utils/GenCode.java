package org.puddingjk.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName : GenCode
 * @Description : 负责生成各种定制化MN、SN或者其它
 * @Author : LuoHongyu
 * @Date: 2020-08-25 20:25
 */
public class GenCode {
    private static final String START_MN = "A1C888888000FB2000";

    /***
     * @Param [qrCode] 二维码
     * @description 规则：前9位为二维码后9位
     * @author LuoHongyu
     * @date 2020/8/25 20:27
     */
    public static String genMn(String qrCode) {
        if (StringUtils.isNotBlank(qrCode)) {
            if (qrCode.length() >= 6) {
                // 执行生成
                return START_MN + qrCode.substring((qrCode.length() - 6));
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String s = genMn("0000987654321");
        System.out.println(s);
    }


    /***
     * @Param [num] 通过数字生成5位数流水号
     * @description 流水号生成
     * @author LuoHongyu
     * @date 2020/8/28 17:58
     */
    public static String genFlowCode(Integer num) {
        if(num.toString().length()>=6){
            return num.toString();
        }
        return String.format("%5d", num).replace(" ", "0");
    }

}
