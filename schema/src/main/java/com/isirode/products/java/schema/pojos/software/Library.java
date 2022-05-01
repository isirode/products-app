package com.isirode.products.java.schema.pojos.software;

import lombok.Data;
import lombok.EqualsAndHashCode;

// FIXME : use typesafe builder ?
@EqualsAndHashCode(callSuper = true)
@Data
public class Library extends SoftwareProduct {
    // FIXME : implement an edge solution which does not require having this
    // Note : multiple possibilities for each language property
    public String writtenIn = "";
    public String writtenFor = "";
}
