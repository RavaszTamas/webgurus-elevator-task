package com.tamas.ravasz.learning.lift.converter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseConverter<SOURCE, TARGET> {
  public abstract TARGET convertSource(SOURCE source, boolean expand);

  public abstract SOURCE convertTarget(TARGET target, boolean expand);

  public TARGET convertSource(SOURCE source) {
    return convertSource(source, false);
  }

  public SOURCE convertTarget(TARGET target) {
    return convertTarget(target, false);
  }

  public List<TARGET> convertSource(List<SOURCE> sourceList) {
    return convertSource(sourceList, false);
  }

  public List<TARGET> convertSource(List<SOURCE> sourceList, boolean expand) {
    if (Objects.isNull(sourceList)) {
      return List.of();
    }
    return sourceList.stream()
        .map(source -> convertSource(source, expand))
        .collect(Collectors.toList());
  }

  public List<SOURCE> convertTarget(List<TARGET> targetList) {
    return convertTarget(targetList, false);
  }

  public List<SOURCE> convertTarget(List<TARGET> targetList, boolean expand) {
    if (Objects.isNull(targetList)) {
      return List.of();
    }
    return targetList.stream()
        .map(target -> convertTarget(target, expand))
        .collect(Collectors.toList());
  }
}
