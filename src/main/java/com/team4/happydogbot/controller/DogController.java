package com.team4.happydogbot.controller;

import com.team4.happydogbot.entity.Dog;
import com.team4.happydogbot.service.DogService;
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
 * Controller class for a Dog object containing a set of API endpoints
 * for accessing routes with separate HTTP methods
 *
 * @see Dog
 * @see DogService
 * @see DogController
 */
@RestController
@RequestMapping("/dog")
@Tag(name = "Dogs", description = "CRUD operations and other endpoints for working with dogs")
public class DogController {

    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @Operation(
            summary = "Adding a dog",
            description = "Adding a new dog from the request body"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The dog has been added",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Incorrect dog parameters",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                            )
                    }
            )
    }
    )
    @PostMapping
    public ResponseEntity<Dog> add(@RequestBody Dog dog) {
        dogService.add(dog);
        return ResponseEntity.ok(dog);
    }

    @Operation(summary = "Getting a dog by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "A dog found by id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
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
                    description = "The dog was found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The dog was not found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                            )
                    }
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Dog> get(@PathVariable Long id) {
        Dog dog = dogService.get(id);
        if (dog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dog);
    }

    @Operation(summary = "Deleting a dog by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "A dog found by id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
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
                    description = "Dog removed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The cat has not been removed"
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (dogService.remove(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Changing dog details",
            description = "Update dog data from request body"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Dog data updated",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dog data not updated",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                            )
                    }
            )
    }
    )
    @PutMapping
    public ResponseEntity<Dog> update(@RequestBody Dog dog) {
        dogService.update(dog);
        return ResponseEntity.ok(dog);
    }

    @Operation(summary = "View all dogs",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dogs found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    )
            }
    )
    @GetMapping("/all")
    public Collection<Dog> getAll() {
        return this.dogService.getAll();
    }
}
