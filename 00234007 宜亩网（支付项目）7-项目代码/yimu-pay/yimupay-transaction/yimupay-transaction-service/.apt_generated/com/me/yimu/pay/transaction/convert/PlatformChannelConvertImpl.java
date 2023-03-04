package com.me.yimu.pay.transaction.convert;

import com.me.yimu.pay.transaction.api.dto.PlatformChannelDTO;
import com.me.yimu.pay.transaction.entity.PlatformChannel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-03T16:24:58+0800",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.21.0.v20200304-1404, environment: Java 1.8.0_301 (Oracle Corporation)"
)
public class PlatformChannelConvertImpl implements PlatformChannelConvert {

    @Override
    public PlatformChannelDTO entity2dto(PlatformChannel entity) {
        if ( entity == null ) {
            return null;
        }

        PlatformChannelDTO platformChannelDTO = new PlatformChannelDTO();

        platformChannelDTO.setChannelCode( entity.getChannelCode() );
        platformChannelDTO.setChannelName( entity.getChannelName() );
        platformChannelDTO.setId( entity.getId() );

        return platformChannelDTO;
    }

    @Override
    public PlatformChannel dto2entity(PlatformChannelDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PlatformChannel platformChannel = new PlatformChannel();

        platformChannel.setChannelCode( dto.getChannelCode() );
        platformChannel.setChannelName( dto.getChannelName() );
        platformChannel.setId( dto.getId() );

        return platformChannel;
    }

    @Override
    public List<PlatformChannelDTO> listentity2listdto(List<PlatformChannel> PlatformChannel) {
        if ( PlatformChannel == null ) {
            return null;
        }

        List<PlatformChannelDTO> list = new ArrayList<PlatformChannelDTO>( PlatformChannel.size() );
        for ( PlatformChannel platformChannel : PlatformChannel ) {
            list.add( entity2dto( platformChannel ) );
        }

        return list;
    }
}
