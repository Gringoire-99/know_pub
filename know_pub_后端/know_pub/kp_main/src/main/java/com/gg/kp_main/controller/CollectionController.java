package com.gg.kp_main.controller;

import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.vo.CollectionItemVo;
import com.gg.kp_common.entity.vo.CollectionVo;
import com.gg.kp_common.entity.vo.save.NewCollection;
import com.gg.kp_common.entity.vo.save.NewCollectionItem;
import com.gg.kp_common.service.CollectionService;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @GetMapping("/get-collection-by-id")
    public Result<CollectionVo> getCollectionById(@ApiParam(required = true) String collectionId) {
        return collectionService.getCollectionById(collectionId);
    }

    @PostMapping("/add-collection")
    @PreAuthorize("hasRole('USER')")
    public Result<Integer> addCollection(@RequestBody NewCollection newCollection) {
        return collectionService.newCollection(newCollection);
    }

    @GetMapping("/get-collections")
    public Result<Page<CollectionVo>> getCollection(@ApiParam(required = true) String userId, @ApiParam PageParams pageParams) {
        return collectionService.getCollections(userId, pageParams);
    }

    @PostMapping("/add-collection-item")
    @PreAuthorize("hasRole('USER')")
    public Result<Integer> addCollectionItem(@RequestBody NewCollectionItem collectionItem) {
        return collectionService.addCollectionItem(collectionItem);
    }

    @GetMapping("/get-collection-items")
    public Result<Page<CollectionItemVo>> getCollectionItems(@ApiParam(required = true) String collectionId, @ApiParam(required = true) String type, @ApiParam PageParams pageParams) {
        return collectionService.getCollectionItems(collectionId,type, pageParams);
    }

    @DeleteMapping("/delete-collection-item")
    @PreAuthorize("hasRole('USER')")
    public Result<Integer> deleteCollectionItem(@RequestParam String collectionItemId) {
        return collectionService.deleteCollectionItem(collectionItemId);
    }

    @DeleteMapping("/delete-collection")
    @PreAuthorize("hasRole('USER')")
    public Result<Integer> deleteCollection(@RequestParam String collectionId) {
        return collectionService.deleteCollection(collectionId);
    }

    @PatchMapping("/update-collection")
    @PreAuthorize("hasRole('USER')")
    public Result<Integer> updateCollection(@RequestBody NewCollection newCollection, @RequestParam String collectionId) {
        return collectionService.updateCollection(newCollection, collectionId);
    }
}
