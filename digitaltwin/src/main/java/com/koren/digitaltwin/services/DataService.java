package com.koren.digitaltwin.services;

import com.koren.digitaltwin.models.SensorData;
import com.koren.digitaltwin.models.WifiSignal;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    public List<SensorData> readCsvFile(String filePath) throws IOException {
        List<SensorData> csvDataList = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource("data.csv");
        try (Reader reader = new FileReader(Paths.get(resource.toURI()).toFile()); CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : csvParser) {
                int nodeId = Integer.parseInt(csvRecord.get(0));
                int tempValue = Integer.parseInt(csvRecord.get(1));
                int humValue = Integer.parseInt(csvRecord.get(2));
                int tvocValue = Integer.parseInt(csvRecord.get(3));

                WifiSignal wifiValue = new WifiSignal(
                                Integer.parseInt(csvRecord.get(4)),
                                Integer.parseInt(csvRecord.get(5)),
                                Integer.parseInt(csvRecord.get(6))
                );

                SensorData sensorData = new SensorData(
                        nodeId, tempValue, humValue, tvocValue, wifiValue
                );
                csvDataList.add(sensorData);
            }

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return csvDataList;
    }
}
