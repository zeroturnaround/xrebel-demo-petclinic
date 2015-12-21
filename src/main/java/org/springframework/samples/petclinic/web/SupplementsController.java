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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Controller
@SessionAttributes("supplements")
public class SupplementsController {

  public SupplementsController() {

  }

  @RequestMapping(value = "/supplements", method = RequestMethod.GET)
  public String processFindForm(Map<String, Object> model) {
    Collection<Supplement> results = getSupplements();
    model.put("supplements", results);
    return "supplements/supplementsList";
  }

  private Collection<Supplement> getSupplements() {
    return new ArrayList<Supplement>() {
      {
        add(new Supplement("A", "123.6"));
        add(new Supplement("B", "234.7"));
        add(new Supplement("C", "345.8"));
        add(new Supplement("D", "456.9"));
      }
    };
  }

}
