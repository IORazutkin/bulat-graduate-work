package com.example.bulatgraduatework.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.*;

public final class CopyProperties {
  public static void copy (Object source, Object destination, String ...ignoreProperties) {
    List<String> ignoreList = new ArrayList<>(Arrays.asList(ignoreProperties));
    ignoreList.addAll(Arrays.asList(getNullPropertyNames(source)));

    String[] ignore = ignoreList.toArray(new String[0]);

      BeanUtils.copyProperties(source, destination, ignore);
  }

  private static String[] getNullPropertyNames (Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
    Set<String> emptyNames = new HashSet<>();
    for(java.beans.PropertyDescriptor pd : pds) {
      //check if value of this property is null then add it to the collection
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) emptyNames.add(pd.getName());
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }
}
