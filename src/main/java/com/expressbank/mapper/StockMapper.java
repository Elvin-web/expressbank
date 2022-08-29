package com.expressbank.mapper;

import com.expressbank.dto.signIn.response.SignInResponse;
import com.expressbank.dto.stoks.response.Stock;
import com.expressbank.entity.StockEntity;
import com.expressbank.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class StockMapper {

    public static final StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);


//    @Mappings({
//            @Mapping(target = "id", source = "id"),
//            @Mapping(target = "callId", source = "call.id"),
//            @Mapping(target = "message", source = "notificationMessage.name"),
//            @Mapping(target = "readStatusId", source = "readStatus"),
//            @Mapping(target = "readStatus", source = "readStatus", qualifiedByName = "readStatus"),
//            @Mapping(target = "status", source = "active", qualifiedByName = "status"),
//            @Mapping(target = "dataDate", source = "dataDate", qualifiedByName = "dataDate")
//    })
    public abstract StockEntity dtoToEntity(Stock stock);
}
