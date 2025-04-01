package com.one.aim.rq;

import com.one.vm.core.BaseRq;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//Take data from front-end
@Getter
@Setter
@NoArgsConstructor
public class LoginRq extends BaseRq {

	private static final long serialVersionUID = 1L;

	//Username Or Email
	private String userName;

	private String password;

}
