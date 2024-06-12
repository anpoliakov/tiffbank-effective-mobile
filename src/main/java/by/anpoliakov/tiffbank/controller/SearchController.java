package by.anpoliakov.tiffbank.controller;

import by.anpoliakov.tiffbank.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/search")
@RequiredArgsConstructor
@Tag(name = "Search Controller", description = "Provides endpoints for user search based on various input data")
public class SearchController {
    private final SearchService searchService;

    @GetMapping("birthday")
    @Operation(
            summary = "Search users by birthdate",
            description = "Search for users whose date of birth is greater than the searched one. The endpoint supports pagination"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> findByBirthday(@RequestParam("param") @NotEmpty String birthday,

                                            @RequestParam(value = "page", required = false, defaultValue = "0")
                                                @Min(0) @Parameter(description = "Page number") Integer page,

                                            @RequestParam(value = "usersPerPage", required = false, defaultValue = "5")
                                                @Parameter(description = "Number of users per page")
                                                @Min(0) @Max(50) Integer usersPerPage) {

        if (page != null && usersPerPage != null) {
            return new ResponseEntity<>(searchService.findByBirthdayWithPagination(birthday, page, usersPerPage), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(searchService.findByBirthday(birthday), HttpStatus.OK);
        }
    }

    @GetMapping("phoneNumber")
    @Operation(
            summary = "Search user by phone number",
            description = "Search for a user by 100% similarity of phone number. Enter the number without the “+” sign."
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> findByPhoneNumber(@RequestParam("param") @NotEmpty String phoneNumber) {
        return new ResponseEntity<>(searchService.findByPhoneNumber(phoneNumber.trim()), HttpStatus.OK);
    }

    @GetMapping("fullName")
    @Operation(
            summary = "Search user by full name",
            description = "Search by string in LIKE format"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> findByFullName(@RequestParam("param") @NotEmpty String fullName) {
        return new ResponseEntity<>(searchService.findByFullName(fullName.trim()), HttpStatus.OK);
    }

    @GetMapping("email")
    @Operation(
            summary = "Search user by email address",
            description = "Search for a user by 100% similarity of email address."
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> findByEmail(@RequestParam("param") @NotEmpty String email) {
        return new ResponseEntity<>(searchService.findByEmail(email.trim()), HttpStatus.OK);
    }
}
