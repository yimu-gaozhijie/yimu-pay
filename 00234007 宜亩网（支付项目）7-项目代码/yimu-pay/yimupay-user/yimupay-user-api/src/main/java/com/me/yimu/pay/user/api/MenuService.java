package com.me.yimu.pay.user.api;


import java.util.List;

import com.me.yimu.pay.domain.BusinessException;
import com.me.yimu.pay.domain.PageVO;
import com.me.yimu.pay.user.api.dto.menu.MenuDTO;
import com.me.yimu.pay.user.api.dto.menu.MenuQueryDTO;

/**
 * 菜单服务
 * 菜单为手工建立维护
 */
public interface MenuService {

    /**
     * 根据ID查询菜单
     * @param id
     * @return
     */
    MenuDTO queryMenu(Long id) throws BusinessException;

    /**
     * 根据应用编码查询菜单列表
     * @param applicationCode
     * @return
     */
    List<MenuDTO> queryMenuByApplicationCode(String applicationCode) throws BusinessException;
    /**
     * 根据条件查询菜单列表
     * @param params
     * @param pageSize
     * @param pageNo
     */
    PageVO<MenuDTO>  queryMenu(MenuQueryDTO params, Integer pageNo, Integer pageSize) throws BusinessException;

    /**
     * 根据权限编码列表获取菜单
     * @param privileges 权限列表
     * @return
     */
    List<MenuDTO> queryMenuByPrivileges(String [] privileges) throws BusinessException;




}
