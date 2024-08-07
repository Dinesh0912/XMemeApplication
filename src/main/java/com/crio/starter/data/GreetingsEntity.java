package com.crio.starter.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "greetings")
@NoArgsConstructor
@AllArgsConstructor
public class GreetingsEntity {

  private String extId;
  private String name;
  private String caption;
  private String url;

}
