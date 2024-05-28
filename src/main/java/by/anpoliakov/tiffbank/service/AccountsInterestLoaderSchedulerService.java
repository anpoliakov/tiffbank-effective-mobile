//package by.anpoliakov.tiffbank.service;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.ResourceAccessException;
//
//@Service
//public class AccountsInterestLoaderSchedulerService {
//    @Scheduled(fixedDelayString = "${api.weather.delay.scan}")
//    protected void scanApi() {
//        try {
//            WeatherApiResponse weatherApiResponse =
//                    restTemplate.getForObject(
//                            weatherConfig.getUrl(),
//                            WeatherApiResponse.class,
//                            weatherConfig.getCity(),
//                            weatherConfig.getKey());
//
//            if (weatherApiResponse == null) {
//                LOGGER.warn("Unable to save weather API response: empty response");
//            } else {
//                WeatherEntity weatherEntity = weatherConverterService.convertToWeatherEntity(weatherApiResponse);
//                weatherRepository.save(weatherEntity);
//            }
//
//        } catch (ResourceAccessException e) {
//            LOGGER.error("Unable to execute weather API: {}", weatherConfig.getUrl());
//        }
//    }
//}
