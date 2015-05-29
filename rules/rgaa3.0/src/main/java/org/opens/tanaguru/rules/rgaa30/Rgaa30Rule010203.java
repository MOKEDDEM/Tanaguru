/*
 * Tanaguru - Automated webpage assessment
 * Copyright (C) 2008-2015  Tanaguru.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact us by mail: tanaguru AT tanaguru DOT org
 */

package org.opens.tanaguru.rules.rgaa30;

import org.opens.tanaguru.ruleimplementation.AbstractNotTestedRuleImplementation;

/**
 * Implementation of the rule 1.2.4 of the referential Rgaa 3.0.
 * <br/>
 * For more details about the implementation, refer to <a href="http://tanaguru-rules-rgaa3.readthedocs.org/en/latest/Rule-1-2-4">the rule 1.2.4 design page.</a>
 * @see <a href="http://references.modernisation.gouv.fr/sites/default/files/RGAA3_RC2-1/referentiel_technique.htm#test-1-2-4"> 1.2.4 rule specification</a>
 *
 */
public class Rgaa30Rule010203 extends AbstractNotTestedRuleImplementation {

    /**
     * Default constructor
     */
    public Rgaa30Rule010203 () {
        super();
    }
    
//    /**
//     * Constructor
//     */
//    public Rgaa30Rule010203() {
//        super(
//                new ImageElementSelector(OBJECT_TYPE_IMG_CSS_LIKE_QUERY, true, false),
//                
//                // the decorative images are part of the scope
//                DECORATIVE_IMAGE_MARKER, 
//                
//                // the informative images are not part of the scope
//                INFORMATIVE_IMAGE_MARKER, 
//
//                // checker for elements identified by marker
//                new TextEmptinessChecker(
//                    // the text element builder
//                    new SimpleTextElementBuilder(),
//                    // solution when text is empty
//                    TestSolution.PASSED, 
//                    // solution when text is not empty
//                    TestSolution.FAILED, 
//                    // no message created when a decorative object with an empty text is found
//                    null, 
//                    // message created when a decorative object with a not empty text is found
//                    DECORATIVE_ELEMENT_WITH_NOT_EMPTY_ALT_MSG, 
//                    // evidence elements
//                    TEXT_ELEMENT2,
//                    DATA_ATTR),
//                
//                // checker for elements not identified by marker
//                new TextEmptinessChecker(
//                    // the text element builder
//                    new SimpleTextElementBuilder(),
//                    // solution when text is empty
//                    TestSolution.NEED_MORE_INFO, 
//                    // solution when text is notempty
//                    TestSolution.NEED_MORE_INFO, 
//                    CHECK_ELEMENT_WITH_EMPTY_ALT_MSG, 
//                    CHECK_ELEMENT_WITH_NOT_EMPTY_ALT_MSG, 
//                    // evidence elements
//                    TEXT_ELEMENT2,
//                    DATA_ATTR)
//            );
//    }

}