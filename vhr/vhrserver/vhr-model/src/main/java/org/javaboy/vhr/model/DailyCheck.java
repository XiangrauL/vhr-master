package org.javaboy.vhr.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备日常检查记录实体类
 * 用于记录设备的故障和维护信息
 */
public class DailyCheck implements Serializable {
    /** 记录ID */
    private Integer id;

    /** 故障问题描述 */
    private String problem;

    /** 设备名称 */
    private String name;

    /** 
     * 创建时间
     * 使用JsonFormat注解确保日期格式化为"yyyy-MM-dd HH:mm:ss"
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createDate;

    //Getter 和 Setter 方法
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getProblem() {return problem;}

    public void setProblem(String problem) {this.problem = problem;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
