package com.onerivet.model.projection;

public class CoverageTypeProjection {

   private final Integer coverageTypeId;
   private final String name;

   public CoverageTypeProjection(
           Integer coverageTypeId,
           String name) {

       this.coverageTypeId = coverageTypeId;
       this.name = name;
   }

   public Integer getCoverageTypeId() {
       return coverageTypeId;
   }

   public String getName() {
       return name;
   }
}
