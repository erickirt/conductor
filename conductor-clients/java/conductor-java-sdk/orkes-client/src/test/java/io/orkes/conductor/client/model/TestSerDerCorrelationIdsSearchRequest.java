/*
 * Copyright 2025 Conductor Authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.client.model;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.util.JsonTemplateSerDeserResolverUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestSerDerCorrelationIdsSearchRequest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSerializationDeserialization() throws IOException {
        // 1. Unmarshal SERVER_JSON to SDK POJO
        String SERVER_JSON = JsonTemplateSerDeserResolverUtil.getJsonString("CorrelationIdsSearchRequest");
        CorrelationIdsSearchRequest request = objectMapper.readValue(SERVER_JSON, CorrelationIdsSearchRequest.class);

        // 2. Assert that the fields are all correctly populated
        assertNotNull(request);
        assertNotNull(request.getCorrelationIds());
        assertNotNull(request.getWorkflowNames());

        assertEquals(1, request.getCorrelationIds().size());
        assertEquals(1, request.getWorkflowNames().size());

        // Check first items in lists
        String expectedCorrelationId = request.getCorrelationIds().get(0);
        String expectedWorkflowName = request.getWorkflowNames().get(0);

        assertNotNull(expectedCorrelationId);
        assertNotNull(expectedWorkflowName);

        // 3. Marshall this POJO to JSON again
        String serializedJson = objectMapper.writeValueAsString(request);

        // 4. Compare the JSONs - nothing should be lost
        assertEquals(objectMapper.readTree(SERVER_JSON), objectMapper.readTree(serializedJson));
    }
}