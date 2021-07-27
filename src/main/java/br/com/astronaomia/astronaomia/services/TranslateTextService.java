package br.com.astronaomia.astronaomia.services;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.Languages;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TranslateTextService {

    public HashMap<String, String> translate(String title, String explanation){
        HashMap<String, String> object = new HashMap<>();
        object.put("titleEN", title);
        object.put("explanationEN", explanation);
        object.put("titlePT", buildTranslation(title));
        object.put("explanationPT", buildTranslation(explanation));
        return object;
    }

    private static String buildTranslation(String text){
        IamAuthenticator iamAuthenticator = new IamAuthenticator("AaJUm2CjZxZZzZhXJcvobxAvjQ2h8e-B446GP70bvj89");
        LanguageTranslator languageTranslator = new LanguageTranslator("2021-06-17", iamAuthenticator);
        languageTranslator.setServiceUrl("https://api.us-south.language-translator.watson.cloud.ibm.com/instances/a042bdf6-f278-4c51-b6f6-38c3d99433c9");
        TranslateOptions translateOptions = new TranslateOptions.Builder()
                .addText(text).modelId("en-pt").target("pt").source("en").build();
        TranslationResult translationResult = languageTranslator.translate(translateOptions).execute().getResult();
        System.out.println(translationResult);
        return translationResult.getTranslations().get(0).getTranslation();
    }


}
