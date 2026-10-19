package com.onerivet.model.projection;

public class ComponentProjection {

   private final Integer componentId;
   private final String name;

   public ComponentProjection(
           Integer componentId,
           String name) {

       this.componentId = componentId;
       this.name = name;
   } 

   public Integer getComponentId() {
       return componentId;
   }

   public String getName() {
       return name;
   }
}
