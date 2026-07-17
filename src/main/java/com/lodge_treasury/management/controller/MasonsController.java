package com.lodge_treasury.management.controller;

import com.lodge_treasury.management.constants.LodgeConstants;
import com.lodge_treasury.management.dto.MasonCreateDto;
import com.lodge_treasury.management.dto.MemberDto;
import com.lodge_treasury.management.dto.ResponseDto;
import com.lodge_treasury.management.service.IMasonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(
        name = "CRUD REST APIs for Masons",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH and DELETE mason details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class MasonsController {

    private IMasonService  imasonService;

    @Operation(
            summary = "Register new Mason REST API",
            description = "REST API to register new Mason and Contact information"
    )
    @ApiResponse(
            responseCode = "201",
            description = "REST API to register a new Mason and Contact information"
    )
    @PostMapping("/registerNewMember")
    public ResponseEntity<ResponseDto> registerNewMason(@Valid @RequestBody MasonCreateDto masonCreateDto) {
        Integer id = imasonService.registerNewMember(masonCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("api/masons/" + id))
                .body(new ResponseDto(LodgeConstants.STATUS_201, LodgeConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Masons REST API",
            description = "REST API to fetch all Masons at the Lodge"
    )
    @ApiResponse(
            responseCode = "200",
            description = "REST API to fetch all Masons at the Lodge"
    )
    @GetMapping("/members")
    public ResponseEntity<List<MemberDto>> fetchMembers() {
        List<MemberDto> memberDtoList = imasonService.findAllMasons();
        return ResponseEntity.status(HttpStatus.OK).body(memberDtoList);
    }
}
