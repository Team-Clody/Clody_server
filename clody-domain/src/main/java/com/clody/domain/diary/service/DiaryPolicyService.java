package com.clody.domain.diary.service;

import com.vane.badwordfiltering.BadWordFiltering;
import java.util.List;

public class DiaryPolicyService {

  public boolean checkForProfanity(List<String> contents){
    BadWordFiltering filter = new BadWordFiltering();
    return contents.stream().anyMatch(filter::check);
  }

}
