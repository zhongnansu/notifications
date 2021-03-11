/*
 * Copyright 2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */

package com.amazon.opendistroforelasticsearch.commons.notifications.model

import com.amazon.opendistroforelasticsearch.notifications.createObjectFromJsonString
import com.amazon.opendistroforelasticsearch.notifications.getJsonString
import com.amazon.opendistroforelasticsearch.notifications.recreateObject
import org.elasticsearch.test.ESTestCase
import org.junit.jupiter.api.Test

class StatusDetailTests {
    @Test
    fun `StatusDetail serialize and deserialize should be equal`() {
        val sampleStatusDetail= StatusDetail(
            "404",
            "invalid recipient",
        )
        val recreatedObject = recreateObject(sampleStatusDetail) { StatusDetail(it) }
        ESTestCase.assertEquals(sampleStatusDetail, recreatedObject)
    }

    @Test
    fun `StatusDetail serialize and deserialize using json should be equal`() {
        val sampleStatusDetail= StatusDetail(
            "404",
            "invalid recipient",
        )
        val jsonString = getJsonString(sampleStatusDetail)
        val recreatedObject = createObjectFromJsonString(jsonString) { StatusDetail.parse(it) }
        ESTestCase.assertEquals(sampleStatusDetail, recreatedObject)
    }
}