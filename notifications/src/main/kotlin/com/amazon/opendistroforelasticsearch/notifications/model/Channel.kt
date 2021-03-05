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

import com.amazon.opendistroforelasticsearch.notifications.util.suppressWarningCast
import org.elasticsearch.common.io.stream.StreamInput
import org.elasticsearch.common.io.stream.StreamOutput
import org.elasticsearch.common.io.stream.Writeable

data class Channel(
    val name: String,
    val type: Type,
    val header: String?,
    val footer: String?,
    val features: List<String>,
    val destination: Destination
) : Writeable {
    enum class Type { Slack, Chime, Webhook }

    data class Slack(
        val url: String
    ) : Writeable {
        companion object {
            val reader = Writeable.Reader { Slack(it) }
        }

        constructor(input: StreamInput) : this(
            url = input.readString()
        )

        override fun writeTo(output: StreamOutput) {
            output.writeString(url)
        }
    }

    data class Chime(
        val url: String
    ) : Writeable {
        companion object {
            val reader = Writeable.Reader { Chime(it) }
        }

        constructor(input: StreamInput) : this(
            url = input.readString()
        )

        override fun writeTo(output: StreamOutput) {
            output.writeString(url)
        }
    }

    data class Webhook(
        val url: String,
        val schema: String,
        val host: String,
        val port: Int,
        val path: String,
        val queryParams: Map<String, String>,
        val headerParams: Map<String, String>,
        val username: String?,
        val password: String?
    ) : Writeable {
        companion object {
            val reader = Writeable.Reader { Webhook(it) }
        }

        constructor(input: StreamInput) : this(
            url = input.readString(),
            schema = input.readString(),
            host = input.readString(),
            port = input.readInt(),
            path = input.readString(),
            queryParams = suppressWarningCast(input.readMap()),
            headerParams = suppressWarningCast(input.readMap()),
            username = input.readOptionalString(),
            password = input.readOptionalString()
        )

        override fun writeTo(output: StreamOutput) {
            output.writeString(url)
            output.writeString(schema)
            output.writeString(host)
            output.writeInt(port)
            output.writeString(path)
            output.writeMap(queryParams)
            output.writeMap(headerParams)
            output.writeOptionalString(username)
            output.writeOptionalString(password)
        }
    }

    data class Destination(
        val slack: Slack?,
        val chime: Chime?,
        val webhook: Webhook?
    ) : Writeable {
        companion object {
            val reader = Writeable.Reader { Destination(it) }
        }

        constructor(input: StreamInput) : this(
            slack = input.readOptionalWriteable(Slack.reader),
            chime = input.readOptionalWriteable(Chime.reader),
            webhook = input.readOptionalWriteable(Webhook.reader)
        )

        override fun writeTo(output: StreamOutput) {
            output.writeOptionalWriteable(slack)
            output.writeOptionalWriteable(chime)
            output.writeOptionalWriteable(webhook)
        }
    }

    constructor(input: StreamInput) : this(
        name = input.readString(),
        type = input.readEnum(Type::class.java),
        header = input.readOptionalString(),
        footer = input.readOptionalString(),
        features = input.readStringList(),
        destination = Destination.reader.read(input)
    )

    override fun writeTo(output: StreamOutput) {
        output.writeString(name)
        output.writeEnum(type)
        output.writeOptionalString(header)
        output.writeOptionalString(footer)
        output.writeStringCollection(features)
        destination.writeTo(output)
    }
}
