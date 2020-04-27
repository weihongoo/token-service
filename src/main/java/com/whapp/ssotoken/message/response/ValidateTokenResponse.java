package com.whapp.ssotoken.message.response;

import java.util.Map;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
public class ValidateTokenResponse {	
	
	public static final int ERROR_MAX_LENGTH = 500;
	
	@JsonProperty("validToken")
	private boolean validToken;
	
	@JsonProperty("username")
	@Size(max = 150)
	private String username;

	@JsonProperty("error")
	@Size(max = ERROR_MAX_LENGTH)
	private String error;

	@JsonProperty("claims")
	private Map<String, Object>	claims;
	
	public ValidateTokenResponse(String error) {
		this.error = error;
	}
}