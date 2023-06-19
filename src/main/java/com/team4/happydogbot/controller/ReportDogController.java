package com.team4.happydogbot.controller;

import com.team4.happydogbot.entity.ReportDog;
import com.team4.happydogbot.service.ReportDogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Controller class for a Dog object containing a set of API endpoints
 * for accessing routes with separate HTTP methods
 *
 * @see ReportDog
 * @see ReportDogService
 * @see ReportDogController
 */
@RestController
@RequestMapping("/report_dog")
@Tag(name = "Reports", description = "CRUD operations and other endpoints for working with reports")
public class ReportDogController {

    private final ReportDogService reportDogService;

    public ReportDogController(ReportDogService reportDogService) {
        this.reportDogService = reportDogService;
    }

    @Operation(
            summary = "Adding a report",
            description = "Adding a new report from the request body with an id assigned from the generator"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The report has been added",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReportDog.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Incorrect report parameters",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReportDog.class))
                            )
                    }
            )
    }
    )
    @PostMapping
    public ResponseEntity<ReportDog> add(@RequestBody ReportDog reportDog) {
        reportDogService.add(reportDog);
        return ResponseEntity.ok(reportDog);
    }

    @Operation(summary = "Getting a report by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Report found by id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDog.class)
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
                    description = "The report was found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReportDog.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The report not found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReportDog.class))
                            )
                    }
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ReportDog> get(@PathVariable Long id) {
        ReportDog reportDog = reportDogService.get(id);
        if (reportDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportDog);
    }

    @Operation(summary = "Getting a photo report by report id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Photo by report id"
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
                    description = "The photo for the report was found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The photo for the report was not found"
            )
    }
    )
    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getPhoto(@Parameter(description = "report id") @PathVariable Long id) {
        ReportDog reportDog = reportDogService.get(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(reportDog.getFileId().length());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"ReportPhoto.jpg\"")
                .body(reportDogService.getFile(id));
    }

    @Operation(summary = "Deleting a report by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Report found by id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDog.class)
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
                    description = "Report deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The report has not been deleted"
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (reportDogService.remove(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Editing a report",
            description = "Update report from request body"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Report data updated",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReportDog.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Report data not updated",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReportDog.class))
                            )
                    }
            )
    }
    )
    @PutMapping
    public ResponseEntity<ReportDog> update(@RequestBody ReportDog reportDog) {
        reportDogService.update(reportDog);
        return ResponseEntity.ok(reportDog);
    }

    @Operation(summary = "View all reports",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reports found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDog.class)
                            )
                    )
            }
    )
    @GetMapping("/all")
    public Collection<ReportDog> getAll() {
        return this.reportDogService.getAll();
    }
}
