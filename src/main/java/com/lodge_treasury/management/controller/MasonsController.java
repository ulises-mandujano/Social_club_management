package com.lodge_treasury.management.controller;

import com.lodge_treasury.management.constants.LodgeConstants;
import com.lodge_treasury.management.dto.AdminMemberDto;
import com.lodge_treasury.management.dto.ApiCustomResponse;
import com.lodge_treasury.management.dto.MasonCreateDto;
import com.lodge_treasury.management.dto.MemberDto;
import com.lodge_treasury.management.entity.Mason;
import com.lodge_treasury.management.mapper.MasonMapper;
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
    public ResponseEntity<ApiCustomResponse<Integer>> registerNewMason(@Valid @RequestBody MasonCreateDto masonCreateDto) {
        Integer id = imasonService.registerNewMember(masonCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("api/masons/"+id))
                .body(ApiCustomResponse.success(LodgeConstants.MESSAGE_201, id));
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
    public ResponseEntity<ApiCustomResponse<List<MemberDto>>> fetchMembers() {
        List<MemberDto> memberDtoList = imasonService.findAllMasons();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiCustomResponse.success("Masons fetched successfully", memberDtoList));
    }

    @Operation(
            summary = "Fetch Mason by ID REST API",
            description = "REST API to fetch a Mason by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Mason found successfully"
    )
    @GetMapping("/members/{id}")
    public ResponseEntity<ApiCustomResponse<MemberDto>> getMason(@PathVariable Integer id) {
        Mason mason = imasonService.getMasonById(id);
        MemberDto dto = MasonMapper.mapMasonToMemberDto(mason, "AM");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiCustomResponse.success("Mason found successfully", dto));
    }

    @Operation(
            summary = "Delete Mason (soft) REST API",
            description = "Soft-delete a Mason by setting the deleted flag to true"
    )
    @ApiResponse(responseCode = "204", description = "Mason deleted successfully")
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMason (@PathVariable Integer id, @RequestParam(required = true) String reason) {
        imasonService.deleteMason(id, reason);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Restore Mason REST API",
            description = "Restore a previously soft-deleted Mason"
    )
    @ApiResponse(responseCode = "200", description = "Mason restored successfully")
    @PatchMapping("/members/{id}/restore")
    public ResponseEntity<ApiCustomResponse<MemberDto>> restoreMason (@PathVariable Integer id) {
        imasonService.restoreMason(id);
        Mason restored = imasonService.getMasonById(id);
        MemberDto dto = MasonMapper.mapMasonToMemberDto(restored, "AM");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiCustomResponse.success("Mason restored successfully", dto));
    }

    @Operation(
            summary = "Fetch all Masons (including deleted) REST API",
            description = "REST API to fetch all Masons including soft-deleted ones (admin use)"
    )
    @ApiResponse(responseCode = "200", description = "All masons fetched successfully")
    @GetMapping("/members/all")
    public ResponseEntity<ApiCustomResponse<List<AdminMemberDto>>> fetchAllMembersIncludingDeleted() {
        List<AdminMemberDto> memberDtoList = imasonService.findAllMasonsIncludingDeleted();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiCustomResponse.success("All masons fetched (including deleted)", memberDtoList));
    }

    @Operation(
            summary = "Hard Delete Mason REST API",
            description = "Permanently delete a Mason and related contact information (admin use only)"
    )
    @ApiResponse(responseCode = "204", description = "Mason permanently deleted")
    @DeleteMapping("/members/{id}/hard")
    public ResponseEntity<Void> hardDeleteMason(@PathVariable Integer id) {
        imasonService.hardDeleteMason(id);
        return ResponseEntity.noContent().build();
    }
}
