package org.javaboy.vhr.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;


public class EmrData implements Serializable {
    private Integer id;

    private String name;

    private String scale;

    private String number;

    private String company;

    private String store;

    private String num;

    private String etc;

    @JsonFormat(pattern = "yyyy-MM-dd")
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

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getScale() {return scale;}

    public void setScale(String scale) {this.scale = scale;}

    public String getNumber() {return number;}

    public void setNumber(String number) {this.number = number;}

    public String getCompany() {return company;}

    public void setCompany(String company) {this.company = company;}

    public String getStore() {return store;}

    public void setStore(String store) {this.store = store;}

    public String getNum() {return num;}

    public void setNum(String num) {this.num = num;}

    public String getEtc() {return etc;}

    public void setEtc(String etc) {this.etc = etc;}
}
