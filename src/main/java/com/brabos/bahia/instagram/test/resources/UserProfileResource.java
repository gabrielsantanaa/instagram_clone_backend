package com.brabos.bahia.instagram.test.resources;

import com.brabos.bahia.instagram.test.domains.UserProfile;
import com.brabos.bahia.instagram.test.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/users")
public class UserProfileResource {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userProfileService.find(id));
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(userProfileService.findAll());
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                              @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
                                              @RequestParam(value = "orderBy", defaultValue = "username")String orderBy,
                                              @RequestParam(value = "direction", defaultValue = "ASC")String direction)
    {
        Page<UserProfile> list = userProfileService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}
