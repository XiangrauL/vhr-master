package org.javaboy.vhr.mapper;

import org.apache.ibatis.annotations.Param;
import org.javaboy.vhr.model.EmrData;
import org.javaboy.vhr.model.Salary;

import java.util.List;

public interface EmrdataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmrData record);

    int insertSelective(EmrData record);

    EmrData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmrData record);
    //选择性更新
    // 如果传入的EmrData对象中的某个属性为null，
    // 那么这个属性在数据库中的对应字段将不会被更新，保持原值。
    // 这种方式通常用于只想更新部分字段的场景，避免了不必要的数据覆盖。
    int updateByPrimaryKey(EmrData record);
    List<EmrData> getAllEmrdata();

    List<EmrData> getEmrdataByPage(@Param("page") Integer page,
                                  @Param("size") Integer size,
                                  @Param("keyword") String keyword,
                                  @Param("name") String name,
                                  @Param("number") String number,
                                  @Param("store") String store,
                                  @Param("company") String company);
    
    Long getTotal(@Param("keyword") String keyword,
                 @Param("name") String name,
                 @Param("number") String number,
                 @Param("store") String store,
                 @Param("company") String company);
}
