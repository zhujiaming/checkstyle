////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that instance variable names conform to a format specified
 * by the format property. The format is a
 * <a href="http://jakarta.apache.org/regexp/apidocs/org/apache/regexp/RE.html">
 * regular expression</a>
 * and defaults to
 * <strong>^[a-z][a-zA-Z0-9]*$</strong>.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="MemberName"/&gt;
 * </pre> 
 * <p>
 * An example of how to configure the check for names that begin with
 * &quot;m&quot;, followed by an upper case letter, and then letters and
 * digits is:
 * </p>
 * <pre>
 * &lt;module name="MemberName"&gt;
 *    &lt;property name="format" value="^m[A-Z][a-zA-Z0-9]*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Rick Giles
 * @version 1.0
 */
public class MemberNameCheck
    extends AbstractNameCheck
{
    /** Creates a new <code>MemberNameCheck</code> instance. */
    public MemberNameCheck()
    {
        super("^[a-z][a-zA-Z0-9]*$");
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    /** @see com.puppycrawl.tools.checkstyle.checks.AbstractNameCheck */
    protected final boolean mustCheckName(DetailAST aAST)
    {
        DetailAST modifiersAST = aAST.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isStatic = (modifiersAST != null)
            && modifiersAST.branchContains(TokenTypes.LITERAL_STATIC);
        final boolean isPublic = (modifiersAST != null)
            && modifiersAST.branchContains(TokenTypes.LITERAL_PUBLIC);

        return (!isStatic && !isPublic && !ScopeUtils.inInterfaceBlock(aAST)
            && !ScopeUtils.inCodeBlock(aAST));
    }
}
