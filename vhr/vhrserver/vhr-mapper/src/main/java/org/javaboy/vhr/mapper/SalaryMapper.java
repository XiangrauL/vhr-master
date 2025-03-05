package org.javaboy.vhr.mapper;

import org.javaboy.vhr.model.Salary;

import java.util.List;

public interface SalaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Salary record);

    int insertSelective(Salary record);

    Salary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Salary record);
    //选择性更新
    // 如果传入的Salary对象中的某个属性为null，
    // 那么这个属性在数据库中的对应字段将不会被更新，保持原值。
    // 这种方式通常用于只想更新部分字段的场景，避免了不必要的数据覆盖。
    int updateByPrimaryKey(Salary record);

    List<Salary> getAllSalaries();
}