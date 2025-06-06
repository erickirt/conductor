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

import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.util.JsonTemplateSerDeserResolverUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestSerDerConductorUser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testSerializationDeserialization() throws Exception {
        // 1. Unmarshal SERVER_JSON to SDK POJO
        String SERVER_JSON = JsonTemplateSerDeserResolverUtil.getJsonString("ConductorUser");
        ConductorUser conductorUser = objectMapper.readValue(SERVER_JSON, ConductorUser.class);

        // 2. Assert that the fields are all correctly populated
        assertNotNull(conductorUser);

        // Check String fields
        assertNotNull(conductorUser.getId());
        assertNotNull(conductorUser.getName());
        assertNotNull(conductorUser.getUuid());

        // Check List fields
        assertNotNull(conductorUser.getGroups());
        assertTrue(conductorUser.getGroups().size() > 0);
        assertNotNull(conductorUser.getGroups().get(0));

        assertNotNull(conductorUser.getRoles());
        assertTrue(conductorUser.getRoles().size() > 0);
        assertNotNull(conductorUser.getRoles().get(0));

        // 3. Marshall this POJO to JSON again
        String serializedJson = objectMapper.writeValueAsString(conductorUser);

        // 4. Compare the JSONs - exclude deprecated applicationUser field
        JsonNode originalJsonNode = objectMapper.readTree(SERVER_JSON);
        JsonNode serializedJsonNode = objectMapper.readTree(serializedJson);

        // Remove applicationUser field from both nodes if present
        if (originalJsonNode instanceof ObjectNode && ((ObjectNode) originalJsonNode).has("applicationUser")) {
            ((ObjectNode) originalJsonNode).remove("applicationUser");
        }

        if (serializedJsonNode instanceof ObjectNode && ((ObjectNode) serializedJsonNode).has("applicationUser")) {
            ((ObjectNode) serializedJsonNode).remove("applicationUser");
        }

        // Compare the modified JSON trees
        assertEquals(originalJsonNode, serializedJsonNode);
    }
}