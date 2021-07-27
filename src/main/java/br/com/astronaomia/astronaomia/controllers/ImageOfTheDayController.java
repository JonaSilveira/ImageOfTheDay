package br.com.astronaomia.astronaomia.controllers;

import br.com.astronaomia.astronaomia.models.ImageOfTheDay;
import br.com.astronaomia.astronaomia.services.ImageOfTheDayService;
import br.com.astronaomia.astronaomia.services.PostOnIgService;
import br.com.astronaomia.astronaomia.services.TranslateTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("astronaomia")
public class ImageOfTheDayController {
    @Autowired
    ImageOfTheDayService imageOfTheDayService ;

    @Autowired
    PostOnIgService postOnIgService;
    @GetMapping("/imageOfTheDay/{date}")
    public String imageOfTheDay(Model model,@Nullable @PathVariable("date") String date){
        System.out.println("Com parametros");
        ImageOfTheDay image = imageOfTheDayService.getImageOfTheDay(date);
        model.addAttribute("imageOfTheDayJson", image);
        return "imageOfTheDay/index";
    }

    @GetMapping("/imageOfTheDay")
    public String imageOfTheDay(Model model){
        System.out.println("Sem parametros");
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        ImageOfTheDay image = imageOfTheDayService.getImageOfTheDay(strDate);
        model.addAttribute("imageOfTheDayJson", image);
        return "imageOfTheDay/index";
    }

    @GetMapping("/postImage")
    public String postImageOfTheDay(Model model){
        AtomicReference<Boolean> atomicBoolean = postOnIgService.postOnInstagran(postOnIgService.loginOnInstagran());
        model.addAttribute("Publicou", atomicBoolean.get());
        return "ig";
    }

}
