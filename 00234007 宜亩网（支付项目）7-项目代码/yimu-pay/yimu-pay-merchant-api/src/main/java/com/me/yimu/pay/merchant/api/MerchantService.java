package com.me.yimu.pay.merchant.api;

import com.me.yimu.pay.domain.BusinessException;
import com.me.yimu.pay.domain.PageVO;
import com.me.yimu.pay.merchant.api.dto.MerchantDTO;
import com.me.yimu.pay.merchant.api.dto.StaffDTO;
import com.me.yimu.pay.merchant.api.dto.StoreDTO;

/**
 * 商户服务接口
 * @author PC
 *
 */
public interface MerchantService {

    /**
    * 根据ID查询详细信息
    * @param merchantId
    * @return
    * @throws BusinessException
    */
    MerchantDTO queryMerchantById(Long merchantId);
    
    /**
     * 商户注册
     * @param merchantDTO
     * @return
     */
    MerchantDTO createMerchant(MerchantDTO merchantDTO) throws BusinessException;
    
    /**
     * 资质申请
     * @param merchantId 商户id
     * @param merchantDTO 资质申请信息
     * @throws BusinessException
     */
    void applyMerchant(Long merchantId, MerchantDTO merchantDTO) throws BusinessException;
    
    /**
    * 商户下新增门店
    * @param storeDTO
    */
    StoreDTO createStore(StoreDTO storeDTO) throws BusinessException;
    
    /**
    * 商户新增员工
    * @param staffDTO
    */
    StaffDTO createStaff(StaffDTO staffDTO) throws BusinessException;

    /**
    * 为门店设置管理员
    * @param storeId
    * @param staffId
    * @throws BusinessException
    */
    void bindStaffToStore(Long storeId, Long staffId) throws BusinessException;
    
    /**
    * 查询租户下的商户
    * @param tenantId
    * @return
    */
    MerchantDTO queryMerchantByTenantId(Long tenantId) throws BusinessException;
    
    /**
    * 分页条件查询商户下门店
    * @param storeDTO
    * @param pageNo
    * @param pageSize
    * @return
    */
    PageVO<StoreDTO> queryStoreByPage(StoreDTO storeDTO, Integer pageNo, Integer pageSize);
    
    /**
    * 查询门店是否属于某商户
    * @param storeId
    * @param merchantId
    * @return
    */
    Boolean queryStoreInMerchant(Long storeId, Long merchantId);

}
