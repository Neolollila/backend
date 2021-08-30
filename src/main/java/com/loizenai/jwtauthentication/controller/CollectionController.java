package com.loizenai.jwtauthentication.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.loizenai.jwtauthentication.model.*;
import com.loizenai.jwtauthentication.repository.*;
import com.loizenai.jwtauthentication.security.services.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/collection")
public class CollectionController {

    @Autowired
    public ThemeRepository themeRepository;

    @Autowired
    public CollectionRepository collectionRepository;

    @Autowired
    public ItemRepository itemRepository;

    @Autowired
    public CommentRepository commentRepository;

    @Autowired
    public LikeRepository likeRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public TypeRepository typeRepository;

    @Autowired
    public FielditemRepository fielditemRepository;



    @GetMapping("/theme")
    public ResponseEntity <List<Theme>> getThemeList() {
        return ResponseEntity.ok(this.themeRepository.findAll());
    }

    @GetMapping("/type")
    public ResponseEntity <List<Type>> getTypeList() {
        return ResponseEntity.ok(this.typeRepository.findAll());
    }

    @PostMapping("/new")
    public ResponseEntity<Collection> createCollection(@RequestBody ObjectNode json){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection newCollection = new Collection();
        newCollection.setName(json.get("name").asText());
        newCollection.setTheme(themeRepository.getOne(json.get("theme").asLong()));
        newCollection.setDescription(json.get("description").asText());
        newCollection.setImage(json.get("image").asText());
        newCollection.setUser(((UserPrinciple) user).getUser());
        return new ResponseEntity<>(collectionRepository.save(newCollection), HttpStatus.CREATED);
    }

    @PostMapping("/new/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection> createUserCollection(@PathVariable(value = "id") Long id, @RequestBody ObjectNode json){
        Collection newCollection = new Collection();
        newCollection.setName(json.get("name").asText());
        newCollection.setTheme(themeRepository.getOne(json.get("theme").asLong()));
        newCollection.setDescription(json.get("description").asText());
        newCollection.setImage(json.get("image").asText());
        newCollection.setUser(userRepository.getOne(id));
        return new ResponseEntity<>(collectionRepository.save(newCollection), HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity <List<Collection>> getCurrentUserCollections() {
        //System.out.println();
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(
                this.collectionRepository.findCollectionByIdUser(((UserPrinciple) user).getUser())
        );
    }

    @GetMapping("/user/{id}")
    public ResponseEntity <List<Collection>> getUserCollections(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(
                this.collectionRepository.findCollectionByIdUser(userRepository.getOne(id))
        );
    }

    @DeleteMapping("/delete/{id}")

    public ResponseEntity<Void> removeCollection(@PathVariable(value = "id") Long id){
        this.collectionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity <Collection> getCollectionById(@PathVariable(value = "id") Long id) {

        return ResponseEntity.ok(this.collectionRepository.findById(id).get());
    }

    @PostMapping("/sendEdit/{id}")
    public ResponseEntity<Void> editCollection(@PathVariable(value = "id") Long id,@RequestBody ObjectNode json){
        Collection updateCollection = this.collectionRepository.findById(id).get();
        updateCollection.setName(json.get("name").asText());
        updateCollection.setImage(json.get("image").asText());
        updateCollection.setTheme(themeRepository.getOne(json.get("theme").asLong()));
        updateCollection.setDescription(json.get("description").asText());
        this.collectionRepository.save(updateCollection);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/newItem")
    public ResponseEntity<Item> createItem(@PathVariable(value = "id") Long id,@RequestBody ObjectNode json){
        Item newItem = new Item();
        newItem.setName(json.get("name").asText());
        newItem.setCollection(collectionRepository.getOne(id));
        newItem = itemRepository.save(newItem);
        for (JsonNode node : json.get("fieldItems")) {
            Fielditem fielditem = new Fielditem();
            fielditem.setName(node.get("name").asText());
            fielditem.setValue(node.get("value").asText());
            fielditem.setType(typeRepository.getOne(node.get("idType").asLong()));
            fielditem.setItem(newItem);
            fielditemRepository.save(fielditem);
        }

        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }


    @GetMapping("/{id}/getItems")
    public ResponseEntity <List<Item>> getItemByCollectionId(@PathVariable(value = "id") Long id) {

        return ResponseEntity.ok(collectionRepository.getOne(id).getItems());
    }

    @GetMapping("/getItem/{id}")
    public ResponseEntity <Item> getItemById(@PathVariable(value = "id") Long id) {

        return ResponseEntity.ok(this.itemRepository.findById(id).get());
    }

    @PostMapping("/editItem/{id}")
    public ResponseEntity<Void> editItem(@PathVariable(value = "id") Long id,@RequestBody ObjectNode json){
        Item updateItem = this.itemRepository.findById(id).get();
        updateItem.setName(json.get("name").asText());
        this.itemRepository.save(updateItem);
        for (JsonNode node : json.get("fieldItems")) {
            Fielditem fielditem = this.fielditemRepository.findById(node.get("id").asLong()).get();
            fielditem.setName(node.get("name").asText());
            fielditem.setValue(node.get("value").asText());
            //fielditem.setType(typeRepository.getOne(node.get("idType").asLong()));
            fielditemRepository.save(fielditem);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteItem/{id}")

    public ResponseEntity<Void> removeItem(@PathVariable(value = "id") Long id){
        this.itemRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteFieldItem/{id}")

    public ResponseEntity<Void> removeFieldItem(@PathVariable(value = "id") Long id){
        this.fielditemRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addComment")
    public ResponseEntity<Comment> addComment(@RequestBody ObjectNode json){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment newComment = new Comment();
        newComment.setText(json.get("text").asText());
        newComment.setItem(itemRepository.getOne(json.get("id_item").asLong()));
        newComment.setUser(((UserPrinciple) user).getUser());
        return new ResponseEntity<Comment>(commentRepository.save(newComment), HttpStatus.CREATED);
    }

    @PostMapping("/addLike/{id}")
    public ResponseEntity<String> addLike(@PathVariable(value = "id") Long id){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Like tr = likeRepository.findByIdUserAndItem(((UserPrinciple) user).getUser(),itemRepository.getOne(id));
        if (tr == null ) {
            Like newLike = new Like();
            newLike.setItem(itemRepository.getOne(id));
            newLike.setUser(((UserPrinciple) user).getUser());
            likeRepository.save(newLike);
        } else {
            likeRepository.deleteById(tr.getId());
        }
        String  likeAmount = likeRepository.countLikes(itemRepository.getOne(id)) ;
        return new ResponseEntity<String>(likeAmount, HttpStatus.CREATED);
    }

    @GetMapping("/getLike/{id}")
    public ResponseEntity<String> getLike(@PathVariable(value = "id") Long id){
        String  likeAmount = likeRepository.countLikes(itemRepository.getOne(id)) ;
        return new ResponseEntity<String>(likeAmount, HttpStatus.CREATED);
    }


    @GetMapping("/home/lastAddedItems")
    public ResponseEntity <List<Item>> getLastAddedItems() {
        Pageable sortedByIdDesc = PageRequest.of(0, 10, Sort.by("id").descending());
        return ResponseEntity.ok(this.itemRepository.lastAddedItems(sortedByIdDesc));
    }

    @GetMapping("/home/largestCollections")
    public ResponseEntity <List<Collection>> getLargestCollections() {//List<Collection>

        return ResponseEntity.ok(this.collectionRepository.findByIds(this.collectionRepository.findBigestCollection()));
    }

}
