package com.one.aim.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.one.aim.bo.UserBO;
import com.one.aim.constants.ErrorCodes;
import com.one.aim.constants.MessageCodes;
import com.one.aim.helper.UserHelper;
import com.one.aim.mapper.UserMapper;
import com.one.aim.repo.UserRepo;
import com.one.aim.rq.LoginRq;
import com.one.aim.rq.UserRq;
import com.one.aim.rs.UserRs;
import com.one.aim.rs.data.UserDataRs;
import com.one.aim.service.UserService;
import com.one.constants.StringConstants;
import com.one.helper.AuditHelper;
import com.one.utils.Utils;
import com.one.vm.core.BaseRs;
import com.one.vm.utils.ResponseUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	// Sign Up
	@Override
	public BaseRs saveUser(UserRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing saveCompany(CompanyRq) ->");
		}

		List<String> errors = UserHelper.validateUser(rq);

		if (Utils.isNotEmpty(errors)) {
			log.error(ErrorCodes.EC_INVALID_INPUT);
			return ResponseUtils.failure(ErrorCodes.EC_INVALID_INPUT, errors);
		}
		String docId = Utils.getValidString(rq.getDocId());
		String message = StringConstants.EMPTY;
		UserBO userBO = null;
		if (Utils.isNotEmpty(docId)) { // UPDATE
			long id = Long.parseLong(docId);
			Optional<UserBO> optUserBO = userRepo.findById(id);
			userBO = optUserBO.get();
			if (userBO == null) {
				log.error(ErrorCodes.EC_USER_NOT_FOUND);
				return ResponseUtils.failure(ErrorCodes.EC_USER_NOT_FOUND);
			}
			AuditHelper.updateAudit(userBO, rq.getUserName());
			message = MessageCodes.MC_UPDATED_SUCCESSFUL;
		} else {
			userBO = new UserBO(rq.getUserName()); // SAVE
			message = MessageCodes.MC_SAVED_SUCCESSFUL;
		}
		String email = Utils.getValidString(rq.getEmail());
		if (!email.equals(Utils.getValidString(userBO.getEmail()))) {
			UserBO extUserBO = userRepo.findByEmail(email);
			if (extUserBO != null) {
				log.error(ErrorCodes.EC_USER_ALREADY_EXIST);
				return ResponseUtils.failure(ErrorCodes.EC_USER_ALREADY_EXIST);
			}
			userBO.setEmail(email);
		}
		String userName = Utils.getValidString(rq.getUserName());
		if (!userName.equals(userBO.getUsername())) {
			userBO.setUsername(userName);
		}
		String phoneNo = Utils.getValidString(rq.getPhoneNo());
		if (!phoneNo.equals(userBO.getPhoneno())) {
			userBO.setPhoneno(phoneNo);
		}
		String password = Utils.getValidString(rq.getPassword());
		if (!password.equals(userBO.getPassword())) {
			userBO.setPassword(password);
		}
		userRepo.save(userBO);
		UserRs userRs = UserMapper.mapToUserRs(userBO);
		return ResponseUtils.success(new UserDataRs(message, userRs));
	}

	// Login
	@Override
	public BaseRs validLoginUser(LoginRq rq) {

		if (log.isDebugEnabled()) {
			log.debug("Executing validLoginUser(LoginRq) ->");
		}
		try {
			String username = Utils.getValidString(rq.getUserName());
			String password = Utils.getValidString(rq.getPassword());
			UserBO emailUserBO = userRepo.findByEmailAndPassword(username, password);
			UserBO userNameUserBO = userRepo.findByUsernameAndPassword(username, password);
			if (emailUserBO == null && userNameUserBO == null) {
				log.error(ErrorCodes.EC_INVALID_USERNAME_PASSWORD);
				return ResponseUtils.failure(ErrorCodes.EC_INVALID_USERNAME_PASSWORD);
			}
			String message = MessageCodes.MC_LOGIN_SUCCESSFUL;
			return ResponseUtils.success(new UserDataRs(message));
		} catch (Exception e) {
			log.debug("Exception in validLoginUser(LoginRq) -> " + e);
			return ResponseUtils.failure(e);
		}
	}

}
