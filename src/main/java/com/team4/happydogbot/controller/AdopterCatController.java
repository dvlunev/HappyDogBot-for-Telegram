package com.team4.happydogbot.controller;

import com.team4.happydogbot.entity.AdopterCat;
import com.team4.happydogbot.service.AdopterCatService;
import com.team4.happydogbot.service.Bot;
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
 * A controller class for an AdopterCat object containing a set of API endpoints
 * for accessing routes with separate HTTP methods
 *
 * @see AdopterCat
 * @see AdopterCatService
 * @see AdopterCatController
 */
@RestController
@RequestMapping("/adopter_cat")
@Tag(name = "Adopters", description = "CRUD operations and other endpoints for working with adopters")
public class AdopterCatController {
    private final AdopterCatService adopterCatService;

    public AdopterCatController(AdopterCatService adopterCatService) {
        this.adopterCatService = adopterCatService;
    }

    @Operation(
            summary = "Adding an adopter",
            description = "Adding a new adopter from the request body"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Adopter has been added",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdopterCat.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Incorrect adopter parameters",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdopterCat.class))
                            )
                    }
            )
    }
    )
    @PostMapping
    public ResponseEntity<AdopterCat> add(@RequestBody AdopterCat adopterCat) {
        adopterCatService.add(adopterCat);
        return ResponseEntity.ok(adopterCat);
    }

    @Operation(summary = "Getting an adopter by chatId",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Adopter found by chatId",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdopterCat.class)
                            )
                    )
            }
    )
    @Parameters(value = {
            @Parameter(name = "chatId", example = "1234567890")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The adopter was found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdopterCat.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The adopter was not found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdopterCat.class))
                            )
                    }
            )
    }
    )
    @GetMapping("/{chatId}")
    public ResponseEntity<AdopterCat> get(@PathVariable Long chatId) {
        AdopterCat adopterCat = adopterCatService.get(chatId);
        if (adopterCat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adopterCat);
    }

    @Operation(summary = "Deleting an adopter by chatId",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Adopter found by chatId",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdopterCat.class)
                            )
                    )
            }
    )
    @Parameters(value = {
            @Parameter(name = "chatId", example = "1234567890")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Adopter removed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The adopter was not removed"
            )
    }
    )
    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> delete(@PathVariable Long chatId) {
        if (adopterCatService.remove(chatId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Changing the details of the adopter",
            description = "Updating the adopter data from the request body"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Adopter details updated",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdopterCat.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Adopter data not updated",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdopterCat.class))
                            )
                    }
            )
    }
    )
    @PutMapping
    public ResponseEntity<AdopterCat> update(@RequestBody AdopterCat adopterCat) {
        adopterCatService.update(adopterCat);
        return ResponseEntity.ok(adopterCat);
    }

    @Operation(summary = "View all adopters",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Adopters found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdopterCat.class)
                            )
                    )
            }
    )
    @GetMapping("/all")
    public Collection<AdopterCat> getAll() {
        return this.adopterCatService.getAll();
    }

    @GetMapping("/send_message")
    @Operation(summary = "Sending a message to a user",
            description = "Sends a message to a shelter user through a bot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message sent"),
            @ApiResponse(responseCode = "404", description = "User with this chatId not found"),
    })
    public ResponseEntity<Void> sendMessage(Long chatId, String textToSend) {
        if (adopterCatService.get(chatId) != null) {
            Bot.sendToTelegram(chatId, textToSend);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

