package com.bodax.home;

import com.bodax.home.pojo.TimeInterval;
import jakarta.inject.Singleton;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;

@Singleton
public class AwsS3BucketWriterImpl implements AwsS3BucketWriter {

    private final S3Client s3Client;
    private static final String BUCKET_NAME = "transparency-paring-results-bucket";
    private static final String FILE_NAME_PREFIX = "generation_data";

    public AwsS3BucketWriterImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void writeData(String data, String type, TimeInterval timeInterval) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(getFileName(type, timeInterval))
                .contentType("text/csv")
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromString(data, StandardCharsets.UTF_8));
    }

    private String getFileName(String type, TimeInterval timeInterval) {
        return FILE_NAME_PREFIX + "_"
                + type + "_"
                + timeInterval.getStart().toLocalDate() + "_"
                + timeInterval.getEnd().toLocalDate() + ".csv";
    }
}
