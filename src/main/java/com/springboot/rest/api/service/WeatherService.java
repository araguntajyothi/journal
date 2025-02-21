package com.springboot.rest.api.service;


import com.springboot.rest.api.cache.AppCache;
import com.springboot.rest.api.constants.Placeholders;
import com.springboot.rest.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;


    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("Weather_of_" + city, WeatherResponse.class);

        if(weatherResponse != null){
            return weatherResponse;
        } else {
            String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);

            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);

            WeatherResponse body = response.getBody();

            if(body != null){
                redisService.set("Weather_of_" + city, body, 3000l);
            }
            return body;
        }

    }


//    public WeatherResponse postWeather(String city) {
//        String finalAPI = API.replace("CITY", city).replace("API_KEY", apiKey);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("key","value");
//        User user = User.builder().userName("kiran").password("kiran").build();
//
//        HttpEntity<User> http = new HttpEntity<>(user, httpHeaders);
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, http, WeatherResponse.class);
//
//        return response.getBody();
//
//    }
}
