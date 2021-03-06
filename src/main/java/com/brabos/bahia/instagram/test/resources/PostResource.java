package com.brabos.bahia.instagram.test.resources;

import com.brabos.bahia.instagram.test.domains.Post;
import com.brabos.bahia.instagram.test.dto.NewPostDTO;
import com.brabos.bahia.instagram.test.dto.PostsByUserDTO;
import com.brabos.bahia.instagram.test.helper.URL;
import com.brabos.bahia.instagram.test.services.PostServices;
import com.brabos.bahia.instagram.test.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping(value = "/posts")
public class PostResource {

    @Autowired
    private PostServices services;

    //return a post according to the given id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(services.findById(id));
    }

    //return a list of posts according to the list of ids in the string "ids" example: 1,2,3
    @GetMapping(value = "/all")
    public ResponseEntity<Page<Post>> findPostsByUsersIds(@RequestParam(value = "ids") String ids,
                                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "linesPerPage", defaultValue = "10")Integer linesPerPage,
                                       @RequestParam(value = "orderBy", defaultValue = "id")String orderBy,
                                       @RequestParam(value = "direction", defaultValue = "DESC")String direction){
        if (ids == null) {
            throw new ObjectNotFoundException("Informe ids dos usuários para pesquisa");
        }
        return ResponseEntity.ok().body(services.getPostsByUsersIds(URL.decodeLongList(ids), page, linesPerPage, orderBy, direction));
    }

    //return a user list of posts **CONTAINING ONLY THE ID AND IMAGE URL**
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<Page<PostsByUserDTO>> findPostsByUserId(@PathVariable("id") Long id,
                                                                  @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                  @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
                                                                  @RequestParam(value = "orderBy", defaultValue = "id")String orderBy,
                                                                  @RequestParam(value = "direction", defaultValue = "DESC")String direction){
        return ResponseEntity.ok().body(services.getPostsByUserId(id, page, linesPerPage, orderBy, direction));
    }



    //create a new post
    @PostMapping
    public ResponseEntity<Void> newPost(@Valid @RequestBody NewPostDTO newPostDTO){
        Post post = services.fromDTO(newPostDTO);
        post = services.insert(post);
        URI uri  = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // like/unlike a post
    @PostMapping(value = "/{id}")
    public ResponseEntity<Void> like(@PathVariable("id") Long postId){
        if (services.like(postId) != null) return ResponseEntity.noContent().build();
        throw new RuntimeException("Erro ao 'likar' postagem");
    }
}
