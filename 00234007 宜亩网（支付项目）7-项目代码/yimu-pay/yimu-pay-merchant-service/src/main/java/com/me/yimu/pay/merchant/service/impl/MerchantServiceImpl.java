package com.me.yimu.pay.merchant.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.me.yimu.pay.domain.BusinessException;
import com.me.yimu.pay.domain.CommonErrorCode;
import com.me.yimu.pay.domain.PageVO;
import com.me.yimu.pay.merchant.api.MerchantService;
import com.me.yimu.pay.merchant.api.dto.MerchantDTO;
import com.me.yimu.pay.merchant.api.dto.StaffDTO;
import com.me.yimu.pay.merchant.api.dto.StoreDTO;
import com.me.yimu.pay.merchant.entity.Merchant;
import com.me.yimu.pay.merchant.entity.Staff;
import com.me.yimu.pay.merchant.entity.Store;
import com.me.yimu.pay.merchant.entity.StoreStaff;
import com.me.yimu.pay.merchant.mapper.MerchantMapper;
import com.me.yimu.pay.merchant.mapper.StaffMapper;
import com.me.yimu.pay.merchant.mapper.StoreMapper;
import com.me.yimu.pay.merchant.mapper.StoreStaffMapper;
import com.me.yimu.pay.user.api.TenantService;
import com.me.yimu.pay.user.api.dto.tenant.CreateTenantRequestDTO;
import com.me.yimu.pay.user.api.dto.tenant.TenantDTO;
import com.me.yimu.pay.utils.PhoneUtil;

import lombok.extern.slf4j.Slf4j;

import com.me.yimu.pay.merchant.convert.MerchantConvert;
import com.me.yimu.pay.merchant.convert.StaffConvert;
import com.me.yimu.pay.merchant.convert.StoreConvert;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2022-12-05
 */
@Service
@Transactional
@Slf4j
public class MerchantServiceImpl implements MerchantService {
    
    @Autowired
    MerchantMapper merchantMapper;

    @Autowired
    StoreMapper storeMapper;
    
    @Autowired
    StaffMapper staffMapper;
    
    @Autowired
    StoreStaffMapper storeStaffMapper;
    
    // 使用Dubbo调用远程接口
    @Reference
    TenantService tenantService;
    
    /**
     * 根据Id查询详细信息
     */
    @Override
    public MerchantDTO queryMerchantById(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
//        MerchantDTO merchantDTO = new MerchantDTO();
//        merchantDTO.setId(merchant.getId());
//        merchantDTO.setMerchantName(merchant.getMerchantName());
        MerchantDTO merchantDTO = MerchantConvert.INSTANCE.entity2dto(merchant);
        return merchantDTO;
    }

//    /**
//     * 商户注册
//     * @param merchantDTO
//     * @return
//     */
//    @Override
//    @Transactional
//    public MerchantDTO createMerchant(MerchantDTO merchantDTO) throws BusinessException {
//        // DTO为空的校验
//        if(merchantDTO == null) {
//            throw new BusinessException(CommonErrorCode.E_100108);
//        }
//        
//        // 手机号非空校验
//        if(StringUtils.isBlank(merchantDTO.getMobile())) {
//            throw new BusinessException(CommonErrorCode.E_100112);
//        }
//        
//        //校验手机号的合法性
//        if (!PhoneUtil.isMatches(merchantDTO.getMobile())) {
//            throw new BusinessException(CommonErrorCode.E_100109);
//        }
//        
//        //联系人非空校验
//        if (StringUtils.isBlank(merchantDTO.getUsername())) {
//            throw new BusinessException(CommonErrorCode.E_100110);
//        }
//        
//        //密码非空校验
//        if (StringUtils.isBlank(merchantDTO.getPassword())) {
//            throw new BusinessException(CommonErrorCode.E_100111);
//        }
//        
//        //校验商户手机号的唯一性,根据商户的手机号查询商户表，如果存在记录则说明已有相同的手机号重复
//        LambdaQueryWrapper<Merchant> lambdaQueryWrapper = 
//                new LambdaQueryWrapper<Merchant>().eq(
//                        Merchant::getMobile, merchantDTO.getMobile());
//        Integer count = merchantMapper.selectCount(lambdaQueryWrapper);
//        if(count > 0) {
//            throw new BusinessException(CommonErrorCode.E_100113);
//        }
//        
//        // 将DTO转换为Entity
//        Merchant merchantEntity = MerchantConvert.INSTANCE.dto2entity(merchantDTO);
//        
//        // 设置审核状态0‐未申请,1‐已申请待审核,2‐审核通过,3‐审核拒绝
//        merchantEntity.setAuditStatus("0");
//        
//        // 保存商户信息
//        merchantMapper.insert(merchantEntity);
//        
//        //将entity转成 dto
//        MerchantDTO merchantDTONew = MerchantConvert.INSTANCE.entity2dto(merchantEntity);
//        
//        return merchantDTONew;
//    }

