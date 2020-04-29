package com.fara.projects.adhub.restclient.httprequest.utils;

import java.io.IOException;
import java.io.InputStream;

public interface StreamConverter {
    String convert(InputStream inputStream) throws IOException;
}