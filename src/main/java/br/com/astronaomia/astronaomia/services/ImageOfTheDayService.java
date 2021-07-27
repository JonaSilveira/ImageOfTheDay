package br.com.astronaomia.astronaomia.services;

import br.com.astronaomia.astronaomia.models.ImageOfTheDay;
import br.com.astronaomia.astronaomia.repository.ImageOfTheDayRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.testcontainers.shaded.okhttp3.OkHttpClient;
import org.testcontainers.shaded.okhttp3.Request;
import org.testcontainers.shaded.okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;

@Service
public class ImageOfTheDayService {

    @Autowired
    private ImageOfTheDayRepository imageOfTheDayRepository;

    @Autowired
    TranslateTextService translateTextService;


    public ImageOfTheDay getImageOfTheDay(@Nullable String date){
        System.out.println(date);
        ImageOfTheDay imageOfTheDay = imageOfTheDayRepository.findByDate(date);
        if(imageOfTheDay!=null) {
            return imageOfTheDay;
        }
        Response response = null;
        String body = "";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.nasa.gov/planetary/apod?api_key=hc0cRNbv7wBaowjhPhgjbHedRGXuSyZDMGHr5T2C&date="+date)
                .method("GET", null)
                .build();
        try{
            response = client.newCall(request).execute();
            body = response.body().string().toString();
        }
        catch (IOException e){
            e.getStackTrace();
        }
        finally {
            response.body().close();
        }

        System.out.println(body);
        return convertJsonToObject(body);
    }

    public ImageOfTheDay convertJsonToObject(String json){
        Gson gson = new Gson();
        ImageOfTheDay imageOfTheDay = gson.fromJson(json, ImageOfTheDay.class);
        HashMap<String, String> mapObject = translateTextService.translate(imageOfTheDay.getTitle(), imageOfTheDay.getExplanation());
        imageOfTheDay.setExplanationPt(mapObject.get("explanationPT"));
        imageOfTheDay.setTitlePt(mapObject.get("titlePT"));
        imageOfTheDayRepository.save(imageOfTheDay);
        System.out.println(imageOfTheDay.toString());
        return imageOfTheDay;
    }

}
