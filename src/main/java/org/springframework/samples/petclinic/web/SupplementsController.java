/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Controller
@SessionAttributes("supplements")
public class SupplementsController {

  @RequestMapping(value = "/supplements", method = RequestMethod.GET)
  public String processFindForm(Map<String, Object> model) {
    Collection<Supplement> results = getSupplements();
    model.put("supplements", results);
    return "supplements/supplementsList";
  }

  private Collection<Supplement> getSupplements() {

    String json =  getSupplementsJson();
//        "[" +
//        "{\"name\": \"A\", \"price\": \"123.6\"}," +
//        "{\"name\": \"B\", \"price\": \"234.7\"}," +
//        "{\"name\": \"C\", \"price\": \"345.8\"}," +
//        "{\"name\": \"D\", \"price\": \"456.9\"}," +
//        "{\"name\": \"E\", \"price\": \"987.9\"}" +
//        "]";

    try {
      ObjectMapper mapper = new ObjectMapper();
      Supplement[] supplements = mapper.readValue(json, Supplement[].class);
      return Arrays.asList(supplements);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  private String getSupplementsJson() {
    StringBuilder sb = new StringBuilder();
    try {
      URL url = new URL("http://localhost:8080/food");
      URLConnection urlConnection = url.openConnection();
      InputStream inputStream = urlConnection.getInputStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while((line = bufferedReader.readLine()) != null) {
        sb.append(line);
      }
      bufferedReader.close();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }


}
