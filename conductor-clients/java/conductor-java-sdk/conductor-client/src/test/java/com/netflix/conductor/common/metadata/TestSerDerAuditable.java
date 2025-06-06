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
package com.netflix.conductor.common.metadata;

import org.junit.jupiter.api.Test;

import com.netflix.conductor.util.JsonTemplateSerDeserResolverUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestSerDerAuditable {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSerializationDeserialization() throws Exception {
        // 1. Unmarshal SERVER_JSON to SDK POJO
        String SERVER_JSON = JsonTemplateSerDeserResolverUtil.getJsonString("Auditable");

        // Since Auditable is abstract, we need a concrete implementation for testing
        AuditableImpl auditable = objectMapper.readValue(SERVER_JSON, AuditableImpl.class);

        // 2. Assert that the fields are all correctly populated
        assertNotNull(auditable);
        assertEquals("sample_ownerApp", auditable.getOwnerApp());
        assertEquals(123L, auditable.getCreateTime());
        assertEquals(123L, auditable.getUpdateTime());
        assertEquals("sample_createdBy", auditable.getCreatedBy());
        assertEquals("sample_updatedBy", auditable.getUpdatedBy());

        // 3. Marshall this POJO to JSON again
        String serializedJson = objectMapper.writeValueAsString(auditable);

        // 4. Compare the JSONs - nothing should be lost
        assertEquals(objectMapper.readTree(SERVER_JSON), objectMapper.readTree(serializedJson));
    }

    // Concrete implementation of Auditable for testing
    public static class AuditableImpl extends Auditable {
        // No additional fields or methods needed for this test
    }
}