/*
 * Copyright 2011 Henry Coles
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
package org.pitest;

import static org.mockito.Mockito.when;

import org.apache.maven.artifact.Artifact;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DependencyFilterTest {

  private DependencyFilter testee;

  @Mock
  private Artifact         artifact;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    this.testee = new DependencyFilter("foo", "bar");
  }

  @Test
  public void shouldAllowWhiteListedGroups() {
    when(this.artifact.getGroupId()).thenReturn("foo");
    this.testee.apply(this.artifact);
  }

  @Test
  public void shouldNotAllowNonWhiteListedGroups() {
    when(this.artifact.getGroupId()).thenReturn("faa");
    this.testee.apply(this.artifact);
  }

}
