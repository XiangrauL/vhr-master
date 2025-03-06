package org.javaboy.vhr.mapper;

import org.javaboy.vhr.model.DailyCheck;

public interface DailycheckMapper {
    int insert(DailyCheck record);

    int insertSelective(DailyCheck record);

}
