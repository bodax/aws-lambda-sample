package com.bodax.home;

import com.bodax.home.pojo.GenerationData;
import com.bodax.home.pojo.Period;
import com.bodax.home.pojo.Point;
import com.bodax.home.pojo.TimeSeries;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Singleton
public class CsvDataWriter {

    public String writeData(GenerationData data) {
        Map<String, Map<String, Integer>> rowsByMtu = new LinkedHashMap<>();
        Set<String> types = new LinkedHashSet<>();

        for (TimeSeries ts : data.getTimeSeries()) {
            Period period = ts.getPeriod();
            String psrType;
            if (ts.getMktPSRType() != null && ts.getMktPSRType().getPsrType() != null) {
                psrType = ts.getMktPSRType().getPsrType();
            } else {
                psrType = "Scheduled Generation (MW)";
            }
            types.add(psrType);
            for (Point point : period.getPoints()) {
                String mtu = getIntervalDate(period, point);
                rowsByMtu.computeIfAbsent(mtu, k -> new HashMap<>()).put(psrType, point.getQuantity());
            }
        }

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema.Builder schemaBuilder = CsvSchema.builder()
                .addColumn("MTU");
        for (String type : types) {
            schemaBuilder.addColumn(type);
        }
        CsvSchema schema = schemaBuilder
                .setUseHeader(true)
                .setColumnSeparator(',')
                .setQuoteChar('"')
                .build();

        List<ObjectNode> csvRows = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : rowsByMtu.entrySet()) {
            ObjectNode node = csvMapper.createObjectNode();
            node.put("MTU", entry.getKey());
            Map<String, Integer> typeQuantities = entry.getValue();
            for (String type : types) {
                Integer value = typeQuantities.get(type);
                if (value != null) {
                    node.put(type, value);
                } else {
                    node.putNull(type);
                }
            }
            csvRows.add(node);
        }
        try {
            return csvMapper.writer(schema).writeValueAsString(csvRows);
        } catch (IOException e) {
            log.error("CSV file couldn't generated: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private long parseResolution(String resolution) {
        return switch (resolution) {
            case "PT15M" -> 15 * 60;
            case "PT30M" -> 30 * 60;
            case "PT45M" -> 45 * 60;
            case "PT60M" -> 60 * 60;
            default -> throw new IllegalArgumentException("Unsupported resolution: " + resolution);
        };
    }

    private String getIntervalDate(Period period, Point point) {
        LocalDateTime startDateTime = period.getTimeInterval().getStart();
        long secondsPerPoint = parseResolution(period.getResolution());
        Duration durationPerPoint = Duration.ofSeconds(secondsPerPoint);
        LocalDateTime start = startDateTime.plusSeconds(secondsPerPoint * (point.getPosition() - 1));
        LocalDateTime end = start.plus(durationPerPoint);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        return formatter.format(start) + " - " + formatter.format(end);
    }
}
