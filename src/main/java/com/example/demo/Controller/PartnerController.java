package com.example.demo.Controller;
import com.example.demo.DTO.Employee;
import com.example.demo.DTO.Partner;
import com.example.demo.Service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerService partnerService;

    @Autowired
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping("/test")
    public ResponseEntity<List<Partner>> getAllPartners() {
        try {
            List<Partner> partners = partnerService.findAllPartners();
            if (partners.isEmpty()) {
                return ResponseEntity.noContent().build(); // 返回204 No Content状态
            }
            return ResponseEntity.ok(partners);
        } catch (Exception e) {
            // 日志记录异常信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 返回500 Internal Server Error状态
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Partner> addPartner(@RequestBody Partner partner) {
        try {
            Partner savedPartner = partnerService.addPartner(partner);
            return new ResponseEntity<>(savedPartner, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // 输出错误信息到控制台
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<Partner>> deletePartner(@RequestBody List<Long> partnerIds){
        try {
            for (Long partnerId : partnerIds) {
                partnerService.deletePartnerById(partnerId);
            }
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            // 日志记录异常信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    // 其他端点...
}
