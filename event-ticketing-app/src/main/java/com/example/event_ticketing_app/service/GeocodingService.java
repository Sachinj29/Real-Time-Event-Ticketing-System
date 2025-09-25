package com.example.event_ticketing_app.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeocodingService {

    private final GeoApiContext geoApiContext;

    public GeocodingResult[] geocodeAddress(String address) throws Exception {
        log.info("Geocoding address: {}", address);
        return GeocodingApi.geocode(geoApiContext, address).await();
    }

    public LatLng getCoordinatesFromAddress(String address) {
        try {
            GeocodingResult[] results = geocodeAddress(address);
            if (results != null && results.length > 0) {
                return results[0].geometry.location;
            }
        } catch (Exception e) {
            log.error("Error geocoding address: {}", address, e);
        }
        return null;
    }

    public AddressDetails getAddressDetails(String address) {
        try {
            GeocodingResult[] results = geocodeAddress(address);
            if (results != null && results.length > 0) {
                GeocodingResult result = results[0];
                return parseAddressComponents(result);
            }
        } catch (Exception e) {
            log.error("Error getting address details: {}", address, e);
        }
        return null;
    }

//    private AddressDetails parseAddressComponents(GeocodingResult result) {
//        AddressDetails details = new AddressDetails();
//        details.setFormattedAddress(result.formattedAddress);
//        details.setLatitude(result.geometry.location.lat);
//        details.setLongitude(result.geometry.location.lng);
//
//        for (AddressComponent component : result.addressComponents) {
//            for (AddressComponentType type : component.types) {
//                switch (type) {
//                    case LOCALITY:
//                        details.setCity(component.longName);
//                        break;
//                    case ADMINISTRATIVE_AREA_LEVEL_1:
//                        details.setState(component.longName);
//                        break;
//                    case COUNTRY:
//                        details.setCountry(component.longName);
//                        break;
//                    case POSTAL_CODE:
//                        details.setPostalCode(component.longName);
//                        break;
//                }
//            }
//        }
//        return details;
//    }


    private AddressDetails parseAddressComponents(GeocodingResult result) {
        AddressDetails details = new AddressDetails();
        details.setFormattedAddress(result.formattedAddress);
        details.setLatitude(result.geometry.location.lat);
        details.setLongitude(result.geometry.location.lng);

        log.info("Parsing address components for: {}", result.formattedAddress);

        for (AddressComponent component : result.addressComponents) {
            log.info("Component: {} - Types: {}", component.longName, java.util.Arrays.toString(component.types));

            for (AddressComponentType type : component.types) {
                switch (type) {
                    case LOCALITY:
                        details.setCity(component.longName);
                        break;
                    case ADMINISTRATIVE_AREA_LEVEL_1:
                        details.setState(component.longName);
                        break;
                    case COUNTRY:
                        details.setCountry(component.longName);
                        break;
                    case POSTAL_CODE:
                        log.info("Found postal code: {}", component.longName);
                        details.setPostalCode(component.longName);
                        break;
                }
            }
        }

        log.info("Final parsed details: {}", details);
        return details;
    }

    // Inner class for address details
    @lombok.Data
    public static class AddressDetails {
        private String formattedAddress;
        private Double latitude;
        private Double longitude;
        private String city;
        private String state;
        private String country;
        private String postalCode;
    }
}
