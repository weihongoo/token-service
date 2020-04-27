package com.whapp.ssotoken.message.request;

import java.util.Map;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor
public class GenerateTokenRequest {
	@JsonProperty("origin")
    @Size(max = 100)
    private String origin;

	@JsonProperty("destination")
    @Size(max = 100)
    private String destination;
	
	@JsonProperty("username")
    @Size(max = 150)
    private String username;
	
	@JsonProperty("claims")
	private Map<String, Object>	claims;
}
