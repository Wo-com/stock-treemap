package com.stock.admin.common.entity;

import com.mybatisflex.annotation.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BaseEntity {

    /**
     * id主键
     */
    private Long id;

    /**
     * 是否删除
     */
//    @Column(isLogicDelete = true)
//    private Integer delFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * ids
     */
    @Column(ignore = true)
    private List<Long> ids;

    /**
     * 当前分页
     */
    @Column(ignore = true)
    private Integer pageNo = 1;

    /**
     * 当前大小
     */
    @Column(ignore = true)
    private Integer pageSize = 10;

}
