/*
 * Copyright (C) 2007-2018, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.lcm

import spock.lang.Specification

class LcmEntityFilterTest extends Specification {

    def "should work empty"() {
        expect:
        new LcmEntityFilter().asQueryParams() == [:]
    }

    def "should add all"() {
        expect:
        new LcmEntityFilter()
                .withDataProduct('dp')
                .withSegment('seg')
                .withClient('c')
                .asQueryParams() == [dataProduct: ['dp'], segment: ['seg'], client: ['c']]
    }
}
