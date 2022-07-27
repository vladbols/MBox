package com.company.mbox.controller.abstractClasses;

import com.google.gson.*;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.core.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class AbstractController {
    protected final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    protected static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    protected static DateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Autowired
    protected DataManager dataManager;

    @Autowired
    private Metadata metadata;

    @Autowired
    private Messages messages;

    protected static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context)
                    -> src == null ? null : new JsonPrimitive(dateFormat.format(src)))
            .registerTypeAdapter(java.sql.Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context)
                    -> src == null ? null : new JsonPrimitive(dateFormat.format(src))).create();

    protected static Gson gsonWithTime = new GsonBuilder()
            .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context)
                    -> src == null ? null : new JsonPrimitive(dateTimeFormat.format(src)))
            .registerTypeAdapter(java.sql.Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context)
                    -> src == null ? null : new JsonPrimitive(dateTimeFormat.format(src))).create();

//    @Inject
//    protected UserSessionSource userSessionSource;
//    @Inject
//    protected ValidationErrorBuilder validationErrorBuilder;
//    @Inject
//    protected Messages messages;
//    @Inject
//    protected Utils utils;
//    @Inject
//    protected CommonService commonService;

//    protected String getFormattedMessage(String key, Object... params) {
//        getMessage("msg://");
//        return messages.formatMainMessage(key, params);
//    }
//
//    protected Locale locale() {
//        return userSessionSource.getLocale();
//    }
//
//    protected String getMessage(String key, String language) {
//        if (StringUtils.isBlank(language)) {
//            return getMessage(key);
//        }
//        return messages.getMainMessage(key, new Locale(language));
//    }
//
//    protected String getMessage(String messagePackage, String key, String language) {
//        return messages.getMessage(messagePackage, key, language != null ? new Locale(language) : locale());
//    }
//
//    protected String getMessage(String key) {
//        return messages.getMainMessage(key, locale());
//    }
//
//    protected String getMessage(Enum<?> caller) {
//        return messages.getMessage(caller);
//    }

    protected final ResponseEntity<?> badRequest(Object message) {
        return ResponseEntity.badRequest().body(Collections.singletonList(message));
    }

    protected final ResponseEntity<?> ok(Object returnValue) {
        return ResponseEntity.ok(returnValue);
    }

    protected String getMessage(String key) {
        return messages.getMessage(key);
    }

    protected String getMessage(String key, String locale) {
        return messages.getMessage(key, new Locale(locale));
    }

//    protected String language() {
//        if (currentUserSubstitution.getAuthenticatedUser() !=  null) {
//            return sessionData.ge .getLocale().getLanguage();
//        }
//        return messages.getMessage();
//    }
//
//    protected String getJsonWithoutEntries(String json) {
//        JsonElement jsonAsElement = gson.fromJson(json, JsonElement.class);
//        if (!jsonAsElement.isJsonNull()) {
//            JsonElement entries = jsonAsElement.getAsJsonObject().get("entries");
//            if (!entries.isJsonNull()) {
//                JsonElement entry = entries.getAsJsonObject().get("entry");
//                if (entry != null) {
//                    return entry.toString();
//                }
//            }
//        }
//        return null;
//    }
}
