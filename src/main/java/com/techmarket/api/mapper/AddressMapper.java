package com.techmarket.api.mapper;

import com.techmarket.api.dto.address.AddressAdminDto;
import com.techmarket.api.dto.address.AddressDto;
import com.techmarket.api.form.address.CreateAddressForm;
import com.techmarket.api.form.address.UpdateAddressForm;
import com.techmarket.api.model.Address;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {NationMapper.class, UserMapper.class})
public interface AddressMapper {
  @BeanMapping(ignoreByDefault = true)
  @Mapping(source = "address", target = "address")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "phone", target = "phone")
  Address fromCreateAddressFormToEntity(CreateAddressForm createAddressForm);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(source = "address", target = "address")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "phone", target = "phone")
  void fromUpdateAddressFormToEntity(UpdateAddressForm updateAddressForm, @MappingTarget Address address);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(source = "id", target = "id")
  @Mapping(source = "address", target = "address")
  @Mapping(source = "ward", target = "wardInfo", qualifiedByName = "fromEntityToAutoCompleteDto")
  @Mapping(source = "district", target = "districtInfo", qualifiedByName = "fromEntityToAutoCompleteDto")
  @Mapping(source = "province", target = "provinceInfo", qualifiedByName = "fromEntityToAutoCompleteDto")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "phone", target = "phone")
  @Named("fromEntityToAddressDto")
  AddressDto fromEntityToAddressDto(Address address);

  @IterableMapping(elementTargetType = AddressDto.class, qualifiedByName = "fromEntityToAddressDto")
  List<AddressDto> fromEntityToAddressDtoList(List<Address> addresses);


  @BeanMapping(ignoreByDefault = true)
  @Mapping(source = "id", target = "id")
  @Mapping(source = "address", target = "address")
  @Mapping(source = "ward", target = "wardInfo", qualifiedByName = "fromEntityToAdminDto")
  @Mapping(source = "district", target = "districtInfo", qualifiedByName = "fromEntityToAdminDto")
  @Mapping(source = "province", target = "provinceInfo", qualifiedByName = "fromEntityToAdminDto")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "phone", target = "phone")
  @Mapping(source = "modifiedDate", target = "modifiedDate")
  @Mapping(source = "createdDate", target = "createdDate")
  @Mapping(source = "user", target = "userInfo", qualifiedByName = "fromUserToUserDto")
  @Named("fromEntityToAddressAdminDto")
  AddressAdminDto fromEntityToAddressAdminDto(Address address);
  @IterableMapping(elementTargetType = AddressAdminDto.class, qualifiedByName = "fromEntityToAddressAdminDto")
  List<AddressAdminDto> fromEntityToAddressAdminDtoList(List<Address> addresses);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(source = "id", target = "id")
  @Mapping(source = "address", target = "address")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "phone", target = "phone")
  @Named("fromEntityToAddressDtoAutoComplete")
  AddressDto fromEntityToAddressDtoAutoComplete(Address address);

  @IterableMapping(elementTargetType = AddressDto.class, qualifiedByName = "fromEntityToAddressDtoAutoComplete")
  List<AddressDto> fromEntityToAddressDtoAutoCompleteList(List<Address> addresses);

}
