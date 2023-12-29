package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@TableName("t_registration")
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 挂号单
     */
    private String id;

    /**
     * 患者id
     */
    private Integer patientId;

    /**
     * 医生id
     */
    private Integer doctorId;

    /**
     * 挂号时间
     */
    private String createTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 病因
     */
    private String etiology;
    
    /**
     * 挂号状态
     */
    private String state;
    
    /**
     * 药物
     */
    private String drug;

    /**
     * 检测项目
     */
    private String checks;

    /**
     * 建议
     */
    private String advice;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 支付状态
     */
    private String priceState;

    @TableField(exist = false)
    private User patient;
    
}
