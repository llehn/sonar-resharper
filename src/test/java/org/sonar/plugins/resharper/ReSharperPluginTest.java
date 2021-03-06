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

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.plugins.resharper.CSharpReSharperProvider.CSharpReSharperRuleRepository;
import org.sonar.plugins.resharper.CSharpReSharperProvider.CSharpReSharperSensor;
import org.sonar.plugins.resharper.VBNetReSharperProvider.VBNetReSharperRuleRepository;
import org.sonar.plugins.resharper.VBNetReSharperProvider.VBNetReSharperSensor;
import org.sonar.plugins.resharper.JsReSharperProvider.JsReSharperRuleRepository;
import org.sonar.plugins.resharper.JsReSharperProvider.JsReSharperSensor;
import org.sonar.plugins.resharper.TsReSharperProvider.TsReSharperRuleRepository;
import org.sonar.plugins.resharper.TsReSharperProvider.TsReSharperSensor;
import org.sonar.plugins.resharper.CssReSharperProvider.CssReSharperRuleRepository;
import org.sonar.plugins.resharper.CssReSharperProvider.CssReSharperSensor;
import org.sonar.plugins.resharper.WebReSharperProvider.WebReSharperRuleRepository;
import org.sonar.plugins.resharper.WebReSharperProvider.WebReSharperSensor;

import java.util.List;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class ReSharperPluginTest {

  @Test
  public void test() {
    assertThat(nonProperties(new ReSharperPlugin().getExtensions())).containsOnly(
      CSharpReSharperRuleRepository.class,
      CSharpReSharperSensor.class,
      JsReSharperRuleRepository.class,
      JsReSharperSensor.class,
      CssReSharperRuleRepository.class,
      CssReSharperSensor.class,
      VBNetReSharperRuleRepository.class,
      VBNetReSharperSensor.class);

    assertThat(propertyKeys(new ReSharperPlugin().getExtensions())).containsOnly(
      "sonar.resharper.projectName",
      "sonar.resharper.solutionFile",
      "sonar.resharper.inspectCodePath",
      "sonar.resharper.timeoutMinutes",

      "sonar.resharper.installDirectory");
  }

  private static Set<String> nonProperties(List extensions) {
    ImmutableSet.Builder builder = ImmutableSet.builder();
    for (Object extension : extensions) {
      if (!(extension instanceof PropertyDefinition)) {
        builder.add(extension);
      }
    }
    return builder.build();
  }

  private static Set<String> propertyKeys(List extensions) {
    ImmutableSet.Builder<String> builder = ImmutableSet.builder();
    for (Object extension : extensions) {
      if (extension instanceof PropertyDefinition) {
        PropertyDefinition property = (PropertyDefinition) extension;
        builder.add(property.key());
      }
    }
    return builder.build();
  }
}
