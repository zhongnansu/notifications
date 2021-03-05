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

package com.amazon.opendistroforelasticsearch.notifications.model

import com.amazon.opendistroforelasticsearch.notifications.recreateObject
import org.elasticsearch.test.ESTestCase
import org.junit.jupiter.api.Test

internal class ChannelTests : ESTestCase() {

    @Test
    fun `Slack serialize and deserialize object should be equal`() {
        val sampleSlack = Channel.Slack("https://domain.com/sample_url#1234567890")
        val recreatedObject = recreateObject(sampleSlack) { Channel.Slack(it) }
        assertEquals(sampleSlack, recreatedObject)
    }

    @Test
    fun `Chime serialize and deserialize object should be equal`() {
        val sampleChime = Channel.Chime("https://domain.com/sample_url#1234567890")
        val recreatedObject = recreateObject(sampleChime) { Channel.Chime(it) }
        assertEquals(sampleChime, recreatedObject)
    }

    @Test
    fun `Webhook serialize and deserialize object should be equal`() {
        val sampleWebhook = Channel.Webhook(
            "https://domain.com/sample_url#1234567890",
            "schema",
            "slack.com",
            1234,
            "sample_path",
            mapOf(Pair("channel", "#1234567890")),
            mapOf(Pair("custom_header", "header_value")),
            "username",
            "password"
        )
        val recreatedObject = recreateObject(sampleWebhook) { Channel.Webhook(it) }
        assertEquals(sampleWebhook, recreatedObject)
    }

    @Test
    fun `Webhook serialize and deserialize object with null values should be equal`() {
        val sampleWebhook = Channel.Webhook(
            "https://domain.com/sample_url#1234567890",
            "schema",
            "slack.com",
            1234,
            "sample_path",
            mapOf(),
            mapOf(),
            null,
            null
        )
        val recreatedObject = recreateObject(sampleWebhook) { Channel.Webhook(it) }
        assertEquals(sampleWebhook, recreatedObject)
    }

    @Test
    fun `Slack destination serialize and deserialize object should be equal`() {
        val sampleSlack = Channel.Slack("https://domain.com/sample_url#1234567890")
        val sampleDestination = Channel.Destination(sampleSlack, null, null)
        val recreatedObject = recreateObject(sampleDestination) { Channel.Destination(it) }
        assertEquals(sampleDestination, recreatedObject)
    }

    @Test
    fun `Chime destination serialize and deserialize object should be equal`() {
        val sampleChime = Channel.Chime("https://domain.com/sample_url#1234567890")
        val sampleDestination = Channel.Destination(null, sampleChime, null)
        val recreatedObject = recreateObject(sampleDestination) { Channel.Destination(it) }
        assertEquals(sampleDestination, recreatedObject)
    }

    @Test
    fun `Webhook destination serialize and deserialize object should be equal`() {
        val sampleWebhook = Channel.Webhook(
            "https://domain.com/sample_url#1234567890",
            "schema",
            "slack.com",
            1234,
            "sample_path",
            mapOf(Pair("channel", "#1234567890")),
            mapOf(Pair("custom_header", "header_value")),
            "username",
            "password"
        )
        val sampleDestination = Channel.Destination(null, null, sampleWebhook)
        val recreatedObject = recreateObject(sampleDestination) { Channel.Destination(it) }
        assertEquals(sampleDestination, recreatedObject)
    }

    @Test
    fun `Channel serialize and deserialize object should be equal`() {
        val sampleChime = Channel.Chime("https://domain.com/sample_url#1234567890")
        val sampleDestination = Channel.Destination(null, sampleChime, null)
        val sampleChannel = Channel(
            "name",
            Channel.Type.Chime,
            "Sample Header",
            "Sample Footer",
            listOf("Feature1", "Feature2", "Feature3"),
            sampleDestination
        )
        val recreatedObject = recreateObject(sampleChannel) { Channel(it) }
        assertEquals(sampleChannel, recreatedObject)
    }

    @Test
    fun `Channel serialize and deserialize object with null values should be equal`() {
        val sampleChime = Channel.Chime("https://domain.com/sample_url#1234567890")
        val sampleDestination = Channel.Destination(null, sampleChime, null)
        val sampleChannel = Channel(
            "name",
            Channel.Type.Chime,
            null,
            null,
            listOf("Feature1", "Feature2", "Feature3"),
            sampleDestination
        )
        val recreatedObject = recreateObject(sampleChannel) { Channel(it) }
        assertEquals(sampleChannel, recreatedObject)
    }
}
