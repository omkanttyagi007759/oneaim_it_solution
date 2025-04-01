package com.one.aim.service;

import com.one.aim.rq.LoginRq;
import com.one.aim.rq.UserRq;
import com.one.vm.core.BaseRs;

public interface UserService {

	public BaseRs saveUser(UserRq rq) throws Exception;

	public BaseRs validLoginUser(LoginRq rq);

}
