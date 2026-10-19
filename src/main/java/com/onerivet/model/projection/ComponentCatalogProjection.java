package com.onerivet.model.projection;

public class ComponentCatalogProjection {

   private final Integer componentId;
   private final String componentName;

   public ComponentCatalogProjection(
           Integer componentId,
           String componentName) {

       this.componentId = componentId;
       this.componentName = componentName;
   }

   public Integer getComponentId() {
       return componentId;
   }

   public String getComponentName() {
       return componentName;
   }
}
