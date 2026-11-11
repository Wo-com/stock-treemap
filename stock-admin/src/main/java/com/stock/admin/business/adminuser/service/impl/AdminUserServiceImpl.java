package com.stock.admin.business.adminuser.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.stock.admin.business.adminuser.entity.AdminUser;
import com.stock.admin.business.adminuser.mapper.AdminUserMapper;
import com.stock.admin.business.adminuser.service.AdminUserService;
import com.stock.admin.common.entity.ApiResult;
import org.springframework.stereotype.Service;

/**
 * 后台用户 Service 实现
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

    @Override
    public ApiResult userSaveOrUpdate(AdminUser adminUser) {
        return ApiResult.success(super.saveOrUpdate(adminUser));
    }

    @Override
    public ApiResult remove(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(AdminUser::getUserId, id);
        return ApiResult.success(super.remove(queryWrapper));
    }

    @Override
    public ApiResult info(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(AdminUser::getUserId, id);
        return ApiResult.success(super.getOne(queryWrapper));
    }

    @Override
    public ApiResult userList() {
        return ApiResult.success(super.list());
    }

    @Override
    public ApiResult page(AdminUser adminUser) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page page = Page.of(adminUser.getPageNo(), adminUser.getPageSize());
        return ApiResult.success(super.page(page, queryWrapper));
    }

}
