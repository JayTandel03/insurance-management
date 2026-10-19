package com.onerivet.model.projection;

public class CoverageFieldProjection {

   private final Integer coverageFieldId;
   private final String name;

   public CoverageFieldProjection(
           Integer coverageFieldId,
           String name) {

       this.coverageFieldId = coverageFieldId;
       this.name = name;
   }

   public Integer getCoverageFieldId() {
       return coverageFieldId;
   }

   public String getName() {
       return name;
   }
}
