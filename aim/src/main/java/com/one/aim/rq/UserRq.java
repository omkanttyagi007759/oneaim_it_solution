package com.one.aim.rq;

import com.one.vm.core.BaseRq;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRq extends BaseRq {

	private static final long serialVersionUID = 1L;

	private String docId;

	private String userName;

	private String email;

	private String password;

	private String phoneNo;
}
