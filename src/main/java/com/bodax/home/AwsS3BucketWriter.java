package com.bodax.home;

import com.bodax.home.pojo.TimeInterval;

public interface AwsS3BucketWriter {
    void writeData(String data, String type, TimeInterval timeInterval);
}
