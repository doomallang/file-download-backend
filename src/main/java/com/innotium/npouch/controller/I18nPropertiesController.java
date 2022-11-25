package com.innotium.npouch.controller;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innotium.npouch.annotation.LoginNotRequire;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // 컨트롤러에서 설정
public class I18nPropertiesController {
	@LoginNotRequire
	@RequestMapping(value = "/api/i18nProperties", method = RequestMethod.GET)
	public void getI18nProperties(HttpServletRequest request, HttpServletResponse response, @RequestParam String propertiesName) {
		Resource resource = new ClassPathResource("/i18n/"+propertiesName + ".json");
		if(!resource.exists()) {
			return;
		}
		try (OutputStream outputStream = response.getOutputStream();
			InputStream inputStream = resource.getInputStream();) {
			if(inputStream != null) {
				IOUtils.copy(inputStream, outputStream);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
