package com.team4.happydogbot.controller;

import com.team4.happydogbot.entity.Cat;
import com.team4.happydogbot.service.CatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Controller class for a Cat object containing a set of API endpoints
 * for accessing routes with separate HTTP methods
 *
 * @see Cat
 * @see CatService
 * @see CatController
 */
@RestController
@RequestMapping("/cat")
@Tag(name = "Cats", description = "CRUD operations and other endpoints for working with cats")
public class CatController {

    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @Operation(
            summary = "Adding a cat",
            description = "Adding a new cat from the request body"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The cat has been added",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Incorrect cat parameters",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                            )
                    }
            )
    }
    )
    @PostMapping
    public ResponseEntity<Cat> add(@RequestBody Cat cat) {
        catService.add(cat);
        return ResponseEntity.ok(cat);
    }

    @Operation(summary = "Getting a cat by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "A cat found by id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    )
            }
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The cat was found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The cat was not found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                            )
                    }
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Cat> get(@PathVariable Long id) {
        Cat dog = catService.get(id);
        if (dog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dog);
    }

    @Operation(summary = "Deleting a cat by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "A cat found by id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    )
            }
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cat removed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The cat has not been removed"
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (catService.remove(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Changing cat details",
            description = "Update cat data from request body"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cat data updated",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Cat data not updated",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                            )
                    }
            )
    }
    )
    @PutMapping
    public ResponseEntity<Cat> update(@RequestBody Cat cat) {
        catService.update(cat);
        return ResponseEntity.ok(cat);
    }

    @Operation(summary = "View all cats",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cats found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    )
            }
    )
    @GetMapping("/all")
    public Collection<Cat> getAll() {
        return this.catService.getAll();
    }
}
