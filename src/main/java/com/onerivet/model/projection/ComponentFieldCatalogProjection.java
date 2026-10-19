package com.onerivet.model.projection;

public class ComponentFieldCatalogProjection {

   private final Integer coverageFieldId;
   private final String coverageFieldName;

   public ComponentFieldCatalogProjection(
           Integer coverageFieldId,
           String coverageFieldName) {

       this.coverageFieldId = coverageFieldId;
       this.coverageFieldName = coverageFieldName;
   }

   public Integer getCoverageFieldId() {
       return coverageFieldId;
   }

   public String getCoverageFieldName() {
       return coverageFieldName;
   }
}
