package org.javaboy.vhr.controller.ptl;

import org.javaboy.vhr.model.EmrData;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.model.RespPageBean;
import org.javaboy.vhr.service.EmrdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ptl/ed")
public class PtlEmrdataController {
    @Autowired
    EmrdataService emrdataService;

    @GetMapping
    public RespPageBean getEmrdataByPage(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     String keyword, String name, 
                                     String number, String store, 
                                     String company) {
        return emrdataService.getEmrdataByPage(page, size, keyword, name, number, store, company);
    }

    @PostMapping
    public RespBean addSalary(@RequestBody EmrData emrData) {
        if (emrdataService.addEmrdata(emrData) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmrDataById(@PathVariable Integer id) {
        if (emrdataService.deleteEmrdataById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @PutMapping("/")
    public RespBean updateEmrDataById(@RequestBody EmrData emrData) {
        if (emrdataService.updateEmrdataById(emrData) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
}
