package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.DailycheckMapper;
import org.javaboy.vhr.model.DailyCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DailycheckService {
    @Autowired
    DailycheckMapper dailycheckMapper;


    public Integer addDailycheck(DailyCheck dailycheck) {
        dailycheck.setCreateDate(new Date());
        return dailycheckMapper.insertSelective(dailycheck);
    }
}
