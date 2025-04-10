package com.bodax.home;

import com.bodax.home.client.TransparencyApiClient;
import io.micronaut.function.aws.MicronautRequestHandler;
import jakarta.inject.Inject;

public class LambdaMainHandler extends MicronautRequestHandler<String, String> {

    @Inject
    private AwsS3BucketWriter awsS3BucketWriter;
    @Inject
    private TransparencyApiClient transparencyApiClient;
    @Inject
    private CsvDataWriter csvDataWriter;

    @Override
    public String execute(String input) {
        var generationData = transparencyApiClient.getGenerationData();
        var csvData = csvDataWriter.writeData(generationData);
        awsS3BucketWriter.writeData(csvData, generationData.getType(), generationData.getTimeInterval());
        return "Fetched data by lambda function have been done";
    }
}
