package com.company.mbox.abstracts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Service("AbstractService")
public class AbstractServiceBean {

    protected final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

    protected static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    protected static DateFormat dateTimeFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

}
