package com.me.yimu.pay.merchant.service.impl;

import com.me.yimu.pay.merchant.entity.Staff;
import com.me.yimu.pay.merchant.mapper.StaffMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.me.yimu.pay.merchant.service.IStaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2022-12-05
 */
@Slf4j
@Service
@Transactional
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService {

}
