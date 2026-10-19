package com.onerivet.model.projection;

public class StandardCoverageProjection {

   private final Integer coverageId;
   private final String name;
   private final Boolean isStandard;

   public StandardCoverageProjection(
           Integer coverageId,
           String name,
           Boolean isStandard) {

       this.coverageId = coverageId;
       this.name = name;
       this.isStandard = isStandard;
   }

   public Integer getCoverageId() {
       return coverageId;
   }

   public String getName() {
       return name;
   }

   public Boolean getIsStandard() {
       return isStandard;
   }
}
