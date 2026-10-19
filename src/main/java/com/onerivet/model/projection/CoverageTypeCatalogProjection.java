package com.onerivet.model.projection;

public class CoverageTypeCatalogProjection {

   private final Integer coverageTypeId;
   private final String coverageTypeName;

   public CoverageTypeCatalogProjection(
           Integer coverageTypeId,
           String coverageTypeName) {

       this.coverageTypeId = coverageTypeId;
       this.coverageTypeName = coverageTypeName;
   }

   public Integer getCoverageTypeId() {
       return coverageTypeId;
   }

   public String getCoverageTypeName() {
       return coverageTypeName;
   }
}
