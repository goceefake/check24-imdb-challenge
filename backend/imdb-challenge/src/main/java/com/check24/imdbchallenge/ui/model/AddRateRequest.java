package com.check24.imdbchallenge.ui.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddRateRequest {
	private Long id;

	@NotNull
	@Max(value = 5, message = "rate value should be less than 5")
	private int rate;
}
