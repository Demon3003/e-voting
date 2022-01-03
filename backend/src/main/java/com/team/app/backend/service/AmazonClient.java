package com.team.app.backend.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
@Slf4j
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("${application.url}")
    private String endpointUrl;

    @Value("${img.upload.path}")
    private String uploadPath;

    @Value("${s3.bucketName}")
    private String bucketName;

    @Value("${s3.accessKeyId}")
    private String accessKeyId;

    @Value("${s3.secretKey}")
    private String secretKey;

    @Value("${s3.region}")
    private String region;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKeyId, this.secretKey);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public String uploadFile(MultipartFile multipartFile) {

        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            fileUrl = endpointUrl + "/img/" + file.getName();
            log.info("dmzh100: fileUrl: {}", fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    // public String uploadFile(MultipartFile multipartFile) {

    //     String fileUrl = "";
    //     try {
    //         File file = convertMultiPartToFile(multipartFile);
    //         String fileName = generateFileName(multipartFile);
    //         fileUrl = endpointUrl + "/" + fileName;
    //         uploadFileTos3bucket(fileName, file);
    //         file.delete();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return fileUrl;
    // }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(bucketName, fileName);
        return "Successfully deleted";
    }

    // private void uploadFileTos3bucket(String fileName, File file) {
    //     s3client.putObject(bucketName, fileName, file);
    // }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(generateFileName(file));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return uploadPath + "/" +
            (new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_"));
    }
}