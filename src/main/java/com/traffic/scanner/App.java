package com.traffic.scanner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(final String[] args) throws IOException, JSONException {
        final String baseUrl = "http://maps.googleapis.com/maps/api/directions/json";// путь к Geocoding API по
        // HTTP
        final Map<String, String> params = new HashMap<String, String>();
        params.put("sensor", "false");// указывает, исходит ли запрос на геокодирование от устройства с датчиком
        params.put("language", "ru");// язык данные на котором мы хотим получить
        params.put("mode", "walking");// способ перемещения, может быть driving, walking, bicycling
        params.put("origin", "Россия, Москва, улица Поклонная, 12");// адрес или текстовое значение широты и
        // отправного пункта маршрута
        params.put("destination", "Россия, Москва, станция метро Парк Победы");// адрес или текстовое значение широты и
        // долготы
        // долготы конечного пункта маршрута
        final String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
        System.out.println(url); // Можем проверить что вернет этот путь в браузере
        final JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        // как правило наиболее подходящий ответ первый и данные о координатах можно получить по пути
        // //results[0]/geometry/location/lng и //results[0]/geometry/location/lat
        JSONObject location = response.getJSONArray("routes").getJSONObject(0);
        location = location.getJSONArray("legs").getJSONObject(0);
        final String distance = location.getJSONObject("distance").getString("text");
        final String duration = location.getJSONObject("duration").getString("text");
        System.out.println(distance + "\n" + duration);
    }

    private static String encodeParams(final Map<String, String> params) throws UnsupportedEncodingException {

        StringBuffer buffer = new StringBuffer();
        for(Map.Entry<String, String> param : params.entrySet()) {
            buffer.append(param.getKey());// получаем значение вида key=value
            buffer.append('=');
            buffer.append(URLEncoder.encode(param.getValue(), "utf-8"));// кодируем строку в соответствии со стандартом HTML 4.01
            buffer.append('&');
        }
        buffer = buffer.deleteCharAt(buffer.lastIndexOf("&"));

        return buffer.toString();
    }


}
