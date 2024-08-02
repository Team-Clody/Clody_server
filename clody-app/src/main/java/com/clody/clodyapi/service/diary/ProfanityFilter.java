package com.clody.clodyapi.service.diary;

import com.vane.badwordfiltering.BadWordFiltering;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProfanityFilter {

  public boolean containsProfanity(List<String> content) {
    BadWordFiltering filter = new BadWordFiltering();
    return content.stream().anyMatch(filter::check);
  }
}
