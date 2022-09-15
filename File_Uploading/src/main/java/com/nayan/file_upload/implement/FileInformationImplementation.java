package com.nayan.file_upload.implement;

import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.Paths.get;
import static java.nio.file.Files.copy;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.nayan.file_upload.entity.FileInformation;
import com.nayan.file_upload.repository.FileInformationRepository;
import com.nayan.file_upload.service.FileInformationService;

@Component
public class FileInformationImplementation implements FileInformationService {
	
	@Autowired
	ServletContext context;
	@Autowired
	private FileInformationRepository fileInfoRepo;
	
	private final String UPLOAD_PATH="F:\\JAVA TUTO\\File Upload With Spring & Angular\\File_Uploading\\src\\main\\resources\\static";

	@Override
	public FileInformation uploadFile(MultipartFile file) throws Exception {
		try {
			String filename = new Date().getTime()+file.getOriginalFilename();
			FileInformation fileInfo = new FileInformation(
					file.getContentType(),
					filename,
					UPLOAD_PATH
					);
	        Path fileStorage = get(UPLOAD_PATH, filename).toAbsolutePath().normalize();
			copy(file.getInputStream(), fileStorage,REPLACE_EXISTING);
			return fileInfoRepo.save(fileInfo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Resource downloadFile(String fileName) throws FileNotFoundException {
		try {
            Path file = get(UPLOAD_PATH,fileName).toAbsolutePath().normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } 
            else {
                throw new FileNotFoundException("Could not find file");
            }
          } 
          catch (MalformedURLException e) {
              throw new FileNotFoundException("Could not download file");
          }
	}

	@Override
	public byte[] getFile(String filename) throws IOException {
		return Files.readAllBytes(new File(UPLOAD_PATH+'/'+filename).toPath());
	}

}
