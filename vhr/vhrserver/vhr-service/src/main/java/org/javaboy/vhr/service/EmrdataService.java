package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmrdataMapper;
import org.javaboy.vhr.model.EmrData;
import org.javaboy.vhr.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmrdataService {
    @Autowired
    EmrdataMapper emrdataMapper;

    public List<EmrData> getAllEmrdata() {
        return emrdataMapper.getAllEmrdata();
    }

    public Integer addEmrdata(EmrData emrdata) {
        emrdata.setCreateDate(new Date());
        return emrdataMapper.insertSelective(emrdata);
    }

    public Integer deleteEmrdataById(Integer id) {
        return emrdataMapper.deleteByPrimaryKey(id);
    }

    public Integer updateEmrdataById(EmrData emrdata) {
        return emrdataMapper.updateByPrimaryKeySelective(emrdata);
    }

    public RespPageBean getEmrdataByPage(Integer page, Integer size, String keyword,
                                         String name, String number,
                                         String store, String company) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<EmrData> data = emrdataMapper.getEmrdataByPage(page, size, keyword,
                name, number, store, company);
        Long total = emrdataMapper.getTotal(keyword, name, number, store, company);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }
}
