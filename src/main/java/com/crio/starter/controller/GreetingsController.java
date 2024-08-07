package com.crio.starter.controller;

import com.crio.starter.exchange.PostResponseDto;
import com.crio.starter.exchange.RequestDto;
import com.crio.starter.exchange.ResponseDto;
import com.crio.starter.service.GreetingsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@AllArgsConstructor
public class GreetingsController {

  private final GreetingsService greetingsService;

  private int counter;

  @GetMapping("/memes/{id}")
  public ResponseEntity<?> sayHello(@PathVariable String id) {
    return greetingsService.getMessage(id);
  }

  @GetMapping("/memes/")
  public List<ResponseDto> sayHello() {
    return greetingsService.getAllMessages();
  }

  @PostMapping("/memes/")
  public ResponseEntity<?> createMessage(@RequestBody RequestDto requestDto) {
    if (isEmpty(requestDto)) {
      return new ResponseEntity<>("No meme!", HttpStatus.BAD_REQUEST);
    }
    if (isDuplicate(requestDto.getName(), requestDto.getUrl(), requestDto.getCaption())) {
      return new ResponseEntity<>("Duplicate Meme!", HttpStatus.CONFLICT);
    }
    counter++;
    String id = createId(counter);
    PostResponseDto postResponseDto = greetingsService.createMessage(id, requestDto.getName(), requestDto.getCaption(), requestDto.getUrl());
    return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
  }

  public boolean isEmpty(RequestDto requestDto) {
    return requestDto.getName() == null && requestDto.getUrl() == null && requestDto.getCaption() == null;
  }

  public boolean isDuplicate(String name, String url, String caption) {
    List<ResponseDto> memeList = greetingsService.getAllMessages();
    for (ResponseDto responseDto : memeList) {
      if (responseDto.getName().equals(name) && responseDto.getUrl().equals(url) && responseDto.getCaption().equals(caption)) {
        return true;
      }
    }
    return false;
  }

  public String createId(int counter) {
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
