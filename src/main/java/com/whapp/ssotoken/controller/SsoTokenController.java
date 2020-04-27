package com.whapp.ssotoken.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.whapp.ssotoken.message.request.GenerateTokenRequest;
import com.whapp.ssotoken.message.request.ValidateTokenRequest;
import com.whapp.ssotoken.message.response.GenerateTokenResponse;
import com.whapp.ssotoken.message.response.ValidateTokenResponse;
import com.whapp.ssotoken.util.JwtUtil;

@RestController
public class SsoTokenController {

	private static final Logger logger = LoggerFactory.getLogger(SsoTokenController.class);

	@Autowired
	JwtUtil tokenUtil;

	@PostMapping("/generateToken")
	public ResponseEntity<GenerateTokenResponse> generateToken(@Valid @RequestBody GenerateTokenRequest tokenRequest) {

		// generate token
		String token = tokenUtil.generateToken("testuser", tokenRequest.getClaims());

		logger.info(printGenerateTokenInfo("Generate Token Success >>", tokenRequest, token, ""));

		// return generated token in response
		GenerateTokenResponse tokenResponse = new GenerateTokenResponse();
		tokenResponse.setToken(token);
		return ResponseEntity.ok(tokenResponse);
	}

	@PostMapping("/validateToken")
	public ResponseEntity<ValidateTokenResponse> validateToken(@Valid @RequestBody ValidateTokenRequest tokenRequest) {
		boolean isValidToken = false;
		List<String> errorList = new ArrayList<>();
		String username = "";

		isValidToken = tokenUtil.validateToken(tokenRequest.getToken(), errorList);
		
		// validate token
		if (isValidToken) {
			// get username from token
			username = tokenUtil.getUserNameFromToken(tokenRequest.getToken());
		}

		logger.info(printValidateTokenInfo(tokenRequest, username, isValidToken, errorList));

		// return token validation result in response
		ValidateTokenResponse tokenResponse = new ValidateTokenResponse();
		tokenResponse.setValidToken(isValidToken);
		if(isValidToken) {
			tokenResponse.setUsername(username);
			tokenResponse.setClaims(tokenUtil.getClaimsFromToken(tokenRequest.getToken()));
		} else {
			if(errorList != null) {
				String error = StringUtils.collectionToDelimitedString(errorList, ",");
				int maxLength = GenerateTokenResponse.ERROR_MAX_LENGTH;
				tokenResponse.setError(error.length() > maxLength ? error.substring(0, maxLength) : error);				
			}
		}

		return ResponseEntity.ok(tokenResponse);
	}

	private String printGenerateTokenInfo(String header, GenerateTokenRequest tokenRequest, String token, String error) {
		StringBuffer str = new StringBuffer(header);
		str.append("|Origin=").append(tokenRequest.getOrigin());
		str.append("|Destination=").append(tokenRequest.getDestination());
		str.append("|Username=").append(tokenRequest.getUsername());
		str.append("|Token=").append(token);
		if (StringUtils.hasLength(error)) {
			str.append("\nError=").append(error);
		}
		return str.toString();
	}

	private String printValidateTokenInfo(ValidateTokenRequest tokenRequest, String username, boolean isValidToken,
			List<String> errorList) {
		StringBuffer str = new StringBuffer("Validate Token >>");
		str.append("|Destination=").append(tokenRequest.getDestination());
		str.append("|Username=").append(username);
		str.append("|ValidToken=").append(isValidToken);
		str.append(isValidToken ? "" : "|Error=" + StringUtils.collectionToDelimitedString(errorList, ","));
		str.append("|Token=").append(tokenRequest.getToken());
		return str.toString();
	}
}