package net.unitedcode.interview;

import lombok.extern.log4j.Log4j2;
import net.unitedcode.interview.json.AirTemperature;
import org.testng.annotations.Test;

import java.util.Date;

import static io.restassured.RestAssured.given;

@Log4j2
public class UnitedCodeApiTest {

    @Test
    public void test001() {
        String expectedStreet = "Clementi Road";
        AirTemperature airTemperature = given()
                .when().get("https://api.data.gov.sg/v1/environment/air-temperature")
                .thenReturn().body().as(AirTemperature.class);
        String expectedId = airTemperature.getMetadata().getStations().stream()
                .filter(s -> s.getName().equals(expectedStreet))
                .findFirst().map(AirTemperature.Metadata.Stations::getId).orElse("");
        Date latestDate = airTemperature.getItems().stream().map(u -> u.timestamp).max(Date::compareTo).orElseThrow();
        Double temperature = airTemperature.getItems().stream()
                .filter(t -> t.timestamp.equals(latestDate)).findFirst().map(r -> r.readings).orElseThrow()
                .stream().filter(r -> r.station_id.equals(expectedId)).findFirst().map(r -> r.value).orElseThrow();
        log.info("Date: " + latestDate + ". Street: '" + expectedStreet + "', temperature: " + temperature);
    }

}
