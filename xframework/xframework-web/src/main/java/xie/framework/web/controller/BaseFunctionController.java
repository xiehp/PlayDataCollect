package xie.framework.web.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import xie.framework.web.utils.UploadUtil;

import java.io.File;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

public class BaseFunctionController<M, ID extends Serializable> extends BaseController {

	private final String PARAM_NAME = "param";

	@RequestMapping(value = "/uploadFile")
	public ResponseEntity<?> fileUpload(@RequestParam("fileUpload") CommonsMultipartFile file, @RequestParam String... params) {

		Map<String, Object> result = null;
		try {
			String fileName = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");

			// TODO 获得路径
			String fileUploadPath = "???";

			String[] tempPathArr = UploadUtil.getCreatePathWithSuffix(fileName, UploadUtil.DEFAULT, fileUploadPath);

			String absolutePath = tempPathArr[0];

			File tempFile = new File(absolutePath);
			file.transferTo(tempFile);

			result = getProcessResult(tempFile, params);

		} catch (Exception e) {
			_log.error("上传文件错误:{}", e.getCause());
		}

		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/autoComplete")
	public ResponseEntity<?> autoComplete(@RequestBody Map<String, Object> params) {
		List<String> list = getAutoCompleteList((String) params.get(PARAM_NAME));
		return new ResponseEntity<List<String>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/chosen")
	public ResponseEntity<?> chosen(@RequestBody Map<String, Object> params) {
		List<M> list = getChosenList(params);
		return new ResponseEntity<List<M>>(list, HttpStatus.OK);
	}

	public List<M> getChosenList(Map<String, Object> params) {
		return null;
	}

	public List<String> getAutoCompleteList(String param) {
		return null;
	}

	public Map<String, Object> getProcessResult(File tempFile, String[] params) {
		return null;
	}
}
