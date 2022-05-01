package com.isirode.products.java.schema.pojos.software;

import lombok.Data;
import lombok.EqualsAndHashCode;

// Language can produce multiple thing, but there is one main production (bytecode etc)
@EqualsAndHashCode(callSuper = true)
@Data
public class Language extends SoftwareProduct {
    // TODO : move this onto paradigms
    public boolean imperative = false;
}
