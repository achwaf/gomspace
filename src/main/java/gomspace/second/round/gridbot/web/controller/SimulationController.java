package gomspace.second.round.gridbot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gomspace.second.round.gridbot.service.SimulationService;
import gomspace.second.round.gridbot.utils.Snapshot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Simulation")
@RestController
@RequestMapping(value = "/api/simulation", produces = "application/json")
public class SimulationController {
	
	@Autowired
    private SimulationService simulationService; 
	
	
	@GetMapping(value = "/reset")
	@ApiOperation(value = "resetSimulation", notes = "reset the simulation", nickname = "reset")
	public void resetSimulation() {
		simulationService.initialize();
	}
	
	@PutMapping(value = "/after/number/of/iterations")
	@ApiOperation(value = "runSimulation", notes = "returns the grid after end of simulation", nickname = "simulation after n iterations")
	public Snapshot runSimulation(@RequestBody final int number) {
		return simulationService.SimulationAfter(number);
	}
	
	@GetMapping(value = "/nextFrame")
	@ApiOperation(value = "nextFrame", notes = "continue simulation and get the next frame", nickname = "nextFrame")
	public Snapshot nextFrame() {
		return simulationService.nextFrame();
	}
	
	
	@GetMapping(value = "/nextFrameByStep")
	@ApiOperation(value = "nextStep", notes = "continue simulation and get the next frame one step at a time", nickname = "nextStep")
	public Snapshot nextStep() {
		return simulationService.nextFrameByStep();
	}


    
	
	
	
}
