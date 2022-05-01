package com.isirode.products.java.schema.pojos.software;

import com.isirode.products.java.schema.pojos.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SoftwareProduct extends Product {
    public String officialWebsiteURL = "";
    public String officialSourceURL = "";
    public String officialDocumentationURL = "";
    public String wikipediaURL = "";
}
