package com.one.bo.core;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseBO {

	private boolean enabled = true;

	private String createdby;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdon;

	private String updatedby;

	@LastModifiedDate
	private LocalDateTime updatedon;

	public BaseBO(String createdby) {
		super();
		this.createdby = createdby;
		this.createdon = LocalDateTime.now();
		this.updatedby = createdby;
		this.updatedon = createdon;
	}
}
