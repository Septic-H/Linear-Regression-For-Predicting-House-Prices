package org.linearregressionforpredictinghouseprices;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RealEstateData {
    private static final int HEADER_LINES = 1;

    public List<RealEstateRecord> loadRecords(String filename) {
        List<RealEstateRecord> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            for (int i = 0; i < HEADER_LINES; i++) {
                reader.readLine();
            }
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                RealEstateRecord record = new RealEstateRecord();
                record.setTransactionDate(parts[1]);
                record.setHouseAge(Double.parseDouble(parts[2]));
                record.setDistanceToMRT(Double.parseDouble(parts[3]));
                record.setNumConvenienceStores(Integer.parseInt(parts[4]));
                record.setLatitude(Double.parseDouble(parts[5]));
                record.setLongitude(Double.parseDouble(parts[6]));
                record.setPrice(Double.parseDouble(parts[7]));
                records.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

    public void saveRecords(List<RealEstateRecord> records, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("X2 house age,X3 distance to the nearest MRT station,X4 number of convenience stores,X5 latitude,X6 longitude,Y house price of unit area\n");
            for (RealEstateRecord record : records) {
                writer.write(record.getHouseAge() + "," + record.getDistanceToMRT() + "," + record.getNumConvenienceStores() + "," + record.getLatitude() + "," + record.getLongitude() + "," + record.getPredictedPrice() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
