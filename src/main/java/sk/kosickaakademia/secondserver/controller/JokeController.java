package sk.kosickaakademia.secondserver.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
public class JokeController {
    String joke1 = "Why did the tree go to the dentist? It needed a root canal.";
    String joke2 = "Where do cows go for entertainment? The mooooo-vies!";
    String joke3 = "What’s an astronaut’s favorite candy? A Mars bar.";
    List<String> list = new ArrayList<>();

    public JokeController(){
      list.add(joke1);
      list.add(joke2);
      list.add(joke3);
    }


    @GetMapping("/jokes")
    public ResponseEntity<String> getJokes(){
        JSONObject object = new JSONObject();
        JSONArray jArray = new JSONArray();
        for(String s : list)
             jArray.add(s);
        object.put("jokes",jArray);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());

    }

    @GetMapping("/joke/{id}")
    public ResponseEntity<String> getJokeById(@PathVariable Integer id){
        JSONObject object = new JSONObject();
        int status;
        if(id<1 || id>list.size()){
            // incorrect id
            object.put("error","Invalid id");
            status = 404;
        }else {
            object.put("joke", list.get(id - 1));
            status = 200;
        }
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());
    }
    @GetMapping("/joke")
    public ResponseEntity<String> getRandomJoke(){

        JSONObject object = new JSONObject();
        int status;
        if(list.size()==0){
            // incorrect id
            object.put("error","No joke in database");
            status = 404;
        }else {
            Random random = new Random();
            int index = random.nextInt(list.size());
            object.put("id",index);
            object.put("joke", list.get(index));
            status = 200;
        }
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());
    }
}
