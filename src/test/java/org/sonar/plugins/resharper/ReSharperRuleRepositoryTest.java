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

import org.junit.Test;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.XMLRuleParser;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class ReSharperRuleRepositoryTest {

  @Test
  public void testCsRules() {
    ReSharperRuleRepository repo = new ReSharperRuleRepository(new ReSharperConfiguration("cs", "cs-resharper"), new XMLRuleParser());
    assertThat(repo.getLanguage()).isEqualTo("cs");
    assertThat(repo.getKey()).isEqualTo("cs-resharper");

    List<Rule> rules = repo.createRules();
    assertThat(rules.size()).isEqualTo(421);
    for (Rule rule : rules) {
      assertThat(rule.getKey()).isNotNull();
      assertThat(rule.getName()).isNotNull();
      assertThat(rule.getDescription()).isNotNull();
    }
  }

    @Test
    public void testJsRules() {
        ReSharperRuleRepository repo = new ReSharperRuleRepository(new ReSharperConfiguration("js", "js-resharper"), new XMLRuleParser());
        assertThat(repo.getLanguage()).isEqualTo("js");
        assertThat(repo.getKey()).isEqualTo("js-resharper");

        List<Rule> rules = repo.createRules();
        assertThat(rules.size()).isEqualTo(94);
        for (Rule rule : rules) {
            assertThat(rule.getKey()).isNotNull();
            assertThat(rule.getName()).isNotNull();
            assertThat(rule.getDescription()).isNotNull();
        }
    }

    @Test
    public void testCssRules() {
        ReSharperRuleRepository repo = new ReSharperRuleRepository(new ReSharperConfiguration("css", "css-resharper"), new XMLRuleParser());
        assertThat(repo.getLanguage()).isEqualTo("css");
        assertThat(repo.getKey()).isEqualTo("css-resharper");

        List<Rule> rules = repo.createRules();
        assertThat(rules.size()).isEqualTo(18);
        for (Rule rule : rules) {
            assertThat(rule.getKey()).isNotNull();
            assertThat(rule.getName()).isNotNull();
            assertThat(rule.getDescription()).isNotNull();
        }
    }

    @Test
    public void testVbNetRules() {
        ReSharperRuleRepository repo = new ReSharperRuleRepository(new ReSharperConfiguration("vbnet", "vbnet-resharper"), new XMLRuleParser());
        assertThat(repo.getLanguage()).isEqualTo("vbnet");
        assertThat(repo.getKey()).isEqualTo("vbnet-resharper");

        List<Rule> rules = repo.createRules();
        assertThat(rules.size()).isEqualTo(237);
        for (Rule rule : rules) {
            assertThat(rule.getKey()).isNotNull();
            assertThat(rule.getName()).isNotNull();
            assertThat(rule.getDescription()).isNotNull();
        }
    }

    @Test
    public void testWebRules() {
        ReSharperRuleRepository repo = new ReSharperRuleRepository(new ReSharperConfiguration("web", "web-resharper"), new XMLRuleParser());
        assertThat(repo.getLanguage()).isEqualTo("web");
        assertThat(repo.getKey()).isEqualTo("web-resharper");

        List<Rule> rules = repo.createRules();
        assertThat(rules.size()).isEqualTo(36);
        for (Rule rule : rules) {
            assertThat(rule.getKey()).isNotNull();
            assertThat(rule.getName()).isNotNull();
            assertThat(rule.getDescription()).isNotNull();
        }
    }

}
