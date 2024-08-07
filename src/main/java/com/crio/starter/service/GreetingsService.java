package com.crio.starter.service;

import com.crio.starter.data.GreetingsEntity;
import com.crio.starter.exchange.PostResponseDto;
import com.crio.starter.exchange.ResponseDto;
import com.crio.starter.repository.GreetingsRepository;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GreetingsService {

  private final GreetingsRepository greetingsRepository;

  public ResponseEntity<?> getMessage(String id) {
    GreetingsEntity greetingsEntity = greetingsRepository.findByExtId(id);
    if (greetingsEntity == null) {
      return new ResponseEntity<>("No meme with the specified id!", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(new ResponseDto(id, greetingsEntity.getName(), greetingsEntity.getCaption(), greetingsEntity.getUrl()), HttpStatus.OK);
  }

  public List<ResponseDto> getAllMessages() {
    List<ResponseDto> messageList = new ArrayList<>();
    int maximum = 100;
    for (long i = greetingsRepository.count(); i > 0 && maximum > 0; i--) {
      maximum--;
      String id = createId((int)i);
      GreetingsEntity greetingsEntity = greetingsRepository.findByExtId(id);
      messageList.add(new ResponseDto(id, greetingsEntity.getName(), greetingsEntity.getCaption(), greetingsEntity.getUrl()));
    }
    return messageList;
  }

  public PostResponseDto createMessage(String id, String name, String caption, String url) {
    GreetingsEntity greetingsEntity = new GreetingsEntity(id, name, caption, url);
    greetingsRepository.save(greetingsEntity);
    return new PostResponseDto(id);
  }

  private String createId(int counter) {
    // int digits = 0;
    // int temp = counter;
    // while (temp > 0) {
    //   digits++;
    //   temp /= 10;
    // }
    // StringBuilder builder = new StringBuilder();
    // int zeros = 3 - digits;
    // for (int i = 0; i < zeros; i++) {
    //   builder.append("0");
    // }
    // builder.append(counter);
    // return builder.toString();
    return Integer.toString(counter);
  }
}
