package org.javaboy.vhr.controller.ptl;

import org.javaboy.vhr.model.DailyCheck;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.DailycheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备日常检查控制器
 * 处理设备故障记录相关的请求
 */
@RestController
@RequestMapping("/ptl/dc")
public class PtlDailycheckController {
    @Autowired
    DailycheckService dailycheckService;

    /**
     * 添加设备故障记录
     * @param dailyCheck 故障记录信息，包含设备名称、故障描述和创建时间
     * @return RespBean 响应结果，包含操作状态和消息
     */
    @PostMapping
    public RespBean addDailycheck(@RequestBody DailyCheck dailyCheck) {
        if (dailycheckService.addDailycheck(dailyCheck) == 1) {
            return RespBean.ok(""); // 移除消息文本
        }
        return RespBean.error("添加失败!");
    }
}
