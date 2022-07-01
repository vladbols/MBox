package com.company.mbox.serviceBean;

import com.company.mbox.services.BaseUtilsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service(BaseUtilsService.NAME)
public class BaseUtilsServiceBean implements BaseUtilsService {


    @Override
    @SuppressWarnings("all")
    public Map<String, Object> getObjMap(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}
