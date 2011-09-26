/*
 * Copyright 2010 Henry Coles
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */

package org.pitest.mutationtest.engine.gregor.mutators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.concurrent.Callable;

import org.junit.Before;
import org.junit.Test;
import org.pitest.functional.FunctionalList;
import org.pitest.mutationtest.MutationDetails;
import org.pitest.mutationtest.engine.Mutant;
import org.pitest.mutationtest.engine.gregor.MutatorTestBase;
import org.pitest.mutationtest.engine.gregor.mutators.MethodCallMutatorTest.HasConstructorCall;
import org.pitest.mutationtest.engine.gregor.mutators.MethodCallMutatorTest.HasIntMethodCall;

public class NonVoidMethodCallMutatorTest extends MutatorTestBase {

  @Before
  public void setupEngineToRemoveVoidMethods() {
    createTesteeWith(mutateOnlyCallMethod(),
        NonVoidMethodCallMutator.NON_VOID_METHOD_CALL_MUTATOR);
  }

  @Test
  public void shouldRemoveNonVoidMethods() throws Exception {
    final Mutant mutant = getFirstMutant(HasIntMethodCall.class);
    assertMutantCallableReturns(new HasIntMethodCall(), mutant, "0");
  }

  private static class HasVoidMethodCall implements Callable<String> {

    private String s = "";

    public void set(final int i) {
      this.s = this.s + i;
    }

    public String call() throws Exception {
      set(1);
      return this.s;
    }

  }

  @Test
  public void shouldNotRemoveVoidMethodCalls() throws Exception {
    assertTrue(findMutationsFor(HasVoidMethodCall.class).isEmpty());
  }

  @Test
  public void shouldNotRemoveConstructorCalls() throws Exception {
    final FunctionalList<MutationDetails> actual = findMutationsFor(HasConstructorCall.class);
    assertFalse(actual.contains(descriptionContaining("Integer")));
  }

  private static class HasObjectMethodCall implements Callable<String> {

    public String call() throws Exception {
      return this.toString();
    }
  }

  @Test
  public void shouldRemoveNonVoidMethodCallReturningObjectType()
      throws Exception {
    final Mutant mutant = getFirstMutant(HasObjectMethodCall.class);
    assertMutantCallableReturns(new HasObjectMethodCall(), mutant, null);
  }

  private static class HasBooleanMethodCall implements Callable<String> {
    private boolean booleanMethod() {
      return true;
    }

    public String call() throws Exception {
      boolean result = booleanMethod();
      return "" + result;
    }
  }

  @Test
  public void shouldRemoveNonVoidMethodCallReturningBooleanType()
      throws Exception {
    final Mutant mutant = getFirstMutant(HasBooleanMethodCall.class);
    assertMutantCallableReturns(new HasBooleanMethodCall(), mutant, "false");
  }

  private static class HasDoubleMethodCall implements Callable<String> {
    private double doubleMethod() {
      return 9123475.3d;
    }

    public String call() throws Exception {
      double result = doubleMethod();
      return "" + result;
    }
  }

  @Test
  public void shouldRemoveNonVoidMethodCallReturningDoubleType()
      throws Exception {
    final Mutant mutant = getFirstMutant(HasDoubleMethodCall.class);
    assertMutantCallableReturns(new HasDoubleMethodCall(), mutant, "0.0");
  }

  private static class HasByteMethodCall implements Callable<String> {
    private byte byteMethod() {
      return 5;
    }

    public String call() throws Exception {
      byte result = byteMethod();
      return "" + result;
    }
  }

  @Test
  public void shouldRemoveNonVoidMethodCallReturningByteType() throws Exception {
    final Mutant mutant = getFirstMutant(HasByteMethodCall.class);
    assertMutantCallableReturns(new HasByteMethodCall(), mutant, "0");
  }

  private static class HasCharMethodCall implements Callable<String> {
    private char charMethod() {
      return 'g';
    }

    public String call() throws Exception {
      char result = charMethod();
      return "" + result;
    }
  }

  @Test
  public void shouldRemoveNonVoidMethodCallReturningCharType() throws Exception {
    final Mutant mutant = getFirstMutant(HasCharMethodCall.class);
    assertMutantCallableReturns(new HasCharMethodCall(), mutant, "" + '\0');
  }

  private static class HasShortMethodCall implements Callable<String> {
    private short shortMethod() {
      return 23;
    }

    public String call() throws Exception {
      short result = shortMethod();
      return "" + result;
    }
  }

  @Test
  public void shouldRemoveNonVoidMethodCallReturningShortType()
      throws Exception {
    final Mutant mutant = getFirstMutant(HasShortMethodCall.class);
    assertMutantCallableReturns(new HasShortMethodCall(), mutant, "0");
  }

  private static class HasLongMethodCall implements Callable<String> {
    private long longMethod() {
      return 23;
    }

    public String call() throws Exception {
      long result = longMethod();
      return "" + result;
    }
  }

  @Test
  public void shouldRemoveNonVoidMethodCallReturningLongType() throws Exception {
    final Mutant mutant = getFirstMutant(HasLongMethodCall.class);
    assertMutantCallableReturns(new HasLongMethodCall(), mutant, "0");
  }

  private static class HasFloatMethodCall implements Callable<String> {
    private float floatMethod() {
      return 23;
    }

    public String call() throws Exception {
      float result = floatMethod();
      return "" + result;
    }
  }

  @Test
  public void shouldRemoveNonVoidMethodCallReturningFloatType()
      throws Exception {
    final Mutant mutant = getFirstMutant(HasFloatMethodCall.class);
    assertMutantCallableReturns(new HasFloatMethodCall(), mutant, "0.0");
  }

  private static class UsesReturnValueOfMethodCall implements Callable<String> {
    public String call() throws Exception {
      return this.toString().toUpperCase();
    }
  }

  @Test(expected = NullPointerException.class)
  public void canCauseNullPointerExceptionWhenMethodCallRemoved()
      throws Exception {
    final Collection<MutationDetails> actual = findMutationsFor(UsesReturnValueOfMethodCall.class);
    final Mutant mutant = getFirstMutant(actual);
    mutateAndCall(new UsesReturnValueOfMethodCall(), mutant);
  }

}
