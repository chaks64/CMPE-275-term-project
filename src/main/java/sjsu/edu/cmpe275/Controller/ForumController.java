package sjsu.edu.cmpe275.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import sjsu.edu.cmpe275.service.impl.ForumServiceImpl;

@Controller
@RequestMapping("/forum")
@CrossOrigin
public class ForumController {

    @Autowired
    private ForumServiceImpl forumService;

    @PostMapping(value = "/createMsg")
    public ResponseEntity<?> createMsg(@RequestBody Map<String, Object> inputJson){
        return forumService.createMsg(inputJson);
    }

    @GetMapping(value = "/messages/{eventid}/{forumType}")
    public ResponseEntity<?> getMsgForParticipantForum(@PathVariable Long eventid,@PathVariable String forumType){
        return forumService.getMsgForParticipantForum(eventid,forumType);
    }

    @GetMapping(value = "/msgs/{eventid}/{forumType}")
    public ResponseEntity<?> getMsgForSignUpForum(@PathVariable Long eventid, @PathVariable String forumType){
        return forumService.getMsgForSignUpForum(eventid,forumType);
    }

}
