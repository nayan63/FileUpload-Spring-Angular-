package com.nayan.file_upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nayan.file_upload.entity.FileInformation;

public interface FileInformationRepository extends JpaRepository<FileInformation, Long> {

}
