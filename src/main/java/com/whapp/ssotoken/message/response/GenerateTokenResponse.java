package com.whapp.ssotoken.message.response;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
public class GenerateTokenResponse {	
	
	public static final int ERROR_MAX_LENGTH = 500;
		
	@JsonProperty("token")
	@Size(max = 500)
	private String token;

	@JsonProperty("error")
	@Size(max = ERROR_MAX_LENGTH)
	private String error;
	
	public GenerateTokenResponse(String error) {
		this.error = error;
	}
}