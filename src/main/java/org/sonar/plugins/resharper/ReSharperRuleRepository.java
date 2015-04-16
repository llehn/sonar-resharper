/*
 * SonarQube ReSharper Plugin
 * Copyright (C) 2014 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.resharper;

import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleRepository;
import org.sonar.api.rules.XMLRuleParser;

import java.util.ArrayList;
import java.util.List;

public class ReSharperRuleRepository extends RuleRepository {

  private static final String REPOSITORY_NAME = "ReSharper";

  private final XMLRuleParser xmlRuleParser;

  public ReSharperRuleRepository(ReSharperConfiguration fxCopConf, XMLRuleParser xmlRuleParser) {
    super(fxCopConf.repositoryKey(), fxCopConf.languageKey());
    setName(REPOSITORY_NAME);
    this.xmlRuleParser = xmlRuleParser;
  }

  @Override
  public List<Rule> createRules() {
      List<Rule> rules = new ArrayList<Rule>();
      if(getLanguage() == "cs") {
          rules.addAll(xmlRuleParser.parse(getClass().getResourceAsStream("/org/sonar/plugins/resharper/rules-cs.xml")));
      }
      else if(getLanguage() == "vbnet") {
          rules.addAll(xmlRuleParser.parse(getClass().getResourceAsStream("/org/sonar/plugins/resharper/rules-vb.xml")));
      }
      else if(getLanguage() == "js") {
          rules.addAll(xmlRuleParser.parse(getClass().getResourceAsStream("/org/sonar/plugins/resharper/rules-js.xml")));
      }
      else if(getLanguage() == "web") {
          rules.addAll(xmlRuleParser.parse(getClass().getResourceAsStream("/org/sonar/plugins/resharper/rules-html.xml")));
      }
      else if(getLanguage() == "css") {
          rules.addAll(xmlRuleParser.parse(getClass().getResourceAsStream("/org/sonar/plugins/resharper/rules-css.xml")));
      }
    return rules;
  }

}
