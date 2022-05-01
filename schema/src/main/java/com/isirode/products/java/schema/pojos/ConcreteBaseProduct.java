package com.isirode.products.java.schema.pojos;

// We need this empty class so that ProductJsonDeserializer does not go on overflow while doing recursive deserialization
// FIXME : find something more elegant ?
public class ConcreteBaseProduct  extends Product {
}
