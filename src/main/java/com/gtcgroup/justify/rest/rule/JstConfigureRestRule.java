/*
 * [Licensed per the Open Source "MIT License".]
 *
 * Copyright (c) 2006 - 2016 by
 * Global Technology Consulting Group, Inc. at
 * http://gtcGroup.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gtcgroup.justify.rest.rule;

import org.h2.bnf.Rule;
import org.junit.rules.TestRule;

import com.gtcgroup.justify.core.base.JstBaseRule;
import com.gtcgroup.justify.rest.filter.internal.LogResponseFilter;
import com.gtcgroup.justify.rest.helper.internal.RestCacheHelper;

/**
 * This {@link Rule} class initializes a RESTful server.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2016 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author Marvin Toll
 * @since v8.3
 */
public class JstConfigureRestRule extends JstBaseRule {

    private static boolean PREVIOUSLY_ADDED_DEFAULT_FILTERS = false;

    /**
     * @param <RULE>
     * @param <PO>
     * @param ruleRestPO
     * @return {@link TestRule}
     */
    @SuppressWarnings("unchecked")
    public static <RULE extends TestRule, PO extends JstRuleRestPO> RULE withParameterObject(final PO ruleRestPO) {

        return (RULE)new JstConfigureRestRule(ruleRestPO);
    }

    private JstRuleRestPO ruleRestPO;

    /**
     * Constructor- protected
     *
     * @param ruleRestPO
     */
    protected JstConfigureRestRule(final JstRuleRestPO ruleRestPO) {
        super();

        this.ruleRestPO = ruleRestPO;
        return;
    }

    /**
     * @see JstBaseTestingRule#afterTM()
     */
    @Override
    public void afterTM() {

        RestCacheHelper.stopInMemoryServer();

        return;
    }

    /**
     * @see JstBaseTestingRule#beforeTM()
     */
    @Override
    public void beforeTM() {

        if (!this.ruleRestPO.isSuppressJsonLogging()) {

            if (!JstConfigureRestRule.PREVIOUSLY_ADDED_DEFAULT_FILTERS) {

                this.ruleRestPO.addResponseFilters(new LogResponseFilter());

                JstConfigureRestRule.PREVIOUSLY_ADDED_DEFAULT_FILTERS = true;
            }
        }

        // TODO: There is bug that precludes using of "packages".
        // If we could determine if the user tried that method...
        // and exception could be thrown warning of the bug.

        RestCacheHelper.initializeRestServer(new JstRuleRestPO(this.ruleRestPO));
    }
}