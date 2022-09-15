package com.nayan.file_upload.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nayan.file_upload.entity.FileInformation;

@Service
public interface FileInformationService {

	FileInformation uploadFile(MultipartFile file) throws Exception;
	Resource downloadFile(String fileName) throws FileNotFoundException;
	byte[] getFile(String filename) throws IOException;
}
