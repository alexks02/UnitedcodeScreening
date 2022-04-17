package net.unitedcode.interview.json;

import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.List;

@SuppressWarnings({"java:S116","java:S1104"})
@Builder
@Value
public class AirTemperature {

    Metadata metadata;
    List<Item> items;
    ApiInfo api_info;

    @Value
    public static class Metadata {

        List<Stations> stations;
        String reading_type;
        String reading_unit;

        @Value
        public static class Stations {
            String id;
            String device_id;
            String name;
            Location location;

            @Value
            public static class Location {
                double latitude;
                double longitude;
            }

        }

    }

    public static class Item {
        public Date timestamp;
        public List<Reading> readings;

        public static class Reading {
            public String station_id;
            public double value;
        }
    }

    public static class ApiInfo {
        String status;
    }

}
