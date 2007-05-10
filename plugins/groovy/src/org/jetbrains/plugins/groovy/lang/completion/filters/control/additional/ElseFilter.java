/*
 * Copyright 2000-2007 JetBrains s.r.o.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.plugins.groovy.lang.completion.filters.control.additional;

import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.plugins.groovy.lang.psi.GroovyFile;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrIfStatement;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.GrCommandArguments;
import org.jetbrains.plugins.groovy.lang.completion.GroovyCompletionData;

/**
 * @author ilyas
 */
public class ElseFilter implements ElementFilter {
  public boolean isAcceptable(Object element, PsiElement context) {
    if (context.getParent() != null &&
        GroovyCompletionData.nearestLeftSibling(context.getParent()) instanceof GrIfStatement) {
      GrIfStatement statement = (GrIfStatement) GroovyCompletionData.nearestLeftSibling(context.getParent());
      if (statement.getElseBranch() == null) { 
        return true;
      }
      return true;
    }
    if (context.getParent() != null &&
        GroovyCompletionData.nearestLeftSibling(context) != null &&
        GroovyCompletionData.nearestLeftSibling(context).getPrevSibling() instanceof GrIfStatement) {
      GrIfStatement statement = (GrIfStatement) GroovyCompletionData.nearestLeftSibling(context).getPrevSibling();
      if (statement.getElseBranch() == null) {
        return true;
      }
    }
    if (context.getParent() != null &&
        context.getParent().getParent() instanceof GrCommandArguments &&
        context.getParent().getParent().getParent().getParent() instanceof GrIfStatement) {
      GrIfStatement statement = (GrIfStatement) context.getParent().getParent().getParent().getParent();
      if (statement.getElseBranch() == null) {
        return true;
      }
    }
    return false;
  }

  public boolean isClassAcceptable(Class hintClass) {
    return true;
  }

  @NonNls
  public String toString() {
    return "filter for 'else' keyword";
  }

}