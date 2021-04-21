package com.bookshop.mybookshop.controllers.rest;

import com.bookshop.mybookshop.data.ApiResponse;
import com.bookshop.mybookshop.domain.author.Author;
import com.bookshop.mybookshop.services.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;

@RestController
@AllArgsConstructor
@RequestMapping("/api/authors")
@Api(description = "Authors data API")
public class AuthorRestApiController {

    private AuthorService authorService;

    @ApiOperation("Get book from BookShop by id")
    @GetMapping("/by-id")
    public ApiResponse<Author> authorById(@RequestParam("id") Integer id) {
        Author author = authorService.receiveAuthorById(id);
        return ApiResponse.setAuthorApiResponse(new ArrayList<>(Collections.singleton(author)));
    }

    @ApiOperation("Get book from BookShop by id")
    @GetMapping("/by-fullname")
    public ApiResponse<Author> authorByFullName(@RequestParam("firstName") String firstName,
                                                @RequestParam("lastName") String lastName) {
        Author author = authorService.receiveAuthorByFullName(firstName, lastName);
        return ApiResponse.setAuthorApiResponse(new ArrayList<>(Collections.singleton(author)));
    }
}
