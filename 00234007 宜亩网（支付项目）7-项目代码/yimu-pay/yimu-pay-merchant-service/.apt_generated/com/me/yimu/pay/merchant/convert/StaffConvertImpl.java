package com.me.yimu.pay.merchant.convert;

import com.me.yimu.pay.merchant.api.dto.StaffDTO;
import com.me.yimu.pay.merchant.entity.Staff;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-03T16:25:06+0800",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.21.0.v20200304-1404, environment: Java 1.8.0_301 (Oracle Corporation)"
)
public class StaffConvertImpl implements StaffConvert {

    @Override
    public StaffDTO entity2dto(Staff entity) {
        if ( entity == null ) {
            return null;
        }

        StaffDTO staffDTO = new StaffDTO();

        staffDTO.setFullName( entity.getFullName() );
        staffDTO.setId( entity.getId() );
        staffDTO.setLastLoginTime( entity.getLastLoginTime() );
        staffDTO.setMerchantId( entity.getMerchantId() );
        staffDTO.setMobile( entity.getMobile() );
        staffDTO.setPosition( entity.getPosition() );
        staffDTO.setStaffStatus( entity.getStaffStatus() );
        staffDTO.setStoreId( entity.getStoreId() );
        staffDTO.setUsername( entity.getUsername() );

        return staffDTO;
    }

    @Override
    public Staff dto2entity(StaffDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Staff staff = new Staff();

        staff.setFullName( dto.getFullName() );
        staff.setId( dto.getId() );
        staff.setLastLoginTime( dto.getLastLoginTime() );
        staff.setMerchantId( dto.getMerchantId() );
        staff.setMobile( dto.getMobile() );
        staff.setPosition( dto.getPosition() );
        staff.setStaffStatus( dto.getStaffStatus() );
        staff.setStoreId( dto.getStoreId() );
        staff.setUsername( dto.getUsername() );

        return staff;
    }

    @Override
    public List<StaffDTO> listentity2dto(List<Staff> staff) {
        if ( staff == null ) {
            return null;
        }

        List<StaffDTO> list = new ArrayList<StaffDTO>( staff.size() );
        for ( Staff staff1 : staff ) {
            list.add( entity2dto( staff1 ) );
        }

        return list;
    }
}
