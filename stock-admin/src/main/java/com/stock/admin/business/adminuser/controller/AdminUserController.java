package com.stock.admin.business.adminuser.controller;

import com.stock.admin.business.adminuser.entity.AdminUser;
import com.stock.admin.business.adminuser.service.AdminUserService;
import com.stock.admin.common.entity.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 后台用户 Controller
 */
@RestController
@RequestMapping("/adminuser")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 保存/更新
     */
    @PostMapping("/saveOrUpdate")
    public ApiResult saveOrUpdate(@RequestBody AdminUser adminUser) {
        return adminUserService.userSaveOrUpdate(adminUser);
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    public ApiResult remove(@RequestBody AdminUser adminUser) {
        return adminUserService.remove(adminUser.getUserId());
    }

    /**
     * 查询一个
     */
    @PostMapping("/info")
    public ApiResult info(@RequestBody AdminUser adminUser) {
        return adminUserService.info(adminUser.getUserId());
    }

    /**
     * 分页查询
     */
    @PostMapping("/page")
    public ApiResult page(@RequestBody AdminUser adminUser) {
        return adminUserService.page(adminUser);
    }

    /**
     * 查询全部
     */
    @PostMapping("/list")
    public ApiResult list(@RequestBody AdminUser adminUser) {
        return adminUserService.userList();
    }

}
