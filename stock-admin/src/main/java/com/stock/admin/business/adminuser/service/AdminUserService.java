package com.stock.admin.business.adminuser.service;

import com.mybatisflex.core.service.IService;
import com.stock.admin.business.adminuser.entity.AdminUser;
import com.stock.admin.common.entity.ApiResult;

/**
 * 后台用户 Service
 */
public interface AdminUserService extends IService<AdminUser> {

    ApiResult userSaveOrUpdate(AdminUser adminUser);

    ApiResult remove(Long id);

    ApiResult info(Long id);

    ApiResult userList();

    ApiResult page(AdminUser adminUser);

}
