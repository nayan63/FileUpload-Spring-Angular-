package com.nayan.file_upload.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nayan.file_upload.entity.FileInformation;
import com.nayan.file_upload.service.FileInformationService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FileInformationController {

	@Autowired
	FileInformationService fileInfoService;
	
	@PostMapping("/upload-file")
	public FileInformation uploadFile(@RequestParam MultipartFile file) throws Exception
	{
		return fileInfoService.uploadFile(file);
	}
	
	@GetMapping("/download/{file}")
	ResponseEntity<Resource> donloadFile(@PathVariable String file) throws FileNotFoundException
	{
		Resource resource = fileInfoService.downloadFile(file);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", file);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
                .headers(httpHeaders).body(resource);
	}
	
	@GetMapping("/get/{file}")
	ResponseEntity<byte[]> getFile(@PathVariable String file) throws IOException
	{
		byte[] image = fileInfoService.getFile(file);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.IMAGE_JPEG)
				.body(image);
	}
	
	
	
}