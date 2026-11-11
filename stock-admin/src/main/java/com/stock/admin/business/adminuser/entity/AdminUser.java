package com.stock.admin.business.adminuser.entity;

import com.mybatisflex.annotation.Table;
import com.stock.admin.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 后台用户表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(value = "admin_user")
public class AdminUser extends BaseEntity implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String accountName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String passwordSalt;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户角色：0-超级管理员|1-管理员|2-开发&测试&运营|3-普通用户（只能查看）
     */
    private Integer role;

    /**
     * 状态：0-失效|1-有效|2-删除
     */
    private Integer userStatus;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    private Long updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}
