/*
 * Copyright 2022 Arktekk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fix

import scalafix.v1._

import scala.meta._

class DropPrivateThis extends SyntacticRule("DropPrivateThis") {

  override def fix(implicit doc: SyntacticDocument): Patch = {
    doc.tree.collect { case mod @ Mod.Private(within) =>
      if (within.is[Term.This])
        Patch.removeTokens(mod.tokens) + Patch.replaceToken(mod.tokens.head, "private")
      else Patch.empty
    }.asPatch
  }
}
