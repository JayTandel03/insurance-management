package com.onerivet.model.enums;


public enum PaymentMethod {


   CREDITCARD("Credit Card"),
   DEBITCARD("Debit Card");


   private final String displayName;


   PaymentMethod(String displayName) {
      this.displayName = displayName;
   }


   public String getDisplayName() {
      return displayName;
   }
}

