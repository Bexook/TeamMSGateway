package com.team.ms.teammsgateway.resource;

import com.tms.common.domain.dto.ApplicationDataDTO;
import com.tms.common.domain.dto.ApplicationDetailsDTO;
import com.tms.common.domain.enumTypes.ApplicationState;
import com.tms.common.security.service.AppRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class ApplicationManagementResource {

    @Autowired
    private AppRegistryService appRegistryService;

    @PostMapping("/application/new")
    public void createdNewApplication(@RequestBody final ApplicationDataDTO newApp) {
        appRegistryService.registerApplication(newApp.getAppRoute(), newApp.getAppName());
    }

    @PostMapping("/application/deactivate/{application-key}")
    public void deactivateApplication(@PathVariable(value = "application-key") final String appKey){
        appRegistryService.deactivateApplication(appKey);
    }

    @GetMapping("/application/list/{application-state}")
    public ResponseEntity<List<ApplicationDetailsDTO>> getAll(@PathVariable("application-state") final ApplicationState applicationState){
        return ResponseEntity.ok().body(appRegistryService.allApplicationFilteredByActivity(applicationState));
    }

}
