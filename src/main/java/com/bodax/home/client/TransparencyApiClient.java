package com.bodax.home.client;

import com.bodax.home.pojo.GenerationData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Singleton
public class TransparencyApiClient {

    private String apiUrl;
    private final String securityToken;
    private final HttpClient httpClient;
    private final XmlMapper xmlMapper;

    public TransparencyApiClient(HttpClient httpClient,
                                 XmlMapper xmlMapper,
                                 @Property(name = "transparency.api.url") String apiUrl,
                                 @Property(name = "transparency.api.securityToken") String securityToken) {
        this.httpClient = httpClient;
        this.xmlMapper = xmlMapper;
        this.securityToken = securityToken;
        this.apiUrl = apiUrl + "&" + "securityToken=" + securityToken;
    }

    /**
     * Method to get generation data from the API.
     *
     * @param `apiUrl` API URL to fetch data from
     * @return Response body as a String
     */
    public GenerationData getGenerationData() {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .GET()
                    .build();

            log.info("Fetching data from url: " + apiUrl);
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (HttpStatus.OK.getCode() == response.statusCode()) {
                log.info("Fetching data was sucesfull");
                return unMarshall(response.body());
            } else {
                log.error("API request failed with status code: {}", response.statusCode());
                throw new RuntimeException("API request failed with status code: " + response.statusCode());
            }

        } catch (Exception e) {
            log.error("Couldn't obtain data from API, url: {} ", apiUrl, e);
            throw new RuntimeException(e);
        }
    }

    private GenerationData unMarshall(String xmlData) throws JsonProcessingException {
        return xmlMapper.readValue(xmlData, GenerationData.class);
    }
}
