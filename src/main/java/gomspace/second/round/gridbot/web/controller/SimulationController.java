package gomspace.second.round.gridbot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gomspace.second.round.gridbot.service.SimulationService;
import io.swagger.annotations.Api;

@Api(value = "Simulation")
@RestController
@RequestMapping(value = "/api/simulation", produces = "application/json")
public class SimulationController {
	
	@Autowired
    private SimulationService simulationService; 

    
	
	
	
}
