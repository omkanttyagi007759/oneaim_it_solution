package com.one.vm.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.one.constants.StringConstants;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseRq extends BaseVM {

    private static final long serialVersionUID = 85381423028274032L;

    private String clientType = StringConstants.EMPTY;

//    @JsonIgnore
//    private String username = StringConstants.EMPTY;

    @JsonIgnore
    private Long userDocId;

}
