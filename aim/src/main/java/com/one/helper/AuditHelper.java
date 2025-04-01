package com.one.helper;

import java.time.LocalDateTime;

import com.one.bo.core.BaseBO;
import com.one.utils.Utils;

public class AuditHelper {

	public static BaseBO createAudit(BaseBO baseBO, String username) {

		if (null == baseBO) {
			baseBO = new BaseBO();
		}

		baseBO.setCreatedon(LocalDateTime.now());
		baseBO.setUpdatedon(baseBO.getCreatedon());
		if (Utils.isNotEmpty(username)) {
			baseBO.setCreatedby(username);
			baseBO.setUpdatedby(username);
		}
		return baseBO;
	}

	public static BaseBO updateAudit(BaseBO baseBO, String username) {

		baseBO.setUpdatedon(LocalDateTime.now());
		if (Utils.isNotEmpty(username)) {
			baseBO.setUpdatedby(username);
		}
		return baseBO;
	}

}
