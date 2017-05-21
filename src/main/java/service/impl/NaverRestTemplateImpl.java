package service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Auth;
import request.naver.NaverRequestParameter;
import response.naver.NaverResponse;
import service.LanguageChecker;
import service.NaverRestTemplate;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jojoldu@gmail.com on 2017. 5. 19.
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

public class NaverRestTemplateImpl implements NaverRestTemplate {
    private static final Logger logger = LoggerFactory.getLogger(NaverRestTemplateImpl.class);

    private static final String TRANSLATE_URL = "https://openapi.naver.com/v1/language/translate";

    @Override
    public String translate(String text, Auth auth) throws UnsupportedEncodingException {
        return null;
    }

    public NaverResponse requestTranslate(LanguageChecker languageChecker, String text, Auth auth) {
        Auth.Naver naverAuth = auth.getNaver();

        Response response = createWebTarget(TRANSLATE_URL)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(Auth.Naver.HEADER_CLIENT_ID, naverAuth.getClientId())
                .header(Auth.Naver.HEADER_CLIENT_SECRET, naverAuth.getClientSecret())
                .header("charset", "UTF-8")
                .post(Entity.entity(createRequestData(languageChecker, text), MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);

        if(response.getStatus() != 200){
            logger.error("Translate Request Exception : {}", response.readEntity(String.class));
            return null;
        }

        return response.readEntity(NaverResponse.class);
    }

    private String createRequestData(LanguageChecker languageChecker, String text) {
        String source = extractFrom(languageChecker, text);
        return NaverRequestParameter.Builder.builder()
                .source(source)
                .target(exchangeLanguageType(languageChecker, source))
                .text(text)
                .build()
                .toUrlParameter();
    }
}