    /**
     * 商户资质申请
     */
    @Override
    public void applyMerchant(Long merchantId, MerchantDTO merchantDTO) throws BusinessException {
        // 接收资质申请信息，更新到商户表
        if (merchantDTO == null || merchantId == null) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        
        // 根据id查询商户
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant == null) {
            throw new BusinessException(CommonErrorCode.E_200002);
        }
        
        Merchant merchantUp = MerchantConvert.INSTANCE.dto2entity(merchantDTO);
        // 已申请待审核
        merchantUp.setAuditStatus("1");
        merchantUp.setTenantId(merchant.getTenantId());
        
        // 更新
        merchantMapper.updateById(merchantUp);
    }
    
    /**
     * 新增员工
     *
     * @param staffDTO 员工信息
     * @return 新增成功的员工信息
     * @throws BusinessException
     */
    @Override
    public StaffDTO createStaff(StaffDTO staffDTO) throws BusinessException {

        //参数合法性校验
        if(staffDTO ==  null || StringUtils.isBlank(staffDTO.getMobile())
                || StringUtils.isBlank(staffDTO.getUsername())
                || staffDTO.getStoreId() == null){
            throw new BusinessException(CommonErrorCode.E_300009);

        }

        //在同一个商户下员工的账号唯一
        Boolean existStaffByUserName = isExistStaffByUserName(staffDTO.getUsername(), staffDTO.getMerchantId());
        if(existStaffByUserName){
            throw new BusinessException(CommonErrorCode.E_100114);
        }

        //在同一个商户下员工的手机号唯一
        Boolean existStaffByMobile = isExistStaffByMobile(staffDTO.getMobile(), staffDTO.getMerchantId());
        if(existStaffByMobile){
            throw new BusinessException(CommonErrorCode.E_100113);
        }
        Staff staff = StaffConvert.INSTANCE.dto2entity(staffDTO);
        staffMapper.insert(staff);

        return StaffConvert.INSTANCE.entity2dto(staff);
    }

    
    /**
    * 根据手机号判断员工是否已在指定商户存在
    * @param mobile 手机号
    * @return
    */
    private boolean isExistStaffByMobile(String mobile, Long merchantId) {
    LambdaQueryWrapper<Staff> lambdaQueryWrapper = new LambdaQueryWrapper<Staff>();
    lambdaQueryWrapper.eq(Staff::getMobile, mobile).eq(Staff::getMerchantId, merchantId);
    int i = staffMapper.selectCount(lambdaQueryWrapper);
    return i > 0;
    }
    
    /**
    * 根据账号判断员工是否已在指定商户存在
    * @param userName
    * @param merchantId
    * @return
    */
    private boolean isExistStaffByUserName(String userName, Long merchantId) {
    LambdaQueryWrapper<Staff> lambdaQueryWrapper = new LambdaQueryWrapper<Staff>();
    lambdaQueryWrapper.eq(Staff::getUsername, userName).eq(Staff::getMerchantId,
    merchantId);
    int i = staffMapper.selectCount(lambdaQueryWrapper);
    return i > 0;
    }
    
    @Override
    public StoreDTO createStore(StoreDTO storeDTO) {
        Store store = StoreConvert.INSTANCE.dto2entity(storeDTO);
        log.info("商户下新增门店"+JSON.toJSONString(store));
        storeMapper.insert(store);
        return StoreConvert.INSTANCE.entity2dto(store);
    }
    
    @Override
    public void bindStaffToStore(Long storeId, Long staffId) {
        StoreStaff storeStaff = new StoreStaff();
        storeStaff.setStoreId(storeId);
        storeStaff.setStaffId(staffId);
        storeStaffMapper.insert(storeStaff);
    }
    
    /**
     * 注册商户服务接口，接收账号、密码、手机号，为了可扩展性使用merchantDto接收数据
     * 调用SaaS接口：新增租户、用户、绑定租户和用户的关系，初始化权限
     * @param merchantDTO 商户注册信息
     * @return 注册成功的商户信息
     */
    @Override
    public MerchantDTO createMerchant(MerchantDTO merchantDTO) throws BusinessException {
        //校验参数的合法性
        if(merchantDTO == null){
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        if(StringUtils.isBlank(merchantDTO.getMobile())){
            throw new BusinessException(CommonErrorCode.E_100112);
        }
        if(StringUtils.isBlank(merchantDTO.getPassword())){
            throw new BusinessException(CommonErrorCode.E_100111);
        }
        //手机号格式校验
        if(!PhoneUtil.isMatches(merchantDTO.getMobile())){
            throw new BusinessException(CommonErrorCode.E_100109);
        }
        //校验手机号的唯一性
        //根据手机号查询商户表，如果存在记录则说明手机号已存在
        Integer count = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getMobile, merchantDTO.getMobile()));
        if(count>0){
            throw new BusinessException(CommonErrorCode.E_100113);
        }

        //调用SaaS接口
        //构建调用参数
        /**
         1、手机号

         2、账号

         3、密码

         4、租户类型：shanju-merchant

         5、默认套餐：shanju-merchant

         6、租户名称，同账号名

         */
        CreateTenantRequestDTO createTenantRequestDTO = new CreateTenantRequestDTO();
        createTenantRequestDTO.setMobile(merchantDTO.getMobile());
        createTenantRequestDTO.setUsername(merchantDTO.getUsername());
        createTenantRequestDTO.setPassword(merchantDTO.getPassword());
        createTenantRequestDTO.setTenantTypeCode("shanju-merchant");//租户类型
        createTenantRequestDTO.setBundleCode("shanju-merchant");//套餐，根据套餐进行分配权限
        createTenantRequestDTO.setName(merchantDTO.getUsername());//租户名称，和账号名一样

        //如果租户在SaaS已经存在，SaaS直接 返回此租户的信息，否则进行添加
        TenantDTO tenantAndAccount = tenantService.createTenantAndAccount(createTenantRequestDTO);
        //获取租户的id
        if(tenantAndAccount == null || tenantAndAccount.getId() == null){
            throw new BusinessException(CommonErrorCode.E_200012);
        }
        //租户的id
        Long tenantId = tenantAndAccount.getId();

        //租户id在商户表唯一
        //根据租户id从商户表查询，如果存在记录则不允许添加商户
        Integer count1 = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getTenantId, tenantId));
        if(count1>0){
            throw new BusinessException(CommonErrorCode.E_200017);
        }


