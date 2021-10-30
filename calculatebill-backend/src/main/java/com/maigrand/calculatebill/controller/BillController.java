package com.maigrand.calculatebill.controller;

import com.maigrand.calculatebill.entity.BillEntity;
import com.maigrand.calculatebill.payload.*;
import com.maigrand.calculatebill.service.BillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bill")
@Api(tags = "Чек")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @GetMapping
    @ApiOperation(value = "Получить все чеки")
    public ResponseEntity<List<BillEntity>> list() {
        List<BillEntity> entity = this.billService.findAll();
        return ResponseEntity.ok(entity);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получить чек по id")
    public ResponseEntity<BillEntity> show(@PathVariable("id") String id) {
        BillEntity entity = this.billService.findById(id);
        return ResponseEntity.ok(entity);
    }

    @PostMapping
    @ApiOperation(value = "Создать чек")
    //todo: status created
    public ResponseEntity<BillEntity> create(@RequestBody BillDetails details) {
        BillEntity entity = this.billService.create(details);
        return ResponseEntity.ok(entity);
    }

    @PostMapping("/{id}/member")
    @ApiOperation(value = "Добавить участника")
    public ResponseEntity<BillEntity> addMember(@PathVariable("id") String id, @RequestBody MemberDetails details) {
        BillEntity entity = this.billService.addMember(id, details);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}/member/{memberId}")
    @ApiOperation(value = "Удалить участника")
    public ResponseEntity<BillEntity> removeMember(
            @PathVariable("id") String id,
            @PathVariable("memberId") String memberId) {
        BillEntity entity = this.billService.removeMember(id, memberId);
        return ResponseEntity.ok(entity);
    }

    @PostMapping("/{id}/member/{memberId}")
    @ApiOperation(value = "Добавить участнику позицию")
    public ResponseEntity<BillEntity> addMemberPosition(
            @PathVariable("id") String id,
            @PathVariable("memberId") String memberId,
            @RequestBody PositionDetails details) {
        BillEntity entity = this.billService.addMemberPosition(id, memberId, details);
        return ResponseEntity.ok(entity);
    }

    @GetMapping("/{id}/calculate")
    @ApiOperation(value = "Подсчитать")
    public ResponseEntity<BillEntity> calculate(@PathVariable("id") String id) {
        BillEntity entity = this.billService.calculate(id);
        return ResponseEntity.ok(entity);
    }
}
