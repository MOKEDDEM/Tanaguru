/*
 * Tanaguru - Automated webpage assessment
 * Copyright (C) 2008-2013  Open-S Company
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
 * Contact us by mail: open-s AT open-s DOT com
 */
package org.opens.tanaguru.rules.rgaa22;

import org.opens.tanaguru.entity.audit.TestSolution;
import org.opens.tanaguru.rules.rgaa22.test.Rgaa22RuleImplementationTestCase;

/**
 * Unit test class for the implementation of the rule 7.12 of the referential RGAA 2.2.
 *
 * @author jkowalczyk
 */
public class Rgaa22Rule07121Test extends Rgaa22RuleImplementationTestCase {

    /**
     * Default constructor
     */
    public Rgaa22Rule07121Test (String testName){
        super(testName);
    }

    @Override
    protected void setUpRuleImplementationClassName() {
        setRuleImplementationClassName(
                "org.opens.tanaguru.rules.rgaa22.Rgaa22Rule07121");
    }

    @Override
    protected void setUpWebResourceMap() {
//        getWebResourceMap().put("Rgaa22.Test.7.12-1Passed-01",
//              getWebResourceFactory().createPage(
//              getTestcasesFilePath() + "rgaa22/Rgaa22Rule07121/RGAA22.Test.7.12-1Passed-01.html"));
//        getWebResourceMap().put("Rgaa22.Test.7.12-2Failed-01",
//              getWebResourceFactory().createPage(
//              getTestcasesFilePath() + "rgaa22/Rgaa22Rule07121/RGAA22.Test.7.12-2Failed-01.html"));
//        getWebResourceMap().put("Rgaa22.Test.7.12-3NMI-01",
//                getWebResourceFactory().createPage(
//                getTestcasesFilePath() + "rgaa22/Rgaa22Rule07121/RGAA22.Test.7.12-3NMI-01.html"));
//        getWebResourceMap().put("Rgaa22.Test.7.12-4NA-01",
//              getWebResourceFactory().createPage(
//              getTestcasesFilePath() + "rgaa22/Rgaa22Rule07121/RGAA22.Test.7.12-4NA-01.html"));
        getWebResourceMap().put("Rgaa22.Test.7.12-5NT-01",
                getWebResourceFactory().createPage(
                getTestcasesFilePath() + "rgaa22/Rgaa22Rule07121/RGAA22.Test.7.12-5NT-01.html"));
    }

    @Override
    protected void setProcess() {
//        assertEquals(TestSolution.PASSED,
//                processPageTest("Rgaa22.Test.7.12-1Passed-01").getValue());
//        assertEquals(TestSolution.FAILED,
//                processPageTest("Rgaa22.Test.7.12-2Failed-01").getValue());
//        assertEquals(TestSolution.NEED_MORE_INFO,
//                processPageTest("Rgaa22.Test.7.12-3NMI-01").getValue());
//        assertEquals(TestSolution.NOT_APPLICABLE,
//                processPageTest("Rgaa22.Test.7.12-4NA-01").getValue());
        assertEquals(TestSolution.NOT_TESTED,
                processPageTest("Rgaa22.Test.7.12-5NT-01").getValue());
    }

    @Override
    protected void setConsolidate() {
//        assertEquals(TestSolution.PASSED,
//                consolidate("Rgaa22.Test.7.12-1Passed-01").getValue());
//        assertEquals(TestSolution.FAILED,
//                consolidate("Rgaa22.Test.7.12-2Failed-01").getValue());
//        assertEquals(TestSolution.NEED_MORE_INFO,
//                consolidate("Rgaa22.Test.7.12-3NMI-01").getValue());
//        assertEquals(TestSolution.NOT_APPLICABLE,
//                consolidate("Rgaa22.Test.7.12-4NA-01").getValue());
        assertEquals(TestSolution.NOT_TESTED,
                consolidate("Rgaa22.Test.7.12-5NT-01").getValue());
    }

}