//        Merchant merchant = new Merchant();
//        merchant.setMobile(merchantDTO.getMobile());
        //..写入其它属性
        //使用MapStruct进行对象转换
        Merchant merchant = MerchantConvert.INSTANCE.dto2entity(merchantDTO);
        //设置所对应的租户的Id
        merchant.setTenantId(tenantId);
        //审核状态为0-未进行资质申请
        merchant.setAuditStatus("0");
        //调用mapper向数据库写入记录
        merchantMapper.insert(merchant);

        //新增门店
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreName("根门店");
        storeDTO.setMerchantId(merchant.getId());//商户id
        StoreDTO store = createStore(storeDTO);

        //新增员工
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setMobile(merchantDTO.getMobile());//手机号
        staffDTO.setUsername(merchantDTO.getUsername());//账号
        staffDTO.setStoreId(store.getId());//员所属门店id
        staffDTO.setMerchantId(merchant.getId());//商户id

        StaffDTO staff = createStaff(staffDTO);

        //为门店设置管理员
        bindStaffToStore(store.getId(),staff.getId());

        //将dto中写入新增商户的id
        //merchantDTO.setId(merchant.getId());
        //将entity转成dto
        return MerchantConvert.INSTANCE.entity2dto(merchant);
    }

    @Override
    public MerchantDTO queryMerchantByTenantId(Long tenantId) {
       Merchant merchant = merchantMapper.selectOne(new QueryWrapper<Merchant>
       ().lambda().eq(Merchant::getTenantId, tenantId));
       return MerchantConvert.INSTANCE.entity2dto(merchant);
    }
    
    @Override
    public PageVO<StoreDTO> queryStoreByPage(StoreDTO storeDTO, Integer pageNo, Integer pageSize) {
        // 创建分页
        Page<Store> page = new Page<>(pageNo, pageSize);
        // 构造查询条件
        QueryWrapper<Store> qw = new QueryWrapper();
        if (null != storeDTO && null != storeDTO.getMerchantId()) {
            qw.lambda().eq(Store::getMerchantId, storeDTO.getMerchantId());
        }
        // 执行查询
        IPage<Store> storeIPage = storeMapper.selectPage(page, qw);
        // entity List转DTO List
        List<StoreDTO> storeList = StoreConvert.INSTANCE.listentity2dto(storeIPage.getRecords());
        // 封装结果集
        return new PageVO<>(storeList, storeIPage.getTotal(), pageNo, pageSize);
    }
    
    /**
    * 查询门店是否属于某商户
    *
    * @param storeId
    * @param merchantId
    * @return
    */
    @Override
    public Boolean queryStoreInMerchant(Long storeId, Long merchantId) {
        Integer count = storeMapper.selectCount(new LambdaQueryWrapper<Store>().eq(Store::getId,
            storeId)
            .eq(Store::getMerchantId, merchantId));
        return count>0;
    }


}
