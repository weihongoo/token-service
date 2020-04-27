package com.whapp.ssotoken.message.request;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor
public class ValidateTokenRequest {
	@JsonProperty("destination")
    @Size(max = 100)
    private String destination;
	
	@JsonProperty("token")
	@Size(max = 500)
	private String token;
}
