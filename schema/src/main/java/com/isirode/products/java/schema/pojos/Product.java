package com.isirode.products.java.schema.pojos;

import com.fasterxml.jackson.annotation.*;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaExamples;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

// TODO : move to own module ?
// TODO : add commentary made by user

// Info : If property is not nullable, it need to be present

// We use Java classes because @Data does not work on a Kotlin class (for the equals) for now
// We don't use Kotlin's data class because they are not "open"
// Properties are public because otherwise Kotlin cannot access them with reflection
@Data
// Cannot use @JsonTypeInfo or @JsonSubTypes
public class Product {
    @JsonPropertyDescription("Type of product")
    // FIXME : those examples are duplicates, or let it this way ?
    // Info : JsonSchemaOptions of kjetland is not usable, examples are better for type anyway
    @JsonSchemaExamples({
        "software.library", "software.language"
    })
    @JsonProperty(required = true)
    public String type = "";

    @JsonPropertyDescription("Unique identifier of this product")
    @JsonProperty(required = true)
    public String name = "";

    @JsonPropertyDescription("Brief description of this product")
    @JsonProperty(required = true)
    public String description = "";

    @JsonPropertyDescription("Placeholder for properties, do not use this directly, just setup additional properties normally")
    public Map<String, Object> properties = new HashMap<>();

    @JsonAnyGetter
    Map<String, Object> getProperties() {
        return properties;
    }

    @JsonAnySetter
    void add(String key, Object value) {
        properties.put(key, value);
    }
}
