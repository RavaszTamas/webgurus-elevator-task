package com.tamas.ravasz.learning.lift.dto;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class InternalPanelDto {
  List<InternalButtonDto> internalButtons;
}
